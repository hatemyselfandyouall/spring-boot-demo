package com.wangxinenpu.springbootdemo.controller;

import com.alibaba.druid.pool.DruidDataSource;
import com.wangxinenpu.springbootdemo.dataobject.po.linkTask.LinkTransferTaskRule;
import com.wangxinenpu.springbootdemo.util.dataSource.sqlParse.CDCCache.MSGTYPECONSTANT;
import com.wangxinenpu.springbootdemo.util.dataSource.sqlParse.CDCCache.TableStatusCache;
import com.wangxinenpu.springbootdemo.util.datatransfer.CDCUtil;
import com.wangxinenpu.springbootdemo.util.datatransfer.LogFile;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.concurrent.Callable;

@Slf4j
public class LogminerFileTask implements Callable {

    private LogFile logFile;
    private Connection connection;
    private String url;
    private String userName;
    private String password;
    private Long startSCN;
    private String timeStamp;
    private String queryString;
    private CDCTask cdcTask;
    private List<LinkTransferTaskRule> linkTransferTaskRules;
    private Integer batchCount;
    private DruidDataSource dataSource;

    public LogminerFileTask(LogFile logFile, Connection connection, String url, String userName, String password, String timeStamp, String queryString, CDCTask cdcTask, List<LinkTransferTaskRule> linkTransferTaskRules, Integer batchCount, Long startSCN, DruidDataSource dataSource) {
        this.logFile = logFile;
        this.connection = connection;
        this.url = url;
        this.userName = userName;
        this.password = password;
        this.timeStamp = timeStamp;
        this.queryString = queryString;
        this.cdcTask = cdcTask;
        this.linkTransferTaskRules = linkTransferTaskRules;
        this.batchCount = batchCount;
        this.startSCN=startSCN;
        this.dataSource=dataSource;
    }

    @Override
    public Object call() throws Exception {
        Statement statement=null;
        String recordSql="";
        Long recordSCN=null;
        Class.forName("oracle.jdbc.OracleDriver");
        try {
            connection=dataSource.getConnection();
            if (connection==null||connection.isClosed()){
                connection= DriverManager.getConnection(url, userName, password);
            }
            CDCUtil.startLogMnrWithArchivedFiles(connection, logFile,startSCN,timeStamp,batchCount);
            statement = connection.createStatement();
            statement.setFetchSize(1000);
            log.info("进行logminer解析"+queryString);
            statement.execute("alter session set nls_date_language='SIMPLIFIED CHINESE'");
            ResultSet resultSet = statement.executeQuery(queryString);
            log.info("解析结束，获得结果集");
            try {
                while (resultSet.next() && cdcTask.working) {
                    Long start=System.currentTimeMillis();
                    if (Long.MAX_VALUE==cdcTask.totalCount)cdcTask.totalCount=0l;cdcTask.totalCount++;
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
                    Long scn = resultSet.getLong("scn");
                    startSCN = scn;
                    timeStamp=timeStamp;
                    recordSql = redoSQL;
                    recordSCN = scn;
                    if ("1".equals(CSF)){
                        if (cdcTask.needAppendMap.get(rowFlag)==null){
                            cdcTask.needAppendMap.put(rowFlag,redoSQL);
                            continue;
                        }else {
                            cdcTask.needAppendMap.put(rowFlag,cdcTask.needAppendMap.get(rowFlag)+redoSQL);
                            continue;
                        }
                    }
                    String undoSQL=resultSet.getString("sql_undo");
                    if (cdcTask.needAppendMap.get(rowFlag)!=null){
                        redoSQL=cdcTask.needAppendMap.get(rowFlag)+redoSQL;
                        undoSQL=null;
                    }
                    if (cdcTask.ColumnFilter(tableName, opeartion, redoSQL,undoSQL, linkTransferTaskRules, seg_owner)) {
//                                System.out.println(System.currentTimeMillis()-start+"3");
                        String MapTableName = seg_owner + "|" + tableName;
                        String tableStatus = TableStatusCache.getStatus(MapTableName);
                        //如果还没开始全量，这个表的数据不管
                        if (org.apache.commons.lang3.StringUtils.isEmpty(tableStatus) || tableStatus.equals(MSGTYPECONSTANT.TABLE_STATUS_NOT_INITED_YET)) {

                        } else {
                            if (Long.MAX_VALUE==cdcTask.redisTotalCount)cdcTask.redisTotalCount=0l;
                            cdcTask.redisTotalCount++;
                            cdcTask.sqlSaver.save(seg_owner,tableName, redoSQL, tableStatus, scn+"|"+cdcTask.totalCount,timeStamp);
                        }
                    }
                }
                log.info("结果集分析结束"+cdcTask.totalCount);
//                        if (totalStartScn!=null) {
                CDCUtil.endLogMnr(connection);
//                        }
            } catch (Throwable e) {
                log.info("", e);
                cdcTask.exceptionWriteCompoent.wirte(recordSql, e, "");
            }
            return new LogminerFileTaskResult().setLastScn(startSCN).setLastTime(timeStamp);
        }catch (Exception e){
            log.error("子分析进程出现异常",e);
            return new LogminerFileTaskResult();
        }finally {
            if (statement!=null&&!statement.isClosed()) {
                statement.close();
            }
            if (connection!=null&&!connection.isClosed()){
                connection.close();
            }
        }
    }
}
