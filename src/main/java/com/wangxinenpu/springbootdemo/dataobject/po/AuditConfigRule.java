package com.wangxinenpu.springbootdemo.dataobject.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * 
 * 审核权限配置规则表
 */
@Data
public class AuditConfigRule implements Serializable {
	private static final transient long serialVersionUID = 1L;

	@Id
	@Column( name="id")
	@ApiModelProperty("id")
	private Long id;

	@Column( name="fun_id")
	@ApiModelProperty("事项id")
	private Long funId;

	@Column( name="fun_name")
	@ApiModelProperty("事项名称")
	private String funName;

	@Column( name="is_leading")
	@ApiModelProperty("是否主导 0：是 1：否")
	private String isLeading;

	@Column( name="au_flag")
	@ApiModelProperty("审核类型，0-自动审核，1-手动配置")
	private String auFlag;

	@Column( name="fun_type")
	@ApiModelProperty("菜单类型 2：统征经办系统 3：统征工伤系统 4：统征网报系统  5：城乡系统")
	private String funType;

	@Column( name="rule_struts")
	@ApiModelProperty("规则状态 1：启动 2：停用")
	private String ruleStruts;

	@Column( name="is_bus")
	@ApiModelProperty("是否接入流程 1：是 2：否")
	private String isBus;

	@Column( name="fun_level")
	@ApiModelProperty("事项级别 1：省级 2：市级 3：区级")
	private String funLevel;

	@Column( name="region_code")
	@ApiModelProperty("区域编码")
	private String regionCode;
}