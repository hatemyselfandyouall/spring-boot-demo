package com.wangxinenpu.springbootdemo.dataobject.dto;

import star.vo.BaseVo;

import java.util.Date;

public class OptSelfServiceMachinesSoftOrgRefDTO extends BaseVo {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column opt_self_service_machines_soft_org_ref.id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column opt_self_service_machines_soft_org_ref.self_service_id
     *
     * @mbggenerated
     */
    private Long selfServiceId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column opt_self_service_machines_soft_org_ref.org_id
     *
     * @mbggenerated
     */
    private Long orgId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column opt_self_service_machines_soft_org_ref.create_time
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column opt_self_service_machines_soft_org_ref.modify_time
     *
     * @mbggenerated
     */
    private Date modifyTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column opt_self_service_machines_soft_org_ref.id
     *
     * @return the value of opt_self_service_machines_soft_org_ref.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column opt_self_service_machines_soft_org_ref.id
     *
     * @param id the value for opt_self_service_machines_soft_org_ref.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column opt_self_service_machines_soft_org_ref.self_service_id
     *
     * @return the value of opt_self_service_machines_soft_org_ref.self_service_id
     *
     * @mbggenerated
     */
    public Long getSelfServiceId() {
        return selfServiceId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column opt_self_service_machines_soft_org_ref.self_service_id
     *
     * @param selfServiceId the value for opt_self_service_machines_soft_org_ref.self_service_id
     *
     * @mbggenerated
     */
    public void setSelfServiceId(Long selfServiceId) {
        this.selfServiceId = selfServiceId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column opt_self_service_machines_soft_org_ref.org_id
     *
     * @return the value of opt_self_service_machines_soft_org_ref.org_id
     *
     * @mbggenerated
     */
    public Long getOrgId() {
        return orgId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column opt_self_service_machines_soft_org_ref.org_id
     *
     * @param orgId the value for opt_self_service_machines_soft_org_ref.org_id
     *
     * @mbggenerated
     */
    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column opt_self_service_machines_soft_org_ref.create_time
     *
     * @return the value of opt_self_service_machines_soft_org_ref.create_time
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column opt_self_service_machines_soft_org_ref.create_time
     *
     * @param createTime the value for opt_self_service_machines_soft_org_ref.create_time
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column opt_self_service_machines_soft_org_ref.modify_time
     *
     * @return the value of opt_self_service_machines_soft_org_ref.modify_time
     *
     * @mbggenerated
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column opt_self_service_machines_soft_org_ref.modify_time
     *
     * @param modifyTime the value for opt_self_service_machines_soft_org_ref.modify_time
     *
     * @mbggenerated
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}