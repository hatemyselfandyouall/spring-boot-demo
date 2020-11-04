package com.wangxinenpu.springbootdemo.dataobject.vo;

import lombok.Data;
import star.vo.BaseVo;

/**
 * 
 * 区域表
 * @author haoxz11MyBatis 
 * @created Sat Mar 30 01:44:16 CST 2019
 * @version $Id: DefaultCommentGenerator.java,v 1.1 2013/10/28 07:59:58 haoxz11 Exp $
 * @haoxz11MyBatis
 */
@Data
public class SysAreaNameDTO extends BaseVo {
	/**
	 *
	 * @haoxz11MyBatis
	 */
	transient private static final long serialVersionUID = -1L;


	/**
	 * 字段：区域名称(市)
	 *
	 * @haoxz11MyBatis
	 */
	private String city;
	/**
	 * 字段：区域名称（区）
	 *
	 * @haoxz11MyBatis
	 */
	private String region;

}