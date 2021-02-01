package com.wangxinenpu.springbootdemo.dataobject.vo.systemManage;

import lombok.Data;
import star.vo.BaseVo;

@Data
public class SystemManageListVo extends BaseVo {


    /**
     * 字段：角色id
     *
     * @haoxz11MyBatis
     */
    private String id;


    /**
     * 字段：菜单同步地址
     *
     * @haoxz11MyBatis
     */
    private String menuAddress;




    /**
     * 字段：渠道标识码
     *
     * @haoxz11MyBatis
     */
    private String channelCode;

    /**
     * 字段：系统接入方式; 1:统一接入；2同步模式
     *
     * @haoxz11MyBatis
     */
    private String accessWay;

    /**
     * 字段:sys_role.area_id
     *
     * @haoxz11MyBatis
     */
    private String areaId;


    /**
     * 字段:sys_role.area_id
     *
     * @haoxz11MyBatis
     */
    private String areaName;


    /**
     * 字段：系统名称
     *
     * @haoxz11MyBatis
     */
    private String systemName;


    /**
     * 字段：系统地址
     *
     * @haoxz11MyBatis
     */
    private String systemAddress;

    /**
     * 字段：是否经办 0 否 ， 1 是
     *
     * @haoxz11MyBatis
     */
    private String isAgency;


    /**
     * 字段：图标
     *
     * @haoxz11MyBatis
     */
    private String icon;

}
