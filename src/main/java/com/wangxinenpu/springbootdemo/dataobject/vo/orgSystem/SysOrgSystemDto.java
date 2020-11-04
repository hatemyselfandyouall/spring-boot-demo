package com.wangxinenpu.springbootdemo.dataobject.vo.orgSystem;

import lombok.Data;
import star.vo.BaseVo;

import java.util.Date;

@Data
public class SysOrgSystemDto extends BaseVo {

    /**
     * 字段：主键id
     *
     * @haoxz11MyBatis
     */
    private Long id;

    /**
     * 字段：机构id
     *
     * @haoxz11MyBatis
     */
    private Long orgId;

    /**
     * 字段：创建时间
     *
     * @haoxz11MyBatis
     */
    private Date createTime;

    /**
     * 字段：修改时间
     *
     * @haoxz11MyBatis
     */
    private Date modifyTime;

    private Long systemId;

    private String systemName;
}
