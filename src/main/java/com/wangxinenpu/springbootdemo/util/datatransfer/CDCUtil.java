package com.wangxinenpu.springbootdemo.util.datatransfer;

import com.wangxinenpu.springbootdemo.controller.CDCTask;
import com.wangxinenpu.springbootdemo.dataobject.po.linkTask.ColumnRuleTypeEnum;
import com.wangxinenpu.springbootdemo.dataobject.po.linkTask.LinkTransferTaskRule;
import com.wangxinenpu.springbootdemo.dataobject.vo.LinkTransferTask.Link.LinkShowVO;
import com.wangxinenpu.springbootdemo.dataobject.vo.root.ResultVo;
import com.wangxinenpu.springbootdemo.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class CDCUtil {

    private static final List<String> OPERATIONS = new ArrayList<>();

    static {
        OPERATIONS.add("INSERT");
        OPERATIONS.add("UPDATE");
        OPERATIONS.add("DELETE");
    }

    private static final Pattern insertPattern = Pattern.compile("(?<=\\(')(.*?)(?=')|(?<=',')(.*?)(?=')|NULL|(?<=NULL,')(.*?)(?=')");

    static {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
        } catch (ClassNotFoundException e) {
            log.error("不能正常加载ojdbc类！");
        }
    }

    public static ResultVo<DataTransResultVO> doTrans(
            String url,
            String userName,
            String passWord,
            String target_url,
            String targetUserName,
            String targetPassword,
            String schema,
            String operations,
            String tables,
            String startTime,
            String endTime,
            String startSCN,
            LinkShowVO fromLink, LinkShowVO toLink) throws Exception{
        ResultVo<DataTransResultVO> resultVo=new ResultVo();
        try {
            long executeStartTime = System.nanoTime();
            log.info("开始测试");
            Connection connection = DriverManager.getConnection(url, userName, passWord);
            Connection targetConnection = DriverManager.getConnection(target_url, targetUserName, targetPassword);
//            prepareNLS(connection);
            List<String> currentFiles = getCurrentFiles(connection);
            List<String> archivedFiles = getArchivedFiles(connection, startTime, endTime);
            if (CollectionUtils.isEmpty(archivedFiles)) {
                archivedFiles=new ArrayList<>();
            }
            archivedFiles.addAll(currentFiles);
            startLogMnrWithArchivedFiles(connection, archivedFiles);

            Statement statement = connection.createStatement();
            statement.setFetchSize(1000);
            statement.setQueryTimeout(0);
            String queryString = String.format("SELECT * FROM v$logmnr_contents where    (operation IN ('INSERT','UPDATE','DELETE','DDL')) and seg_owner in('EMPQUERY','OTHERQUERY','VILQUERY','INJURY')and table_name in ('ACD8','RF02','LA02','LA06','LC48','LC47','RF08','AC35','LC46','LC45','AC51','BC20','MV_BE03_JZXM','AA10','AC50','MV_BE03_DW','EXT6802','LC31','EXT6803','LC30','AA06','LC37','AA05','AA02','AA03','RC01_SJZ','AC43','AC62','BB10','AC63','AC60','MV_BB02','AC61','BB08','AA17','LC69','LC68','AC53','LCE1','LCE4','LCE3','MV_BE03_RY','LC51','AE28','AD07','AE29','LC59','LC58','LC57','AA26','AE23','LC56','AC66','AC67','IC08','IC09','AC82','AC83','MV_BC02','IC07','AC80','IC92','IC95','IC96','RB01_SJZ','IC93','LC04','BA08','LC03','IC10','LC01','BA06','AC77','BA04','IC13','BC43','MV_BC15','MV_BC12','MV_BE03_CSQ','AC92','AC90','MV_BC95','AD27','IC88','AC05','IC89','AC02','IC87','AC01','IC05','LCB2','MV_BC60','MV_AC93','MV_IC98','MV_IC97','LB01','MV_IC91','IC30','MV_IC94','MV_IC90','LB02','AE53','AC97','BF41','BC65','AB07','AF02','IC20','AB01','AB02','AC20','LC17')");
            if (!StringUtils.isEmpty(startSCN)) {
                queryString = queryString + "and scn >" + startSCN;
            }
            if (!StringUtils.isEmpty(startTime)) {
                queryString += "and timestamp > to_date('" + startTime + "', 'yyyy-mm-dd hh24:mi:ss')";
            }
            if (!StringUtils.isEmpty(endTime)) {
                queryString += "and timestamp < to_date('" + endTime + "', 'yyyy-mm-dd hh24:mi:ss')";
            }
            log.info("调用String为" + queryString);
            ResultSet resultSet = statement
                    .executeQuery(queryString
                    );
            log.info("进入循环");
            int rowCount = 0;
            PreparedStatement tempStatement = null;
//            List<String> redoSQls=new ArrayList<>();
            Map<String, Map<String, List<String>>> insertMap = new HashMap<>();
            Map<String, TablePrestatementVO> tablePrestatementVOMap = new HashMap<>();
            /**
             * 此处涉及到一个数据插入的问题，单纯以数据取出为标准，5万条的查询时间为8秒，但是，保存到新的库，5w条的用时就很长了，这里的做法:
             * step1.将数据以5万条为单位放入内存
             * step2.将数据根据所属表分组
             * step3.将表中数据根据根据表-操作类型进行批量操作--这里处于效率考虑，必须使用preStatement，试试这样优化之后的效果吧
             */
            String lastSCN = "";
            String lastTime="";
            DataTransResultVO dataTransResultVO=null;
            String callStirng = "alter session set nls_date_language='american'";
//                System.out.println(callStirng);
            targetConnection
                    .prepareCall(callStirng)
                    .execute();
            Long insertSuccessCount=0l;Long insertFailCount=0l;Long updateSuccessCount=0l;Long updateFailCount=0l;Long deleteSuccessCount=0l;Long deleteFailCount=0l;
            List<StatementExecuteFailDTO> statementExecuteFailDTOS=new ArrayList<>();
            while (resultSet.next()) {
                rowCount++;
                String redoSQL = resultSet.getString("sql_redo");
                if (redoSQL.lastIndexOf(";") == redoSQL.length() - 1) {
                    redoSQL = redoSQL.split(";")[0];
                }
                String tableName = resultSet.getString("table_name");
                String opeartion = resultSet.getString("operation");
                if (insertMap.get(tableName) == null) {
                    Map<String, List<String>> operationMap = new HashMap<>();
                    List<String> tempList = new ArrayList<>();
                    tempList.add(redoSQL);
                    operationMap.put(opeartion, tempList);
                    insertMap.put(tableName, operationMap);
                } else {
                    Map<String, List<String>> operationMap = insertMap.get(tableName);
                    if (operationMap.get(opeartion) == null) {
                        List<String> tempList = new ArrayList<>();
                        tempList.add(redoSQL);
                        operationMap.put(opeartion, tempList);
                    } else {
                        List<String> redoSqls = operationMap.get(opeartion);
                        redoSqls.add(redoSQL);
                    }
                }
                if (rowCount >= 1000) {
                    doBatchSave(targetConnection, insertMap, tablePrestatementVOMap,insertSuccessCount,insertFailCount,updateSuccessCount,updateFailCount,deleteSuccessCount,deleteFailCount,toLink);
                    insertMap.clear();
                    rowCount = 0;
                }
                lastSCN = resultSet.getString("scn");
                lastTime=resultSet.getString("timestamp");
                dataTransResultVO=new DataTransResultVO().setLastScn(lastSCN).setLastTime(lastTime.substring(0,lastTime.length()-2));
            }
            doBatchSave(targetConnection, insertMap, tablePrestatementVOMap, insertSuccessCount, insertFailCount, updateSuccessCount, updateFailCount, deleteSuccessCount, deleteFailCount, toLink);
            log.info("查询时的最终scn为" + lastSCN);
            dataTransResultVO.setSuccessTransRowCount(insertSuccessCount+updateSuccessCount+deleteSuccessCount);
            dataTransResultVO.setFailTransRowCount(insertFailCount+updateFailCount+deleteFailCount);
            long executeEndTime = System.nanoTime();
            dataTransResultVO.setExecuteTimeSecond(executeEndTime-executeStartTime);
            dataTransResultVO.setExecuteTime(DateUtils.formatTime(executeEndTime-executeStartTime));
            resultVo.setResult(dataTransResultVO);
            resultVo.setResultDes("进行oracle增量抽取成功，最终的scn为"+lastSCN);
            resultVo.setSuccess(true);
//            resultVo.setResultDes(lastSCN);
            return resultVo;
        } catch (Exception e) {
            log.error("测试cdc异常", e);
            resultVo.setSuccess(false);
            resultVo.setResultDes("进行oracle增量抽取异常。原因为"+e.getMessage());
            throw e;
        }
    }

//    public static void prepareNLS(Connection connection) throws SQLException {
//        if (connection != null)
//            connection
//                    .createStatement()
//                    .execute("ALTER SESSION SET NLS_DATE_FORMAT = 'DD-MON-YYYY HH24:MI:SS'");
//    }

    public static void startLogMnr(Connection connection, String timestamp) throws SQLException {
        try {
            if (connection != null) {
                String callStirng = "alter session set nls_date_language='american'";
//                System.out.println(callStirng);
                connection
                        .prepareCall(callStirng)
                        .execute();
                callStirng = "BEGIN  \n" +
                        "dbms_logmnr.add_logfile(logfilename=>'/data/oracle/oradata/orcl/redo03.log',options=>dbms_logmnr.NEW);  \n" +
                        "dbms_logmnr.add_logfile(logfilename=>'/data/oracle/oradata/orcl/redo02.log',options=>dbms_logmnr.ADDFILE);  \n" +
                        "dbms_logmnr.add_logfile(logfilename=>'/data/oracle/oradata/orcl/redo01.log',options=>dbms_logmnr.ADDFILE);  \n" +
                        "dbms_logmnr.START_LOGMNR(  OPTIONS => DBMS_LOGMNR.DICT_FROM_ONLINE_CATALOG +DBMS_LOGMNR.SKIP_CORRUPTION+ DBMS_LOGMNR.COMMITTED_DATA_ONLY+ DBMS_LOGMNR.NO_ROWID_IN_STMT);\n" +
                        "END; ";
                System.out.println(callStirng);
                connection
                        .prepareCall(callStirng)
                        .execute();
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public static void endLogMnr(Connection connection) throws SQLException {
        if (connection != null)
            connection
                    .prepareCall("begin DBMS_LOGMNR.END_LOGMNR; end;")
                    .execute();
    }

    public static List<String> getArchivedFiles(Connection connection, String startTime, String endTime) throws SQLException {
        String getArchivedFiles = "select recid, name, first_time from v$archived_log where 1=1";
        if (!StringUtils.isEmpty(startTime)) {
            getArchivedFiles += "and first_time > to_date('" + startTime + "', 'yyyy-mm-dd hh24:mi:ss')";
        }
        if (!StringUtils.isEmpty(endTime)) {
            getArchivedFiles += "and first_time < to_date('" + endTime + "', 'yyyy-mm-dd hh24:mi:ss')";
        }
        log.info(getArchivedFiles);
        List<String> files = new ArrayList<>();
        ResultSet resultSet = connection.createStatement().executeQuery(getArchivedFiles);
        while (resultSet.next()) {
            files.add(resultSet.getString("name"));
        }
        resultSet.close();
        return files;
    }

    public static Properties readProperties(String name) throws IOException {
        Properties properties = new Properties();
        properties.load(Class.class.getResourceAsStream("/src/main/resources/" + name + ".properties"));
        return properties;
    }

    public static void main(String[] args)throws Exception {
        Long totalStartTime=1623037905034L;
        String url="jdbc:oracle:thin:@//172.16.81.11:1521/hzrsrac";
        String userName="sjhl_fy";
        String passWord="sjhl_pwdfy21";
        Connection connection = DriverManager.getConnection(url, userName, passWord);
//            prepareNLS(connection);
        String startString = DateUtils.parseLongtoDate(totalStartTime, "yyyy-MM-dd HH:mm:ss");
        List<String> currentFiles = getCurrentFiles(connection);
        List<String> archivedFiles = getArchivedFiles(connection, startString, "");
        if (CollectionUtils.isEmpty(archivedFiles)) {
            archivedFiles=new ArrayList<>();
        }
        archivedFiles.addAll(currentFiles);
        startLogMnrWithArchivedFiles(connection, archivedFiles);

        Statement statement = connection.createStatement();
        statement.setFetchSize(1000);
        statement.setQueryTimeout(0);
        String queryString = String.format(
                "SELECT * FROM v$logmnr_contents where    (operation IN ('INSERT','UPDATE','DELETE','DDL')) and seg_owner in('EMPQUERY')and table_name in ('ACD8') "
        );
//        if (!StringUtils.isEmpty(startSCN)) {
//            queryString = queryString + "and scn >" + startSCN;
//        }
        if (!StringUtils.isEmpty(startString)) {
            queryString += "and timestamp > to_date('" + startString + "', 'yyyy-mm-dd hh24:mi:ss')";
        }
//        if (!StringUtils.isEmpty(endTime)) {
//            queryString += "and timestamp < to_date('" + endTime + "', 'yyyy-mm-dd hh24:mi:ss')";
//        }
        log.info("调用String为" + queryString);
        ResultSet resultSet = statement
                .executeQuery(queryString
                );
        int count=0;
        while (resultSet.next()){
            String sqlRedo=resultSet.getString("sql_redo");
            if (sqlRedo.contains("330183")) {
                count++;
                System.out.println(sqlRedo+"|" + count);
                System.out.println(CDCTask.ColumnFilter("ACD8","INSERT",sqlRedo,null,
                        Arrays.asList(new LinkTransferTaskRule().setSegName("EMPQUERY")
                                .setTargetTablesString("ACD8")
                                .setColumnName("aab301").setColumnValue("330183").setColumnRuleType(ColumnRuleTypeEnum.EQUALS)),"EMPQUERY"));
            }
        }

    }
    public static void startLogMnrWithArchivedFiles(Connection connection, List<String> archivedFiles) throws SQLException {
//        String callStirng = "alter session set nls_date_language='american'";
//        System.out.println(callStirng);
//        connection
//                .prepareCall(callStirng)
//                .execute();
        StringBuilder stringBuilder = new StringBuilder("BEGIN ");
        for (int i = 0; i < archivedFiles.size(); i++) {
            if (i == 0) {
                stringBuilder.append("dbms_logmnr.add_logfile(logfilename=>'").append(archivedFiles.get(0)).append("',options=>dbms_logmnr.NEW);");
            } else {
                stringBuilder.append("dbms_logmnr.add_logfile(logfilename=>'").append(archivedFiles.get(i)).append("',options=>dbms_logmnr.ADDFILE);");
            }
        }
        stringBuilder.append("dbms_logmnr.START_LOGMNR(  OPTIONS => DBMS_LOGMNR.DICT_FROM_ONLINE_CATALOG +DBMS_LOGMNR.SKIP_CORRUPTION+ DBMS_LOGMNR.COMMITTED_DATA_ONLY+ DBMS_LOGMNR.NO_ROWID_IN_STMT);");
        stringBuilder.append("END;");
        String callStirng = stringBuilder.toString();
        System.out.println(callStirng);
        connection
                .prepareCall(callStirng)
                .execute();
    }

    public static List<String> getCurrentFiles(Connection connection) throws SQLException {
        List<String> files = new ArrayList<>();
        ResultSet resultSet = connection.createStatement().executeQuery("select l.STATUS,s.MEMBER from v$log l,v$logfile s where l.GROUP# = s.GROUP# and l.STATUS='CURRENT'");
        while (resultSet.next()) {
            files.add(resultSet.getString("MEMBER"));
        }
        resultSet.close();
        return files;
    }

    private static void doBatchSave(Connection targetConnection, Map<String, Map<String, List<String>>> insertMap, Map<String, TablePrestatementVO> tablePrestatementVOMap, Long insertSuccessCount, Long insertFailCount, Long updateSuccessCount, Long updateFailCount, Long deleteSuccessCount, Long deleteFailCount, LinkShowVO toLink) throws SQLException {
        for (String table : insertMap.keySet()) {
            for (String opeartion : OPERATIONS) {
                List<String> redoSqls = insertMap.get(table).get(opeartion);
                if (CollectionUtils.isEmpty(redoSqls)) {
                    continue;
                }
                TablePrestatementVO prestatementVO;
                if (tablePrestatementVOMap.get(table) == null) {
                    prestatementVO = new TablePrestatementVO();
                    tablePrestatementVOMap.put(table, prestatementVO);
                } else {
                    prestatementVO = tablePrestatementVOMap.get(table);
                }
                PreparedStatement preparedStatement = null;
                switch (opeartion) {
                    case "INSERT":
                        try (Statement statement = targetConnection.createStatement()){
//                            String fastFlag=DynamicProperties.staticProperties.getProperty("cdc.fastInsert");
                            String fastFlag="2";
                            if ("1".equals(fastFlag)) {
                                for (String redoSql : redoSqls) {
                                    statement.addBatch(redoSql);
                                }
                                statement.executeBatch();
                            }else {
                                if (prestatementVO.getInsertSQL() == null) {
                                    createInsertPreSQL(redoSqls.get(0), prestatementVO);
                                }
                                preparedStatement = targetConnection.prepareStatement(prestatementVO.getInsertSQL());
                                for (String redoSql : redoSqls) {
                                    doSqlSet(redoSql, preparedStatement,toLink);
                                }
                                preparedStatement.executeBatch();
                            }
                            //
                            insertSuccessCount++;
                        } catch (BatchUpdateException e) {
                            /**
                             * todo statement的速度可比prestatement慢太多了，得想个办法
                             */
                            log.error("批量插入异常,不得不转为使用Statement进行插入，异常为", e);
                            log.error("第" + e.getUpdateCounts().length + "条sql插入异常，插入异常的sql为" + redoSqls.get(+e.getUpdateCounts().length));
                            //todo 这里的策略值得考虑,出错的这条是必须要使用statement保存的，剩余的条数，如果不到10条，就state到结束，反之就使用prestatement好了，治标不如治本，这里没必要进一步优化，优化handler那一部分好了
                            for (int i=0;i<e.getUpdateCounts().length;i++){
                                log.error(e.getUpdateCounts()[i]+"");
                            }
                            for (String redoSql : redoSqls) {
                                try {
                                    Statement statement = targetConnection.createStatement();
                                    statement.executeUpdate(redoSql);
                                    statement.close();
                                }catch (Exception ex){

                                }
                            }
                        }
                        if (preparedStatement != null) {
                            preparedStatement.close();
                        }
                        break;
                    case "UPDATE":
                        /**
                         * to 好像没有好用的修改预编译写法，看看情况吧，应当是能够抽象出来的，但是现在没空
                         */
//                        if (prestatementVO.getUpdateSQL()==null){
//                            createUpadtePreSQL(redoSqls.get(0),prestatementVO);
//                            System.out.println("test");
//                        }
                        for (String redoSql : redoSqls) {
                            Statement statement = targetConnection.createStatement();
                            statement.executeUpdate(redoSql);
                            statement.close();
                        }
//                        preparedStatement.executeBatch();
//                        preparedStatement.close();
                        updateSuccessCount++;
                        break;
                    case "DELETE":
//                        if (prestatementVO.getDeleteSQL()==null){
//                            createDeletePreSQL(redoSqls.get(0),prestatementVO);
//                            System.out.println("test");
//                        }
//                        preparedStatement=targetConnection.prepareStatement(prestatementVO.getDeleteSQL());
//                        for (String redoSql:redoSqls){
//                            doSqlSet(redoSql,preparedStatement);
//                            preparedStatement.addBatch();
//                        }
//                        preparedStatement.executeBatch();
//                        preparedStatement.close();
                        /**
                         * to 同上，出现效率问题再优化吧
                         */
                        for (String redoSql : redoSqls) {
//                            log.info("删除语句"+redoSql);
                            Statement statement = targetConnection.createStatement();
                            statement.executeUpdate(redoSql);
                            statement.close();
                        }
                        deleteSuccessCount++;
                        break;
                }
            }
        }
    }

    private static  void doSqlSet(String redoSql, PreparedStatement preparedStatement, LinkShowVO toLink) throws SQLException {
        Matcher matcher = insertPattern.matcher(redoSql);
        Integer count = 1;
        List<String> values=new ArrayList<>();
        while (matcher.find()) {
            values.add(matcher.group());
        }
//        for (int i=0;i<values.size();i++){
//            log.info(i+"|"+values.get(i));
//        }
        preparedStatement.setInt(1, Integer.valueOf(values.get(0)));
        preparedStatement.setString(2, values.get(1));
        preparedStatement.setString(3, values.get(2));
        preparedStatement.setInt(4, Integer.valueOf(values.get(3)));
        preparedStatement.setString(5,  values.get(4));
        preparedStatement.setString(6,  values.get(5));
        preparedStatement.setString(7,  values.get(6));
        preparedStatement.setString(8,  values.get(7));
        preparedStatement.setInt(9, Integer.valueOf(values.get(8)));
        preparedStatement.setInt(10, Integer.valueOf(values.get(9)));
        preparedStatement.setInt(11, Integer.valueOf(values.get(10)));
        preparedStatement.setInt(12, Integer.valueOf(values.get(11)));
        preparedStatement.setInt(13, Integer.valueOf(values.get(12)));
        preparedStatement.setTimestamp(14,  new Timestamp((new Date().getTime())));
        preparedStatement.setTimestamp(15,  new Timestamp((new Date().getTime())));
//        preparedStatement.setInt(16,values.get(15));
        preparedStatement.setString(16, values.get(15));
        preparedStatement.addBatch();
    }

    private static void createInsertPreSQL(String s, TablePrestatementVO prestatementVO) {
        String temp = s.replaceAll("(?<=\\(')(.*?)(?=')|(?<=',')(.*?)(?=')|NULL|(?<=NULL,')(.*?)(?=')", "?").replaceAll("'", "");
        log.info("生成的insert语句为"+temp);
        prestatementVO.setInsertSQL(temp);
    }
}

