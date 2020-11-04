package com.wangxinenpu.springbootdemo.dataobject.vo;

import star.vo.BaseVo;

import java.util.Date;

/**
 * 
 * 审核权限配置表
 * @author haoxz11MyBatis 
 * @created Tue May 28 01:26:21 CST 2019
 * @version $Id: DefaultCommentGenerator.java,v 1.1 2013/10/28 07:59:58 haoxz11 Exp $
 * @haoxz11MyBatis
 */
public class SysCheckConfigDTO extends BaseVo {
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
	private Long funId;

	/**
	 * 字段：业务类型编号
	 *
	 * @haoxz11MyBatis
	 */
	private String funName;

	/**
	 * 字段：是否主导 0：是 1：否
	 *
	 * @haoxz11MyBatis
	 */
	private String isLeading;
	
	/**
	 * 字段：是否驳回 0：是 1：否
	 *
	 * @haoxz11MyBatis
	 */
	private String isReject;
	
	/**
	 * 字段：是否审核人员重复 0：是 1：否
	 *
	 * @haoxz11MyBatis
	 */
	private String isProRepeat;

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
	 * 机构ID
	 */
	private Long orgId;
	
	
	//审核标志，0-自动审核，1-手动配置
	private String auFlag;
	
	/**
	 * 天正审核层级
	 */
	private String checkLv;
	
	
	private String belongsRole;
	private String step1;
	private String step2;
	private String step3;
	
	//配置的区域ID
	private Long areaId;
	

	public Long getAreaId() {
		return areaId;
	}

	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}

	/**
	 * 读取：主键ID
	 *
	 * @return sys_check_config.id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 设置：主键ID
	 *
	 * @param id sys_check_config.id
	 *
	 * @haoxz11MyBatis
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 读取：业务ID
	 *
	 * @return sys_check_config.fun_id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getFunId() {
		return funId;
	}

	/**
	 * 设置：业务ID
	 *
	 * @param funId sys_check_config.fun_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setFunId(Long funId) {
		this.funId = funId;
	}

	/**
	 * 读取：业务类型编号
	 *
	 * @return sys_check_config.fun_name
	 *
	 * @haoxz11MyBatis
	 */
	public String getFunName() {
		return funName;
	}

	/**
	 * 设置：业务类型编号
	 *
	 * @param funName sys_check_config.fun_name
	 *
	 * @haoxz11MyBatis
	 */
	public void setFunName(String funName) {
		this.funName = funName;
	}

	/**
	 * 读取：是否主导 0：是 1：否
	 *
	 * @return sys_check_config.is_leading
	 *
	 * @haoxz11MyBatis
	 */
	public String getIsLeading() {
		return isLeading;
	}

	/**
	 * 设置：是否主导 0：是 1：否
	 *
	 * @param isLeading sys_check_config.is_leading
	 *
	 * @haoxz11MyBatis
	 */
	public void setIsLeading(String isLeading) {
		this.isLeading = isLeading;
	}

	/**
	 * 读取：创建时间
	 *
	 * @return sys_check_config.create_time
	 *
	 * @haoxz11MyBatis
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 设置：创建时间
	 *
	 * @param createTime sys_check_config.create_time
	 *
	 * @haoxz11MyBatis
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 读取：修改时间
	 *
	 * @return sys_check_config.modify_time
	 *
	 * @haoxz11MyBatis
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * 设置：修改时间
	 *
	 * @param modifyTime sys_check_config.modify_time
	 *
	 * @haoxz11MyBatis
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getAuFlag() {
		return auFlag;
	}

	public void setAuFlag(String auFlag) {
		this.auFlag = auFlag;
	}

	public String getIsReject() {
		return isReject;
	}

	public void setIsReject(String isReject) {
		this.isReject = isReject;
	}

	public String getIsProRepeat() {
		return isProRepeat;
	}

	public void setIsProRepeat(String isProRepeat) {
		this.isProRepeat = isProRepeat;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public String getBelongsRole() {
		return belongsRole;
	}

	public void setBelongsRole(String belongsRole) {
		this.belongsRole = belongsRole;
	}

	public String getStep1() {
		return step1;
	}

	public void setStep1(String step1) {
		this.step1 = step1;
	}

	public String getStep2() {
		return step2;
	}

	public void setStep2(String step2) {
		this.step2 = step2;
	}

	public String getStep3() {
		return step3;
	}

	public void setStep3(String step3) {
		this.step3 = step3;
	}

	public String getCheckLv() {
		return checkLv;
	}

	public void setCheckLv(String checkLv) {
		this.checkLv = checkLv;
	}
	
	
	
}