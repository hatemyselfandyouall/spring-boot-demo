package com.wangxinenpu.springbootdemo.util.dataSource.sqlParse;

import lombok.Data;

import java.util.List;

@Data
public class SQLParseDTO {
    private String tableName;

    private List<String> columns;

    private List<String> values;
}
