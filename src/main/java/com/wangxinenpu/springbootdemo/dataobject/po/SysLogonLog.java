package com.wangxinenpu.springbootdemo.dataobject.po;

import star.vo.BaseVo;

import java.util.Date;

/**
 * 
 * 系统登录日志
 * @author haoxz11MyBatis 
 * @created Sat Mar 30 01:44:16 CST 2019
 * @version $Id: DefaultCommentGenerator.java,v 1.1 2013/10/28 07:59:58 haoxz11 Exp $
 * @haoxz11MyBatis
 */
public class SysLogonLog extends BaseVo {
	/**
	 *
	 * @haoxz11MyBatis
	 */
	transient private static final long serialVersionUID = -1L;

	/**
	 * 字段：登录日志id
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
	 * 字段：登录ip
	 *
	 * @haoxz11MyBatis
	 */
	private String logonIp;

	/**
	 * 字段：上线时间
	 *
	 * @haoxz11MyBatis
	 */
	private Date logonTime;

	/**
	 * 字段：下线时间
	 *
	 * @haoxz11MyBatis
	 */
	private Date logoffTime;

	/**
	 * 字段：登录成功失败标志，1为成功，0为失败
	 *
	 * @haoxz11MyBatis
	 */
	private String successFlag;

	/**
	 * 字段：登录失败原因
	 *
	 * @haoxz11MyBatis
	 */
	private String failReason;

	/**
	 * 字段：下线原因，主要是session超时销毁，还是手动下线
	 *
	 * @haoxz11MyBatis
	 */
	private String logoffReason;

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
	 * 读取：登录日志id
	 *
	 * @return sys_logon_log.id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 设置：登录日志id
	 *
	 * @param id sys_logon_log.id
	 *
	 * @haoxz11MyBatis
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 读取：用户id
	 *
	 * @return sys_logon_log.user_id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * 设置：用户id
	 *
	 * @param userId sys_logon_log.user_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * 读取：登录ip
	 *
	 * @return sys_logon_log.logon_ip
	 *
	 * @haoxz11MyBatis
	 */
	public String getLogonIp() {
		return logonIp;
	}

	/**
	 * 设置：登录ip
	 *
	 * @param logonIp sys_logon_log.logon_ip
	 *
	 * @haoxz11MyBatis
	 */
	public void setLogonIp(String logonIp) {
		this.logonIp = logonIp;
	}

	/**
	 * 读取：上线时间
	 *
	 * @return sys_logon_log.logon_time
	 *
	 * @haoxz11MyBatis
	 */
	public Date getLogonTime() {
		return logonTime;
	}

	/**
	 * 设置：上线时间
	 *
	 * @param logonTime sys_logon_log.logon_time
	 *
	 * @haoxz11MyBatis
	 */
	public void setLogonTime(Date logonTime) {
		this.logonTime = logonTime;
	}

	/**
	 * 读取：下线时间
	 *
	 * @return sys_logon_log.logoff_time
	 *
	 * @haoxz11MyBatis
	 */
	public Date getLogoffTime() {
		return logoffTime;
	}

	/**
	 * 设置：下线时间
	 *
	 * @param logoffTime sys_logon_log.logoff_time
	 *
	 * @haoxz11MyBatis
	 */
	public void setLogoffTime(Date logoffTime) {
		this.logoffTime = logoffTime;
	}

	/**
	 * 读取：登录成功失败标志，1为成功，0为失败
	 *
	 * @return sys_logon_log.success_flag
	 *
	 * @haoxz11MyBatis
	 */
	public String getSuccessFlag() {
		return successFlag;
	}

	/**
	 * 设置：登录成功失败标志，1为成功，0为失败
	 *
	 * @param successFlag sys_logon_log.success_flag
	 *
	 * @haoxz11MyBatis
	 */
	public void setSuccessFlag(String successFlag) {
		this.successFlag = successFlag;
	}

	/**
	 * 读取：登录失败原因
	 *
	 * @return sys_logon_log.fail_reason
	 *
	 * @haoxz11MyBatis
	 */
	public String getFailReason() {
		return failReason;
	}

	/**
	 * 设置：登录失败原因
	 *
	 * @param failReason sys_logon_log.fail_reason
	 *
	 * @haoxz11MyBatis
	 */
	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}

	/**
	 * 读取：下线原因，主要是session超时销毁，还是手动下线
	 *
	 * @return sys_logon_log.logoff_reason
	 *
	 * @haoxz11MyBatis
	 */
	public String getLogoffReason() {
		return logoffReason;
	}

	/**
	 * 设置：下线原因，主要是session超时销毁，还是手动下线
	 *
	 * @param logoffReason sys_logon_log.logoff_reason
	 *
	 * @haoxz11MyBatis
	 */
	public void setLogoffReason(String logoffReason) {
		this.logoffReason = logoffReason;
	}

	/**
	 * 读取：创建日期
	 *
	 * @return sys_logon_log.create_time
	 *
	 * @haoxz11MyBatis
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 设置：创建日期
	 *
	 * @param createTime sys_logon_log.create_time
	 *
	 * @haoxz11MyBatis
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 读取：修改时间
	 *
	 * @return sys_logon_log.modify_time
	 *
	 * @haoxz11MyBatis
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * 设置：修改时间
	 *
	 * @param modifyTime sys_logon_log.modify_time
	 *
	 * @haoxz11MyBatis
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
}