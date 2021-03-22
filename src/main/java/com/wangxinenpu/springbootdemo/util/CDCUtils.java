package com.wangxinenpu.springbootdemo.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Slf4j
public class CDCUtils {

    public static void prepareNLS(Connection connection) throws SQLException {
        if (connection != null)
            connection
                    .createStatement()
                    .execute("ALTER SESSION SET NLS_DATE_FORMAT = 'DD-MON-YYYY HH24:MI:SS'");
    }

    public static void startLogMnr(Connection connection, String timestamp) throws SQLException {
        try {
            if (connection != null) {
                String callStirng = "alter session set nls_date_language='american'";
                System.out.println(callStirng);
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
        }catch (Exception e){
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
        String getArchivedFiles="select recid, name, first_time from v$archived_log where 1=1";
            if (!StringUtils.isEmpty(startTime)){
                getArchivedFiles+="and first_time > to_date('"+ startTime+"', 'yyyy-mm-dd hh24:mi:ss')";
            }
            if (!StringUtils.isEmpty(endTime) ){
                getArchivedFiles+="and first_time < to_date('"+ endTime+"', 'yyyy-mm-dd hh24:mi:ss')";
            }
            log.info(getArchivedFiles);
            List<String> files=new ArrayList<>();
        ResultSet resultSet=connection.createStatement().executeQuery(getArchivedFiles);
            while (resultSet.next()){
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

    public static void startLogMnrWithArchivedFiles(Connection connection, List<String> archivedFiles) throws SQLException {
        String callStirng = "alter session set nls_date_language='american'";
        System.out.println(callStirng);
        connection
                .prepareCall(callStirng)
                .execute();
        StringBuilder stringBuilder=new StringBuilder("BEGIN ");
        for (int i=0;i<archivedFiles.size();i++){
            if (i==0){
                stringBuilder.append("dbms_logmnr.add_logfile(logfilename=>'").append(archivedFiles.get(0)).append("',options=>dbms_logmnr.NEW);");
            }else {
                stringBuilder.append("dbms_logmnr.add_logfile(logfilename=>'").append(archivedFiles.get(i)).append("',options=>dbms_logmnr.ADDFILE);");
            }
        }
        stringBuilder.append("dbms_logmnr.START_LOGMNR(  OPTIONS => DBMS_LOGMNR.DICT_FROM_ONLINE_CATALOG +DBMS_LOGMNR.SKIP_CORRUPTION+ DBMS_LOGMNR.COMMITTED_DATA_ONLY+ DBMS_LOGMNR.NO_ROWID_IN_STMT);");
        stringBuilder.append("END;");
        callStirng=stringBuilder.toString();
        System.out.println(callStirng);
        connection
                .prepareCall(callStirng)
                .execute();
    }

    public static List<String> getCurrentFiles(Connection connection) throws SQLException {
        List<String> files=new ArrayList<>();
        ResultSet resultSet=connection.createStatement().executeQuery("select l.STATUS,s.MEMBER from v$log l,v$logfile s where l.GROUP# = s.GROUP# and l.STATUS='CURRENT'");
        while (resultSet.next()){
            files.add(resultSet.getString("MEMBER"));
        }
        resultSet.close();
        return files;
    }
}
