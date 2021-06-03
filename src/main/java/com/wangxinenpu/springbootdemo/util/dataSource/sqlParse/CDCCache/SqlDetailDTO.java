package com.wangxinenpu.springbootdemo.util.dataSource.sqlParse.CDCCache;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SqlDetailDTO {

    private String sql;

    private String scn;
}
