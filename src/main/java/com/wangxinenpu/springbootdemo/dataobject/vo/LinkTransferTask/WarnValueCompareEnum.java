package com.wangxinenpu.springbootdemo.dataobject.vo.LinkTransferTask;

public enum WarnValueCompareEnum {

    EQUALS("equals"),
    LIKE("like"),
    GREATER("greater"),
    SMALLER("smaller"),;

    private  String name;

    WarnValueCompareEnum(String name){
        this.name=name;
    }

    public String getName() {
        return name;
    }
}
