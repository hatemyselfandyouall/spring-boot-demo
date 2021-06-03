package com.wangxinenpu.springbootdemo.util.dataSource.sqlParse.CDCCache;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

@Data
@Slf4j
public class TableStatusCache {

    public static  final Map<String, String> statusMap=new HashMap<>();

    public static void setStatus(String segName,String tableName, String status, Connection connection){
        statusMap.put(segName+"|"+tableName,status);
        log.info("表"+tableName+"进入状态"+status);
        if (MSGTYPECONSTANT.TABLE_STATUS_FULL_EXTRACT_FINAL.equals(status)){
            SQLSaver.executeSQLs(segName+"|"+tableName,connection);
        }
    }

    public static String getStatus(String tableName){
       return statusMap.get(tableName);
    }
}
