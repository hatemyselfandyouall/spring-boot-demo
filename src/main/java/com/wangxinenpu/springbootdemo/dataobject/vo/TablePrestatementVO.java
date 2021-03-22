package com.wangxinenpu.springbootdemo.dataobject.vo;

import lombok.Data;

import java.sql.PreparedStatement;

@Data
public class TablePrestatementVO {

    private String tableName;

    private String insertSQL;

    private String updateSQL;

    private String deleteSQL;


}
