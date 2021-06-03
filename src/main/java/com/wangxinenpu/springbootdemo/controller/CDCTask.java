package com.wangxinenpu.springbootdemo.controller;

import com.wangxinenpu.springbootdemo.dataobject.po.linkTask.LinkTransferTaskRule;
import com.wangxinenpu.springbootdemo.dataobject.vo.LinkTransferTask.LinkTransferTaskCDDVO;
import com.wangxinenpu.springbootdemo.util.DateUtils;
import com.wangxinenpu.springbootdemo.util.dataSource.sqlParse.CDCCache.MSGTYPECONSTANT;
import com.wangxinenpu.springbootdemo.util.dataSource.sqlParse.CDCCache.SQLSaver;
import com.wangxinenpu.springbootdemo.util.dataSource.sqlParse.CDCCache.TableStatusCache;
import com.wangxinenpu.springbootdemo.util.dataSource.sqlParse.InsertSQLParseDTO;
import com.wangxinenpu.springbootdemo.util.dataSource.sqlParse.SQLParseDTO;
import com.wangxinenpu.springbootdemo.util.dataSource.sqlParse.SqlParseUtil;
import com.wangxinenpu.springbootdemo.util.dataSource.sqlParse.UpdateSQLParseDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class CDCTask implements Runnable{

    private Long totalStartTime;

    private List<LinkTransferTaskCDDVO> linkTransferTasks;

    private DefaultMQProducer defaultMQProducer;

    public CDCTask(Long totalStartTime, List<LinkTransferTaskCDDVO> linkTransferTasks, DefaultMQProducer defaultMQProducer) {
        this.totalStartTime = totalStartTime;
        this.linkTransferTasks = linkTransferTasks;
        this.defaultMQProducer = defaultMQProducer;
    }


    @Override
    public void run() {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            String startString= DateUtils.parseLongtoDate(totalStartTime,"yyyy-MM-dd HH:mm:ss");
            String connectUrl="jdbc:oracle:thin:@//172.16.81.11:1521/hzrsrac";
            String targetUserName="sjhl_fy";
            String targetPassword="sjhl_pwdfy21";
            Connection targetConnection = DriverManager.getConnection(connectUrl, targetUserName, targetPassword);
            targetConnection.createStatement().execute("BEGIN dbms_logmnr.start_logmnr(STARTTIME =>to_date('"+startString+"' , 'yyyy-mm-dd hh24:mi:ss'),options => DBMS_LOGMNR.DICT_FROM_ONLINE_CATALOG + DBMS_LOGMNR.CONTINUOUS_MINE + DBMS_LOGMNR.COMMITTED_DATA_ONLY);END;");
//            PreparedStatement preparedStatement=targetConnection.prepareStatement("SELECT * FROM v$logmnr_contents where  seg_owner = 'SYNC' and table_name" +
//                    " in ('AC85')  AND (operation IN ('INSERT','UPDATE','DELETE','DDL'))");
            Set<String> segNames=null;
            Set<String> tableNames=null;
            List<LinkTransferTaskRule> linkTransferTaskRules=new ArrayList<>();
            for (LinkTransferTaskCDDVO linkTransferTaskCDDVO:linkTransferTasks){
                segNames=linkTransferTasks.stream().map(i->i.getSegName()).distinct().collect(Collectors.toSet());
                tableNames=linkTransferTasks.stream().map(i->i.getTargetTablesString()).distinct().collect(Collectors.toSet());
                if (linkTransferTaskCDDVO.getLinkTransferTaskRule()!=null){
                    linkTransferTaskRules.addAll(linkTransferTaskCDDVO.getLinkTransferTaskRule());
                }
            }
            String segString="(";
            for (String temp:segNames){
                segString+="'"+temp+"',";
            }
            segString=segString.substring(0,segString.length()-1);
            segString+=")";
            String tableString="(";
            for (String temp:tableNames){
                tableString+="'"+temp+"',";
            }
            tableString=tableString.substring(0,tableString.length()-1);
            tableString+=")";
            PreparedStatement preparedStatement=targetConnection.prepareStatement("SELECT * FROM v$logmnr_contents where  "+
                    "  (operation IN ('INSERT','UPDATE','DELETE','DDL')) and seg_owner in"+segString+"and table_name in " +tableString);
            preparedStatement.setFetchSize(10);
            ResultSet resultSet = preparedStatement.executeQuery();
            Integer count=0;
            while (resultSet.next()) {
                String redoSQL = resultSet.getString("sql_redo");
                if (redoSQL.lastIndexOf(";") == redoSQL.length() - 1) {
                    redoSQL = redoSQL.split(";")[0];
                }
                String tableName = resultSet.getString("table_name");
                String opeartion = resultSet.getString("operation");
                String seg_owner = resultSet.getString("seg_owner");
                String timeStamp = resultSet.getString("timestamp");
                String scn = resultSet.getString("scn");
                if (ColumnFilter(tableName, opeartion, redoSQL, resultSet.getString("sql_undo"), linkTransferTaskRules, seg_owner)) {
                    String MapTableName=seg_owner+"|"+tableName;
                            String tableStatus=TableStatusCache.getStatus(MapTableName);
                            //如果还没开始全量，这个表的数据不管
                            if (org.apache.commons.lang3.StringUtils.isEmpty(tableStatus)||tableStatus.equals(MSGTYPECONSTANT.TABLE_STATUS_NOT_INITED_YET)){

                            }else {
                                //如果全量已经开始，但是尚未增量，此时进行记录但是不操作
                                //如果全量已经结束，则按顺序入库
                                String sql=redoSQL;
                                Long scnLongValue=Long.valueOf(scn);
                                SQLSaver.save(tableName,sql,tableStatus,scnLongValue);
                            }

                }
            }
        }catch (Exception e){
            log.error("",e);
        }
    }

    private static boolean ColumnFilter(String tableName, String opeartion, String redoSQL, String sqlUndo, List<LinkTransferTaskRule> linkTransferTaskRuleList,String segName)throws Exception {
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
            switch (opeartion){
                case "insert":
                    InsertSQLParseDTO insertSQLParseDTO= SqlParseUtil.test_insert(redoSQL);
                    return  checkSQLRule(insertSQLParseDTO,listMap);
                case "update":
                    UpdateSQLParseDTO updateSQLParseDTO=SqlParseUtil.test_update(redoSQL);
                    UpdateSQLParseDTO updoParse=SqlParseUtil.test_update(sqlUndo);
                    return  checkSQLRule(updateSQLParseDTO,listMap)||checkSQLRule(updoParse,listMap);
                case "delete":
                    insertSQLParseDTO=SqlParseUtil.test_insert(sqlUndo);
                    return  checkSQLRule(insertSQLParseDTO,listMap);
                default:
                    return false;
            }
        }catch (Exception e){
            return true;
        }

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
}