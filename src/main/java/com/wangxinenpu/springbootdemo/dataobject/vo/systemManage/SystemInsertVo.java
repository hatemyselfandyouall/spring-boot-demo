package com.wangxinenpu.springbootdemo.dataobject.vo.systemManage;

import lombok.Data;
import star.vo.BaseVo;

import javax.persistence.Column;

@Data
public class SystemInsertVo extends BaseVo {

    /**
     * 字段：系统id
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
     * 字段：创建人id
     *
     * @haoxz11MyBatis
     */
    @Column( name="creator_Id")
    private String creatorId;

    private String icon;

    private String isAgency;
}
