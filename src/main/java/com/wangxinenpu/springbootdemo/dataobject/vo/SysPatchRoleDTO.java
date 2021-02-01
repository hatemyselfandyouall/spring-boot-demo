package com.wangxinenpu.springbootdemo.dataobject.vo;

import lombok.Data;
import star.vo.BaseVo;

import java.util.List;

@Data
public class SysPatchRoleDTO extends BaseVo {

    private List<String> roleIdList;
    private List<SysRoleFunctionDTO> treeInfo;

}
