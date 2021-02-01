package com.wangxinenpu.springbootdemo.dataobject.vo.systemManage;

import lombok.Data;
import star.vo.BaseVo;

@Data
public class SystemManageTree extends BaseVo {


    private Long id;

    private Long parentId;

    /**
     * 字段：渠道标识码
     *
     * @haoxz11MyBatis
     */
    private String channelCode;


    /**
     * 字段:sys_role.area_id
     *
     * @haoxz11MyBatis
     */
    private Long areaId;


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



}
