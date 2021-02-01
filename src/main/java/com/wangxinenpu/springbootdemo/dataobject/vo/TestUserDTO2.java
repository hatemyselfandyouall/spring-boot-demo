package com.wangxinenpu.springbootdemo.dataobject.vo;

import com.wangxinenpu.springbootdemo.dataobject.vo.root.LogBaseVo;
import star.util.validate.ValidationParam;
import star.util.validate.ValidationType;

import java.util.Date;
import java.util.List;

/**
 * 
 * 系统用户表
 * @author haoxz11MyBatis 
 * @created Thu Mar 21 14:10:27 CST 2019
 * @version $Id: DefaultCommentGenerator.java,v 1.1 2013/10/28 07:59:58 haoxz11 Exp $
 * @haoxz11MyBatis
 */
public class TestUserDTO2 extends LogBaseVo {
	transient private static final long serialVersionUID = -1L;

	/**
	 * 字段：用户ID
	 */
	private String id;

	/**
	 * 字段：登录名
	 */
	@ValidationParam(type = ValidationType.NotBlank, msg = "登录名不能为空")
	private String logonName;

	/**
	 * 字段：登录密码
	 */
	@ValidationParam(type = ValidationType.NotBlank, msg = "密码不能为空")
	private String passwd;

	/**
	 * 字段：显示名
	 *
	 * @haoxz11MyBatis
	 */
	@ValidationParam(type = ValidationType.NotBlank, msg = "显示名不能为空")
	private String displayName;

	/**
	 * 字段：区域id
	 */
	private Long areaId;

	/**
	 * 字段：机构id
	 */
	private Long orgId;

	/**
	 * 字段：用户状态:1正常、2锁定、3注销
	 */
	private String userState;

	/**
	 * 字段：1超级管理员，2行政区管理员，3机构管理员，4业务操作员
	 */
	private String userType;

	/**
	 * 字段：证件类型:1身份证、2军官证、3户口本、4护照、5其他
	 */
	private String cardType;

	/**
	 * 字段：证件号码
	 */
	private String cardId;

	/**
	 * 字段：联系电话
	 */
	private String tel;

	/**
	 * 字段：手机
	 */
	private String mobile;

	/**
	 * 字段：电子邮箱
	 */
	private String email;

	/**
	 * 字段：通讯地址
	 */
	private String userAddr;

	/**
	 * 字段：备注
	 */
	private String remark;

	/**
	 * 字段：创建人id
	 */
	private String creatorId;

	/**
	 * 字段：用户锁定时间
	 */
	private Date lockTime;

	/**
	 * 字段：用户解锁时间
	 */
	private Date unlockTime;

	/**
	 * 字段：用户锁定原因
	 */
	private String lockReason;

	/**
	 * 字段：用户过期时间
	 */
	private Date userExpireDate;

	/**
	 * 字段：用户连续登录失败次数
	 */
	private Integer failNum;

	/**
	 * 字段：密码过期策略：1系统配置周期，2永不过期，3指定日期
	 */
	private String pwExpireType;

	/**
	 * 字段：密码过期时间
	 */
	private Date pwExpireDate;

	/**
	 * 字段：密码最近修改时间
	 */
	private Date pwEditDate;

	/**
	 * 字段：用户签到状态 1到 2退
	 */
	private String signsTate;

	/**
	 * 字段：所属部门
	 */
	private String department;

	/**
	 * 字段：创建日期
	 */
	private Date createTime;

	/**
	 * 字段：修改时间
	 */
	private Date modifyTime;
	
	
	/**
	 * 
	 */
    List<SysUserRoleDTO> sysUserRoleList;
	
	public List<SysUserRoleDTO> getSysUserRoleList() {
		return sysUserRoleList;
	}

	public void setSysUserRoleList(List<SysUserRoleDTO> sysUserRoleList) {
		this.sysUserRoleList = sysUserRoleList;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public TestUserDTO2() {
		super();
	}

	public TestUserDTO2(String logonName, String displayName) {
		super();
		this.logonName = logonName;
		this.displayName = displayName;
		
	}
	
	
	

	/**
	 * 读取：用户ID
	 *
	 * @return sys_user.id
	 *
	 * @haoxz11MyBatis
	 */
	public String getId() {
		return id;
	}

	/**
	 * 设置：用户ID
	 *
	 * @param id sys_user.id
	 *
	 * @haoxz11MyBatis
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 读取：登录名
	 *
	 * @return sys_user.logon_name
	 *
	 * @haoxz11MyBatis
	 */
	public String getLogonName() {
		return logonName;
	}

	/**
	 * 设置：登录名
	 *
	 * @param logonName sys_user.logon_name
	 *
	 * @haoxz11MyBatis
	 */
	public void setLogonName(String logonName) {
		this.logonName = logonName;
	}

	/**
	 * 读取：登录密码
	 *
	 * @return sys_user.passwd
	 *
	 * @haoxz11MyBatis
	 */
	public String getPasswd() {
		return passwd;
	}

	/**
	 * 设置：登录密码
	 *
	 * @param passwd sys_user.passwd
	 *
	 * @haoxz11MyBatis
	 */
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	/**
	 * 读取：显示名
	 *
	 * @return sys_user.display_name
	 *
	 * @haoxz11MyBatis
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * 设置：显示名
	 *
	 * @param displayName sys_user.display_name
	 *
	 * @haoxz11MyBatis
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * 读取：区域id
	 *
	 * @return sys_user.area_id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getAreaId() {
		return areaId;
	}

	/**
	 * 设置：区域id
	 *
	 * @param areaId sys_user.area_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}

	/**
	 * 读取：机构id
	 *
	 * @return sys_user.org_id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getOrgId() {
		return orgId;
	}

	/**
	 * 设置：机构id
	 *
	 * @param orgId sys_user.org_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	/**
	 * 读取：用户状态:1正常、2锁定、3注销
	 *
	 * @return sys_user.user_state
	 *
	 * @haoxz11MyBatis
	 */
	public String getUserState() {
		return userState;
	}

	/**
	 * 设置：用户状态:1正常、2锁定、3注销
	 *
	 * @param userState sys_user.user_state
	 *
	 * @haoxz11MyBatis
	 */
	public void setUserState(String userState) {
		this.userState = userState;
	}

	/**
	 * 读取：1超级管理员，2行政区管理员，3机构管理员，4业务操作员
	 *
	 * @return sys_user.user_type
	 *
	 * @haoxz11MyBatis
	 */
	public String getUserType() {
		return userType;
	}

	/**
	 * 设置：1超级管理员，2行政区管理员，3机构管理员，4业务操作员
	 *
	 * @param userType sys_user.user_type
	 *
	 * @haoxz11MyBatis
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}

	/**
	 * 读取：证件类型:1身份证、2军官证、3户口本、4护照、5其他
	 *
	 * @return sys_user.card_type
	 *
	 * @haoxz11MyBatis
	 */
	public String getCardType() {
		return cardType;
	}

	/**
	 * 设置：证件类型:1身份证、2军官证、3户口本、4护照、5其他
	 *
	 * @param cardType sys_user.card_type
	 *
	 * @haoxz11MyBatis
	 */
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	/**
	 * 读取：证件号码
	 *
	 * @return sys_user.card_id
	 *
	 * @haoxz11MyBatis
	 */
	public String getCardId() {
		return cardId;
	}

	/**
	 * 设置：证件号码
	 *
	 * @param cardId sys_user.card_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	/**
	 * 读取：联系电话
	 *
	 * @return sys_user.tel
	 *
	 * @haoxz11MyBatis
	 */
	public String getTel() {
		return tel;
	}

	/**
	 * 设置：联系电话
	 *
	 * @param tel sys_user.tel
	 *
	 * @haoxz11MyBatis
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}

	/**
	 * 读取：手机
	 *
	 * @return sys_user.mobile
	 *
	 * @haoxz11MyBatis
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * 设置：手机
	 *
	 * @param mobile sys_user.mobile
	 *
	 * @haoxz11MyBatis
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * 读取：电子邮箱
	 *
	 * @return sys_user.email
	 *
	 * @haoxz11MyBatis
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * 设置：电子邮箱
	 *
	 * @param email sys_user.email
	 *
	 * @haoxz11MyBatis
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * 读取：通讯地址
	 *
	 * @return sys_user.user_addr
	 *
	 * @haoxz11MyBatis
	 */
	public String getUserAddr() {
		return userAddr;
	}

	/**
	 * 设置：通讯地址
	 *
	 * @param userAddr sys_user.user_addr
	 *
	 * @haoxz11MyBatis
	 */
	public void setUserAddr(String userAddr) {
		this.userAddr = userAddr;
	}

	/**
	 * 读取：备注
	 *
	 * @return sys_user.remark
	 *
	 * @haoxz11MyBatis
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * 设置：备注
	 *
	 * @param remark sys_user.remark
	 *
	 * @haoxz11MyBatis
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 读取：创建人id
	 *
	 * @return sys_user.creator_id
	 *
	 * @haoxz11MyBatis
	 */
	public String getCreatorId() {
		return creatorId;
	}

	/**
	 * 设置：创建人id
	 *
	 * @param creatorId sys_user.creator_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	/**
	 * 读取：用户锁定时间
	 *
	 * @return sys_user.lock_time
	 *
	 * @haoxz11MyBatis
	 */
	public Date getLockTime() {
		return lockTime;
	}

	/**
	 * 设置：用户锁定时间
	 *
	 * @param lockTime sys_user.lock_time
	 *
	 * @haoxz11MyBatis
	 */
	public void setLockTime(Date lockTime) {
		this.lockTime = lockTime;
	}

	/**
	 * 读取：用户解锁时间
	 *
	 * @return sys_user.unlock_time
	 *
	 * @haoxz11MyBatis
	 */
	public Date getUnlockTime() {
		return unlockTime;
	}

	/**
	 * 设置：用户解锁时间
	 *
	 * @param unlockTime sys_user.unlock_time
	 *
	 * @haoxz11MyBatis
	 */
	public void setUnlockTime(Date unlockTime) {
		this.unlockTime = unlockTime;
	}

	/**
	 * 读取：用户锁定原因
	 *
	 * @return sys_user.lock_reason
	 *
	 * @haoxz11MyBatis
	 */
	public String getLockReason() {
		return lockReason;
	}

	/**
	 * 设置：用户锁定原因
	 *
	 * @param lockReason sys_user.lock_reason
	 *
	 * @haoxz11MyBatis
	 */
	public void setLockReason(String lockReason) {
		this.lockReason = lockReason;
	}

	/**
	 * 读取：用户过期时间
	 *
	 * @return sys_user.user_expire_date
	 *
	 * @haoxz11MyBatis
	 */
	public Date getUserExpireDate() {
		return userExpireDate;
	}

	/**
	 * 设置：用户过期时间
	 *
	 * @param userExpireDate sys_user.user_expire_date
	 *
	 * @haoxz11MyBatis
	 */
	public void setUserExpireDate(Date userExpireDate) {
		this.userExpireDate = userExpireDate;
	}

	/**
	 * 读取：用户连续登录失败次数
	 *
	 * @return sys_user.fail_num
	 *
	 * @haoxz11MyBatis
	 */
	public Integer getFailNum() {
		return failNum;
	}

	/**
	 * 设置：用户连续登录失败次数
	 *
	 * @param failNum sys_user.fail_num
	 *
	 * @haoxz11MyBatis
	 */
	public void setFailNum(Integer failNum) {
		this.failNum = failNum;
	}

	/**
	 * 读取：密码过期策略：1系统配置周期，2永不过期，3指定日期
	 *
	 * @return sys_user.pw_expire_type
	 *
	 * @haoxz11MyBatis
	 */
	public String getPwExpireType() {
		return pwExpireType;
	}

	/**
	 * 设置：密码过期策略：1系统配置周期，2永不过期，3指定日期
	 *
	 * @param pwExpireType sys_user.pw_expire_type
	 *
	 * @haoxz11MyBatis
	 */
	public void setPwExpireType(String pwExpireType) {
		this.pwExpireType = pwExpireType;
	}

	/**
	 * 读取：密码过期时间
	 *
	 * @return sys_user.pw_expire_date
	 *
	 * @haoxz11MyBatis
	 */
	public Date getPwExpireDate() {
		return pwExpireDate;
	}

	/**
	 * 设置：密码过期时间
	 *
	 * @param pwExpireDate sys_user.pw_expire_date
	 *
	 * @haoxz11MyBatis
	 */
	public void setPwExpireDate(Date pwExpireDate) {
		this.pwExpireDate = pwExpireDate;
	}

	/**
	 * 读取：密码最近修改时间
	 *
	 * @return sys_user.pw_edit_date
	 *
	 * @haoxz11MyBatis
	 */
	public Date getPwEditDate() {
		return pwEditDate;
	}

	/**
	 * 设置：密码最近修改时间
	 *
	 * @param pwEditDate sys_user.pw_edit_date
	 *
	 * @haoxz11MyBatis
	 */
	public void setPwEditDate(Date pwEditDate) {
		this.pwEditDate = pwEditDate;
	}

	/**
	 * 读取：用户签到状态 1到 2退
	 *
	 * @return sys_user.signs_tate
	 *
	 * @haoxz11MyBatis
	 */
	public String getSignsTate() {
		return signsTate;
	}

	/**
	 * 设置：用户签到状态 1到 2退
	 *
	 * @param signsTate sys_user.signs_tate
	 *
	 * @haoxz11MyBatis
	 */
	public void setSignsTate(String signsTate) {
		this.signsTate = signsTate;
	}

	/**
	 * 读取：所属部门
	 *
	 * @return sys_user.department
	 *
	 * @haoxz11MyBatis
	 */
	public String getDepartment() {
		return department;
	}

	/**
	 * 设置：所属部门
	 *
	 * @param department sys_user.department
	 *
	 * @haoxz11MyBatis
	 */
	public void setDepartment(String department) {
		this.department = department;
	}

	/**
	 * 读取：创建日期
	 *
	 * @return sys_user.create_time
	 *
	 * @haoxz11MyBatis
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 设置：创建日期
	 *
	 * @param createTime sys_user.create_time
	 *
	 * @haoxz11MyBatis
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 读取：修改时间
	 *
	 * @return sys_user.modify_time
	 *
	 * @haoxz11MyBatis
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * 设置：修改时间
	 *
	 * @param modifyTime sys_user.modify_time
	 *
	 * @haoxz11MyBatis
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	
}