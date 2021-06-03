package com.wangxinenpu.springbootdemo.dataobject.vo.LinkTransferTask;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class ExtraSearchWordVO implements Serializable {

    @ApiModelProperty("字段名")
    private String columnName;

    @ApiModelProperty("操作符")
    private ExtraSearchEnum extraSearchEnum;

    @ApiModelProperty("查询值")
    private String value;

}
