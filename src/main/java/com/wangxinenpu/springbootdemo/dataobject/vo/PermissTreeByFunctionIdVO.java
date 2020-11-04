package com.wangxinenpu.springbootdemo.dataobject.vo;

import com.alibaba.fastjson.JSONArray;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = true)
public class PermissTreeByFunctionIdVO implements Serializable {

    private JSONArray orgTree;

    private List<Long> hasPermissionList;

    private List<SysOrgWithPermissionVO> sysOrgWithPermissionVOS;
}
