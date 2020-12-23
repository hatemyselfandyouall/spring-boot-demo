 
package com.wangxinenpu.springbootdemo.dataobject.po;

import com.wangxinenpu.springbootdemo.dataobject.vo.SysCheckInformationDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Data
@Accessors(chain = true)
public class OpenapiOrg implements Serializable {


	//========== properties ==========

	@Id
	@GeneratedValue(generator="JDBC")
    @ApiModelProperty("")
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

    @ApiModelProperty("status")
    @Column( name="status")
    private OpenapiOrgStatusEnum status;

    @ApiModelProperty("自助机检测码")
    @Column( name="selfmachine_validate_code")
    private String selfmachineValidateCode;

    @ApiModelProperty("")
    @Column( name="create_time")
    private Date createTime;

    @ApiModelProperty("")
    @Column( name="creator_id")
    private Long creatorId;

    @ApiModelProperty("")
    @Column( name="creator_name")
    private String creatorName;

    @ApiModelProperty("")
    @Column( name="modify_time")
    private Date modifyTime;

    @ApiModelProperty("")
    @Column( name="modify_id")
    private Long modifyId;

    @ApiModelProperty("")
    @Column( name="modify_name")
    private String modifyName;

    @ApiModelProperty("证书编号")
    @Column( name="certificate_code")
    private String certificateCode;

    @ApiModelProperty("审核者id")
    @Column( name="auditor_id")
    private Long auditorId;

    @ApiModelProperty("审核者名字")
    @Column( name="auditor_name")
    private String auditorName;

    @ApiModelProperty("操作日志id")
    @Column( name="opseno")
    private Long opseno;

    @ApiModelProperty("审核备注")
    @Column( name="audit_remark")
    private String auditRemark;

    @ApiModelProperty("审核拒绝原因")
    @Column( name="audit_refuse_reason")
    private String auditRefuseReason;

    @ApiModelProperty("审核结果")
    @Column( name="audit_result")
    private OpenapiOrgAuditResultEnum auditResult;

    @Transient
    @ApiModelProperty("菜单id")
    @Column( name="functionId")
    private Long functionId;

    @Transient
    @ApiModelProperty("审核数据")
    List<SysCheckInformationDTO> list;

}
