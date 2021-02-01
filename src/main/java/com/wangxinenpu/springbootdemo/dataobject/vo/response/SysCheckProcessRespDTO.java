package com.wangxinenpu.springbootdemo.dataobject.vo.response;

import star.util.StringUtil;
import star.vo.BaseVo;

/**
 * 
 * 审核流程表
 * @author haoxz11MyBatis 
 * @created Tue May 28 01:26:21 CST 2019
 * @version $Id: DefaultCommentGenerator.java,v 1.1 2013/10/28 07:59:58 haoxz11 Exp $
 * @haoxz11MyBatis
 */
public class SysCheckProcessRespDTO extends BaseVo {
	/**
	 *
	 * @haoxz11MyBatis
	 */
	transient private static final long serialVersionUID = -1L;


	/**
	 * 字段：步骤
	 */
	private String step;
	/**
	 * 字段：审核配置ID
	 */
	private Long configId;
	
	/**
	 * 字段：版本号
	 */
	private String version;
	
	
	

	public SysCheckProcessRespDTO() {
		super();
	}
	public SysCheckProcessRespDTO(String step, Long configId, String version) {
		super();
		this.step = step;
		this.configId = configId;
		this.version = version;
	}
	
	/**
	 * 获取上级审核流程的数据对象
	 * @user xxh
	 * @since 2020年6月8日下午5:19:39
	 */
	public SysCheckProcessRespDTO doPreSysCheckProcessRespDTO() {
		int mstep = 0;
		if(StringUtil.isNotEmpty(step) && Integer.valueOf(step).intValue()>0) {//step >0
			mstep = Integer.valueOf(step).intValue()-1;
			return new SysCheckProcessRespDTO(String.valueOf(mstep>0?mstep:0), configId, version);
		}
		return null;
	}
	
	/**
	 * 获取上级审核流程的数据对象 0
	 * @return
	 */
	public SysCheckProcessRespDTO doMyStep0SysCheckProcessRespDTO() {
		int mstep = 0;
		if(StringUtil.isNotEmpty(step) && Integer.valueOf(step).intValue()==0) {//step =0
			return new SysCheckProcessRespDTO(String.valueOf(mstep), configId, version);
		}
		return null;
	}

	public String getStep() {
		return step;
	}

	public void setStep(String step) {
		this.step = step;
	}

	public Long getConfigId() {
		return configId;
	}

	public void setConfigId(Long configId) {
		this.configId = configId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
	
	

}