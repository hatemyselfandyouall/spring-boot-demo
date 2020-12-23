 
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
public class OpenapiOrgShortname implements Serializable {


	//========== properties ==========

	@Id
	@GeneratedValue(generator="JDBC")
    @ApiModelProperty("")
    @Column( name="id")
    private Long id;

    @ApiModelProperty("")
    @Column( name="org_name")
    private String orgName;

    @ApiModelProperty("")
    @Column( name="org_code")
    private String orgCode;


    @ApiModelProperty("")
    @Column( name="area_name")
    private String areaName;

    @ApiModelProperty("")
    @Column( name="area_code")
    private String areaCode;


    @ApiModelProperty("")
    @Column( name="short_name")
    private String shortName;

    @ApiModelProperty("")
    @Column( name="create_time")
    private Date createTime;

    @ApiModelProperty("")
    @Column( name="modify_time")
    private Date modifyTime;

    @ApiModelProperty("")
    @Column( name="modifier_name")
    private String modifierName;




}
