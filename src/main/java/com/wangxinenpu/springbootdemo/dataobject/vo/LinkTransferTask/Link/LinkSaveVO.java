package com.wangxinenpu.springbootdemo.dataobject.vo.LinkTransferTask.Link;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

@Data
public class LinkSaveVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("")
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

    @ApiModelProperty("接口类型:1-查询2-新增3-修改4-删除")
    @Column(name = "interface_type")
    private String interfaceType;

    @ApiModelProperty("事件触发类型：1-外部api2-数据模型接口3-数据查询接口")
    @Column(name = "trigger_type")
    private String triggerType;

    @ApiModelProperty("1表示允许直连数据库服务器")
    @Column( name="can_ssh_dataserver")
    private Integer canSshDataserver;

    @ApiModelProperty("")
    @Column( name="dataserver_username")
    private String dataserverUsername;


    @ApiModelProperty("")
    @Column( name="dataserver_password")
    private String dataserverPassword;

    @ApiModelProperty("接口id")
    @Column( name="relate_interface")
    private String relateInterface;

    @ApiModelProperty("路径")
    @Column( name="path")
    private String path;
}
