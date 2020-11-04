package com.wangxinenpu.springbootdemo.dataobject.po;

import star.vo.BaseVo;

import javax.persistence.Column;
import java.util.Date;

/**
 * 
 * 审核任务指派表
 * @author haoxz11MyBatis 
 * @created Tue May 28 01:26:21 CST 2019
 * @version $Id: DefaultCommentGenerator.java,v 1.1 2013/10/28 07:59:58 haoxz11 Exp $
 * @haoxz11MyBatis
 */
public class SysCheckAssign extends BaseVo {
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
	 * 字段：业务ID
	 *
	 * @haoxz11MyBatis
	 */
	private Long busId;
	
	/**
	 * 字段：指派人ID
	 *
	 * @haoxz11MyBatis
	 */
	private String initiatorId;

	/**
	 * 字段：指派人名称
	 *
	 * @haoxz11MyBatis
	 */
	private String initiatorName;
	

	/**
	 * 字段：指派对象ID
	 *
	 * @haoxz11MyBatis
	 */
	private String assignorId;

	/**
	 * 字段：指派对象名称
	 *
	 * @haoxz11MyBatis
	 */
	private String assignorName;

	/**
	 * 字段：指派时间
	 *
	 * @haoxz11MyBatis
	 */
	private Date assignTime;

	/**
	 * 字段：指派原因
	 *
	 * @haoxz11MyBatis
	 */
	private String assignReason;

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
	 * 读取：主键ID
	 *
	 * @return sys_check_assign.id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 设置：主键ID
	 *
	 * @param id sys_check_assign.id
	 *
	 * @haoxz11MyBatis
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 读取：流程ID
	 *
	 * @return sys_check_assign.pro_id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getProId() {
		return proId;
	}

	/**
	 * 设置：流程ID
	 *
	 * @param proId sys_check_assign.pro_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setProId(Long proId) {
		this.proId = proId;
	}

	/**
	 * 读取：指派人ID
	 *
	 * @return sys_check_assign.assignor_id
	 *
	 * @haoxz11MyBatis
	 */
	public String getAssignorId() {
		return assignorId;
	}

	/**
	 * 设置：指派人ID
	 *
	 * @param assignorId sys_check_assign.assignor_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setAssignorId(String assignorId) {
		this.assignorId = assignorId;
	}

	/**
	 * 读取：指派人名称
	 *
	 * @return sys_check_assign.assignor_name
	 *
	 * @haoxz11MyBatis
	 */
	public String getAssignorName() {
		return assignorName;
	}

	/**
	 * 设置：指派人名称
	 *
	 * @param assignorName sys_check_assign.assignor_name
	 *
	 * @haoxz11MyBatis
	 */
	public void setAssignorName(String assignorName) {
		this.assignorName = assignorName;
	}

	/**
	 * 读取：指派时间
	 *
	 * @return sys_check_assign.assign_time
	 *
	 * @haoxz11MyBatis
	 */
	public Date getAssignTime() {
		return assignTime;
	}

	/**
	 * 设置：指派时间
	 *
	 * @param assignTime sys_check_assign.assign_time
	 *
	 * @haoxz11MyBatis
	 */
	public void setAssignTime(Date assignTime) {
		this.assignTime = assignTime;
	}

	/**
	 * 读取：指派原因
	 *
	 * @return sys_check_assign.assign_reason
	 *
	 * @haoxz11MyBatis
	 */
	public String getAssignReason() {
		return assignReason;
	}

	/**
	 * 设置：指派原因
	 *
	 * @param assignReason sys_check_assign.assign_reason
	 *
	 * @haoxz11MyBatis
	 */
	public void setAssignReason(String assignReason) {
		this.assignReason = assignReason;
	}

	/**
	 * 读取：创建时间
	 *
	 * @return sys_check_assign.create_time
	 *
	 * @haoxz11MyBatis
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 设置：创建时间
	 *
	 * @param createTime sys_check_assign.create_time
	 *
	 * @haoxz11MyBatis
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 读取：修改时间
	 *
	 * @return sys_check_assign.modify_time
	 *
	 * @haoxz11MyBatis
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * 设置：修改时间
	 *
	 * @param modifyTime sys_check_assign.modify_time
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

	public String getInitiatorId() {
		return initiatorId;
	}

	public void setInitiatorId(String initiatorId) {
		this.initiatorId = initiatorId;
	}

	public String getInitiatorName() {
		return initiatorName;
	}

	public void setInitiatorName(String initiatorName) {
		this.initiatorName = initiatorName;
	}


	public Long getHisId() {
		return hisId;
	}

	public void setHisId(Long hisId) {
		this.hisId = hisId;
	}
}