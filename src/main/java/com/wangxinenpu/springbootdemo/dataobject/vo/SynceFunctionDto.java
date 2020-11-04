package com.wangxinenpu.springbootdemo.dataobject.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import star.vo.BaseVo;

@Data
public class SynceFunctionDto extends BaseVo {
	
	private static final long serialVersionUID = -6658128538240390388L;
	@ApiModelProperty("天正一体化菜单id")
	private String tzId ;

	@ApiModelProperty("父节点id")
	private String tzParentId ;

	@ApiModelProperty("菜单名称")
	private String title;
	
	@ApiModelProperty("菜单类型")
	private String nodeType;
	
	@ApiModelProperty("菜单地址")
	private String location;
	
	@ApiModelProperty("菜单描述")
	private String description;
	
	@ApiModelProperty("排序号")
	private String funOrder;
	
	@ApiModelProperty("所属系统")
	private String clientCode;


}
