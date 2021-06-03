package com.wangxinenpu.springbootdemo.dataobject.po.linkTask;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
public class Link implements Serializable {


    //========== properties ==========

    @Id
    @GeneratedValue(generator = "JDBC")
    @ApiModelProperty("id")
    @Column(name = "id")
    private Long id;

    @ApiModelProperty("数据连接名")
    @Column(name = "name")
    private String name;

    @ApiModelProperty("数据连接描述")
    @Column(name = "description")
    private String description;

    @ApiModelProperty("所属文件夹id，为空则不属于任何文件夹")
    @Column(name = "folder_id")
    private Long folderId;

    @ApiModelProperty("数据连接类型编码")
    @Column(name = "link_type_code")
    private String linkTypeCode;

    @ApiModelProperty("0未删除1已删除")
    @Column(name = "is_delete")
    private Integer isDelete;

    @ApiModelProperty("创建者id")
    @Column(name = "creator_id")
    private String creatorId;

    @ApiModelProperty("创建者姓名")
    @Column(name = "creator_name")
    private String creatorName;

    @ApiModelProperty("部门id")
    @Column(name = "m_id")
    private Long mId;

    @ApiModelProperty("操作人id")
    @Column(name = "operator_id")
    private String operatorId;

    @ApiModelProperty("操作人姓名")
    @Column(name = "operator_name")
    private String operatorName;

    @ApiModelProperty("0非系统特有连接，1系统特有连接")
    @Column(name = "is_system")
    private Integer isSystem;

    @ApiModelProperty("")
    @Column(name = "create_time")
    private Date createTime;

    @ApiModelProperty("")
    @Column(name = "modify_time")
    private Date modifyTime;

    @ApiModelProperty("接口类型:接口类型:1-查询2-新增3-修改4-删除")
    @Column(name = "interface_type")
    private String interfaceType;

    @ApiModelProperty("事件触发类型：1-外部api2-数据模型接口3-数据查询接口")
    @Column(name = "trigger_type")
    private String triggerType;

    @ApiModelProperty("接口id")
    @Column( name="relate_interface")
    private String relateInterface;

    @ApiModelProperty("路径")
    @Column( name="path")
    private String path;
}
