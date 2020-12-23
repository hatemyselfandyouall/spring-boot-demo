package com.wangxinenpu.springbootdemo.dataobject.vo.OpenapiSelfmachine;

import com.wangxinenpu.springbootdemo.dataobject.vo.root.PageVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.List;

@Data
public class OpenapiSelfmachineListVO extends PageVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String keyWord;

    @ApiModelProperty("所属机构")
    @Column( name="org_name")
    private String orgName;


    private List<String> orgNames;

    @ApiModelProperty("机器型号")
    @Column( name="machine_type")
    private String machineType;

    private String orgCode;

    @ApiModelProperty("机构id")
    @Column( name="org_id")
    private Long orgId;

    @ApiModelProperty("客户端版本")
    @Column( name="client_version")
    private String clientVersion;

    @ApiModelProperty("mac地址")
    @Column( name="mac_address")
    private String macAddress;

    private List<String> orgNameList;
}
