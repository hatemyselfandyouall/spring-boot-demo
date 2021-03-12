//package com.wangxinenpu.springbootdemo.config;
//
//import lombok.extern.slf4j.Slf4j;
//import nl.whizzkit.oracdc.CDC;
//import nl.whizzkit.oracdc.CDCUtils;
//import nl.whizzkit.oracdc.ShutdownThread;
//import nl.whizzkit.oracdc.writer.IWritable;
//import org.apache.log4j.Logger;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import java.sql.*;
//import java.util.Properties;
//import java.util.Vector;
//
//@Component
//@Slf4j
//public class CDCTestConfig {
//
//    private Connection connection;
//    private Logger logger = Logger.getLogger(CDC.class);
//    private Statement statement;
//
//
//    @PostConstruct
//    private void test() throws Exception{
////        System.out.println("test");
////        ShutdownThread shutdownThread = new ShutdownThread(this);
////        Runtime.getRuntime().addShutdownHook(shutdownThread);
//
//        Properties properties = CDCUtils.readProperties("cdc");
//
//        Vector<IWritable> iWritables = new Vector<>();
//        for (String iWritable : properties.getProperty("writers").split(","))
//            iWritables.add((IWritable) (Class.forName(iWritable.trim()).getConstructor().newInstance()));
//        log.info("开始测试");
//        Class.forName(properties.getProperty("driver"));
//        connection = DriverManager.getConnection(properties.getProperty("url"), CDCUtils.readProperties("db"));
//
//        CDCUtils.prepareNLS(connection);
//        CDCUtils.startLogMnr(connection, properties.getProperty("start"));
//
//        statement = connection.createStatement();
//        statement.setFetchSize(1);
//        statement.setQueryTimeout(0);
//
//        ResultSet resultSet = statement
//                .executeQuery(String.format(
//                        "SELECT scn,timestamp,operation,seg_owner,table_name,row_id,sql_redo FROM v$logmnr_contents WHERE table_name in (%s) AND seg_owner = %s AND operation IN (%s)",
//                        properties.getProperty("tables"),
//                        properties.getProperty("schema"),
//                        properties.getProperty("operations")
//                        )
//                );
//        try {
//            while (resultSet.next())
//                for (IWritable iWritable : iWritables)
//                    iWritable.write(resultSet);
//        } catch (SQLTimeoutException ex) {
//            logger.error(ex.getMessage());
//        }
//    }
//}
