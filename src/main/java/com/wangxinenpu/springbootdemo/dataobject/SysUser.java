package com.wangxinenpu.springbootdemo.dataobject;

import java.util.Date;

public class SysUser {

    private Integer userid;

    private String username;

    private String userpassword;

    private String workno;

    private String realname;

    private String phone;

    private Date lastlogintime;

    private String status;

    private Date createTime;

    private String salt;

    private Date pwdedittime;

    /**
     * @return userId
     */
    public Integer getUserid() {
        return userid;
    }

    /**
     * @param userid
     */
    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    /**
     * @return userName
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return userPassword
     */
    public String getUserpassword() {
        return userpassword;
    }

    /**
     * @param userpassword
     */
    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }

    /**
     * @return workNo
     */
    public String getWorkno() {
        return workno;
    }

    /**
     * @param workno
     */
    public void setWorkno(String workno) {
        this.workno = workno;
    }

    /**
     * @return realName
     */
    public String getRealname() {
        return realname;
    }

    /**
     * @param realname
     */
    public void setRealname(String realname) {
        this.realname = realname;
    }

    /**
     * @return phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return lastLogintime
     */
    public Date getLastlogintime() {
        return lastlogintime;
    }

    /**
     * @param lastlogintime
     */
    public void setLastlogintime(Date lastlogintime) {
        this.lastlogintime = lastlogintime;
    }

    /**
     * @return status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return salt
     */
    public String getSalt() {
        return salt;
    }

    /**
     * @param salt
     */
    public void setSalt(String salt) {
        this.salt = salt;
    }

    /**
     * @return pwdEditTime
     */
    public Date getPwdedittime() {
        return pwdedittime;
    }

    /**
     * @param pwdedittime
     */
    public void setPwdedittime(Date pwdedittime) {
        this.pwdedittime = pwdedittime;
    }

    @Override
    public String toString() {
        return "SysUser{" +
                "userid=" + userid +
                ", username='" + username + '\'' +
                ", userpassword='" + userpassword + '\'' +
                ", workno='" + workno + '\'' +
                ", realname='" + realname + '\'' +
                ", phone='" + phone + '\'' +
                ", lastlogintime=" + lastlogintime +
                ", status='" + status + '\'' +
                ", createTime=" + createTime +
                ", salt='" + salt + '\'' +
                ", pwdedittime=" + pwdedittime +
                '}';
    }
}