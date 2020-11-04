package com.wangxinenpu.springbootdemo.dataobject.vo.response;


import com.wangxinenpu.springbootdemo.dataobject.vo.root.LogBaseVo;

/**
 * 
 * 系统用户表
 * @author haoxz11MyBatis 
 * @created Thu Mar 21 14:10:27 CST 2019
 * @version $Id: DefaultCommentGenerator.java,v 1.1 2013/10/28 07:59:58 haoxz11 Exp $
 * @haoxz11MyBatis
 */
public class SysUserDisplayNameRespDTO extends LogBaseVo {
	transient private static final long serialVersionUID = -1L;

	/**
	 * 字段：用户ID
	 */
	private Long id;

	/**
	 *显示名
	 * @user xxh
	 * @since 2020年6月10日下午5:27:56
	 */
	private String dName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getdName() {
		return dName;
	}

	public void setdName(String dName) {
		this.dName = dName;
	}

	
	
	

}