/**
 * 
 */
package com.wangxinenpu.springbootdemo.dataobject.vo;

import java.io.Serializable;

/**
 * @author xxh
 * @date 2017年6月15日 下午3:18:00
 */
public class FileSysResultVo<T> implements Serializable {

	private static final long serialVersionUID = 7687489806083603128L;

	private boolean success = false;
	
	private String code = "-100";
	
	private String errorMsg;
	
	private T value;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.setCode("100");
		this.success = success;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

}
