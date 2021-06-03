package com.wangxinenpu.springbootdemo.util.datatransfer;

import lombok.Data;

@Data
public class TablePrestatementVO {

    private String tableName;

    private String insertSQL;

    private String updateSQL;

    private String deleteSQL;


}
