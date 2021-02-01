 
package com.wangxinenpu.springbootdemo.dataobject.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Data
@Accessors(chain = true)
public class SysRoleInnertranFunction implements Serializable {


	//========== properties ==========

	@Id
	@GeneratedValue(generator="JDBC")
    @ApiModelProperty("关联id")
    @Column( name="relation_id")
    private String relationId;

    @ApiModelProperty("角色id")
    @Column( name="role_id")
    private String roleId;

    @ApiModelProperty("功能id")
    @Column( name="function_id")
    private Long functionId;


    @ApiModelProperty("0未删除1已删除")
    @Column( name="is_delete")
    private Integer isDelete;

    @ApiModelProperty("选择状态 0：全选 1：半选")
    @Column( name="select_state")
    private String selectState;

    @ApiModelProperty("创建时间")
    @Column( name="create_time")
    private Date createTime;

    @ApiModelProperty("修改时间")
    @Column( name="modify_time")
    private Date modifyTime;




}
