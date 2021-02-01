package com.wangxinenpu.springbootdemo.dataobject.po;

import star.vo.BaseVo;

import java.util.Date;

/**
 * 签领表
 * @author Administrator
 *
 */
public class SysCheckSigning extends BaseVo{
	
	/**
	 *
	 * @haoxz11MyBatis
	 */
	transient private static final long serialVersionUID = -1L;

	/**
	 * 字段：主键ID
	 *
	 * @haoxz11MyBatis
	 */
	private Long id;
	
	/**
	 * 字段：业务ID
	 *
	 * @haoxz11MyBatis
	 */
	private Long busId;

	/**
	 * 字段：流程ID
	 *
	 * @haoxz11MyBatis
	 */
	private Long proId;

	/**
	 * 字段：流程名称
	 *
	 * @haoxz11MyBatis
	 */
	private String proName;

	/**
	 * 字段：提审人ID
	 *
	 * @haoxz11MyBatis
	 */
	private String operateUserId;
	
	/**
	 * 字段：提审人名称
	 *
	 * @haoxz11MyBatis
	 */
	private String operateUserName;
	
	
	/**
	 * 字段：签领人ID
	 *
	 * @haoxz11MyBatis
	 */
	private String signingUserId;
	
	/**
	 * 字段：提审时间
	 *
	 * @haoxz11MyBatis
	 */
	private Date arraignmentTime;

	/**
	 * 字段：创建时间
	 *
	 * @haoxz11MyBatis
	 */
	private Date createTime;

	/**
	 * 字段：修改时间
	 *
	 * @haoxz11MyBatis
	 */
	private Date modifyTime;
	
	/**
	 * 字段：业务类型Id
	 *
	 * @haoxz11MyBatis
	 */
	private Long busTypeId;
	
	/**
	 * 字段：业务类型名称
	 *
	 * @haoxz11MyBatis
	 */
	private String busTypeName;
	
	/**
	 * 字段：办事方名称
	 *
	 * @haoxz11MyBatis
	 */
	private String companyName;

	public Long getState() {
		return state;
	}

	public void setState(Long state) {
		this.state = state;
	}

	/**
	 * 字段：办事方类型

	 *
	 * @haoxz11MyBatis

	 */
	private String bizUserType;

	/**
	 * 字段：0待签领1已签领2已被签领
	 *
	 * @haoxz11MyBatis
	 */
	private Long state;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getProId() {
		return proId;
	}

	public void setProId(Long proId) {
		this.proId = proId;
	}

	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	public String getOperateUserId() {
		return operateUserId;
	}

	public void setOperateUserId(String operateUserId) {
		this.operateUserId = operateUserId;
	}

	public String getOperateUserName() {
		return operateUserName;
	}

	public void setOperateUserName(String operateUserName) {
		this.operateUserName = operateUserName;
	}

	public String getSigningUserId() {
		return signingUserId;
	}

	public void setSigningUserId(String signingUserId) {
		this.signingUserId = signingUserId;
	}

	public Date getArraignmentTime() {
		return arraignmentTime;
	}

	public void setArraignmentTime(Date arraignmentTime) {
		this.arraignmentTime = arraignmentTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Long getBusTypeId() {
		return busTypeId;
	}

	public void setBusTypeId(Long busTypeId) {
		this.busTypeId = busTypeId;
	}

	public String getBusTypeName() {
		return busTypeName;
	}

	public void setBusTypeName(String busTypeName) {
		this.busTypeName = busTypeName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getBizUserType() {
		return bizUserType;
	}

	public void setBizUserType(String bizUserType) {
		this.bizUserType = bizUserType;
	}

	public Long getBusId() {
		return busId;
	}

	public void setBusId(Long busId) {
		this.busId = busId;
	}


}
