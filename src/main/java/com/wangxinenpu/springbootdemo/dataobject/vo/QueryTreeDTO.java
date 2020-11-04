package com.wangxinenpu.springbootdemo.dataobject.vo;

import star.vo.BaseVo;

import java.util.List;

public class QueryTreeDTO extends BaseVo {
   private List<SysRoleDTO> queryTreeList;

    public List<SysRoleDTO> getQueryTreeList() {
        return queryTreeList;
    }

    public void setQueryTreeList(List<SysRoleDTO> queryTreeList) {
        this.queryTreeList = queryTreeList;
    }
}
