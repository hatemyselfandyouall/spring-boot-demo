package com.wangxinenpu.springbootdemo.dataobject.vo.OpenapiOrgShortname;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Data
public class OpenapiOrgShortnameSaveVO implements Serializable {

    private static final long serialVersionUID = 1L;

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
    @Column( name="modifier_name")
    private String modifierName;


}
