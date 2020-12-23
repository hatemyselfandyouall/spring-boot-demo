 
package com.wangxinenpu.springbootdemo.dataobject.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;


@Data
@Accessors(chain = true)
public class OpenapiSelfmachine implements Serializable {


	//========== properties ==========

	@Id
	@GeneratedValue(generator="JDBC")
    @ApiModelProperty("")
    @Column( name="id")
    private Long id;

    @ApiModelProperty("唯一编码")
    @Column( name="unique_code")
    private String uniqueCode;

    @ApiModelProperty("")
    @Column( name="ip")
    private String ip;

    @ApiModelProperty("mac地址")
    @Column( name="mac_address")
    private String macAddress;

    @ApiModelProperty("操作系统编码")
    @Column( name="system_code")
    private String systemCode;

    @ApiModelProperty("机构名")
    @Column( name="org_name")
    private String orgName;

    @ApiModelProperty("机构id")
    @Column( name="org_id")
    private Long orgId;

    @ApiModelProperty("自助机类型id")
    @Column( name="machine_type_id")
    private Long machineTypeId;

    @ApiModelProperty("自助机地址id")
    @Column( name="machine_address_id")
    private Long machineAddressId;

    @ApiModelProperty("自助机实际地址")
    @Column( name="machine_address")
    private String machineAddress;

    @ApiModelProperty("备注")
    @Column( name="remark")
    private String remark;

    @ApiModelProperty("激活状态")
    @Column( name="active_statu")
    private OpenapiSelfmachineEnum activeStatu;

    @ApiModelProperty("创建时间")
    @Column( name="create_time")
    private Date createTime;

    @ApiModelProperty("客户端版本")
    @Column( name="client_version")
    private String clientVersion;

    @ApiModelProperty("qt版本")
    @Column( name="qt_version")
    private String qtVersion;


    @ApiModelProperty("http版本")
    @Column( name="http_version")
    private String httpVersion;

    @ApiModelProperty("本日是开启的")
    @Column( name="is_open_today")
    private Integer isOpenToday;
}
