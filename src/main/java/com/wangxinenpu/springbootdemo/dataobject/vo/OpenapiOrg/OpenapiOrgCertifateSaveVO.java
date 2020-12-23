package com.wangxinenpu.springbootdemo.dataobject.vo.OpenapiOrg;

import com.wangxinenpu.springbootdemo.dataobject.po.OpenapiOrgStatusEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;

@Data
public class OpenapiOrgCertifateSaveVO implements Serializable {

    private static final long serialVersionUID = 1L;

    //========== properties ==========

    @Column( name="id")
    private Long id;

    @ApiModelProperty("机构id")
    @Column( name="org_id")
    private Long orgId;

    @ApiModelProperty("机构名")
    @Column( name="org_name")
    private String orgName;

    @ApiModelProperty("")
    @Column( name="org_code")
    private String orgCode;

    @ApiModelProperty("机构次序")
    @Column( name="sort_number")
    private Integer sortNumber;

    @ApiModelProperty("地区id")
    @Column( name="area_id")
    private Long areaId;

    @ApiModelProperty("地区名")
    @Column( name="area_name")
    private String areaName;

    @ApiModelProperty("ip网段")
    @Column( name="ip_segment")
    private String ipSegment;

    @ApiModelProperty("字母缩写")
    @Column( name="abbreviation")
    private String abbreviation;

    @ApiModelProperty("")
    @Column( name="app_key")
    private String appKey;

    @ApiModelProperty("")
    @Column( name="app_secret")
    private String appSecret;

    @ApiModelProperty("证书文件key")
    @Column( name="certificate_key")
    private String certificateKey;

    @ApiModelProperty("限制数量")
    @Column( name="limit_count")
    private Integer limitCount;

    @ApiModelProperty("允许访问时间-起")
    @Column( name="access_time_start")
    private Date accessTimeStart;

    @ApiModelProperty("允许访问时间-止")
    @Column( name="access_time_final")
    private Date accessTimeFinal;

    @ApiModelProperty("证书")
    @Column( name="certificate")
    private String certificate;

    @ApiModelProperty("描述")
    @Column( name="distribution")
    private String distribution;

    @Column( name="status")
    private OpenapiOrgStatusEnum status;

    @ApiModelProperty("自助机校验码")
    @Column( name="selfmachine_validate_code")
    private String selfmachineValidateCode;

    @ApiModelProperty("")
    @Column( name="creator_id")
    private Long creatorId;

    @ApiModelProperty("")
    @Column( name="creator_name")
    private String creatorName;

    @ApiModelProperty("证书编号")
    @Column( name="certificate_code")
    private String certificateCode;

}
