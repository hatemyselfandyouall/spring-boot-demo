package com.wangxinenpu.springbootdemo.dataobject.vo.systemManage;

import lombok.Data;

import java.io.Serializable;

@Data
public class SystemTreeVo implements Serializable {

    /**
     * 字段：渠道标识码
     *
     * @haoxz11MyBatis
     */
    private String channelCode;


    private Long id;


    /**
     * 字段：系统名称
     *
     * @haoxz11MyBatis
     */
    private String systemName;
}
