package com.wangxinenpu.springbootdemo.dataobject.po;

import star.vo.BaseVo;

import java.util.Date;

/**
 * 
 * 机构险种表
 * @author haoxz11MyBatis 
 * @created Fri Apr 19 10:59:43 CST 2019
 * @version $Id: DefaultCommentGenerator.java,v 1.1 2013/10/28 07:59:58 haoxz11 Exp $
 * @haoxz11MyBatis
 */
public class SysOrginstype extends BaseVo {
	/**
	 *
	 * @haoxz11MyBatis
	 */
	transient private static final long serialVersionUID = -1L;

	/**
	 * 字段：主键id
	 *
	 * @haoxz11MyBatis
	 */
	private Long id;

	/**
	 * 字段：机构id
	 *
	 * @haoxz11MyBatis
	 */
	private Long orgId;

	/**
	 * 字段：险种id
	 *
	 * @haoxz11MyBatis
	 */
	private Long insId;
	
	//险种编码
	private String codeValue;

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
	 * 读取：主键id
	 *
	 * @return sys_orginstype.id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 设置：主键id
	 *
	 * @param id sys_orginstype.id
	 *
	 * @haoxz11MyBatis
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 读取：机构id
	 *
	 * @return sys_orginstype.org_id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getOrgId() {
		return orgId;
	}

	/**
	 * 设置：机构id
	 *
	 * @param orgId sys_orginstype.org_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	/**
	 * 读取：险种id
	 *
	 * @return sys_orginstype.ins_id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getInsId() {
		return insId;
	}

	/**
	 * 设置：险种id
	 *
	 * @param insId sys_orginstype.ins_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setInsId(Long insId) {
		this.insId = insId;
	}

	/**
	 * 读取：创建时间
	 *
	 * @return sys_orginstype.create_time
	 *
	 * @haoxz11MyBatis
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 设置：创建时间
	 *
	 * @param createTime sys_orginstype.create_time
	 *
	 * @haoxz11MyBatis
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 读取：修改时间
	 *
	 * @return sys_orginstype.modify_time
	 *
	 * @haoxz11MyBatis
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * 设置：修改时间
	 *
	 * @param modifyTime sys_orginstype.modify_time
	 *
	 * @haoxz11MyBatis
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getCodeValue() {
		return codeValue;
	}

	public void setCodeValue(String codeValue) {
		this.codeValue = codeValue;
	}
	
}