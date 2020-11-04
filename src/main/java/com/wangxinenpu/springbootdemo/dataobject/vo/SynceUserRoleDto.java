package com.wangxinenpu.springbootdemo.dataobject.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import star.vo.BaseVo;

@Data
public class SynceUserRoleDto extends BaseVo {
	
	private static final long serialVersionUID = -6658128538240390388L;

	@ApiModelProperty("天正一体化用户id")
	private String uid;

	@ApiModelProperty("天正一体化角色id列表")
	private String roleCodes ;

	@ApiModelProperty("天正一体化机构id")
	private Long orgId;


}
