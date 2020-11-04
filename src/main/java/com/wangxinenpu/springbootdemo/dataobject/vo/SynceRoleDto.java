package com.wangxinenpu.springbootdemo.dataobject.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import star.vo.BaseVo;

@Data
public class SynceRoleDto extends BaseVo {
	
	private static final long serialVersionUID = -6658128538240390388L;
	@ApiModelProperty("天正一体化角色id")
	private String roleCode ;

	@ApiModelProperty("天正一体化角色名称")
	private String name ;

	@ApiModelProperty("天正一体化机构id")
	private Long orgId;


}
