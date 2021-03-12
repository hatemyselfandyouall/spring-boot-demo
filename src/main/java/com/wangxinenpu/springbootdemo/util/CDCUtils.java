package com.wangxinenpu.springbootdemo.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

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
                callStirng = "begin DBMS_LOGMNR.START_LOGMNR( STARTTIME => '" + timestamp + "', OPTIONS => DBMS_LOGMNR.DICT_FROM_ONLINE_CATALOG+ DBMS_LOGMNR.CONTINUOUS_MINE); end;";
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

    public static Properties readProperties(String name) throws IOException {
        Properties properties = new Properties();
        properties.load(Class.class.getResourceAsStream("/src/main/resources/" + name + ".properties"));
        return properties;
    }

}
