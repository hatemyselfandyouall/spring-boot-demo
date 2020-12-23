package com.wangxinenpu.springbootdemo.dataobject.dto.sitematters;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class MattersDto<T> implements Serializable {

    private static final long serialVersionUID = -172905342446394384L;

    @ApiModelProperty("事项id")
    private Long id;

    @ApiModelProperty("事项名称")
    private String name;

    @ApiModelProperty("业务类型名称")
    private String businessTypeName;

}
