package com.wangxinenpu.springbootdemo.dataobject.vo;

import star.vo.BaseVo;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 
 * 审核业务表
 * @author haoxz11MyBatis 
 * @created Tue May 28 01:26:21 CST 2019
 * @version $Id: DefaultCommentGenerator.java,v 1.1 2013/10/28 07:59:58 haoxz11 Exp $
 * @haoxz11MyBatis
 */
public class SysCheckBusinessDTO extends BaseVo {
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
	 * 字段：审核配置ID
	 *
	 * @haoxz11MyBatis
	 */
	private Long configId;

	/**
	 * 字段：操作名称
	 *
	 * @haoxz11MyBatis
	 */
	private String operatioName;

	/**
	 * 字段：提审时间
	 *
	 * @haoxz11MyBatis
	 */
	private Date arraignmentTime;

	/**
	 * 字段：提审人员
	 *
	 * @haoxz11MyBatis
	 */
	private String arraignmentPeople;

	/**
	 * 字段：提审人ID
	 *
	 * @haoxz11MyBatis
	 */
	private String arraignmentPeopleId;

	/**
	 * 字段：任务状态 0：待审核 1：已通过 2：已驳回 3：已撤销
	 *
	 * @haoxz11MyBatis
	 */
	private String taskStatus;

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
	
	//单位名称
	private String companyName;
	//统一社会信用代码
	private String creditCode;
	//流程版本
	private String version;
	//类型 1：单位 2：个人
	private String type;
	//撤销原因
	private String revokeCause;
	//操作日志ID
	private Long prseno;
	
	//流程ID
	private Long proId;
	//流程名称
	private String proName;
	//流程步骤
	private String step;
	//审核人员ID
	private String checkPeopleId;
	//审核人员
	private String checkPeopleName;
	//审核结果
	private String checkResult;
	//分派规则
	private String dispatchRule;
	//下一环节流程ID
	private Long belowProId;
	
	//当事人id
	private Long objectId;
	//业务类型编码
	private String aaa121;
	//区域
	private Long areaId;
	//当前业务ID流程ID集合
	List<Map<String, Object>> pbList;
	
	//单位名字
	private String name;
	
	

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 读取：主键ID
	 *
	 * @return sys_check_business.id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 设置：主键ID
	 *
	 * @param id sys_check_business.id
	 *
	 * @haoxz11MyBatis
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 读取：审核配置ID
	 *
	 * @return sys_check_business.config_id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getConfigId() {
		return configId;
	}

	/**
	 * 设置：审核配置ID
	 *
	 * @param configId sys_check_business.config_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setConfigId(Long configId) {
		this.configId = configId;
	}

	/**
	 * 读取：操作名称
	 *
	 * @return sys_check_business.operatio_name
	 *
	 * @haoxz11MyBatis
	 */
	public String getOperatioName() {
		return operatioName;
	}

	/**
	 * 设置：操作名称
	 *
	 * @param operatioName sys_check_business.operatio_name
	 *
	 * @haoxz11MyBatis
	 */
	public void setOperatioName(String operatioName) {
		this.operatioName = operatioName;
	}

	/**
	 * 读取：提审时间
	 *
	 * @return sys_check_business.arraignment_time
	 *
	 * @haoxz11MyBatis
	 */
	public Date getArraignmentTime() {
		return arraignmentTime;
	}

	/**
	 * 设置：提审时间
	 *
	 * @param arraignmentTime sys_check_business.arraignment_time
	 *
	 * @haoxz11MyBatis
	 */
	public void setArraignmentTime(Date arraignmentTime) {
		this.arraignmentTime = arraignmentTime;
	}

	/**
	 * 读取：提审人员
	 *
	 * @return sys_check_business.arraignment_people
	 *
	 * @haoxz11MyBatis
	 */
	public String getArraignmentPeople() {
		return arraignmentPeople;
	}

	/**
	 * 设置：提审人员
	 *
	 * @param arraignmentPeople sys_check_business.arraignment_people
	 *
	 * @haoxz11MyBatis
	 */
	public void setArraignmentPeople(String arraignmentPeople) {
		this.arraignmentPeople = arraignmentPeople;
	}

	/**
	 * 读取：提审人ID
	 *
	 * @return sys_check_business.arraignment_people_id
	 *
	 * @haoxz11MyBatis
	 */
	public String getArraignmentPeopleId() {
		return arraignmentPeopleId;
	}

	/**
	 * 设置：提审人ID
	 *
	 * @param arraignmentPeopleId sys_check_business.arraignment_people_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setArraignmentPeopleId(String arraignmentPeopleId) {
		this.arraignmentPeopleId = arraignmentPeopleId;
	}

	/**
	 * 读取：任务状态 0：待审核 1：已通过 2：已驳回 3：已撤销
	 *
	 * @return sys_check_business.task_status
	 *
	 * @haoxz11MyBatis
	 */
	public String getTaskStatus() {
		return taskStatus;
	}

	/**
	 * 设置：任务状态 0：待审核 1：已通过 2：已驳回 3：已撤销
	 *
	 * @param taskStatus sys_check_business.task_status
	 *
	 * @haoxz11MyBatis
	 */
	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}

	/**
	 * 读取：创建时间
	 *
	 * @return sys_check_business.create_time
	 *
	 * @haoxz11MyBatis
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 设置：创建时间
	 *
	 * @param createTime sys_check_business.create_time
	 *
	 * @haoxz11MyBatis
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 读取：修改时间
	 *
	 * @return sys_check_business.modify_time
	 *
	 * @haoxz11MyBatis
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * 设置：修改时间
	 *
	 * @param modifyTime sys_check_business.modify_time
	 *
	 * @haoxz11MyBatis
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCreditCode() {
		return creditCode;
	}

	public void setCreditCode(String creditCode) {
		this.creditCode = creditCode;
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

	public String getStep() {
		return step;
	}

	public void setStep(String step) {
		this.step = step;
	}


	public String getCheckPeopleId() {
		return checkPeopleId;
	}

	public void setCheckPeopleId(String checkPeopleId) {
		this.checkPeopleId = checkPeopleId;
	}

	public String getCheckPeopleName() {
		return checkPeopleName;
	}

	public void setCheckPeopleName(String checkPeopleName) {
		this.checkPeopleName = checkPeopleName;
	}

	public String getCheckResult() {
		return checkResult;
	}

	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRevokeCause() {
		return revokeCause;
	}

	public void setRevokeCause(String revokeCause) {
		this.revokeCause = revokeCause;
	}

	public Long getObjectId() {
		return objectId;
	}

	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}

	public String getAaa121() {
		return aaa121;
	}

	public void setAaa121(String aaa121) {
		this.aaa121 = aaa121;
	}

	public Long getAreaId() {
		return areaId;
	}

	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}

	public String getDispatchRule() {
		return dispatchRule;
	}

	public void setDispatchRule(String dispatchRule) {
		this.dispatchRule = dispatchRule;
	}

	public Long getBelowProId() {
		return belowProId;
	}

	public void setBelowProId(Long belowProId) {
		this.belowProId = belowProId;
	}

	public Long getPrseno() {
		return prseno;
	}

	public void setPrseno(Long prseno) {
		this.prseno = prseno;
	}

	public List<Map<String, Object>> getPbList() {
		return pbList;
	}

	public void setPbList(List<Map<String, Object>> pbList) {
		this.pbList = pbList;
	}

	
	
}