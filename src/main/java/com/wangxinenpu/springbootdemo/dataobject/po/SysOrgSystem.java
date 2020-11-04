package com.wangxinenpu.springbootdemo.dataobject.po;

import star.vo.BaseVo;

import java.util.Date;

public class SysOrgSystem extends BaseVo {

    transient private static final long serialVersionUID = -155574L;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Long getSystemId() {
        return systemId;
    }

    public void setSystemId(Long systemId) {
        this.systemId = systemId;
    }
}
