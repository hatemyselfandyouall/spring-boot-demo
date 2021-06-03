package com.wangxinenpu.springbootdemo.util.dataSource.sqlParse.CDCCache;

import lombok.Data;

@Data
public class SaveTask  {

    private Long scn;
    private String sql;
    public SaveTask(Long scn, String sql){
        this.scn=scn;
        this.sql=sql;
    }

}
