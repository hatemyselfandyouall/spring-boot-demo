package com.wangxinenpu.springbootdemo.dataobject.vo.SysOrgDepartment;

import com.wangxinenpu.springbootdemo.dataobject.vo.SysUserDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SysOrgDepartmentVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("")
    private Long id;

    @ApiModelProperty("科室名称")
    private String name;

    List<SysUserDTO> list;
}
