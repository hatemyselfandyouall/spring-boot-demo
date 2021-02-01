package com.wangxinenpu.springbootdemo.dataobject.vo;

import star.vo.BaseVo;

import java.util.Date;

/**
 * 
 * 业务类型表
 * @author haoxz11MyBatis 
 * @created Tue May 07 11:54:06 CST 2019
 * @version $Id: DefaultCommentGenerator.java,v 1.1 2013/10/28 07:59:58 haoxz11 Exp $
 * @haoxz11MyBatis
 */
public class SysBusinessTypeDTO extends BaseVo {
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
	 * 字段：业务编号
	 *
	 * @haoxz11MyBatis
	 */
	private String number;

	/**
	 * 字段：业务名称
	 *
	 * @haoxz11MyBatis
	 */
	private String name;

	/**
	 * 字段：父节点ID
	 *
	 * @haoxz11MyBatis
	 */
	private Long parentId;

	/**
	 * 字段：是否有效 0：有效1：无效
	 *
	 * @haoxz11MyBatis
	 */
	private Long active;

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
	 * 编号名称组合
	 */
	private String numberName;
	

	public String getNumberName() {
		return numberName;
	}

	public void setNumberName(String numberName) {
		this.numberName = numberName;
	}

	/**
	 * 读取：主键ID
	 *
	 * @return sys_business_type.id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 设置：主键ID
	 *
	 * @param id sys_business_type.id
	 *
	 * @haoxz11MyBatis
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 读取：业务编号
	 *
	 * @return sys_business_type.number
	 *
	 * @haoxz11MyBatis
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * 设置：业务编号
	 *
	 * @param number sys_business_type.number
	 *
	 * @haoxz11MyBatis
	 */
	public void setNumber(String number) {
		this.number = number;
	}

	/**
	 * 读取：业务名称
	 *
	 * @return sys_business_type.name
	 *
	 * @haoxz11MyBatis
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置：业务名称
	 *
	 * @param name sys_business_type.name
	 *
	 * @haoxz11MyBatis
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 读取：父节点ID
	 *
	 * @return sys_business_type.parent_id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getParentId() {
		return parentId;
	}

	/**
	 * 设置：父节点ID
	 *
	 * @param parentId sys_business_type.parent_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	/**
	 * 读取：是否有效 0：有效1：无效
	 *
	 * @return sys_business_type.active
	 *
	 * @haoxz11MyBatis
	 */
	public Long getActive() {
		return active;
	}

	/**
	 * 设置：是否有效 0：有效1：无效
	 *
	 * @param active sys_business_type.active
	 *
	 * @haoxz11MyBatis
	 */
	public void setActive(Long active) {
		this.active = active;
	}

	/**
	 * 读取：创建时间
	 *
	 * @return sys_business_type.create_time
	 *
	 * @haoxz11MyBatis
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 设置：创建时间
	 *
	 * @param createTime sys_business_type.create_time
	 *
	 * @haoxz11MyBatis
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 读取：修改时间
	 *
	 * @return sys_business_type.modify_time
	 *
	 * @haoxz11MyBatis
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * 设置：修改时间
	 *
	 * @param modifyTime sys_business_type.modify_time
	 *
	 * @haoxz11MyBatis
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
}