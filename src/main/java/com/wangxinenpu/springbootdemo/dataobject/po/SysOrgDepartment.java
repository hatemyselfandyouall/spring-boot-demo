 
package com.wangxinenpu.springbootdemo.dataobject.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Data
public class SysOrgDepartment implements Serializable {


	//========== properties ==========

	@Id
	@GeneratedValue(generator="JDBC")
    @ApiModelProperty("")
    @Column( name="id")
    private Long id;

    @ApiModelProperty("科室名称")
    @Column( name="name")
    private String name;

    @ApiModelProperty("科室代码")
    @Column( name="code")
    private String code;

    @ApiModelProperty("所属机构")
    @Column( name="org_id")
    private Long orgId;

    @ApiModelProperty("联系人")
    @Column( name="linkman")
    private String linkman;

    @ApiModelProperty("科室负责人")
    @Column( name="leader")
    private String leader;

    @ApiModelProperty("科室简称")
    @Column( name="short_name")
    private String shortName;

    @ApiModelProperty("科室描述")
    @Column( name="depart_describe")
    private String departDescribe;

    @ApiModelProperty("创建时间")
    @Column( name="create_time")
    private Date createTime;

    @ApiModelProperty("修改时间")
    @Column( name="modify_time")
    private Date modifyTime;

    @ApiModelProperty("天正一体化科室id")
    @Column( name="uuid")
    private String uuid;




}
