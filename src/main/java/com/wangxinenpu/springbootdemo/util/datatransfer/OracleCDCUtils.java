//package com.wangxinenpu.springbootdemo.util.datatransfer;
//
//
//import com.taobao.diamond.extend.DynamicProperties;
//import com.wangxinenpu.springbootdemo.dataobject.vo.LinkTransferTask.Link.LinkShowVO;
//import com.wangxinenpu.springbootdemo.dataobject.vo.LinkTransferTask.table.TableInfo;
//import com.wangxinenpu.springbootdemo.util.DateUtils;
//import com.wangxinenpu.springbootdemo.util.dataSource.preStatement.PrepareStatementUtil;
//import com.wangxinenpu.springbootdemo.util.dataSource.sqlParse.InsertSQLParseDTO;
//import com.wangxinenpu.springbootdemo.util.scp.FileTransUtil;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.util.CollectionUtils;
//import org.springframework.util.StringUtils;
//import star.vo.result.ResultVo;
//
//import java.io.IOException;
//import java.sql.*;
//import java.util.Date;
//import java.util.*;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//import java.util.stream.Collectors;
//
//@Slf4j
//public class OracleCDCUtils {
//
//    private static final List<String> OPERATIONS = new ArrayList<>();
//
//    private static String localDataBaseUrl=DynamicProperties.staticProperties.getProperty("cdc.local.oracle.url");
//
//    private static String localUserName=DynamicProperties.staticProperties.getProperty("cdc.local.oracle.userName");
//
//    private static String localPassword=DynamicProperties.staticProperties.getProperty("cdc.local.oracle.password");
//
//    private static String localServerUrl=DynamicProperties.staticProperties.getProperty("cdc.local.server.url");
//
//    private static String localServerName=DynamicProperties.staticProperties.getProperty("cdc.local.server.userName");
//
//    private static String localServerPassword=DynamicProperties.staticProperties.getProperty("cdc.local.server.password");
//
//    private static String parseLocalFile=DynamicProperties.staticProperties.getProperty("cdc.local.server.parseLocal");
//    static {
//        OPERATIONS.add("INSERT");
//        OPERATIONS.add("UPDATE");
//        OPERATIONS.add("DELETE");
//    }
//
//    private static final Pattern insertPattern = Pattern.compile("(?<=\\(')(.*?)(?=')|(?<=',')(.*?)(?=')|NULL|(?<=NULL,')(.*?)(?=')");
//
//    static {
//        try {
//            Class.forName("oracle.jdbc.OracleDriver");
//        } catch (ClassNotFoundException e) {
//            log.error("不能正常加载ojdbc类！");
//        }
//    }
//
//    public static ResultVo<DataTransResultVO> doTrans(
//            String url,
//            String userName,
//            String passWord,
//            String target_url,
//            String targetUserName,
//            String targetPassword,
//            String schema,
//            String operations,
//            String tables,
//            String startTime,
//            String endTime,
//            String startSCN,
//            LinkShowVO fromLink, LinkShowVO toLink, Integer isOfflineMode) throws Exception {
//        ResultVo<DataTransResultVO> resultVo = new ResultVo();
//        try {
//            long executeStartTime = System.nanoTime();
//            log.info("开始测试");
//            Connection connection = DriverManager.getConnection(url, userName, passWord);
//            Connection targetConnection = DriverManager.getConnection(target_url, targetUserName, targetPassword);
////            prepareNLS(connection);
//            List<String> currentFiles = getCurrentFiles(connection);
//            List<String> archivedFiles = getArchivedFiles(connection, startTime, endTime);
//            if (CollectionUtils.isEmpty(archivedFiles)) {
//                archivedFiles = new ArrayList<>();
//            }
//            archivedFiles.addAll(currentFiles);
//
//            //此处，如果我们非离线模式，我们就会直接在目标数据库中执行，如果我们不想怎么做，我们就得将文件ssh到本地，随后分片执行
//            if (1==isOfflineMode){
//                //将连接改为本地连接
//                connection=getLocalConnection();
//                archivedFiles=new ArrayList<>();
//                //开始到目标服务器下载日志文件
//                for (String archiveFile:archivedFiles){
//                    //此时，我们的服务已经下载到本地
//                    String fileInServer= FileTransUtil.DownLoadFile(localServerUrl,localServerName,localServerPassword,archiveFile);
//                    //如果本体和目标服务器在一个服务器上，那我们就可以直接使用这个地址的文件来解析，如果不在，那我们得上传上去才行
//                    archivedFiles.add(fileInServer);
//                }
//                if ("1".equals(parseLocalFile)){
//                    //本地分析的情况
//                    startLogMnrWithArchivedFiles(connection, archivedFiles);
//                }else {
//                    //todo 异地分析的情况下-干哦，为什么我要异地分析
//                    //这种情况下，我要把文件上传到目标服务器
//                    List<String> uploadedFilesPaths= FileTransUtil.uploadFiles(localServerUrl,localServerName,localServerPassword,archivedFiles);
//                    startLogMnrWithArchivedFiles(connection, uploadedFilesPaths);
//                }
//            }else {
//                startLogMnrWithArchivedFiles(connection, archivedFiles);
//            }
//            Statement statement = connection.createStatement();
//            statement.setFetchSize(1000);
//            statement.setQueryTimeout(0);
//            String queryString = String.format(
//                    "SELECT scn,timestamp,operation,seg_owner,table_name,row_id,sql_redo FROM v$logmnr_contents WHERE table_name in ('%s') AND seg_owner = '%s' AND operation IN (%s)",
//                    tables,
//                    schema,
//                    operations
//            );
//            if (!StringUtils.isEmpty(startSCN)) {
//                queryString = queryString + "and scn >" + startSCN;
//            }
//            if (!StringUtils.isEmpty(startTime)) {
//                queryString += "and timestamp > to_date('" + startTime + "', 'yyyy-mm-dd hh24:mi:ss')";
//            }
//            if (!StringUtils.isEmpty(endTime)) {
//                queryString += "and timestamp < to_date('" + endTime + "', 'yyyy-mm-dd hh24:mi:ss')";
//            }
//            log.info("调用String为" + queryString);
//            ResultSet resultSet = statement
//                    .executeQuery(queryString
//                    );
//            log.info("进入循环");
//            int rowCount = 0;
//            PreparedStatement tempStatement = null;
////            List<String> redoSQls=new ArrayList<>();
//            Map<String, Map<String, List<String>>> insertMap = new HashMap<>();
//            Map<String, TablePrestatementVO> tablePrestatementVOMap = new HashMap<>();
//            /**
//             * 此处涉及到一个数据插入的问题，单纯以数据取出为标准，5万条的查询时间为8秒，但是，保存到新的库，5w条的用时就很长了，这里的做法:
//             * step1.将数据以5万条为单位放入内存
//             * step2.将数据根据所属表分组
//             * step3.将表中数据根据根据表-操作类型进行批量操作--这里处于效率考虑，必须使用preStatement，试试这样优化之后的效果吧
//             */
//            String lastSCN = "";
//            String lastTime = "";
//            DataTransResultVO dataTransResultVO = new DataTransResultVO();
//            String callStirng = "alter session set nls_date_language='american'";
////                System.out.println(callStirng);
//            targetConnection
//                    .prepareCall(callStirng)
//                    .execute();
//            Long insertSuccessCount = 0l;
//            Long insertFailCount = 0l;
//            Long updateSuccessCount = 0l;
//            Long updateFailCount = 0l;
//            Long deleteSuccessCount = 0l;
//            Long deleteFailCount = 0l;
//            List<StatementExecuteFailDTO> statementExecuteFailDTOS = new ArrayList<>();
//            while (resultSet.next()) {
//                rowCount++;
//                String redoSQL = resultSet.getString("sql_redo");
//                if (redoSQL.lastIndexOf(";") == redoSQL.length() - 1) {
//                    redoSQL = redoSQL.split(";")[0];
//                }
//                String tableName = resultSet.getString("table_name");
//                String opeartion = resultSet.getString("operation");
//                if (insertMap.get(tableName) == null) {
//                    Map<String, List<String>> operationMap = new HashMap<>();
//                    List<String> tempList = new ArrayList<>();
//                    tempList.add(redoSQL);
//                    operationMap.put(opeartion, tempList);
//                    insertMap.put(tableName, operationMap);
//                } else {
//                    Map<String, List<String>> operationMap = insertMap.get(tableName);
//                    if (operationMap.get(opeartion) == null) {
//                        List<String> tempList = new ArrayList<>();
//                        tempList.add(redoSQL);
//                        operationMap.put(opeartion, tempList);
//                    } else {
//                        List<String> redoSqls = operationMap.get(opeartion);
//                        redoSqls.add(redoSQL);
//                    }
//                }
//                if (rowCount >= 1000) {
//                    doBatchSave(targetConnection, insertMap, tablePrestatementVOMap, insertSuccessCount, insertFailCount, updateSuccessCount, updateFailCount, deleteSuccessCount, deleteFailCount, toLink);
//                    insertMap.clear();
//                    rowCount = 0;
//                }
//                lastSCN = resultSet.getString("scn");
//                lastTime = resultSet.getString("timestamp");
//                dataTransResultVO = new DataTransResultVO().setLastScn(lastSCN).setLastTime(lastTime.substring(0, lastTime.length() - 2));
//            }
//            doBatchSave(targetConnection, insertMap, tablePrestatementVOMap, insertSuccessCount, insertFailCount, updateSuccessCount, updateFailCount, deleteSuccessCount, deleteFailCount, toLink);
//            log.info("查询时的最终scn为" + lastSCN);
//            dataTransResultVO.setSuccessTransRowCount(insertSuccessCount + updateSuccessCount + deleteSuccessCount);
//            dataTransResultVO.setFailTransRowCount(insertFailCount + updateFailCount + deleteFailCount);
//            long executeEndTime = System.nanoTime();
//            dataTransResultVO.setExecuteTimeSecond(executeEndTime - executeStartTime);
//            dataTransResultVO.setExecuteTime(DateUtils.formatTime(executeEndTime - executeStartTime));
//            resultVo.setResult(dataTransResultVO);
//            resultVo.setResultDes("进行oracle增量抽取成功，最终的scn为" + lastSCN);
//            resultVo.setSuccess(true);
////            resultVo.setResultDes(lastSCN);
//            return resultVo;
//        } catch (Exception e) {
//            log.error("测试cdc异常", e);
//            resultVo.setSuccess(false);
//            resultVo.setResultDes("进行oracle增量抽取异常。原因为" + e.getMessage());
//            throw e;
//        }
//    }
//
//    private static Connection getLocalConnection() throws SQLException {
//        Connection connection= DriverManager.getConnection(localDataBaseUrl,localUserName,localPassword);
//        return connection;
//    }
//
////    public static void prepareNLS(Connection connection) throws SQLException {
////        if (connection != null)
////            connection
////                    .createStatement()
////                    .execute("ALTER SESSION SET NLS_DATE_FORMAT = 'DD-MON-YYYY HH24:MI:SS'");
////    }
//
//    public static void startLogMnr(Connection connection, String timestamp) throws SQLException {
//        try {
//            if (connection != null) {
//                String callStirng = "alter session set nls_date_language='american'";
////                System.out.println(callStirng);
//                connection
//                        .prepareCall(callStirng)
//                        .execute();
//                callStirng = "BEGIN  \n" +
//                        "dbms_logmnr.add_logfile(logfilename=>'/data/oracle/oradata/orcl/redo03.log',options=>dbms_logmnr.NEW);  \n" +
//                        "dbms_logmnr.add_logfile(logfilename=>'/data/oracle/oradata/orcl/redo02.log',options=>dbms_logmnr.ADDFILE);  \n" +
//                        "dbms_logmnr.add_logfile(logfilename=>'/data/oracle/oradata/orcl/redo01.log',options=>dbms_logmnr.ADDFILE);  \n" +
//                        "dbms_logmnr.START_LOGMNR(  OPTIONS => DBMS_LOGMNR.DICT_FROM_ONLINE_CATALOG +DBMS_LOGMNR.SKIP_CORRUPTION+ DBMS_LOGMNR.COMMITTED_DATA_ONLY+ DBMS_LOGMNR.NO_ROWID_IN_STMT);\n" +
//                        "END; ";
//                System.out.println(callStirng);
//                connection
//                        .prepareCall(callStirng)
//                        .execute();
//            }
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//
//    }
//
//    public static void endLogMnr(Connection connection) throws SQLException {
//        if (connection != null)
//            connection
//                    .prepareCall("begin DBMS_LOGMNR.END_LOGMNR; end;")
//                    .execute();
//    }
//
//    public static List<String> getArchivedFiles(Connection connection, String startTime, String endTime) throws SQLException {
//        String getArchivedFiles = "select recid, name, first_time from v$archived_log where 1=1";
//        if (!StringUtils.isEmpty(startTime)) {
//            getArchivedFiles += "and first_time > to_date('" + startTime + "', 'yyyy-mm-dd hh24:mi:ss')";
//        }
//        if (!StringUtils.isEmpty(endTime)) {
//            getArchivedFiles += "and first_time < to_date('" + endTime + "', 'yyyy-mm-dd hh24:mi:ss')";
//        }
//        log.info(getArchivedFiles);
//        List<String> files = new ArrayList<>();
//        ResultSet resultSet = connection.createStatement().executeQuery(getArchivedFiles);
//        while (resultSet.next()) {
//            files.add(resultSet.getString("name"));
//        }
//        resultSet.close();
//        return files;
//
//    }
//
//    public static Properties readProperties(String name) throws IOException {
//        Properties properties = new Properties();
//        properties.load(Class.class.getResourceAsStream("/src/main/resources/" + name + ".properties"));
//        return properties;
//    }
//
//    public static void startLogMnrWithArchivedFiles(Connection connection, List<String> archivedFiles) throws SQLException {
//        String callStirng = "alter session set nls_date_language='american'";
//        System.out.println(callStirng);
//        connection
//                .prepareCall(callStirng)
//                .execute();
//        StringBuilder stringBuilder = new StringBuilder("BEGIN ");
//        for (int i = 0; i < archivedFiles.size(); i++) {
//            if (i == 0) {
//                stringBuilder.append("dbms_logmnr.add_logfile(logfilename=>'").append(archivedFiles.get(0)).append("',options=>dbms_logmnr.NEW);");
//            } else {
//            stringBuilder.append("dbms_logmnr.add_logfile(logfilename=>'").append(archivedFiles.get(i)).append("',options=>dbms_logmnr.ADDFILE);");
//        }
//    }
//        stringBuilder.append("dbms_logmnr.START_LOGMNR(  OPTIONS => DBMS_LOGMNR.DICT_FROM_ONLINE_CATALOG +DBMS_LOGMNR.SKIP_CORRUPTION+ DBMS_LOGMNR.COMMITTED_DATA_ONLY+ DBMS_LOGMNR.NO_ROWID_IN_STMT);");
//        stringBuilder.append("END;");
//        callStirng = stringBuilder.toString();
//        System.out.println(callStirng);
//        connection
//                .prepareCall(callStirng)
//                .execute();
//    }
//
//    public static List<String> getCurrentFiles(Connection connection) throws SQLException {
//        List<String> files = new ArrayList<>();
//        ResultSet resultSet = connection.createStatement().executeQuery("select l.STATUS,s.MEMBER from v$log l,v$logfile s where l.GROUP# = s.GROUP# and l.STATUS='CURRENT'");
//        while (resultSet.next()) {
//            files.add(resultSet.getString("MEMBER"));
//        }
//        resultSet.close();
//        return files;
//    }
//
//    private static void doBatchSave(Connection targetConnection, Map<String, Map<String, List<String>>> insertMap, Map<String, TablePrestatementVO> tablePrestatementVOMap, Long insertSuccessCount, Long insertFailCount, Long updateSuccessCount, Long updateFailCount, Long deleteSuccessCount, Long deleteFailCount, LinkShowVO toLink) throws SQLException {
//        Map<String, TableInfo> tableInfoMap = toLink.getTableInfos().stream().collect(Collectors.toMap(i -> i.getTableName(), i -> i));
//        for (String table : insertMap.keySet()) {
//            for (String opeartion : OPERATIONS) {
//                List<String> redoSqls = insertMap.get(table).get(opeartion);
//                if (CollectionUtils.isEmpty(redoSqls)) {
//                    continue;
//                }
////                TablePrestatementVO prestatementVO;
////                if (tablePrestatementVOMap.get(table) == null) {
////                    prestatementVO = new TablePrestatementVO();
////                    tablePrestatementVOMap.put(table, prestatementVO);
////                } else {
////                    prestatementVO = tablePrestatementVOMap.get(table);
////                }
//                PreparedStatement preparedStatement = null;
//                switch (opeartion) {
//                    case "INSERT":
//                        try (Statement statement = targetConnection.createStatement()) {
//                            String fastFlag = DynamicProperties.staticProperties.getProperty("cdc.fastInsert");
//                            if ("1".equals(fastFlag)) {
//                                for (String redoSql : redoSqls) {
//                                    statement.addBatch(redoSql);
//                                }
//                                statement.executeBatch();
//                            } else {
//                                List<InsertSQLParseDTO> params = new ArrayList<>();
//                                for (String redoSql : redoSqls) {
//                                    InsertSQLParseDTO param= PrepareStatementUtil.tranSQLintoParam(redoSql);
//                                    params.add(param);
//                                }
//                                TableInfo tableInfo = tableInfoMap.get(table);
//                                PrepareStatementUtil.batchInsert(targetConnection, params, tableInfo);
//                            }
//                        } catch (BatchUpdateException e) {
//                            /**
//                             * todo statement的速度可比prestatement慢太多了，得想个办法
//                             */
//                            log.error("批量插入异常,不得不转为使用Statement进行插入，异常为", e);
//                            log.error("第" + e.getUpdateCounts().length + "条sql插入异常，插入异常的sql为" + redoSqls.get(+e.getUpdateCounts().length));
//                            //todo 这里的策略值得考虑,出错的这条是必须要使用statement保存的，剩余的条数，如果不到10条，就state到结束，反之就使用prestatement好了，治标不如治本，这里没必要进一步优化，优化handler那一部分好了
//                            for (int i = 0; i < e.getUpdateCounts().length; i++) {
//                                log.error(e.getUpdateCounts()[i] + "");
//                            }
//                            for (String redoSql : redoSqls) {
//                                try {
//                                    Statement statement = targetConnection.createStatement();
//                                    statement.executeUpdate(redoSql);
//                                    statement.close();
//                                } catch (Exception ex) {
//
//                                }
//                            }
//                        } catch (Exception e) {
//                            Statement statement = targetConnection.createStatement();
//                            for (String redoSql : redoSqls) {
//                                statement.addBatch(redoSql);
//                            }
//                            statement.executeBatch();
//                        }
//                        if (preparedStatement != null) {
//                            preparedStatement.close();
//                        }
//                        break;
//                    case "UPDATE":
//                        /**
//                         * to 好像没有好用的修改预编译写法，看看情况吧，应当是能够抽象出来的，但是现在没空
//                         */
////                        if (prestatementVO.getUpdateSQL()==null){
////                            createUpadtePreSQL(redoSqls.get(0),prestatementVO);
////                            System.out.println("test");
////                        }
//                        for (String redoSql : redoSqls) {
//                            Statement statement = targetConnection.createStatement();
//                            statement.executeUpdate(redoSql);
//                            statement.close();
//                        }
////                        preparedStatement.executeBatch();
////                        preparedStatement.close();
//                        updateSuccessCount++;
//                        break;
//                    case "DELETE":
////                        if (prestatementVO.getDeleteSQL()==null){
////                            createDeletePreSQL(redoSqls.get(0),prestatementVO);
////                            System.out.println("test");
////                        }
////                        preparedStatement=targetConnection.prepareStatement(prestatementVO.getDeleteSQL());
////                        for (String redoSql:redoSqls){
////                            doSqlSet(redoSql,preparedStatement);
////                            preparedStatement.addBatch();
////                        }
////                        preparedStatement.executeBatch();
////                        preparedStatement.close();
//                        /**
//                         * to 同上，出现效率问题再优化吧
//                         */
//                        for (String redoSql : redoSqls) {
////                            log.info("删除语句"+redoSql);
//                            Statement statement = targetConnection.createStatement();
//                            statement.executeUpdate(redoSql);
//                            statement.close();
//                        }
//                        deleteSuccessCount++;
//                        break;
//                }
//            }
//        }
//    }
//
//    private static void doSqlSet(String redoSql, PreparedStatement preparedStatement, LinkShowVO toLink) throws SQLException {
//        Matcher matcher = insertPattern.matcher(redoSql);
//        Integer count = 1;
//        List<String> values = new ArrayList<>();
//        while (matcher.find()) {
//            values.add(matcher.group());
//        }
////        for (int i=0;i<values.size();i++){
////            log.info(i+"|"+values.get(i));
////        }
//        preparedStatement.setInt(1, Integer.valueOf(values.get(0)));
//        preparedStatement.setString(2, values.get(1));
//        preparedStatement.setString(3, values.get(2));
//        preparedStatement.setInt(4, Integer.valueOf(values.get(3)));
//        preparedStatement.setString(5, values.get(4));
//        preparedStatement.setString(6, values.get(5));
//        preparedStatement.setString(7, values.get(6));
//        preparedStatement.setString(8, values.get(7));
//        preparedStatement.setInt(9, Integer.valueOf(values.get(8)));
//        preparedStatement.setInt(10, Integer.valueOf(values.get(9)));
//        preparedStatement.setInt(11, Integer.valueOf(values.get(10)));
//        preparedStatement.setInt(12, Integer.valueOf(values.get(11)));
//        preparedStatement.setInt(13, Integer.valueOf(values.get(12)));
//        preparedStatement.setTimestamp(14, new Timestamp((new Date().getTime())));
//        preparedStatement.setTimestamp(15, new Timestamp((new Date().getTime())));
////        preparedStatement.setInt(16,values.get(15));
//        preparedStatement.setString(16, values.get(15));
//        preparedStatement.addBatch();
//    }
//
//    private static void createInsertPreSQL(String s, TablePrestatementVO prestatementVO) {
//        String temp = s.replaceAll("(?<=\\(')(.*?)(?=')|(?<=',')(.*?)(?=')|NULL|(?<=NULL,')(.*?)(?=')", "?").replaceAll("'", "");
//        log.info("生成的insert语句为" + temp);
//        prestatementVO.setInsertSQL(temp);
//    }
//}
