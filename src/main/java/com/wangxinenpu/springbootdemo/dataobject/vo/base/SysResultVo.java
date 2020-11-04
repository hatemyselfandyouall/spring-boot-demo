package com.wangxinenpu.springbootdemo.dataobject.vo.base;

import star.vo.BaseVo;

public class SysResultVo<T> extends BaseVo {
	private static final long serialVersionUID = -1222614520893986846L;
	private T result;
	private boolean isSuccess = false;
	private String code;
	private String resultDes;
	private String msg;
	private String condition;

	
	public static <E> SysResultVo<E> newResultVo() {
		return new SysResultVo<E>();
	}
	
	public T getResult() {
		return this.result;
	}

	public void setResult(T result) {
		this.result = result;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public boolean isSuccess() {
		return this.isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getResultDes() {
		return this.resultDes;
	}

	public void setResultDes(String resultDes) {
		this.resultDes = resultDes;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}
	
	
	
	
}
