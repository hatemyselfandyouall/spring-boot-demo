package com.wangxinenpu.springbootdemo.dataobject.vo;

/**
 * api公共code
 * (与业务code区分)
 */
public enum BaseCodeEnum {
    //-1	系统错误
    //0	成功
    // 1	 业务异常
    ERROR(-1,"系统错误"),
    SUCCESS(0,"成功"),
    PAGE_ERROR(400,"分页数据异常"),
    BUSINESS_ERROR(1,"业务异常");

    BaseCodeEnum(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }

    private Integer code;
    private String msg;
    public void setCode(Integer code){
        this.code = code;
    }
    public void setMsg(String msg){
        this.msg = msg;
    }
    public Integer getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }

}
