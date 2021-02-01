package com.wangxinenpu.springbootdemo.dataobject.vo;

import star.vo.BaseVo;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * 
 * 系统机构表
 * @author haoxz11MyBatis 
 * @created Mon Mar 25 14:59:55 CST 2019
 * @version $Id: DefaultCommentGenerator.java,v 1.1 2013/10/28 07:59:58 haoxz11 Exp $
 * @haoxz11MyBatis
 */
public class SysOrgDTO extends BaseVo {
	/**
	 *
	 * @haoxz11MyBatis
	 */
	transient private static final long serialVersionUID = -1L;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	/**
	 * 天正一体化机构id
	 */
	private String uuid;

	/**
	 * 机构类型1：区域2：单位3：部门/科室4：下属单位5：乡镇街道6：村、社区7：网格9：虚拟组织
	 */
	private String orgType;

	/**
	 * 字段：机构id(系统生成,唯一关键字)
	 *
	 * @haoxz11MyBatis
	 */
	@Id
	@GeneratedValue(generator="JDBC")
	private Long id;

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * 字段：机构名称

	 *
	 * @haoxz11MyBatis
	 */
	private String orgName;

	/**
	 * 字段：机构代码(即机构的编号，输入的编号)
	 *
	 * @haoxz11MyBatis
	 */
	private String orgenterCode;


	/**
	 * 字段：父机构id
	 *
	 * @haoxz11MyBatis
	 */
	private Long parentId;

	/**
	 * 字段：机构简称
	 *
	 * @haoxz11MyBatis
	 */
	private String shortName;

	/**
	 * 字段：行政区划代码
	 *
	 * @haoxz11MyBatis
	 */
	private String regionCode;

	/**
	 * 字段：机构负责人
	 *
	 * @haoxz11MyBatis
	 */
	private String leader;

	/**
	 * 字段：联系人
	 *
	 * @haoxz11MyBatis
	 */
	private String linkman;

	/**
	 * 字段：联系电话
	 *
	 * @haoxz11MyBatis
	 */
	private String tel;

	/**
	 * 字段：机构地址
	 *
	 * @haoxz11MyBatis
	 */
	private String orgAddr;

	/**
	 * 字段：机构描述
	 *
	 * @haoxz11MyBatis
	 */
	private String orgDesc;

	/**
	 * 字段：在同一级机构中的序号
	 *
	 * @haoxz11MyBatis
	 */
	private Integer orgOrder;

	/**
	 * 字段：机构状态,即有效标志：1、有效，0、无效
	 *
	 * @haoxz11MyBatis
	 */
	private String orgState;

	/**
	 * 字段：主管部门
	 *
	 * @haoxz11MyBatis
	 */
	private String superdept;

	/**
	 * 字段：系统机构编码（自生成的编号）
	 *
	 * @haoxz11MyBatis
	 */
	private String orgautoCode;

	/**
	 * 字段：邮编
	 *
	 * @haoxz11MyBatis
	 */
	private String zip;

	/**
	 * 字段：路径
	 *
	 * @haoxz11MyBatis
	 */
	private String idpath;

	/**
	 * 字段：类型
	 *
	 * @haoxz11MyBatis
	 */
	private Integer type;
	
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
	 * 险种名称
	 *
	 * @haoxz11MyBatis
	 */
	private String orgTypeName;

	/**
	 * 区域名称
	 *
	 * @haoxz11MyBatis
	 */
	private String areaName;

	/**
	 * 统一社会信用代码
	 *
	 * @haoxz11MyBatis
	 */
	private String orgCode;
	
	/**
	 * 审核配置同步：0 未同步 1 已同步
	 */
	private int isSynchronization;

	public String getYthRegionCode() {
		return ythRegionCode;
	}

	public void setYthRegionCode(String ythRegionCode) {
		this.ythRegionCode = ythRegionCode;
	}

	public String getOrgTag() {
		return orgTag;
	}

	public void setOrgTag(String orgTag) {
		this.orgTag = orgTag;
	}

	/**
	 * 天正行政区划编码
	 * @haoxz11MyBatis
	 */
	private String ythRegionCode;

	/**
	 * 机构标签
	 * @haoxz11MyBatis
	 */
	private String orgTag;

	/**
	 * 读取：机构id(系统生成,唯一关键字)
	 *
	 * @return sys_org.id
	 *
	 * @haoxz11MyBatis
	 */

	public Long getId() {
		return id;
	}

	/**
	 * 设置：机构id(系统生成,唯一关键字)
	 *
	 * @param id sys_org.id
	 *
	 * @haoxz11MyBatis
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 读取：机构名称
	 *
	 * @return sys_org.org_name
	 *
	 * @haoxz11MyBatis
	 */
	public String getOrgName() {
		return orgName;
	}

	/**
	 * 设置：机构名称
	 *
	 * @param orgName sys_org.org_name
	 *
	 * @haoxz11MyBatis
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	/**
	 * 读取：机构代码(即机构的编号，输入的编号)
	 *
	 * @return sys_org.orgenter_code
	 *
	 * @haoxz11MyBatis
	 */
	public String getOrgenterCode() {
		return orgenterCode;
	}

	/**
	 * 设置：机构代码(即机构的编号，输入的编号)
	 *
	 * @param orgenterCode sys_org.orgenter_code
	 *
	 * @haoxz11MyBatis
	 */
	public void setOrgenterCode(String orgenterCode) {
		this.orgenterCode = orgenterCode;
	}

	/**
	 * 读取：父机构id
	 *
	 * @return sys_org.parent_id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getParentId() {
		return parentId;
	}

	/**
	 * 设置：父机构id
	 *
	 * @param parentId sys_org.parent_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	/**
	 * 读取：机构简称
	 *
	 * @return sys_org.short_name
	 *
	 * @haoxz11MyBatis
	 */
	public String getShortName() {
		return shortName;
	}

	/**
	 * 设置：机构简称
	 *
	 * @param shortName sys_org.short_name
	 *
	 * @haoxz11MyBatis
	 */
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	/**
	 * 读取：行政区划代码
	 *
	 * @return sys_org.region_code
	 *
	 * @haoxz11MyBatis
	 */
	public String getRegionCode() {
		return regionCode;
	}

	/**
	 * 设置：行政区划代码
	 *
	 * @param regionCode sys_org.region_code
	 *
	 * @haoxz11MyBatis
	 */
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	/**
	 * 读取：机构负责人
	 *
	 * @return sys_org.leader
	 *
	 * @haoxz11MyBatis
	 */
	public String getLeader() {
		return leader;
	}

	/**
	 * 设置：机构负责人
	 *
	 * @param leader sys_org.leader
	 *
	 * @haoxz11MyBatis
	 */
	public void setLeader(String leader) {
		this.leader = leader;
	}

	/**
	 * 读取：联系人
	 *
	 * @return sys_org.linkman
	 *
	 * @haoxz11MyBatis
	 */
	public String getLinkman() {
		return linkman;
	}

	/**
	 * 设置：联系人
	 *
	 * @param linkman sys_org.linkman
	 *
	 * @haoxz11MyBatis
	 */
	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	/**
	 * 读取：联系电话
	 *
	 * @return sys_org.tel
	 *
	 * @haoxz11MyBatis
	 */
	public String getTel() {
		return tel;
	}

	/**
	 * 设置：联系电话
	 *
	 * @param tel sys_org.tel
	 *
	 * @haoxz11MyBatis
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}

	/**
	 * 读取：机构地址
	 *
	 * @return sys_org.org_addr
	 *
	 * @haoxz11MyBatis
	 */
	public String getOrgAddr() {
		return orgAddr;
	}

	/**
	 * 设置：机构地址
	 *
	 * @param orgAddr sys_org.org_addr
	 *
	 * @haoxz11MyBatis
	 */
	public void setOrgAddr(String orgAddr) {
		this.orgAddr = orgAddr;
	}

	/**
	 * 读取：机构描述
	 *
	 * @return sys_org.org_desc
	 *
	 * @haoxz11MyBatis
	 */
	public String getOrgDesc() {
		return orgDesc;
	}

	/**
	 * 设置：机构描述
	 *
	 * @param orgDesc sys_org.org_desc
	 *
	 * @haoxz11MyBatis
	 */
	public void setOrgDesc(String orgDesc) {
		this.orgDesc = orgDesc;
	}

	/**
	 * 读取：在同一级机构中的序号
	 *
	 * @return sys_org.org_order
	 *
	 * @haoxz11MyBatis
	 */
	public Integer getOrgOrder() {
		return orgOrder;
	}

	/**
	 * 设置：在同一级机构中的序号
	 *
	 * @param orgOrder sys_org.org_order
	 *
	 * @haoxz11MyBatis
	 */
	public void setOrgOrder(Integer orgOrder) {
		this.orgOrder = orgOrder;
	}

	/**
	 * 读取：机构状态,即有效标志：1、有效，0、无效
	 *
	 * @return sys_org.org_state
	 *
	 * @haoxz11MyBatis
	 */
	public String getOrgState() {
		return orgState;
	}

	/**
	 * 设置：机构状态,即有效标志：1、有效，0、无效
	 *
	 * @param orgState sys_org.org_state
	 *
	 * @haoxz11MyBatis
	 */
	public void setOrgState(String orgState) {
		this.orgState = orgState;
	}

	/**
	 * 读取：主管部门
	 *
	 * @return sys_org.superdept
	 *
	 * @haoxz11MyBatis
	 */
	public String getSuperdept() {
		return superdept;
	}

	/**
	 * 设置：主管部门
	 *
	 * @param superdept sys_org.superdept
	 *
	 * @haoxz11MyBatis
	 */
	public void setSuperdept(String superdept) {
		this.superdept = superdept;
	}

	/**
	 * 读取：系统机构编码（自生成的编号）
	 *
	 * @return sys_org.orgauto_code
	 *
	 * @haoxz11MyBatis
	 */
	public String getOrgautoCode() {
		return orgautoCode;
	}

	/**
	 * 设置：系统机构编码（自生成的编号）
	 *
	 * @param orgautoCode sys_org.orgauto_code
	 *
	 * @haoxz11MyBatis
	 */
	public void setOrgautoCode(String orgautoCode) {
		this.orgautoCode = orgautoCode;
	}

	/**
	 * 读取：邮编
	 *
	 * @return sys_org.zip
	 *
	 * @haoxz11MyBatis
	 */
	public String getZip() {
		return zip;
	}

	/**
	 * 设置：邮编
	 *
	 * @param zip sys_org.zip
	 *
	 * @haoxz11MyBatis
	 */
	public void setZip(String zip) {
		this.zip = zip;
	}

	/**
	 * 读取：路径
	 *
	 * @return sys_org.idpath
	 *
	 * @haoxz11MyBatis
	 */
	public String getIdpath() {
		return idpath;
	}

	/**
	 * 设置：路径
	 *
	 * @param idpath sys_org.idpath
	 *
	 * @haoxz11MyBatis
	 */
	public void setIdpath(String idpath) {
		this.idpath = idpath;
	}

	/**
	 * 读取：创建日期
	 *
	 * @return sys_org.create_time
	 *
	 * @haoxz11MyBatis
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 设置：创建日期
	 *
	 * @param createTime sys_org.create_time
	 *
	 * @haoxz11MyBatis
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 读取：修改时间
	 *
	 * @return sys_org.modify_time
	 *
	 * @haoxz11MyBatis
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	public String getOrgTypeName() {
		return orgTypeName;
	}

	public void setOrgTypeName(String orgTypeName) {
		this.orgTypeName = orgTypeName;
	}

	/**
	 * 设置：修改时间
	 *
	 * @param modifyTime sys_org.modify_time
	 *
	 * @haoxz11MyBatis
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public int getIsSynchronization() {
		return isSynchronization;
	}

	public void setIsSynchronization(int isSynchronization) {
		this.isSynchronization = isSynchronization;
	}
	
}