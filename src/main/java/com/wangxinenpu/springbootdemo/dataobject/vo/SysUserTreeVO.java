package com.wangxinenpu.springbootdemo.dataobject.vo;

import com.alibaba.fastjson.JSONArray;
import com.wangxinenpu.springbootdemo.dataobject.vo.SysOrgDepartment.SysOrgDepartmentVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SysUserTreeVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("有科室用户list")
    List<SysOrgDepartmentVO> userListByDepartment;

    @ApiModelProperty("无科室用户list")
    List<SysUserDTO> userListNoDepartment;

    @ApiModelProperty("有关联关系用户list")
    List<SysUserRoleDTO> userAndRoleList;
    
    @ApiModelProperty("机构用户树结构")
    JSONArray userListTreeArray;
}
