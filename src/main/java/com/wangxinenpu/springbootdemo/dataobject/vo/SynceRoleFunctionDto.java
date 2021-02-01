package com.wangxinenpu.springbootdemo.dataobject.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import star.vo.BaseVo;

@Data
public class SynceRoleFunctionDto extends BaseVo {
	
	private static final long serialVersionUID = -6658128538240390388L;
	@ApiModelProperty("天正一体化角色id")
	private String roleId ;

	@ApiModelProperty("天正一体化菜单id")
	private String resourceId ;

}
