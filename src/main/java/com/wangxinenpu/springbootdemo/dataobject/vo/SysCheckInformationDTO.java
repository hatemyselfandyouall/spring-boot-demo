package com.wangxinenpu.springbootdemo.dataobject.vo;

import star.vo.BaseVo;

import javax.persistence.Column;
import java.util.Date;

/**
 * 
 * 审核信息表
 * @author haoxz11MyBatis 
 * @created Tue May 28 01:26:21 CST 2019
 * @version $Id: DefaultCommentGenerator.java,v 1.1 2013/10/28 07:59:58 haoxz11 Exp $
 * @haoxz11MyBatis
 */
public class SysCheckInformationDTO extends BaseVo {
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
	 * 字段：流程ID
	 *
	 * @haoxz11MyBatis
	 */
	private Long proId;
	
	/**
	 * 审核业务ID
	 */
	private Long busId;

	/**
	 * 字段：审核人ID
	 *
	 * @haoxz11MyBatis
	 */
	private String checkPeopleId;

	/**
	 * 字段：审核人名称
	 *
	 * @haoxz11MyBatis
	 */
	private String checkPeopleName;

	/**
	 * 字段：审核时间
	 *
	 * @haoxz11MyBatis
	 */
	private Date checkTime;

	/**
	 * 字段：审核结果 0：待审核 1：通过 2：驳回   3:撤销
	 *
	 * @haoxz11MyBatis
	 */
	private String checkResult;

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
	
	//驳回原因
	private String rejectCause;
	
	
	//流程名称
	private String proName;
	//流程步骤
	private String step;
	//分派规则

    private String dispatchRule;


	/**
	 * 读取：主键ID
	 *
	 * @return sys_check_information.id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 设置：主键ID
	 *
	 * @param id sys_check_information.id
	 *
	 * @haoxz11MyBatis
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 读取：流程ID
	 *
	 * @return sys_check_information.pro_id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getProId() {
		return proId;
	}

	/**
	 * 设置：流程ID
	 *
	 * @param proId sys_check_information.pro_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setProId(Long proId) {
		this.proId = proId;
	}

	/**
	 * 读取：审核人ID
	 *
	 * @return sys_check_information.check_people_id
	 *
	 * @haoxz11MyBatis
	 */
	public String getCheckPeopleId() {
		return checkPeopleId;
	}

	/**
	 * 设置：审核人ID
	 *
	 * @param checkPeopleId sys_check_information.check_people_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setCheckPeopleId(String checkPeopleId) {
		this.checkPeopleId = checkPeopleId;
	}

	/**
	 * 读取：审核人名称
	 *
	 * @return sys_check_information.check_people_name
	 *
	 * @haoxz11MyBatis
	 */
	public String getCheckPeopleName() {
		return checkPeopleName;
	}

	/**
	 * 设置：审核人名称
	 *
	 * @param checkPeopleName sys_check_information.check_people_name
	 *
	 * @haoxz11MyBatis
	 */
	public void setCheckPeopleName(String checkPeopleName) {
		this.checkPeopleName = checkPeopleName;
	}

	/**
	 * 读取：审核时间
	 *
	 * @return sys_check_information.check_time
	 *
	 * @haoxz11MyBatis
	 */
	public Date getCheckTime() {
		return checkTime;
	}

	/**
	 * 设置：审核时间
	 *
	 * @param checkTime sys_check_information.check_time
	 *
	 * @haoxz11MyBatis
	 */
	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}

	/**
	 * 读取：审核结果 0：待审核 1：通过 2：驳回 3:撤销
	 *
	 * @return sys_check_information.check_result
	 *
	 * @haoxz11MyBatis
	 */
	public String getCheckResult() {
		return checkResult;
	}

	/**
	 * 设置：审核结果 0：待审核 1：通过 2：驳回 3:撤销
	 *
	 * @param checkResult sys_check_information.check_result
	 *
	 * @haoxz11MyBatis
	 */
	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}

	/**
	 * 读取：创建时间
	 *
	 * @return sys_check_information.create_time
	 *
	 * @haoxz11MyBatis
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 设置：创建时间
	 *
	 * @param createTime sys_check_information.create_time
	 *
	 * @haoxz11MyBatis
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 读取：修改时间
	 *
	 * @return sys_check_information.modify_time
	 *
	 * @haoxz11MyBatis
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * 设置：修改时间
	 *
	 * @param modifyTime sys_check_information.modify_time
	 *
	 * @haoxz11MyBatis
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Long getBusId() {
		return busId;
	}

	public void setBusId(Long busId) {
		this.busId = busId;
	}

	public String getRejectCause() {
		return rejectCause;
	}

	public void setRejectCause(String rejectCause) {
		this.rejectCause = rejectCause;
	}

	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}
	public Long getHisId() {
		return hisId;
	}

	public void setHisId(Long hisId) {
		this.hisId = hisId;
	}

	public String getStep() {
		return step;
	}

	public void setStep(String step) {
		this.step = step;
	}

	public String getDispatchRule() {
		return dispatchRule;
	}

	public void setDispatchRule(String dispatchRule) {
		this.dispatchRule = dispatchRule;
	}
	
}