package com.wangxinenpu.springbootdemo.util.dataSource.sqlParse;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class DeleteSQLParseDTO {

    private String tableName;

    private List<String> columns;

    private List<String> values;

}