package com.wangxinenpu.springbootdemo.dataobject.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.List;

@Data
public class SaveSysFunctionScopeForOrgsSaveVO implements Serializable {



    @ApiModelProperty("此角色的此机构对此菜单有权限")
    @Column( name="func_id")
    private List<Long> funcIds;


    @ApiModelProperty("对此角色进行权限配置")
    @Column( name="belong_role_id")
    private String belongRoleId;

    private List<Long> orgIds;
}
