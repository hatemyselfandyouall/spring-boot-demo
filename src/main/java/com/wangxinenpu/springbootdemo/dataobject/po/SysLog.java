package com.wangxinenpu.springbootdemo.dataobject.po;

import star.vo.BaseVo;

import java.util.Date;

/**
 * 
 * 系统日志
 * @author haoxz11MyBatis 
 * @created Sat Mar 30 01:44:16 CST 2019
 * @version $Id: DefaultCommentGenerator.java,v 1.1 2013/10/28 07:59:58 haoxz11 Exp $
 * @haoxz11MyBatis
 */
public class SysLog extends BaseVo {
	/**
	 *
	 * @haoxz11MyBatis
	 */
	transient private static final long serialVersionUID = -1L;

	/**
	 * 字段：日志id
	 *
	 * @haoxz11MyBatis
	 */
	private Long id;

	/**
	 * 字段：功能id
	 *
	 * @haoxz11MyBatis
	 */
	private Long functionId;

	/**
	 * 字段：事件类型（debug调试、info 提示、warn 警告、error 错误、fatal 严重错误）
	 *
	 * @haoxz11MyBatis
	 */
	private String eventType;

	/**
	 * 字段：事件内容
	 *
	 * @haoxz11MyBatis
	 */
	private String content;

	/**
	 * 字段：发生时间
	 *
	 * @haoxz11MyBatis
	 */
	private Date occurTime;

	/**
	 * 字段：用户id
	 *
	 * @haoxz11MyBatis
	 */
	private Long userId;

	/**
	 * 字段：操作日志id
	 *
	 * @haoxz11MyBatis
	 */
	private String operatelogId;

	/**
	 * 字段：登录ip
	 *
	 * @haoxz11MyBatis
	 */
	private String logonIp;

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
	 * 读取：日志id
	 *
	 * @return sys_log.id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 设置：日志id
	 *
	 * @param id sys_log.id
	 *
	 * @haoxz11MyBatis
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 读取：功能id
	 *
	 * @return sys_log.function_id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getFunctionId() {
		return functionId;
	}

	/**
	 * 设置：功能id
	 *
	 * @param functionId sys_log.function_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setFunctionId(Long functionId) {
		this.functionId = functionId;
	}

	/**
	 * 读取：事件类型（debug调试、info 提示、warn 警告、error 错误、fatal 严重错误）
	 *
	 * @return sys_log.event_type
	 *
	 * @haoxz11MyBatis
	 */
	public String getEventType() {
		return eventType;
	}

	/**
	 * 设置：事件类型（debug调试、info 提示、warn 警告、error 错误、fatal 严重错误）
	 *
	 * @param eventType sys_log.event_type
	 *
	 * @haoxz11MyBatis
	 */
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	/**
	 * 读取：事件内容
	 *
	 * @return sys_log.content
	 *
	 * @haoxz11MyBatis
	 */
	public String getContent() {
		return content;
	}

	/**
	 * 设置：事件内容
	 *
	 * @param content sys_log.content
	 *
	 * @haoxz11MyBatis
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 读取：发生时间
	 *
	 * @return sys_log.occur_time
	 *
	 * @haoxz11MyBatis
	 */
	public Date getOccurTime() {
		return occurTime;
	}

	/**
	 * 设置：发生时间
	 *
	 * @param occurTime sys_log.occur_time
	 *
	 * @haoxz11MyBatis
	 */
	public void setOccurTime(Date occurTime) {
		this.occurTime = occurTime;
	}

	/**
	 * 读取：用户id
	 *
	 * @return sys_log.user_id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * 设置：用户id
	 *
	 * @param userId sys_log.user_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * 读取：操作日志id
	 *
	 * @return sys_log.operatelog_id
	 *
	 * @haoxz11MyBatis
	 */
	public String getOperatelogId() {
		return operatelogId;
	}

	/**
	 * 设置：操作日志id
	 *
	 * @param operatelogId sys_log.operatelog_id
	 *
	 * @haoxz11MyBatis
	 */
	public void setOperatelogId(String operatelogId) {
		this.operatelogId = operatelogId;
	}

	/**
	 * 读取：登录ip
	 *
	 * @return sys_log.logon_ip
	 *
	 * @haoxz11MyBatis
	 */
	public String getLogonIp() {
		return logonIp;
	}

	/**
	 * 设置：登录ip
	 *
	 * @param logonIp sys_log.logon_ip
	 *
	 * @haoxz11MyBatis
	 */
	public void setLogonIp(String logonIp) {
		this.logonIp = logonIp;
	}

	/**
	 * 读取：创建日期
	 *
	 * @return sys_log.create_time
	 *
	 * @haoxz11MyBatis
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 设置：创建日期
	 *
	 * @param createTime sys_log.create_time
	 *
	 * @haoxz11MyBatis
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 读取：修改时间
	 *
	 * @return sys_log.modify_time
	 *
	 * @haoxz11MyBatis
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * 设置：修改时间
	 *
	 * @param modifyTime sys_log.modify_time
	 *
	 * @haoxz11MyBatis
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
}