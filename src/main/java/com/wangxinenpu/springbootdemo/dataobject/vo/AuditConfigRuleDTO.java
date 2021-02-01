package com.wangxinenpu.springbootdemo.dataobject.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import star.vo.BaseVo;


/**
 * 
 * 审核权限规则配置表
 */
@Data
public class AuditConfigRuleDTO extends BaseVo {

	@ApiModelProperty("id")
	private Long id;

	@ApiModelProperty("事项id")
	private Long funId;

	@ApiModelProperty("事项名称")
	private String funName;

	@ApiModelProperty("是否主导 0：是 1：否")
	private String isLeading;

	@ApiModelProperty("是否主导描述")
	private String isLeadingDsc;

	@ApiModelProperty("审核类型，0-自动审核，1-手动配置")
	private String auFlag;

	@ApiModelProperty("审核类型描述")
	private String auFlagDsc;

	@ApiModelProperty("菜单类型 2：统征经办系统 3：统征工伤系统 4：统征网报系统  5：城乡系统")
	private String funType;

	@ApiModelProperty("菜单类型描述")
	private String funTypeDsc;

	@ApiModelProperty("规则状态 1：启动 2：停用")
	private String ruleStruts;

	@ApiModelProperty("规则状态描述")
	private String ruleStrutsDsc;

	@ApiModelProperty("是否接入流程 1：是 2：否")
	private String isBus;

	@ApiModelProperty("是否接入流程描述")
	private String isBusDsc;

	@ApiModelProperty("事项级别 1：省级 2：市级 3：区级")
	private String funLevel;

	@ApiModelProperty("事项级别描述")
	private String funLevelDsc;

	@ApiModelProperty("区域编码")
	private String regionCode;

	@ApiModelProperty("区域名称")
	private String areaName;

}