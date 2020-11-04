package com.wangxinenpu.springbootdemo.dataobject.vo.SysOrgDepartment;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SysOrgDepartmentDeleteVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("科室id")
    private Long id;

    @ApiModelProperty("机构id")
    private Long orgId;

    @ApiModelProperty("科室对应的内码")
    private String uuid;
}
