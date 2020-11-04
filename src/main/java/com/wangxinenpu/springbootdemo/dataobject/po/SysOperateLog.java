package com.wangxinenpu.springbootdemo.dataobject.po;

import star.vo.BaseVo;

import java.util.Date;

/**
 * 
 * 系统操作日志
 * @author haoxz11MyBatis 
 * @created Sat Mar 30 01:44:16 CST 2019
 * @version $Id: DefaultCommentGenerator.java,v 1.1 2013/10/28 07:59:58 haoxz11 Exp $
 * @haoxz11MyBatis
 */
public class SysOperateLog extends BaseVo {
	/**
	 *
	 * @haoxz11MyBatis
	 */
	transient private static final long serialVersionUID = -1L;

	/**
	 * 字段：操作日志id
	 *
	 * @haoxz11MyBatis
	 */
	private Long id;

	/**
	 * 字段：用户id
	 *
	 * @haoxz11MyBatis
	 */
	private Long userId;

	/**
	 * 字段：登录日志id
	 *
	 * @haoxz11MyBatis
	 */
	private Long logonLogId;

	/**
	 * 字段：登录ip
	 *
	 * @haoxz11MyBatis
	 */
	private String logonIp;

	/**
	 * 字段：操作
	 *
	 * @haoxz11MyBatis
	 */
	private String operate;

	/**
	 * 字段：请求url
	 *
	 * @haoxz11MyBatis
	 */
	private String url;

	/**
	 * 字段：开始时间
	 *
	 * @haoxz11MyBatis
	 */
	private Date beginTime;

	/**
	 * 字段：结束时间
	 *
	 * @haoxz11MyBatis
	 */
	private Date endTime;

	/**
	 * 字段：功能id
	 *
	 * @haoxz11MyBatis
	 */
	private Long functionId;

	/**
	 * 字段：描述
	 *
	 * @haoxz11MyBatis
	 */
	private String description;

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
	 * 读取：操作日志id
	 *
	 * @return sys_operate_log.id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 设置：操作日志id
	 *
	 * @param id sys_operate_log.id
	 *
	 * @haoxz11MyBatis
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 读取：用户id
	 *
	 * @return sys_operate_log.user_id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * 设置：用户id
	 *
	 * @param userId sys_operate_log.user_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * 读取：登录日志id
	 *
	 * @return sys_operate_log.logon_log_id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getLogonLogId() {
		return logonLogId;
	}

	/**
	 * 设置：登录日志id
	 *
	 * @param logonLogId sys_operate_log.logon_log_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setLogonLogId(Long logonLogId) {
		this.logonLogId = logonLogId;
	}

	/**
	 * 读取：登录ip
	 *
	 * @return sys_operate_log.logon_ip
	 *
	 * @haoxz11MyBatis
	 */
	public String getLogonIp() {
		return logonIp;
	}

	/**
	 * 设置：登录ip
	 *
	 * @param logonIp sys_operate_log.logon_ip
	 *
	 * @haoxz11MyBatis
	 */
	public void setLogonIp(String logonIp) {
		this.logonIp = logonIp;
	}

	/**
	 * 读取：操作
	 *
	 * @return sys_operate_log.operate
	 *
	 * @haoxz11MyBatis
	 */
	public String getOperate() {
		return operate;
	}

	/**
	 * 设置：操作
	 *
	 * @param operate sys_operate_log.operate
	 *
	 * @haoxz11MyBatis
	 */
	public void setOperate(String operate) {
		this.operate = operate;
	}

	/**
	 * 读取：请求url
	 *
	 * @return sys_operate_log.url
	 *
	 * @haoxz11MyBatis
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * 设置：请求url
	 *
	 * @param url sys_operate_log.url
	 *
	 * @haoxz11MyBatis
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 读取：开始时间
	 *
	 * @return sys_operate_log.begin_time
	 *
	 * @haoxz11MyBatis
	 */
	public Date getBeginTime() {
		return beginTime;
	}

	/**
	 * 设置：开始时间
	 *
	 * @param beginTime sys_operate_log.begin_time
	 *
	 * @haoxz11MyBatis
	 */
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	/**
	 * 读取：结束时间
	 *
	 * @return sys_operate_log.end_time
	 *
	 * @haoxz11MyBatis
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * 设置：结束时间
	 *
	 * @param endTime sys_operate_log.end_time
	 *
	 * @haoxz11MyBatis
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * 读取：功能id
	 *
	 * @return sys_operate_log.function_id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getFunctionId() {
		return functionId;
	}

	/**
	 * 设置：功能id
	 *
	 * @param functionId sys_operate_log.function_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setFunctionId(Long functionId) {
		this.functionId = functionId;
	}

	/**
	 * 读取：描述
	 *
	 * @return sys_operate_log.description
	 *
	 * @haoxz11MyBatis
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 设置：描述
	 *
	 * @param description sys_operate_log.description
	 *
	 * @haoxz11MyBatis
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 读取：创建日期
	 *
	 * @return sys_operate_log.create_time
	 *
	 * @haoxz11MyBatis
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 设置：创建日期
	 *
	 * @param createTime sys_operate_log.create_time
	 *
	 * @haoxz11MyBatis
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 读取：修改时间
	 *
	 * @return sys_operate_log.modify_time
	 *
	 * @haoxz11MyBatis
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * 设置：修改时间
	 *
	 * @param modifyTime sys_operate_log.modify_time
	 *
	 * @haoxz11MyBatis
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
}