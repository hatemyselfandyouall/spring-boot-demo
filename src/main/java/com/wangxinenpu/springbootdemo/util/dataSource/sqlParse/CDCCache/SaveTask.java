package com.wangxinenpu.springbootdemo.util.dataSource.sqlParse.CDCCache;

import lombok.Data;

@Data
public class SaveTask  {

    private String scn;
    private String sql;
    private String time;
    private String segOwner;
    private String tableName;
    public SaveTask(String segOwner,String tableName,String scn, String sql,String time){
        this.scn=scn;
        this.sql=sql;
        this.time=time;
        this.segOwner=segOwner;
        this.tableName=tableName;
    }

}
