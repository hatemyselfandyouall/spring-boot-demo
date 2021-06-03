package com.wangxinenpu.springbootdemo.dataobject.vo.LinkTransferTask;

public enum ExtraSearchEnum {

    EQUALS("equals"),
    LIKE("like"),
    GREATER("greater"),
    SMALLER("smaller"),;

    private  String name;

    ExtraSearchEnum(String name){
        this.name=name;
    }

    public String getName() {
        return name;
    }
}
