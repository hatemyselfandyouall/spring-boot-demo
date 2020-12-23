package com.wangxinenpu.springbootdemo.dataobject.dto.sitematters;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class MattersAreaDto<T> implements Serializable {

    private static final long serialVersionUID = -172905342446394384L;

    @ApiModelProperty("事项区域关联表id")
    private Long id;

    @ApiModelProperty("区域id")
    private List<Long> areaIds;

    @ApiModelProperty("账号类型id")
    private Long accountTypeId;

    @ApiModelProperty("网上办图标id")
    private Long blockPcId;

    @ApiModelProperty("掌上办图标id")
    private Long blockAppId;

    @ApiModelProperty("事项id")
    private Long mattersId;
}
