package com.wangxinenpu.springbootdemo.controller;

import com.wangxinenpu.springbootdemo.dataobject.po.linkTask.LinkTransferTaskRule;
import com.wangxinenpu.springbootdemo.util.dataSource.sqlParse.CDCCache.MSGTYPECONSTANT;
import com.wangxinenpu.springbootdemo.util.dataSource.sqlParse.CDCCache.TableStatusCache;
import com.wangxinenpu.springbootdemo.util.datatransfer.CDCUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

@AllArgsConstructor
@Slf4j
public class LogminerFileTask implements Callable {

    private String fileName;
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
    @Override
    public Object call() throws Exception {
        Statement statement=null;
        String recordSql="";
        Long recordSCN=null;
        Class.forName("oracle.jdbc.OracleDriver");
        Connection connection=  DriverManager.getConnection(url, userName, password);
        try {
            List<String>archivedFiles= Arrays.asList(fileName);
            CDCUtil.startLogMnrWithArchivedFiles(connection, archivedFiles,startSCN,timeStamp,batchCount);
            statement = connection.createStatement();
            statement.setFetchSize(1000);
            log.info("进行logminer解析"+queryString);
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
                    if ("1".equals(CSF)&&cdcTask.needAppendMap.get(rowFlag)==null){
                        if (cdcTask.needAppendMap.get(rowFlag)==null){
                            cdcTask.needAppendMap.put(rowFlag,redoSQL);
                            continue;
                        }
                    }
                    if (cdcTask.needAppendMap.get(rowFlag)!=null){
                        redoSQL=cdcTask.needAppendMap.get(rowFlag)+redoSQL;
                    }
                    if (cdcTask.ColumnFilter(tableName, opeartion, redoSQL, resultSet.getString("sql_undo"), linkTransferTaskRules, seg_owner)) {
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
        }catch (Exception e){
            log.error("子分析进程出现异常");
            return new LogminerFileTaskResult();
        }finally {
            if (statement!=null&&!statement.isClosed()) {
                statement.close();
            }
            if (connection!=null&&!connection.isClosed()) {
                connection.close();
            }
        }
        return new LogminerFileTaskResult().setLastScn(startSCN).setLastTime(timeStamp);
    }
}
