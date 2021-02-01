package com.wangxinenpu.springbootdemo.dataobject.vo;

import star.vo.BaseVo;

import javax.persistence.Column;
import java.util.Date;

/**
 * 
 * 审核资料表
 * @author haoxz11MyBatis 
 * @created Tue May 28 01:26:21 CST 2019
 * @version $Id: DefaultCommentGenerator.java,v 1.1 2013/10/28 07:59:58 haoxz11 Exp $
 * @haoxz11MyBatis
 */
public class SysCheckMaterialDTO extends BaseVo {
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

	@Column( name="his_id")
	private Long hisId;

	/**
	 * 字段：资料名称
	 *
	 * @haoxz11MyBatis
	 */
	private String name;

	/**
	 * 字段：字数限制
	 *
	 * @haoxz11MyBatis
	 */
	private String wordCount;

	/**
	 * 字段：资料内容
	 *
	 * @haoxz11MyBatis
	 */
	private String content;

	/**
	 * 字段：流程ID
	 *
	 * @haoxz11MyBatis
	 */
	private Long proId;

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
	 * @return sys_check_material.id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 设置：主键id
	 *
	 * @param id sys_check_material.id
	 *
	 * @haoxz11MyBatis
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 读取：资料名称
	 *
	 * @return sys_check_material.name
	 *
	 * @haoxz11MyBatis
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置：资料名称
	 *
	 * @param name sys_check_material.name
	 *
	 * @haoxz11MyBatis
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 读取：字数限制
	 *
	 * @return sys_check_material.word_count
	 *
	 * @haoxz11MyBatis
	 */
	public String getWordCount() {
		return wordCount;
	}

	/**
	 * 设置：字数限制
	 *
	 * @param wordCount sys_check_material.word_count
	 *
	 * @haoxz11MyBatis
	 */
	public void setWordCount(String wordCount) {
		this.wordCount = wordCount;
	}

	/**
	 * 读取：资料内容
	 *
	 * @return sys_check_material.content
	 *
	 * @haoxz11MyBatis
	 */
	public String getContent() {
		return content;
	}

	/**
	 * 设置：资料内容
	 *
	 * @param content sys_check_material.content
	 *
	 * @haoxz11MyBatis
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 读取：流程ID
	 *
	 * @return sys_check_material.pro_id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getProId() {
		return proId;
	}

	/**
	 * 设置：流程ID
	 *
	 * @param proId sys_check_material.pro_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setProId(Long proId) {
		this.proId = proId;
	}

	/**
	 * 读取：创建时间
	 *
	 * @return sys_check_material.create_time
	 *
	 * @haoxz11MyBatis
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 设置：创建时间
	 *
	 * @param createTime sys_check_material.create_time
	 *
	 * @haoxz11MyBatis
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 读取：修改时间
	 *
	 * @return sys_check_material.modify_time
	 *
	 * @haoxz11MyBatis
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * 设置：修改时间
	 *
	 * @param modifyTime sys_check_material.modify_time
	 *
	 * @haoxz11MyBatis
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Long getHisId() {
		return hisId;
	}

	public void setHisId(Long hisId) {
		this.hisId = hisId;
	}
}