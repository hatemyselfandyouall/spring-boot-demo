package com.wangxinenpu.springbootdemo.dataobject.vo.SiteMatters;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class MattersUpdateDto<T> implements Serializable {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty("事项列表id")
    private List<Long> ids;

    @ApiModelProperty("事项类型id")
    private Long iconCategoryId;

    @ApiModelProperty("业务类型 1医保，2社保")
    private Integer bussType;
}
