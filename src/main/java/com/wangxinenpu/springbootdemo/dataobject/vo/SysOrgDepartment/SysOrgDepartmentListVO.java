package com.wangxinenpu.springbootdemo.dataobject.vo.SysOrgDepartment;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SysOrgDepartmentListVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("机构id")
    private Long orgId;

    @ApiModelProperty("科室名称")
    private String name;

    @ApiModelProperty("天正对应科室内码")
    private String uuid;

}
