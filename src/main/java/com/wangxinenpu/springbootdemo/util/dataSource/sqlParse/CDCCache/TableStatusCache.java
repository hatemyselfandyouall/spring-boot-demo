package com.wangxinenpu.springbootdemo.util.dataSource.sqlParse.CDCCache;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class TableStatusCache {

    public static  final Map<String, String> statusMap=new HashMap<>();
    @Autowired
    SQLSaver sqlSaver;

    public  void setStatus(String segName,String tableName, String status, Connection connection){
        statusMap.put(segName+"|"+tableName,status);
        log.info("表"+tableName+"进入状态"+status);
        if (MSGTYPECONSTANT.TABLE_STATUS_FULL_EXTRACT_FINAL.equals(status)){
            sqlSaver.executeSQLs(segName+"|"+tableName,connection);
        }
    }

    public static String getStatus(String tableName){
       return statusMap.get(tableName);
    }
}
