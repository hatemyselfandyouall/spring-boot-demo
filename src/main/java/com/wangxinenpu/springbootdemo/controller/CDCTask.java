package com.wangxinenpu.springbootdemo.controller;

import com.alibaba.druid.pool.DruidDataSource;
import com.wangxinenpu.springbootdemo.config.ExceptionWriteCompoent;
import com.wangxinenpu.springbootdemo.dao.mapper.LinkTransferTaskTotalMapper;
import com.wangxinenpu.springbootdemo.dataobject.po.linkTask.ColumnRuleTypeEnum;
import com.wangxinenpu.springbootdemo.dataobject.po.linkTask.LinkTransferTaskRule;
import com.wangxinenpu.springbootdemo.dataobject.po.linkTask.LinkTransferTaskTotal;
import com.wangxinenpu.springbootdemo.dataobject.vo.LinkTransferTask.LinkTransferTaskCDDVO;
import com.wangxinenpu.springbootdemo.util.DateUtils;
import com.wangxinenpu.springbootdemo.util.dataSource.sqlParse.*;
import com.wangxinenpu.springbootdemo.util.dataSource.sqlParse.CDCCache.MSGTYPECONSTANT;
import com.wangxinenpu.springbootdemo.util.dataSource.sqlParse.CDCCache.SQLSaver;
import com.wangxinenpu.springbootdemo.util.dataSource.sqlParse.CDCCache.TableStatusCache;
import com.wangxinenpu.springbootdemo.util.datatransfer.CDCUtil;
import com.wangxinenpu.springbootdemo.util.datatransfer.LogFile;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Slf4j
public class CDCTask implements Runnable{

    public final ExceptionWriteCompoent exceptionWriteCompoent;
    public Long totalStartTime;

    public Long totalStartScn;
    public List<LinkTransferTaskCDDVO> linkTransferTasks;

    private DefaultMQProducer defaultMQProducer;

    private String fromLinkUrl;
    private String cdcfromusername;
    private String cdcfrompassword;

    private static CDCTask instance;
    public ExecutorService executorService;

    public boolean working=true;

    public String recordSql = "";
    public Long recordSCN = null;
    public Long totalCount=0l;
    public Long redisTotalCount=0l;
    private RedisTemplate redisTemplate;
    private LinkTransferTaskTotal linkTransferTaskTotal;
    private LinkTransferTaskTotalMapper linkTransferTaskTotalMapper;
    public SQLSaver sqlSaver;
    public static Map<String,String>needAppendMap=new HashMap<>();
    private DruidDataSource dataSource;


    public static CDCTask getInstance(Long totalStartTime, List<LinkTransferTaskCDDVO> linkTransferTasks, DefaultMQProducer defaultMQProducer, ExceptionWriteCompoent exceptionWriteCompoent, String fromLinkUrl,
                                      String cdcfromusername, String cdcfrompassword, Long totalStartSCN, LinkTransferTaskTotal linkTransferTaskTotal,
                                      LinkTransferTaskTotalMapper linkTransferTaskTotalMapper, RedisTemplate redisTemplate, SQLSaver sqlSaver, DruidDataSource dataSource) {
            synchronized (CDCTask.class) {
                if (instance == null) {
                    instance = new CDCTask(totalStartTime,linkTransferTasks,defaultMQProducer,exceptionWriteCompoent,fromLinkUrl,cdcfromusername,cdcfrompassword,totalStartSCN,linkTransferTaskTotal,linkTransferTaskTotalMapper,redisTemplate,sqlSaver,dataSource);
                }else {
                    instance.setWorking(false);
                    instance = new CDCTask(totalStartTime,linkTransferTasks,defaultMQProducer,exceptionWriteCompoent,fromLinkUrl,cdcfromusername,cdcfrompassword,totalStartSCN,linkTransferTaskTotal,linkTransferTaskTotalMapper,redisTemplate,sqlSaver,dataSource);
                }
            }
        return instance;
    }

    private CDCTask(Long totalStartTime, List<LinkTransferTaskCDDVO> linkTransferTasks, DefaultMQProducer defaultMQProducer, ExceptionWriteCompoent exceptionWriteCompoent,
                    String fromLinkUrl, String cdcfromusername,
                    String cdcfrompassword, Long totalStartSCN,
                    LinkTransferTaskTotal linkTransferTaskTotal, LinkTransferTaskTotalMapper linkTransferTaskTotalMapper,
                    RedisTemplate redisTemplate,SQLSaver sqlSaver,DruidDataSource dataSource) {
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
        this.dataSource=dataSource;
        this.executorService=Executors.newFixedThreadPool(9);
    }


    @Override
    public void run() {
        Connection targetConnection=null;
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            String startString = totalStartTime!=null?DateUtils.parseLongtoDate(totalStartTime, "yyyy-MM-dd HH:mm:ss"):"";
            String connectUrl = fromLinkUrl;
            String targetUserName = cdcfromusername;
            String targetPassword = cdcfrompassword;
            targetConnection = dataSource.getConnection();
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
            Integer batchCount=5000000;
            while (working) {
                Statement statement=null;
                try {
                        List<LogFile> currentFiles = CDCUtil.getCurrentFiles(targetConnection);
                        List<LogFile> archivedFiles = CDCUtil.getArchivedFiles(targetConnection, startString, "",totalStartScn);
                        if (CollectionUtils.isEmpty(archivedFiles)) {
                            archivedFiles=new ArrayList<>();
                        }
                        archivedFiles.addAll(currentFiles);
                    String queryString="SELECT sql_redo,table_name,operation,seg_owner,timestamp,CSF,RS_ID,sql_undo,scn,SSN FROM v$logmnr_contents where  " + "  (operation IN ('INSERT','UPDATE','DELETE','DDL'))" +
                            " and seg_owner in" + segString + "and table_name in " + tableString;
                    List<Future<LogminerFileTaskResult>> futures=new ArrayList<>();
                    for (LogFile file:archivedFiles){
                        log.info("启动分析子任务");
                        Future<LogminerFileTaskResult> future=executorService.submit(new LogminerFileTask(file,targetConnection,fromLinkUrl,targetUserName,targetPassword,
                                startString ,queryString,this,linkTransferTaskRules,batchCount,totalStartScn,dataSource));
                        futures.add(future);
                    }
                    Long scnFlag=totalStartScn;
                    Long lowerSCN=0l;
                    for (int i=0;i<futures.size();i++){
                        Future<LogminerFileTaskResult> future=futures.get(i);
                        LogminerFileTaskResult logminerFileTaskResult=null;
                        try {
                            logminerFileTaskResult= future.get(10, TimeUnit.MINUTES);
                        }catch (TimeoutException e){
                            log.info("获取超时，抛弃这部分数据!",e);
                            linkTransferTaskRules=null;
                        }
                        log.info("接收到返回"+logminerFileTaskResult);
                            if (logminerFileTaskResult!=null&&logminerFileTaskResult.getLastScn() != null ) {
                                if (i==0){
                                    lowerSCN=logminerFileTaskResult.getLastScn();
                                }else {
                                    if (lowerSCN<logminerFileTaskResult.getLastScn()){
                                        lowerSCN=logminerFileTaskResult.getLastScn();
                                    }
                                }
                            }
                    }
                    if ((totalStartScn==null||lowerSCN>totalStartScn)&&lowerSCN!=0){
                        totalStartScn = lowerSCN+1;
                    }
                    if (scnFlag!=null&&scnFlag.equals(totalStartScn)){
                        batchCount+=5000000;
                    }else {
                        batchCount=5000000;
                    }
                }catch (Exception e){
                    log.error("logminer连接时异常",e);
                    if (statement!=null&&!statement.isClosed()){
                        statement.close();
                    }
                    if (targetConnection!=null&&!targetConnection.isClosed()){
                        targetConnection.close();
                    }

                    targetConnection= dataSource.getConnection();
                    continue;
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

    private Connection getConnectionWithExceptionCatche(String connectUrl, String targetUserName, String targetPassword) {
        try {
            return  DriverManager.getConnection(connectUrl, targetUserName, targetPassword);
        }catch (Exception e){
            log.info("记录到异常，继续获取"+e);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            return getConnectionWithExceptionCatche(connectUrl,targetUserName,targetPassword);
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
//            log.info("不应为空的参数记录"+tableName+opeartion+redoSQL+sqlUndo);
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
            log.info("判断过程中抛出异常",e);
            log.info("导致判断异常的redosql={},udosql={},opeartion={},tableName={}，segName={}",redoSQL,sqlUndo,opeartion,tableName,segName);
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
                DeleteSQLParseDTO deleteSQLParseDTO = SqlParseUtil.test_delete(redoSQL);
                insertSQLParseDTO = SqlParseUtil.test_insert(sqlUndo);
                sqlDetailDTOS.add(insertSQLParseDTO);
                sqlDetailDTOS.add(deleteSQLParseDTO);
        }
        return sqlDetailDTOS;
    }
    private static boolean checkSQLRule(SQLParseDTO insertSQLParseDTO, Map<String,List<LinkTransferTaskRule>> listMap){
       if (insertSQLParseDTO==null||insertSQLParseDTO.getColumns()==null){
           return true;
       }
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

    public static void main(String[] args) throws Exception {
        String sql="insert into \"EMPQUERY\".\"AC05\"(\"AAZ203\",\"AAA027\",\"AAB301\",\"AAE140\",\"AAC001\",\"AAB001\",\"AAC050\",\"AAE035\",\"AAE160\",\"AAZ157\",\"AAZ159\",\"AAZ158\",\"AAZ002\",\"AAE002\",\"BAE001\",\"PRSENO\",\"CREATE_TIME\",\"MODIFY_TIME\",\"AAE031\",\"AAE036\",\"AAE011\") values ('4000000052044523','330185','330185','210','4000000013225467','3011000805404464','11','20210601','6101','4000000032817350','4000000013440004','26414801','907946964','202106','09','163999900487817336',TO_DATE('18-6月 -21', 'DD-MON-RR'),TO_DATE('18-6月 -21', 'DD-MON-RR'),NULL,TO_DATE('18-6月 -21', 'DD-MON-RR'),'55637')";
        System.out.println(ColumnFilter("AC05","INSERT",sql,null,Arrays.asList(new LinkTransferTaskRule()
                .setTargetTablesString("AC05").setSegName("EMPQUERY").setColumnRuleType(ColumnRuleTypeEnum.EQUALS)
        .setColumnValue("330183").setColumnName("aab301")),"EMPQUERY"));
    }
}
