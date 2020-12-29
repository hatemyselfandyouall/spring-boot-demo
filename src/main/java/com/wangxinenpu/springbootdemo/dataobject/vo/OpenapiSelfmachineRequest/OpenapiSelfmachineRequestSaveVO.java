package com.wangxinenpu.springbootdemo.dataobject.vo.OpenapiSelfmachineRequest;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

@Data
public class OpenapiSelfmachineRequestSaveVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("")
    @Column( name="ip")
    private String ip;

    @ApiModelProperty("mac地址")
    @Column( name="mac_address")
    private String macAddress;

    @ApiModelProperty("")
    @Column( name="app_key")
    private String appKey;

    @ApiModelProperty("机器型号")
    @Column( name="machine_type")
    private String machineType;

    @ApiModelProperty("证书")
    @Column( name="certificate")
    private String certificate;

    @ApiModelProperty("唯一编码")
    @Column( name="unique_code")
    private String uniqueCode;

    @ApiModelProperty("客户端版本")
    @Column( name="client_version")
    private String clientVersion;

    @ApiModelProperty("机构名")
    @Column( name="org_name")
    private String orgName;

    @ApiModelProperty("操作系统编码")
    @Column( name="system_code")
    private String systemCode;

    @ApiModelProperty("md5加密值")
    @Column( name="md5_value")
    private String md5Value;

    @ApiModelProperty("ip段")
    @Column( name="ip_segment")
    private String ipSegment;

    @ApiModelProperty("qt版本")
    @Column( name="qt_version")
    private String qtVersion;

    @ApiModelProperty("http版本")
    @Column( name="http_version")
    private String httpVersion;
}
