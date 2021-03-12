package com.wangxinenpu.springbootdemo.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.wangxinenpu.springbootdemo.util.CDCUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

@RestController
@RequestMapping(value = "cdcTest")
@Slf4j
public class CDCTestController {

    @Value("${cdc.url}")
    private String url;

    @Value("${cdc.userName}")
    private String userName;

    @Value("${cdc.passWord}")
    private String passWord;

    @Value("${cdc.tables}")
    private String tables;

    @Value("${cdc.schema}")
    private String schema;

    @Value("${cdc.driver}")
    private String driver;

    @Value("${cdc.startTime}")
    private String startTime;

    @Value("${cdc.operations}")
    private String operations;

    private Connection connection;
//    private Logger logger = Logger.getLogger(CDC.class);
    private Statement statement;

    @RequestMapping("testConnect")
        public String testConnect(){
        try {
//            Properties properties = CDCUtils.readProperties("cdc");

//            Vector<IWritable> iWritables = new Vector<>();
//            for (String iWritable : properties.getProperty("writers").split(","))
//                iWritables.add((IWritable) (Class.forName(iWritable.trim()).getConstructor().newInstance()));
            log.info("开始测试");
            Class.forName(driver);
            connection = DriverManager.getConnection(url,userName,passWord);

            CDCUtils.prepareNLS(connection);
            CDCUtils.startLogMnr(connection, startTime);

            statement = connection.createStatement();
            statement.setFetchSize(1);
            statement.setQueryTimeout(0);

            String queryString=String.format(
                    "SELECT scn,timestamp,operation,seg_owner,table_name,row_id,sql_redo FROM v$logmnr_contents WHERE table_name in (%s) AND seg_owner = %s AND operation IN (%s)",
                    tables,
                    schema,
                    operations
            );
            log.info("调用String为"+queryString);
            ResultSet resultSet = statement
                    .executeQuery(queryString
                    );
            log.info("进入循环");
            while (resultSet.next()) {
                    log.info("循环执行");
//                    for (IWritable iWritable : iWritables)
//                        iWritable.write(resultSet);
                    log.info("1");
                    log.info(JSONObject.toJSONString(resultSet));
                }
        }catch (Exception e){
            log.error("测试cdc异常",e);
        }
        return "hello CDC";
    }
}

