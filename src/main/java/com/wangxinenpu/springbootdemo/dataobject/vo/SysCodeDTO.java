package com.wangxinenpu.springbootdemo.dataobject.vo;

import star.vo.BaseVo;

import java.util.Date;

/**
 * 
 * 代码维护表
 * @author haoxz11MyBatis 
 * @created Fri Apr 19 10:59:43 CST 2019
 * @version $Id: DefaultCommentGenerator.java,v 1.1 2013/10/28 07:59:58 haoxz11 Exp $
 * @haoxz11MyBatis
 */
public class SysCodeDTO extends BaseVo {
	/**
	 *
	 * @haoxz11MyBatis
	 */
	transient private static final long serialVersionUID = -1L;

	/**
	 * 字段：代码id
	 *
	 * @haoxz11MyBatis
	 */
	private Long id;

	/**
	 * 字段：代码类别
	 *
	 * @haoxz11MyBatis
	 */
	private String codeType;

	/**
	 * 字段：代码值
	 *
	 * @haoxz11MyBatis
	 */
	private String codeValue;

	/**
	 * 字段：代码名称
	 *
	 * @haoxz11MyBatis
	 */
	private String codeName;

	/**
	 * 字段：参数分类
	 *
	 * @haoxz11MyBatis
	 */
	private String parameterSort;

	/**
	 * 字段：当前有效标志::1－有效，0－无效
	 *
	 * @haoxz11MyBatis
	 */
	private Integer effectiveSign;

	/**
	 * 字段：代码可维护标志::1－可维护，0－不可维护
	 *
	 * @haoxz11MyBatis
	 */
	private Integer maintainSign;

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
	 * 读取：代码id
	 *
	 * @return sys_code.id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 设置：代码id
	 *
	 * @param id sys_code.id
	 *
	 * @haoxz11MyBatis
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 读取：代码类别
	 *
	 * @return sys_code.code_type
	 *
	 * @haoxz11MyBatis
	 */
	public String getCodeType() {
		return codeType;
	}

	/**
	 * 设置：代码类别
	 *
	 * @param codeType sys_code.code_type
	 *
	 * @haoxz11MyBatis
	 */
	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}

	/**
	 * 读取：代码值
	 *
	 * @return sys_code.code_value
	 *
	 * @haoxz11MyBatis
	 */
	public String getCodeValue() {
		return codeValue;
	}

	/**
	 * 设置：代码值
	 *
	 * @param codeValue sys_code.code_value
	 *
	 * @haoxz11MyBatis
	 */
	public void setCodeValue(String codeValue) {
		this.codeValue = codeValue;
	}

	/**
	 * 读取：代码名称
	 *
	 * @return sys_code.code_name
	 *
	 * @haoxz11MyBatis
	 */
	public String getCodeName() {
		return codeName;
	}

	/**
	 * 设置：代码名称
	 *
	 * @param codeName sys_code.code_name
	 *
	 * @haoxz11MyBatis
	 */
	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	/**
	 * 读取：参数分类
	 *
	 * @return sys_code.parameter_sort
	 *
	 * @haoxz11MyBatis
	 */
	public String getParameterSort() {
		return parameterSort;
	}

	/**
	 * 设置：参数分类
	 *
	 * @param parameterSort sys_code.parameter_sort
	 *
	 * @haoxz11MyBatis
	 */
	public void setParameterSort(String parameterSort) {
		this.parameterSort = parameterSort;
	}

	/**
	 * 读取：当前有效标志::1－有效，0－无效
	 *
	 * @return sys_code.effective_sign
	 *
	 * @haoxz11MyBatis
	 */
	public Integer getEffectiveSign() {
		return effectiveSign;
	}

	/**
	 * 设置：当前有效标志::1－有效，0－无效
	 *
	 * @param effectiveSign sys_code.effective_sign
	 *
	 * @haoxz11MyBatis
	 */
	public void setEffectiveSign(Integer effectiveSign) {
		this.effectiveSign = effectiveSign;
	}

	/**
	 * 读取：代码可维护标志::1－可维护，0－不可维护
	 *
	 * @return sys_code.maintain_sign
	 *
	 * @haoxz11MyBatis
	 */
	public Integer getMaintainSign() {
		return maintainSign;
	}

	/**
	 * 设置：代码可维护标志::1－可维护，0－不可维护
	 *
	 * @param maintainSign sys_code.maintain_sign
	 *
	 * @haoxz11MyBatis
	 */
	public void setMaintainSign(Integer maintainSign) {
		this.maintainSign = maintainSign;
	}

	/**
	 * 读取：创建时间
	 *
	 * @return sys_code.create_time
	 *
	 * @haoxz11MyBatis
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 设置：创建时间
	 *
	 * @param createTime sys_code.create_time
	 *
	 * @haoxz11MyBatis
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 读取：修改时间
	 *
	 * @return sys_code.modify_time
	 *
	 * @haoxz11MyBatis
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * 设置：修改时间
	 *
	 * @param modifyTime sys_code.modify_time
	 *
	 * @haoxz11MyBatis
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
}