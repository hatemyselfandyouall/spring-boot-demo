package com.wangxinenpu.springbootdemo.dataobject.vo.OpenapiOrg;

import com.wangxinenpu.springbootdemo.dataobject.po.OpenapiOrgStatusEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

@Data
public class OpenapiOrgSaveVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("")
    @Column( name="id")
    private Long id;

    @ApiModelProperty("机构编码")
    @Column( name="org_code")
    private String orgCode;

    @ApiModelProperty("机构名")
    @Column( name="org_name")
    private String orgName;

    @ApiModelProperty("地区id")
    @Column( name="area_id")
    private Long areaId;

    @ApiModelProperty("地区名")
    @Column( name="area_name")
    private String areaName;

    @ApiModelProperty("ip网段")
    @Column( name="ip_segment")
    private String ipSegment;

    @ApiModelProperty("限制数量")
    @Column( name="limit_count")
    private String limitCount;

    @ApiModelProperty("允许访问时间-起")
    @Column( name="access_time_start")
    private Date accessTimeStart;

    @ApiModelProperty("允许访问时间-止")
    @Column( name="access_time_final")
    private Date accessTimeFinal;

    @ApiModelProperty("描述")
    @Column( name="distribution")
    private String distribution;

    @ApiModelProperty("字母缩写")
    @Column( name="abbreviation")
    private String abbreviation;

    @ApiModelProperty("自助机校验码")
    @Column( name="selfmachine_validate_code")
    private String selfmachineValidateCode;

    @Column( name="status")
    @ApiModelProperty("自助机自己")
    private OpenapiOrgStatusEnum status;

    @ApiModelProperty("操作日志id")
    @Column( name="opseno")
    private Long opseno;

    @Transient
    @ApiModelProperty("菜单id")
    @Column( name="functionId")
    private Long functionId;

}
