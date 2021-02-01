package com.wangxinenpu.springbootdemo.util;


import com.wangxinenpu.springbootdemo.dataobject.vo.BaseCodeEnum;

public class BusException extends RuntimeException {

    private static final long serialVersionUID = 7090283990625759498L;

    private BaseCodeEnum errorCode;
    private String errMsg;
    private Integer errCode;

    public BusException() {
        super();
    }

    public BusException(BaseCodeEnum errorCode) {
        this.errorCode = errorCode;
        this.errCode = errorCode.getCode();
        this.errMsg = errorCode.getMsg();
    }

    public BusException(BaseCodeEnum errorCode, String extraErrmessage) {
        this.errCode = errorCode.getCode();
        this.errMsg =   extraErrmessage;
    }

    public BusException(Integer errCode, String errMsg) {
        super();
        errorCode.setCode(errCode);
        errorCode.setMsg(errMsg);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public BaseCodeEnum getExceptionEnum() {
        return errorCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public Integer getErrCode() {
        return errCode;
    }
}
