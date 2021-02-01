package com.wangxinenpu.springbootdemo.dataobject.vo.systemManage;

import lombok.Data;

@Data
public class SystemManageQueryVo extends PageVO {

    private String accessWay;

    /**
     * 字段:sys_role.area_id
     *
     * @haoxz11MyBatis
     */
    private Long areaId;


    private String systemName;

    private String isAgency;

    //列表是否显示管理系统
    private int isShowManagementSystem=0;
}
