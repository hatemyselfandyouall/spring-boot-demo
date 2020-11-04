 
package com.wangxinenpu.springbootdemo.dataobject.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;


@Data
@Accessors(chain = true)
public class SysFunctionScope implements Serializable {


	//========== properties ==========

	@Id
	@GeneratedValue(generator="JDBC")
    @ApiModelProperty("")
    @Column( name="id")
    private Long id;

    @ApiModelProperty("此角色的此机构对此菜单有权限")
    @Column( name="func_id")
    private Long funcId;

    @ApiModelProperty("此角色的此机构对此菜单有权限")
    @Column( name="org_id")
    private Long orgId;

    @ApiModelProperty("对此角色进行权限配置")
    @Column( name="belong_role_id")
    private String belongRoleId;

    @ApiModelProperty("0未删除1已删除")
    @Column( name="is_delete")
    private Integer isDelete;

    @ApiModelProperty("")
    @Column( name="create_time")
    private Date createTime;




}
