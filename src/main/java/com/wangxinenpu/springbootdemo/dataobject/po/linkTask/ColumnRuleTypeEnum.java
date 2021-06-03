package com.wangxinenpu.springbootdemo.dataobject.po.linkTask;

public enum ColumnRuleTypeEnum {
    EQUALS("equals"),
    LIKE("like"),
    GREATER("greater"),
    SMALLER("smaller"),;

    private  String name;

    ColumnRuleTypeEnum(String name){
        this.name=name;
    }

    public String getName() {
        return name;
    }
}
