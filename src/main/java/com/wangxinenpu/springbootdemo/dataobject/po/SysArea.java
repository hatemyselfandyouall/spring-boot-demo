package com.wangxinenpu.springbootdemo.dataobject.po;

import star.vo.BaseVo;

import java.util.Date;

/**
 * 
 * 区域表
 * @author haoxz11MyBatis 
 * @created Sat Mar 30 01:44:16 CST 2019
 * @version $Id: DefaultCommentGenerator.java,v 1.1 2013/10/28 07:59:58 haoxz11 Exp $
 * @haoxz11MyBatis
 */
public class SysArea extends BaseVo {
	/**
	 *
	 * @haoxz11MyBatis
	 */
	transient private static final long serialVersionUID = -1L;

	/**
	 * 字段：区域ID
	 *
	 * @haoxz11MyBatis
	 */
	private Long id;

	/**
	 * 字段：区域名称
	 *
	 * @haoxz11MyBatis
	 */
	private String areaName;

	/**
	 * 字段：aaa147
	 *
	 * @haoxz11MyBatis
	 */
	private String aaa147;

	/**
	 * 字段：父节点ID
	 *
	 * @haoxz11MyBatis
	 */
	private String parentId;

	/**
	 * 字段：prseno
	 *
	 * @haoxz11MyBatis
	 */
	private Long prseno;

	/**
	 * 字段：eae037
	 *
	 * @haoxz11MyBatis
	 */
	private String eae037;

	/**
	 * 字段：路径
	 *
	 * @haoxz11MyBatis
	 */
	private String idpath;

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
	 * 读取：区域ID
	 *
	 * @return sys_area.id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 设置：区域ID
	 *
	 * @param id sys_area.id
	 *
	 * @haoxz11MyBatis
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 读取：区域名称
	 *
	 * @return sys_area.area_name
	 *
	 * @haoxz11MyBatis
	 */
	public String getAreaName() {
		return areaName;
	}

	/**
	 * 设置：区域名称
	 *
	 * @param areaName sys_area.area_name
	 *
	 * @haoxz11MyBatis
	 */
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	/**
	 * 读取：aaa147
	 *
	 * @return sys_area.aaa147
	 *
	 * @haoxz11MyBatis
	 */
	public String getAaa147() {
		return aaa147;
	}

	/**
	 * 设置：aaa147
	 *
	 * @param aaa147 sys_area.aaa147
	 *
	 * @haoxz11MyBatis
	 */
	public void setAaa147(String aaa147) {
		this.aaa147 = aaa147;
	}

	/**
	 * 读取：父节点ID
	 *
	 * @return sys_area.parent_id
	 *
	 * @haoxz11MyBatis
	 */
	public String getParentId() {
		return parentId;
	}

	/**
	 * 设置：父节点ID
	 *
	 * @param parentId sys_area.parent_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	/**
	 * 读取：prseno
	 *
	 * @return sys_area.prseno
	 *
	 * @haoxz11MyBatis
	 */
	public Long getPrseno() {
		return prseno;
	}

	/**
	 * 设置：prseno
	 *
	 * @param prseno sys_area.prseno
	 *
	 * @haoxz11MyBatis
	 */
	public void setPrseno(Long prseno) {
		this.prseno = prseno;
	}

	/**
	 * 读取：eae037
	 *
	 * @return sys_area.eae037
	 *
	 * @haoxz11MyBatis
	 */
	public String getEae037() {
		return eae037;
	}

	/**
	 * 设置：eae037
	 *
	 * @param eae037 sys_area.eae037
	 *
	 * @haoxz11MyBatis
	 */
	public void setEae037(String eae037) {
		this.eae037 = eae037;
	}

	/**
	 * 读取：路径
	 *
	 * @return sys_area.idpath
	 *
	 * @haoxz11MyBatis
	 */
	public String getIdpath() {
		return idpath;
	}

	/**
	 * 设置：路径
	 *
	 * @param idpath sys_area.idpath
	 *
	 * @haoxz11MyBatis
	 */
	public void setIdpath(String idpath) {
		this.idpath = idpath;
	}

	/**
	 * 读取：创建时间
	 *
	 * @return sys_area.create_time
	 *
	 * @haoxz11MyBatis
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 设置：创建时间
	 *
	 * @param createTime sys_area.create_time
	 *
	 * @haoxz11MyBatis
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 读取：修改时间
	 *
	 * @return sys_area.modify_time
	 *
	 * @haoxz11MyBatis
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * 设置：修改时间
	 *
	 * @param modifyTime sys_area.modify_time
	 *
	 * @haoxz11MyBatis
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
}