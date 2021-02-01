package com.wangxinenpu.springbootdemo.dataobject.po;

import star.vo.BaseVo;

import java.util.Date;

/**
 * 
 * 系统规则表
 * @author haoxz11MyBatis 
 * @created Sat Mar 30 01:44:16 CST 2019
 * @version $Id: DefaultCommentGenerator.java,v 1.1 2013/10/28 07:59:58 haoxz11 Exp $
 * @haoxz11MyBatis
 */
public class SysRule extends BaseVo {
	/**
	 *
	 * @haoxz11MyBatis
	 */
	transient private static final long serialVersionUID = -1L;

	/**
	 * 字段：规则id
	 *
	 * @haoxz11MyBatis
	 */
	private Long id;

	/**
	 * 字段：规则名称
	 *
	 * @haoxz11MyBatis
	 */
	private String ruleName;

	/**
	 * 字段：详细描述
	 *
	 * @haoxz11MyBatis
	 */
	private String detaile;

	/**
	 * 字段：规则类型（如1.目录，2.规则--每个规则为一个java）
	 *
	 * @haoxz11MyBatis
	 */
	private String ruleType;

	/**
	 * 字段：创建者
	 *
	 * @haoxz11MyBatis
	 */
	private String creater;

	/**
	 * 字段：是否有效
	 *
	 * @haoxz11MyBatis
	 */
	private String active;

	/**
	 * 字段：父节点id
	 *
	 * @haoxz11MyBatis
	 */
	private Long parentId;

	/**
	 * 字段：英文名，用于创建目录、规则
	 *
	 * @haoxz11MyBatis
	 */
	private String eName;

	/**
	 * 字段：规则路径（由于是否生成文件，导致该字段可能无法使用，预留）
	 *
	 * @haoxz11MyBatis
	 */
	private String rulePath;

	/**
	 * 字段：输入项,可用于传参完整性校验
	 *
	 * @haoxz11MyBatis
	 */
	private String input;

	/**
	 * 字段：输出项，保留项，目前没用
	 *
	 * @haoxz11MyBatis
	 */
	private String output;

	/**
	 * 字段：是否生成索引，0--不生成，1--生成
	 *
	 * @haoxz11MyBatis
	 */
	private String indexFlag;

	/**
	 * 字段：代码是否生成文件，0--不生成，1--生成，若为目录，可选则不生成
	 *
	 * @haoxz11MyBatis
	 */
	private String isCreate;

	/**
	 * 字段：创建日期
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
	 * 读取：规则id
	 *
	 * @return sys_rule.id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 设置：规则id
	 *
	 * @param id sys_rule.id
	 *
	 * @haoxz11MyBatis
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 读取：规则名称
	 *
	 * @return sys_rule.rule_name
	 *
	 * @haoxz11MyBatis
	 */
	public String getRuleName() {
		return ruleName;
	}

	/**
	 * 设置：规则名称
	 *
	 * @param ruleName sys_rule.rule_name
	 *
	 * @haoxz11MyBatis
	 */
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	/**
	 * 读取：详细描述
	 *
	 * @return sys_rule.detaile
	 *
	 * @haoxz11MyBatis
	 */
	public String getDetaile() {
		return detaile;
	}

	/**
	 * 设置：详细描述
	 *
	 * @param detaile sys_rule.detaile
	 *
	 * @haoxz11MyBatis
	 */
	public void setDetaile(String detaile) {
		this.detaile = detaile;
	}

	/**
	 * 读取：规则类型（如1.目录，2.规则--每个规则为一个java）
	 *
	 * @return sys_rule.rule_type
	 *
	 * @haoxz11MyBatis
	 */
	public String getRuleType() {
		return ruleType;
	}

	/**
	 * 设置：规则类型（如1.目录，2.规则--每个规则为一个java）
	 *
	 * @param ruleType sys_rule.rule_type
	 *
	 * @haoxz11MyBatis
	 */
	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}

	/**
	 * 读取：创建者
	 *
	 * @return sys_rule.creater
	 *
	 * @haoxz11MyBatis
	 */
	public String getCreater() {
		return creater;
	}

	/**
	 * 设置：创建者
	 *
	 * @param creater sys_rule.creater
	 *
	 * @haoxz11MyBatis
	 */
	public void setCreater(String creater) {
		this.creater = creater;
	}

	/**
	 * 读取：是否有效
	 *
	 * @return sys_rule.active
	 *
	 * @haoxz11MyBatis
	 */
	public String getActive() {
		return active;
	}

	/**
	 * 设置：是否有效
	 *
	 * @param active sys_rule.active
	 *
	 * @haoxz11MyBatis
	 */
	public void setActive(String active) {
		this.active = active;
	}

	/**
	 * 读取：父节点id
	 *
	 * @return sys_rule.parent_id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getParentId() {
		return parentId;
	}

	/**
	 * 设置：父节点id
	 *
	 * @param parentId sys_rule.parent_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	/**
	 * 读取：英文名，用于创建目录、规则
	 *
	 * @return sys_rule.e_name
	 *
	 * @haoxz11MyBatis
	 */
	public String geteName() {
		return eName;
	}

	/**
	 * 设置：英文名，用于创建目录、规则
	 *
	 * @param eName sys_rule.e_name
	 *
	 * @haoxz11MyBatis
	 */
	public void seteName(String eName) {
		this.eName = eName;
	}

	/**
	 * 读取：规则路径（由于是否生成文件，导致该字段可能无法使用，预留）
	 *
	 * @return sys_rule.rule_path
	 *
	 * @haoxz11MyBatis
	 */
	public String getRulePath() {
		return rulePath;
	}

	/**
	 * 设置：规则路径（由于是否生成文件，导致该字段可能无法使用，预留）
	 *
	 * @param rulePath sys_rule.rule_path
	 *
	 * @haoxz11MyBatis
	 */
	public void setRulePath(String rulePath) {
		this.rulePath = rulePath;
	}

	/**
	 * 读取：输入项,可用于传参完整性校验
	 *
	 * @return sys_rule.input
	 *
	 * @haoxz11MyBatis
	 */
	public String getInput() {
		return input;
	}

	/**
	 * 设置：输入项,可用于传参完整性校验
	 *
	 * @param input sys_rule.input
	 *
	 * @haoxz11MyBatis
	 */
	public void setInput(String input) {
		this.input = input;
	}

	/**
	 * 读取：输出项，保留项，目前没用
	 *
	 * @return sys_rule.output
	 *
	 * @haoxz11MyBatis
	 */
	public String getOutput() {
		return output;
	}

	/**
	 * 设置：输出项，保留项，目前没用
	 *
	 * @param output sys_rule.output
	 *
	 * @haoxz11MyBatis
	 */
	public void setOutput(String output) {
		this.output = output;
	}

	/**
	 * 读取：是否生成索引，0--不生成，1--生成
	 *
	 * @return sys_rule.index_flag
	 *
	 * @haoxz11MyBatis
	 */
	public String getIndexFlag() {
		return indexFlag;
	}

	/**
	 * 设置：是否生成索引，0--不生成，1--生成
	 *
	 * @param indexFlag sys_rule.index_flag
	 *
	 * @haoxz11MyBatis
	 */
	public void setIndexFlag(String indexFlag) {
		this.indexFlag = indexFlag;
	}

	/**
	 * 读取：代码是否生成文件，0--不生成，1--生成，若为目录，可选则不生成
	 *
	 * @return sys_rule.is_create
	 *
	 * @haoxz11MyBatis
	 */
	public String getIsCreate() {
		return isCreate;
	}

	/**
	 * 设置：代码是否生成文件，0--不生成，1--生成，若为目录，可选则不生成
	 *
	 * @param isCreate sys_rule.is_create
	 *
	 * @haoxz11MyBatis
	 */
	public void setIsCreate(String isCreate) {
		this.isCreate = isCreate;
	}

	/**
	 * 读取：创建日期
	 *
	 * @return sys_rule.create_time
	 *
	 * @haoxz11MyBatis
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 设置：创建日期
	 *
	 * @param createTime sys_rule.create_time
	 *
	 * @haoxz11MyBatis
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 读取：修改时间
	 *
	 * @return sys_rule.modify_time
	 *
	 * @haoxz11MyBatis
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * 设置：修改时间
	 *
	 * @param modifyTime sys_rule.modify_time
	 *
	 * @haoxz11MyBatis
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
}