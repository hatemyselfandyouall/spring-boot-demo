package com.wangxinenpu.springbootdemo.controller;

import com.wangxinenpu.springbootdemo.config.ExceptionWriteCompoent;
import com.wangxinenpu.springbootdemo.dao.mapper.LinkTransferTaskTotalMapper;
import com.wangxinenpu.springbootdemo.dataobject.po.linkTask.LinkTransferTaskRule;
import com.wangxinenpu.springbootdemo.dataobject.po.linkTask.LinkTransferTaskTotal;
import com.wangxinenpu.springbootdemo.dataobject.vo.LinkTransferTask.LinkTransferTaskCDDVO;
import com.wangxinenpu.springbootdemo.util.DateUtils;
import com.wangxinenpu.springbootdemo.util.dataSource.sqlParse.CDCCache.MSGTYPECONSTANT;
import com.wangxinenpu.springbootdemo.util.dataSource.sqlParse.CDCCache.SQLSaver;
import com.wangxinenpu.springbootdemo.util.dataSource.sqlParse.CDCCache.TableStatusCache;
import com.wangxinenpu.springbootdemo.util.dataSource.sqlParse.InsertSQLParseDTO;
import com.wangxinenpu.springbootdemo.util.dataSource.sqlParse.SQLParseDTO;
import com.wangxinenpu.springbootdemo.util.dataSource.sqlParse.SqlParseUtil;
import com.wangxinenpu.springbootdemo.util.dataSource.sqlParse.UpdateSQLParseDTO;
import com.wangxinenpu.springbootdemo.util.datatransfer.CDCUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.sql.*;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Slf4j
public class CDCTask implements Runnable{

    private final ExceptionWriteCompoent exceptionWriteCompoent;
    private Long totalStartTime;

    private Long totalStartScn;
    private List<LinkTransferTaskCDDVO> linkTransferTasks;

    private DefaultMQProducer defaultMQProducer;

    private String fromLinkUrl;
    private String cdcfromusername;
    private String cdcfrompassword;

    private static CDCTask instance;

    private boolean working=true;

    public String recordSql = "";
    public Long recordSCN = null;
    public Long totalCount=0l;
    public Long redisTotalCount=0l;
    private RedisTemplate redisTemplate;
    private LinkTransferTaskTotal linkTransferTaskTotal;
    private LinkTransferTaskTotalMapper linkTransferTaskTotalMapper;
    private SQLSaver sqlSaver;
    private Map<String,String>needAppendMap=new HashMap<>();


    public static CDCTask getInstance(Long totalStartTime, List<LinkTransferTaskCDDVO> linkTransferTasks, DefaultMQProducer defaultMQProducer, ExceptionWriteCompoent exceptionWriteCompoent, String fromLinkUrl,
                                      String cdcfromusername, String cdcfrompassword, Long totalStartSCN, LinkTransferTaskTotal linkTransferTaskTotal,
    LinkTransferTaskTotalMapper linkTransferTaskTotalMapper,RedisTemplate redisTemplate,SQLSaver sqlSaver) {
        if (instance == null) {
            synchronized (CDCTask.class) {
                if (instance == null) {
                    instance = new CDCTask(totalStartTime,linkTransferTasks,defaultMQProducer,exceptionWriteCompoent,fromLinkUrl,cdcfromusername,cdcfrompassword,totalStartSCN,linkTransferTaskTotal,linkTransferTaskTotalMapper,redisTemplate,sqlSaver);
                }else {
                    instance.setWorking(false);
                    instance = new CDCTask(totalStartTime,linkTransferTasks,defaultMQProducer,exceptionWriteCompoent,fromLinkUrl,cdcfromusername,cdcfrompassword,totalStartSCN,linkTransferTaskTotal,linkTransferTaskTotalMapper,redisTemplate,sqlSaver);
                }
            }

        }
        return instance;
    }

    private CDCTask(Long totalStartTime, List<LinkTransferTaskCDDVO> linkTransferTasks, DefaultMQProducer defaultMQProducer, ExceptionWriteCompoent exceptionWriteCompoent,
                    String fromLinkUrl, String cdcfromusername,
                    String cdcfrompassword, Long totalStartSCN,
                    LinkTransferTaskTotal linkTransferTaskTotal, LinkTransferTaskTotalMapper linkTransferTaskTotalMapper,
                    RedisTemplate redisTemplate,SQLSaver sqlSaver) {
        this.totalStartTime = totalStartTime;
        this.linkTransferTasks = linkTransferTasks;
        this.defaultMQProducer = defaultMQProducer;
        this.exceptionWriteCompoent=exceptionWriteCompoent;
        this.fromLinkUrl=fromLinkUrl;
        this.cdcfromusername=cdcfromusername;
        this.cdcfrompassword=cdcfrompassword;
        this.totalStartScn=totalStartSCN;
        this.redisTemplate=redisTemplate;
        this.sqlSaver=sqlSaver;
    }


    @Override
    public void run() {
        Connection targetConnection=null;
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            String startString = DateUtils.parseLongtoDate(totalStartTime, "yyyy-MM-dd HH:mm:ss");
            String connectUrl = fromLinkUrl;
            String targetUserName = cdcfromusername;
            String targetPassword = cdcfrompassword;
            targetConnection = DriverManager.getConnection(connectUrl, targetUserName, targetPassword);
            List<String> currentFiles = CDCUtil.getCurrentFiles(targetConnection);
            List<String> archivedFiles = CDCUtil.getArchivedFiles(targetConnection, startString, "");
            if (CollectionUtils.isEmpty(archivedFiles)) {
                archivedFiles=new ArrayList<>();
            }
            archivedFiles.addAll(currentFiles);
            CDCUtil.startLogMnrWithArchivedFiles(targetConnection, archivedFiles,totalStartScn,startString);
            Set<String> segNames = null;
            Set<String> tableNames = null;
            List<LinkTransferTaskRule> linkTransferTaskRules = new ArrayList<>();
            for (LinkTransferTaskCDDVO linkTransferTaskCDDVO : linkTransferTasks) {
                segNames = linkTransferTasks.stream().map(i -> i.getSegName()).distinct().collect(Collectors.toSet());
                tableNames = linkTransferTasks.stream().map(i -> i.getTargetTablesString()).distinct().collect(Collectors.toSet());
                if (linkTransferTaskCDDVO.getLinkTransferTaskRule() != null) {
                    linkTransferTaskRules.addAll(linkTransferTaskCDDVO.getLinkTransferTaskRule());
                }
            }
            String segString = "(";
            for (String temp : segNames) {
                segString += "'" + temp + "',";
            }
            segString = segString.substring(0, segString.length() - 1);
            segString += ")";
            String tableString = "(";
            for (String temp : tableNames) {
                tableString += "'" + temp + "',";
            }
            tableString = tableString.substring(0, tableString.length() - 1);
            tableString += ")";
            while (working) {
                if (totalStartScn!=null) {
                    currentFiles = CDCUtil.getCurrentFiles(targetConnection);
                    archivedFiles = CDCUtil.getArchivedFiles(targetConnection, startString, "");
                    if (CollectionUtils.isEmpty(archivedFiles)) {
                        archivedFiles=new ArrayList<>();
                    }
                    archivedFiles.addAll(currentFiles);
                    CDCUtil.startLogMnrWithArchivedFiles(targetConnection, archivedFiles,totalStartScn,startString);
                }
                String queryString="SELECT * FROM v$logmnr_contents where  " + "  (operation IN ('INSERT','UPDATE','DELETE','DDL')) and seg_owner in" + segString + "and table_name in " + tableString;
                Statement statement = targetConnection.createStatement();
                statement.setFetchSize(1000);
                log.info("进行logminer解析");
                ResultSet resultSet = statement.executeQuery(queryString);
                log.info("解析结束，获得结果集");
                try {
                        while (resultSet.next() && working) {
                            Long start=System.currentTimeMillis();
                            totalCount++;
                            String redoSQL = resultSet.getString("sql_redo");
                        if (redoSQL.lastIndexOf(";") == redoSQL.length() - 1) {
                            redoSQL = redoSQL.substring(0, redoSQL.length() - 1);
                        }
                        String tableName = resultSet.getString("table_name");
                        String opeartion = resultSet.getString("operation");
                        String seg_owner = resultSet.getString("seg_owner");
                        String timeStamp = resultSet.getString("timestamp");
                            String CSF = resultSet.getString("CSF");
                            String rowFlag=resultSet.getString("RS_ID")+"|"+resultSet.getString("SSN");
                            if ("1".equals(CSF)&&needAppendMap.get(rowFlag)==null){
                                if (needAppendMap.get(rowFlag)==null){
                                    needAppendMap.put(rowFlag,redoSQL);
                                    continue;
                                }
                            }
                            if (needAppendMap.get(rowFlag)!=null){
                                redoSQL=needAppendMap.get(rowFlag)+redoSQL;
                            }
                            Long scn = resultSet.getLong("scn");
                            recordSql = redoSQL;
                            recordSCN = scn;
//                            System.out.println(System.currentTimeMillis()-start+"2");
                            if (ColumnFilter(tableName, opeartion, redoSQL, resultSet.getString("sql_undo"), linkTransferTaskRules, seg_owner)) {
//                                System.out.println(System.currentTimeMillis()-start+"3");
                                String MapTableName = seg_owner + "|" + tableName;
                            String tableStatus = TableStatusCache.getStatus(MapTableName);
                            //如果还没开始全量，这个表的数据不管
                            if (org.apache.commons.lang3.StringUtils.isEmpty(tableStatus) || tableStatus.equals(MSGTYPECONSTANT.TABLE_STATUS_NOT_INITED_YET)) {

                            } else {
                                String sql = redoSQL;
                                totalStartScn = scn;
                                startString=timeStamp;
                                redisTotalCount++;
                                sqlSaver.save(seg_owner,tableName, sql, tableStatus, scn,timeStamp);
                            }
                            }
                        }
                    log.info("结果集分析结束"+totalCount);
                        CDCUtil.endLogMnr(targetConnection);
                } catch (Throwable e) {
                        log.info("", e);
                        exceptionWriteCompoent.wirte(recordSql, e, recordSCN);
                    }finally {

                    }
            }
            }catch(Exception e){
                log.error("", e);
            }finally {
            try {
                if (targetConnection!=null&&!targetConnection.isClosed()){
                    targetConnection.close();
                }
            }catch (Exception e){
                log.error("关闭连接错误",e);
            }

        }

    }

    private void saveNowStatus(Long scn, String timeStamp) {
        try {
            Executors.newSingleThreadExecutor().submit(()->{
                linkTransferTaskTotal.setNowParseScn(scn).setNowParseTime(timeStamp);
                linkTransferTaskTotalMapper.updateByPrimaryKey(linkTransferTaskTotal);
            });
        }catch (Exception e){
            //do no thing
        }

    }

    public static boolean ColumnFilter(String tableName, String opeartion, String redoSQL, String sqlUndo, List<LinkTransferTaskRule> linkTransferTaskRuleList,String segName)throws Exception {
        if (CollectionUtils.isEmpty(linkTransferTaskRuleList))return true;
        if (StringUtils.isEmpty(tableName)||StringUtils.isEmpty(opeartion)||StringUtils.isEmpty(segName)){
            log.info("不应为空的参数记录,并返回成功"+tableName+opeartion+redoSQL+sqlUndo);
            return true;
        }
        if (StringUtils.isEmpty(redoSQL)||StringUtils.isEmpty(sqlUndo)){
            log.info("不应为空的参数记录"+tableName+opeartion+redoSQL+sqlUndo);
        }
        Map<String,List<LinkTransferTaskRule>> linkTransferTaskRuleMap=linkTransferTaskRuleList.stream().collect(Collectors.groupingBy(i->i.getSegName()+i.getTargetTablesString()));
        //如果对该表没设规则，那就全部返回true
        if (CollectionUtils.isEmpty(linkTransferTaskRuleMap.get(segName+tableName))){
            return true;
        }
        List<LinkTransferTaskRule> linkTransferTaskRules=linkTransferTaskRuleMap.get(segName+tableName);
        try {
            Map<String,List<LinkTransferTaskRule>> listMap=linkTransferTaskRules.stream().collect(Collectors.groupingBy(i->i.getColumnName().toUpperCase()));
            opeartion=opeartion.toLowerCase();
            List<SQLParseDTO> sqlParseDTOS=parseSQL(opeartion,redoSQL,sqlUndo);
            if (!CollectionUtils.isEmpty(sqlParseDTOS)){
                for (SQLParseDTO sqlParseDTO:sqlParseDTOS){
                    if (!checkSQLRule(sqlParseDTO,listMap)){
                        return false;
                    }
                }
            }
            return true;
        }catch (Exception e){
            return true;
        }

    }

    private static List<SQLParseDTO> parseSQL(String opeartion,String redoSQL,String sqlUndo) throws Exception{
        List<SQLParseDTO>sqlDetailDTOS=new ArrayList<>();
        switch (opeartion) {
            case "insert":
                InsertSQLParseDTO insertSQLParseDTO = SqlParseUtil.test_insert(redoSQL);
                sqlDetailDTOS.add(insertSQLParseDTO);
                break;
            case "update":
                UpdateSQLParseDTO updateSQLParseDTO = SqlParseUtil.test_update(redoSQL);
                UpdateSQLParseDTO updoParse = SqlParseUtil.test_update(sqlUndo);
                sqlDetailDTOS.add(updateSQLParseDTO);
                sqlDetailDTOS.add(updoParse);
                break;
            case "delete":
                updateSQLParseDTO = SqlParseUtil.test_update(redoSQL);
                insertSQLParseDTO = SqlParseUtil.test_insert(sqlUndo);
                sqlDetailDTOS.add(insertSQLParseDTO);
                sqlDetailDTOS.add(updateSQLParseDTO);
        }
        return sqlDetailDTOS;
    }
    private static boolean checkSQLRule(SQLParseDTO insertSQLParseDTO, Map<String,List<LinkTransferTaskRule>> listMap){
        for (int i=0;i<insertSQLParseDTO.getColumns().size();i++){
            String column=insertSQLParseDTO.getColumns().get(i).toUpperCase();
            String value=insertSQLParseDTO.getValues().get(i);
            if (listMap.get(column)!=null){
                List<LinkTransferTaskRule> linkTransferTaskRules=listMap.get(column);
                for (LinkTransferTaskRule linkTransferTaskRule:linkTransferTaskRules){
                    switch (linkTransferTaskRule.getColumnRuleType()){
                        case EQUALS:
                            if (!linkTransferTaskRule.getColumnValue().equals(value)){
                                return false;
                            }else {
                                return true;
                            }
                        case LIKE:
                            if (!linkTransferTaskRule.getColumnValue().contains(value)){
                                return false;
                            }else {
                                return true;
                            }
                    }
                }
            }
        }
        return true;
    }
    public static   String getPK(String tableName,Connection connection) {
        String PKName = null;
        try {
            DatabaseMetaData dmd = connection.getMetaData();
            ResultSet rs = dmd.getPrimaryKeys(null, "%", tableName);
            if (rs.next()) {
                PKName = rs.getString("column_name");
                rs.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return PKName;
    }

    public void setWorking(boolean working) {
        this.working = working;
    }
}
