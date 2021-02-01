package com.wangxinenpu.springbootdemo.dataobject.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class SysOrgWithPermissionVO extends SysOrgDTO implements Serializable {

    private Integer hasPermission;

    private Boolean disabled;
}
