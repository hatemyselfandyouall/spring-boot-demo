package com.wangxinenpu.springbootdemo.util.datatransfer;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class LogFile {

    private String fileName;

    private Long startSCN;

    private Long endSCN;
}
