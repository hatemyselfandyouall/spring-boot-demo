package com.wangxinenpu.springbootdemo.dataobject.vo.OpenapiOrg;

import com.wangxinenpu.springbootdemo.dataobject.po.OpenapiOrgStatusEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

@Data
public class OpenapiOrgAuditVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("接口id")
    @Column( name="id")
    private Long id;

    @ApiModelProperty("状态")
    @Column( name="status")
    private OpenapiOrgStatusEnum status;

    @ApiModelProperty("审核备注")
    @Column( name="audit_remark")
    private String auditRemark;

    @ApiModelProperty("审核拒绝原因")
    @Column( name="audit_refuse_reason")
    private String auditRefuseReason;


}
