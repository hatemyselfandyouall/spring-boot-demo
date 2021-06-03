package com.wangxinenpu.springbootdemo.util.datatransfer;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class StatementExecuteFailDTO {
    private String operateType;

    private String failStatement;

    private String fileMsg;
}
