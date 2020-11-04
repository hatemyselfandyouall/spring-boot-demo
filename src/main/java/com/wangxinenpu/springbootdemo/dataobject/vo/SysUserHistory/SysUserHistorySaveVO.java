package com.wangxinenpu.springbootdemo.dataobject.vo.SysUserHistory;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
public class SysUserHistorySaveVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator="JDBC")
    @ApiModelProperty("用户编号")
    @Column( name="code")
    private String code;

    @ApiModelProperty("用户id")
    @Column( name="id")
    private Long id;

    @ApiModelProperty("登录名")
    @Column( name="logon_name")
    private String logonName;

    @ApiModelProperty("登录密码")
    @Column( name="passwd")
    private String passwd;

    @ApiModelProperty("显示名")
    @Column( name="display_name")
    private String displayName;

    @ApiModelProperty("区域id")
    @Column( name="area_id")
    private Long areaId;

    @ApiModelProperty("机构id")
    @Column( name="org_id")
    private Long orgId;

    @ApiModelProperty("用户状态:1正常、2锁定、3注销")
    @Column( name="user_state")
    private String userState;

    @ApiModelProperty("1超级管理员，2行政区管理员，3机构管理员，4业务操作员")
    @Column( name="user_type")
    private String userType;

    @ApiModelProperty("证件类型:1身份证、2军官证、3户口本、4护照、5其他")
    @Column( name="card_type")
    private String cardType;

    @ApiModelProperty("证件号码")
    @Column( name="card_id")
    private String cardId;

    @ApiModelProperty("联系电话")
    @Column( name="tel")
    private String tel;

    @ApiModelProperty("手机")
    @Column( name="mobile")
    private String mobile;

    @ApiModelProperty("电子邮箱")
    @Column( name="email")
    private String email;

    @ApiModelProperty("通讯地址")
    @Column( name="user_addr")
    private String userAddr;

    @ApiModelProperty("备注")
    @Column( name="remark")
    private String remark;

    @ApiModelProperty("创建人id")
    @Column( name="creator_id")
    private String creatorId;

    @ApiModelProperty("用户锁定时间")
    @Column( name="lock_time")
    private Date lockTime;

    @ApiModelProperty("用户解锁时间")
    @Column( name="unlock_time")
    private Date unlockTime;

    @ApiModelProperty("用户锁定原因")
    @Column( name="lock_reason")
    private String lockReason;

    @ApiModelProperty("用户过期时间")
    @Column( name="user_expire_date")
    private Date userExpireDate;

    @ApiModelProperty("用户连续登录失败次数")
    @Column( name="fail_num")
    private Integer failNum;

    @ApiModelProperty("密码过期策略：1系统配置周期，2永不过期，3指定日期")
    @Column( name="pw_expire_type")
    private String pwExpireType;

    @ApiModelProperty("密码过期时间")
    @Column( name="pw_expire_date")
    private Date pwExpireDate;

    @ApiModelProperty("密码最近修改时间")
    @Column( name="pw_edit_date")
    private Date pwEditDate;

    @ApiModelProperty("用户签到状态 1到 2退")
    @Column( name="signs_tate")
    private String signsTate;

    @ApiModelProperty("所属部门")
    @Column( name="department")
    private String department;

    @ApiModelProperty("创建日期")
    @Column( name="create_time")
    private Date createTime;

    @ApiModelProperty("修改时间")
    @Column( name="modify_time")
    private Date modifyTime;

    @ApiModelProperty("回退唯一字段")
    @Column( name="prseno")
    private Long prseno;

    @ApiModelProperty("登录ip")
    @Column( name="last_load_ip")
    private String lastLoadIp;

    @ApiModelProperty("登录时间")
    @Column( name="last_load_time")
    private Date lastLoadTime;

    @ApiModelProperty("头像图片地址")
    @Column( name="head_url")
    private String headUrl;

    @ApiModelProperty("一体化平台用户ID")
    @Column( name="yth_user_id")
    private String ythUserId;

}
