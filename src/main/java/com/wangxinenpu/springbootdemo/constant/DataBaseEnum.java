package com.wangxinenpu.springbootdemo.constant;

public enum DataBaseEnum {
    SYSBASE("sysbase"),
    COMMOM("common"),
    REPORT("report"),
    WORKFLOW("workflow"),
    ACCEPT("accept"),
    ARCHIVE("archive"),
    EMPCOLLECTION("empcollection"),
    EMPINSURED("empinsured"),
    EMPOVERLAP("empoverlap"),
    EMPPARAM("empparam"),
    EMPTREATMENT("emptreatment"),
    EMPFINANCE("empfinance"),
    INJURY("injury"),
    NETWORK("network"),
    PLATFORM("platform"),
    VILCOLLECTION("vilcollection"),
    VILINSURED("vilinsured"),
    VILPARAM("vilparam"),
    VILTREATMENT("viltreatment"),
    VILFINANCE("vilfinance"),
    VILBASIC("vilbasic"),
    ZJAPP("zjapp"),
    SHMACHINE("shmachine");

    String val;

    public String getVal() {
        return this.val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    private DataBaseEnum(String val) {
        this.val = val;
    }
}