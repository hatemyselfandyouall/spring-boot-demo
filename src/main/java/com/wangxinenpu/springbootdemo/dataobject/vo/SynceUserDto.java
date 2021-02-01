package com.wangxinenpu.springbootdemo.dataobject.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import star.vo.BaseVo;

@Data
public class SynceUserDto extends BaseVo {
	
	private static final long serialVersionUID = -6658128538240390388L;

	@ApiModelProperty("天正一体化id")
	private String ythUserId ;

	@ApiModelProperty("账号")
	private String logonName ;

	@ApiModelProperty("用户名称")
	private String displayName;

	@ApiModelProperty("证件号码")
	private String cardId;

	@ApiModelProperty("区域编码")
	private Long areaId;

	@ApiModelProperty("机构id")
	private Long orgId;

	@ApiModelProperty("用户状态:1正常、2锁定、3注销")
	private String userState;

	@ApiModelProperty("用户类型：1超级管理员，2行政区管理员，3机构管理员，4业务操作员")
	private String userType;

	@ApiModelProperty("联系电话")
	private String tel;

	@ApiModelProperty("手机")
	private String mobile;

	@ApiModelProperty("电子邮箱")
	private String email;

	@ApiModelProperty("通讯地址")
	private String userAddr;

	@ApiModelProperty("天正机构内码")
	private String uuid;

	@ApiModelProperty("0：业务管理员1：机构管理员")
	private Integer ythUserType;

	@ApiModelProperty("机构类型:2机构3科室")
	private String orgType;

}
