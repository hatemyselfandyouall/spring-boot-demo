package com.wangxinenpu.springbootdemo.dataobject.vo.OpenapiOrg;

import com.wangxinenpu.springbootdemo.dataobject.po.OpenapiOrgAuditResultEnum;
import com.wangxinenpu.springbootdemo.dataobject.po.OpenapiOrgStatusEnum;
import com.wangxinenpu.springbootdemo.dataobject.vo.root.PageVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.List;

@Data
public class OpenapiOrgListVO extends PageVO implements Serializable {

    @ApiModelProperty("机构编码")
    @Column( name="org_code")
    private String orgCode;


    @ApiModelProperty("地区id")
    @Column( name="area_id")
    private Long areaId;

    @ApiModelProperty("审核人名称")
    @Column( name="auditor_name")
    private String auditorName;

    @ApiModelProperty("创建者姓名")
    @Column( name="creator_name")
    private String creatorName;

    @ApiModelProperty("创建时间上界")
    private String createTimeBegin;

    @ApiModelProperty("创建时间下界")
    private String createTimeEnd;

    @ApiModelProperty("审核结果")
    @Column( name="audit_result")
    private List<OpenapiOrgAuditResultEnum> auditResults;

    private List<OpenapiOrgStatusEnum> statusEnumList;

    @ApiModelProperty("操作日志id")
    @Column( name="opseno")
    private Long opseno;

    @ApiModelProperty("值为1，create_time desc，值为其他 create_time asc")
    private Integer orderFlag;

    private static final long serialVersionUID = 1L;

}
