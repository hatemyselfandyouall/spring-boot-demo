package com.wangxinenpu.springbootdemo.dataobject.vo;

import star.vo.BaseVo;

import java.util.Date;
import java.util.List;

/**
 *
 * 系统功能表
 * @author haoxz11MyBatis
 * @created Mon Mar 25 14:59:55 CST 2019
 * @version $Id: DefaultCommentGenerator.java,v 1.1 2013/10/28 07:59:58 haoxz11 Exp $
 * @haoxz11MyBatis
 */
public class SysFunctionTreeVo extends BaseVo {
    /**
     *
     * @haoxz11MyBatis
     */
    transient private static final long serialVersionUID = -135L;

    /**
     * 字段：功能id
     *

     * @haoxz11MyBatis
     */
    private Long id;

    private List<SysFunctionTreeVo> children;


    /**
     * 字段：链接
     *
     * @haoxz11MyBatis
     */
    private String location;

    /**
     * 字段：标题
     *
     * @haoxz11MyBatis
     */
    private String title;

    /**
     * 字段：父功能id
     *
     * @haoxz11MyBatis
     */
    private Long parentId;

    /**
     * 字段：排序号
     *
     * @haoxz11MyBatis
     */
    private Integer funOrder;

    /**
     * 字段：节点类型:1-菜单节点，2-菜单叶子，3-按钮
     *
     * @haoxz11MyBatis
     */
    private String nodeType;

    /**
     * 字段：是否记日志，1、记，0、不记
     *
     * @haoxz11MyBatis
     */
    private String isLog;

    /**
     * 字段：开发人员
     *
     * @haoxz11MyBatis
     */
    private String devEloper;

    /**
     * 字段：节点图标
     *
     * @haoxz11MyBatis
     */
    private String icon;

    /**
     * 字段：功能的中文描述
     *
     * @haoxz11MyBatis
     */
    private String description;

    /**
     * 字段：功能分类,0通用1系统功能2业务功能3事项功能（扩展：个人、单位、机构）
     *
     * @haoxz11MyBatis
     */
    private String funType;

    /**
     * 字段：是否有效
     *
     * @haoxz11MyBatis
     */
    private String active;

    /**
     * 字段：功能编号
     *
     * @haoxz11MyBatis
     */
    private String funCode;

    /**
     * 字段：文件名
     *
     * @haoxz11MyBatis
     */
    private String fileName;

    /**
     * 字段：文件路径
     *
     * @haoxz11MyBatis
     */
    private String filePath;

    /**
     * 字段：审核类型，0-自动审核，1-手动配置
     *
     * @haoxz11MyBatis
     */
    private String auFlag;

    /**
     * 字段：操作日志回退标志，0-不可以，1-可以
     *
     * @haoxz11MyBatis
     */
    private String rbFlag;

    /**
     * 字段：创建日期
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

    /**
     * 字段：vue代码
     *
     * @haoxz11MyBatis
     */
    private String vueCode;

    /**
     * 业务类型ID
     */
    private Long businessId;

    /**
     *
     */
    private Long auditHierarchy;

    /**
     * 字段：是否接入流程，1、是，2、否
     *
     * @haoxz11MyBatis
     */
    private String isBus;


    /**
     * 字段：组名
     *
     * @haoxz11MyBatis
     */
    private String groupName;

    /**
     * 系统类型 1：管理系统  2： 统征经办系统 3：统征工伤系统 4：统征网报系统
     */
    private String systemType;


    private List<SystemIconDTO> iconList;

    public List<SysFunctionTreeVo> getChildren() {
        return children;
    }

    public void setChildren(List<SysFunctionTreeVo> children) {
        this.children = children;
    }

    public List<SystemIconDTO> getIconList() {
        return iconList;
    }

    public void setIconList(List<SystemIconDTO> iconList) {
        this.iconList = iconList;
    }

    public String getGroupName() {
        return groupName;
    }


    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    public Long getAuditHierarchy() {
        return auditHierarchy;
    }

    public void setAuditHierarchy(Long auditHierarchy) {
        this.auditHierarchy = auditHierarchy;
    }

    /**
     * 读取：功能id
     *
     * @return sys_function.id
     *
     * @haoxz11MyBatis
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置：功能id
     *
     * @param id sys_function.id
     *
     * @haoxz11MyBatis
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 读取：链接
     *
     * @return sys_function.location
     *
     * @haoxz11MyBatis
     */
    public String getLocation() {
        return location;
    }

    /**
     * 设置：链接
     *
     * @param location sys_function.location
     *
     * @haoxz11MyBatis
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * 读取：标题
     *
     * @return sys_function.title
     *
     * @haoxz11MyBatis
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置：标题
     *
     * @param title sys_function.title
     *
     * @haoxz11MyBatis
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 读取：父功能id
     *
     * @return sys_function.parent_id
     *
     * @haoxz11MyBatis
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * 设置：父功能id
     *
     * @param parentId sys_function.parent_id
     *
     * @haoxz11MyBatis
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * 读取：排序号
     *
     * @return sys_function.fun_order
     *
     * @haoxz11MyBatis
     */
    public Integer getFunOrder() {
        return funOrder;
    }

    /**
     * 设置：排序号
     *
     * @param funOrder sys_function.fun_order
     *
     * @haoxz11MyBatis
     */
    public void setFunOrder(Integer funOrder) {
        this.funOrder = funOrder;
    }

    /**
     * 读取：节点类型:1-菜单节点，2-菜单叶子，3-按钮
     *
     * @return sys_function.node_type
     *
     * @haoxz11MyBatis
     */
    public String getNodeType() {
        return nodeType;
    }

    /**
     * 设置：节点类型:1-菜单节点，2-菜单叶子，3-按钮
     *
     * @param nodeType sys_function.node_type
     *
     * @haoxz11MyBatis
     */
    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    /**
     * 读取：是否记日志，1、记，0、不记
     *
     * @return sys_function.is_log
     *
     * @haoxz11MyBatis
     */
    public String getIsLog() {
        return isLog;
    }

    /**
     * 设置：是否记日志，1、记，0、不记
     *
     * @param isLog sys_function.is_log
     *
     * @haoxz11MyBatis
     */
    public void setIsLog(String isLog) {
        this.isLog = isLog;
    }

    /**
     * 读取：开发人员
     *
     * @return sys_function.dev_eloper
     *
     * @haoxz11MyBatis
     */
    public String getDevEloper() {
        return devEloper;
    }

    /**
     * 设置：开发人员
     *
     * @param devEloper sys_function.dev_eloper
     *
     * @haoxz11MyBatis
     */
    public void setDevEloper(String devEloper) {
        this.devEloper = devEloper;
    }

    /**
     * 读取：节点图标
     *
     * @return sys_function.icon
     *
     * @haoxz11MyBatis
     */
    public String getIcon() {
        return icon;
    }

    /**
     * 设置：节点图标
     *
     * @param icon sys_function.icon
     *
     * @haoxz11MyBatis
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * 读取：功能的中文描述
     *
     * @return sys_function.description
     *
     * @haoxz11MyBatis
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置：功能的中文描述
     *
     * @param description sys_function.description
     *
     * @haoxz11MyBatis
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 读取：功能分类,0通用1系统管理2业务功能3安全管理（扩展：个人、单位、机构）
     *
     * @return sys_function.fun_type
     *
     * @haoxz11MyBatis
     */
    public String getFunType() {
        return funType;
    }

    /**
     * 设置：功能分类,0通用1系统管理2业务功能3安全管理（扩展：个人、单位、机构）
     *
     * @param funType sys_function.fun_type
     *
     * @haoxz11MyBatis
     */
    public void setFunType(String funType) {
        this.funType = funType;
    }

    /**
     * 读取：是否有效
     *
     * @return sys_function.active
     *
     * @haoxz11MyBatis
     */
    public String getActive() {
        return active;
    }

    /**
     * 设置：是否有效
     *
     * @param active sys_function.active
     *
     * @haoxz11MyBatis
     */
    public void setActive(String active) {
        this.active = active;
    }

    /**
     * 读取：功能编号
     *
     * @return sys_function.fun_code
     *
     * @haoxz11MyBatis
     */
    public String getFunCode() {
        return funCode;
    }

    /**
     * 设置：功能编号
     *
     * @param funCode sys_function.fun_code
     *
     * @haoxz11MyBatis
     */
    public void setFunCode(String funCode) {
        this.funCode = funCode;
    }

    /**
     * 读取：文件名
     *
     * @return sys_function.file_name
     *
     * @haoxz11MyBatis
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * 设置：文件名
     *
     * @param fileName sys_function.file_name
     *
     * @haoxz11MyBatis
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * 读取：文件路径
     *
     * @return sys_function.file_path
     *
     * @haoxz11MyBatis
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * 设置：文件路径
     *
     * @param filePath sys_function.file_path
     *
     * @haoxz11MyBatis
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * 读取：审核标志，0-自动审核，1-手动配置
     *
     * @return sys_function.au_flag
     *
     * @haoxz11MyBatis
     */
    public String getAuFlag() {
        return auFlag;
    }

    /**
     * 设置：审核标志，0-自动审核，1-手动配置
     *
     * @param auFlag sys_function.au_flag
     *
     * @haoxz11MyBatis
     */
    public void setAuFlag(String auFlag) {
        this.auFlag = auFlag;
    }

    /**
     * 读取：操作日志回退标志，0-不可以，1-可以
     *
     * @return sys_function.rb_flag
     *
     * @haoxz11MyBatis
     */
    public String getRbFlag() {
        return rbFlag;
    }

    /**
     * 设置：操作日志回退标志，0-不可以，1-可以
     *
     * @param rbFlag sys_function.rb_flag
     *
     * @haoxz11MyBatis
     */
    public void setRbFlag(String rbFlag) {
        this.rbFlag = rbFlag;
    }

    /**
     * 读取：创建日期
     *
     * @return sys_function.create_time
     *
     * @haoxz11MyBatis
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置：创建日期
     *
     * @param createTime sys_function.create_time
     *
     * @haoxz11MyBatis
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 读取：修改时间
     *
     * @return sys_function.modify_time
     *
     * @haoxz11MyBatis
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * 设置：修改时间
     *
     * @param modifyTime sys_function.modify_time
     *
     * @haoxz11MyBatis
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * 读取：vue代码
     *
     * @return sys_function.vue_code
     *
     * @haoxz11MyBatis
     */
    public String getVueCode() {
        return vueCode;
    }

    /**
     * 设置：vue代码
     *
     * @param vueCode sys_function.vue_code
     *
     * @haoxz11MyBatis
     */
    public void setVueCode(String vueCode) {
        this.vueCode = vueCode;
    }

    public String getIsBus() {
        return isBus;
    }

    public void setIsBus(String isBus) {
        this.isBus = isBus;
    }

    public String getSystemType() {
        return systemType;
    }

    public void setSystemType(String systemType) {
        this.systemType = systemType;
    }
}