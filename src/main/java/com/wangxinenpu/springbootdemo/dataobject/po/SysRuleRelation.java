package com.wangxinenpu.springbootdemo.dataobject.po;

import star.vo.BaseVo;

import java.util.Date;

/**
 * 
 * 规则关系表
 * @author haoxz11MyBatis 
 * @created Sat Mar 30 01:44:16 CST 2019
 * @version $Id: DefaultCommentGenerator.java,v 1.1 2013/10/28 07:59:58 haoxz11 Exp $
 * @haoxz11MyBatis
 */
public class SysRuleRelation extends BaseVo {
	/**
	 *
	 * @haoxz11MyBatis
	 */
	transient private static final long serialVersionUID = -1L;

	/**
	 * 字段：规则关系id
	 *
	 * @haoxz11MyBatis
	 */
	private Long id;

	/**
	 * 字段：规则id
	 *
	 * @haoxz11MyBatis
	 */
	private Long ruleId;

	/**
	 * 字段：模块id
	 *
	 * @haoxz11MyBatis
	 */
	private Long functionId;

	/**
	 * 字段：字段名，例如：form1.aac001
	 *
	 * @haoxz11MyBatis
	 */
	private String fieldName;

	/**
	 * 字段：统筹区，如330220,330333（若绑定统筹区，则只有该统筹区能进行校验，否则该规则是通用规则，所有统筹区都能校验）
	 *
	 * @haoxz11MyBatis
	 */
	private Integer aaa027;

	/**
	 * 字段：顺序号，预留
	 *
	 * @haoxz11MyBatis
	 */
	private Long ordern;

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
	 * 读取：规则关系id
	 *
	 * @return sys_rule_relation.id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 设置：规则关系id
	 *
	 * @param id sys_rule_relation.id
	 *
	 * @haoxz11MyBatis
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 读取：规则id
	 *
	 * @return sys_rule_relation.rule_id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getRuleId() {
		return ruleId;
	}

	/**
	 * 设置：规则id
	 *
	 * @param ruleId sys_rule_relation.rule_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setRuleId(Long ruleId) {
		this.ruleId = ruleId;
	}

	/**
	 * 读取：模块id
	 *
	 * @return sys_rule_relation.function_id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getFunctionId() {
		return functionId;
	}

	/**
	 * 设置：模块id
	 *
	 * @param functionId sys_rule_relation.function_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setFunctionId(Long functionId) {
		this.functionId = functionId;
	}

	/**
	 * 读取：字段名，例如：form1.aac001
	 *
	 * @return sys_rule_relation.field_name
	 *
	 * @haoxz11MyBatis
	 */
	public String getFieldName() {
		return fieldName;
	}

	/**
	 * 设置：字段名，例如：form1.aac001
	 *
	 * @param fieldName sys_rule_relation.field_name
	 *
	 * @haoxz11MyBatis
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	/**
	 * 读取：统筹区，如330220,330333（若绑定统筹区，则只有该统筹区能进行校验，否则该规则是通用规则，所有统筹区都能校验）
	 *
	 * @return sys_rule_relation.aaa027
	 *
	 * @haoxz11MyBatis
	 */
	public Integer getAaa027() {
		return aaa027;
	}

	/**
	 * 设置：统筹区，如330220,330333（若绑定统筹区，则只有该统筹区能进行校验，否则该规则是通用规则，所有统筹区都能校验）
	 *
	 * @param aaa027 sys_rule_relation.aaa027
	 *
	 * @haoxz11MyBatis
	 */
	public void setAaa027(Integer aaa027) {
		this.aaa027 = aaa027;
	}

	/**
	 * 读取：顺序号，预留
	 *
	 * @return sys_rule_relation.ordern
	 *
	 * @haoxz11MyBatis
	 */
	public Long getOrdern() {
		return ordern;
	}

	/**
	 * 设置：顺序号，预留
	 *
	 * @param ordern sys_rule_relation.ordern
	 *
	 * @haoxz11MyBatis
	 */
	public void setOrdern(Long ordern) {
		this.ordern = ordern;
	}

	/**
	 * 读取：创建日期
	 *
	 * @return sys_rule_relation.create_time
	 *
	 * @haoxz11MyBatis
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 设置：创建日期
	 *
	 * @param createTime sys_rule_relation.create_time
	 *
	 * @haoxz11MyBatis
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 读取：修改时间
	 *
	 * @return sys_rule_relation.modify_time
	 *
	 * @haoxz11MyBatis
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * 设置：修改时间
	 *
	 * @param modifyTime sys_rule_relation.modify_time
	 *
	 * @haoxz11MyBatis
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
}