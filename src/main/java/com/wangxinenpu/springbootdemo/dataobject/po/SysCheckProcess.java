package com.wangxinenpu.springbootdemo.dataobject.po;

import star.vo.BaseVo;

import javax.persistence.Column;
import java.util.Date;

/**
 * 
 * 审核流程表
 * @author haoxz11MyBatis 
 * @created Tue May 28 01:26:21 CST 2019
 * @version $Id: DefaultCommentGenerator.java,v 1.1 2013/10/28 07:59:58 haoxz11 Exp $
 * @haoxz11MyBatis
 */
public class SysCheckProcess extends BaseVo {
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

	@Column( name="his_id")
	private Long hisId;

	/**
	 * 字段：审核流程名称
	 *
	 * @haoxz11MyBatis
	 */
	private String name;

	/**
	 * 字段：分派规则 1：自主签领 2：手动指派 3：系统轮询 4：系统随机 5：规则分派
	 *
	 * @haoxz11MyBatis
	 */
	private String dispatchRule;

	/**
	 * 字段：是否有补充资料 0：无 1：有
	 *
	 * @haoxz11MyBatis
	 */
	private String isMaterial;

	/**
	 * 字段：步骤
	 *
	 * @haoxz11MyBatis
	 */
	private String step;
	
	/**
	 * 字段：审核配置ID
	 *
	 * @haoxz11MyBatis
	 */
	private Long configId;
	
	/**
	 * 字段：版本号
	 *
	 * @haoxz11MyBatis
	 */
	private String version;
	
	/**
	 * 字段：轮询次数
	 *
	 * @haoxz11MyBatis
	 */
	private Integer pollingNum;

	/**
	 * 字段：创建时间
	 *
	 * @haoxz11MyBatis
	 */
	private Date createTime;

	/**
	 * 字段：创建时间
	 *
	 * @haoxz11MyBatis
	 */
	private Date modifyTime;

	/**
	 * 是否终审 1：是 0：否
	 */
	private Integer isLast;

	private Long orgId;

	private String orgIdPath;


	/**
	 * 读取：主键ID
	 *
	 * @return sys_check_process.id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 设置：主键ID
	 *
	 * @param id sys_check_process.id
	 *
	 * @haoxz11MyBatis
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 读取：审核流程名称
	 *
	 * @return sys_check_process.name
	 *
	 * @haoxz11MyBatis
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置：审核流程名称
	 *
	 * @param name sys_check_process.name
	 *
	 * @haoxz11MyBatis
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 读取：分派规则 1：自主签领 2：手动指派 3：系统轮询 4：系统随机 5：规则分派
	 *
	 * @return sys_check_process.dispatch_rule
	 *
	 * @haoxz11MyBatis
	 */
	public String getDispatchRule() {
		return dispatchRule;
	}

	/**
	 * 设置：分派规则 1：自主签领 2：手动指派 3：系统轮询 4：系统随机 5：规则分派
	 *
	 * @param dispatchRule sys_check_process.dispatch_rule
	 *
	 * @haoxz11MyBatis
	 */
	public void setDispatchRule(String dispatchRule) {
		this.dispatchRule = dispatchRule;
	}

	/**
	 * 读取：是否有补充资料 0：无 1：有
	 *
	 * @return sys_check_process.is_material
	 *
	 * @haoxz11MyBatis
	 */
	public String getIsMaterial() {
		return isMaterial;
	}

	/**
	 * 设置：是否有补充资料 0：无 1：有
	 *
	 * @param isMaterial sys_check_process.is_material
	 *
	 * @haoxz11MyBatis
	 */
	public void setIsMaterial(String isMaterial) {
		this.isMaterial = isMaterial;
	}

	/**
	 * 读取：步骤
	 *
	 * @return sys_check_process.step
	 *
	 * @haoxz11MyBatis
	 */
	public String getStep() {
		return step;
	}

	/**
	 * 设置：步骤
	 *
	 * @param step sys_check_process.step
	 *
	 * @haoxz11MyBatis
	 */
	public void setStep(String step) {
		this.step = step;
	}

	/**
	 * 读取：创建时间
	 *
	 * @return sys_check_process.create_time
	 *
	 * @haoxz11MyBatis
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 设置：创建时间
	 *
	 * @param createTime sys_check_process.create_time
	 *
	 * @haoxz11MyBatis
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 读取：创建时间
	 *
	 * @return sys_check_process.modify_time
	 *
	 * @haoxz11MyBatis
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * 设置：创建时间
	 *
	 * @param modifyTime sys_check_process.modify_time
	 *
	 * @haoxz11MyBatis
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Long getConfigId() {
		return configId;
	}

	public void setConfigId(Long configId) {
		this.configId = configId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Long getHisId() {
		return hisId;
	}

	public void setHisId(Long hisId) {
		this.hisId = hisId;
	}

	public Integer getPollingNum() {
		return pollingNum;
	}

	public void setPollingNum(Integer pollingNum) {
		this.pollingNum = pollingNum;
	}

	public Integer getIsLast() {
		return isLast;
	}

	public void setIsLast(Integer isLast) {
		this.isLast = isLast;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public String getOrgIdPath() {
		return orgIdPath;
	}

	public void setOrgIdPath(String orgIdPath) {
		this.orgIdPath = orgIdPath;
	}
}