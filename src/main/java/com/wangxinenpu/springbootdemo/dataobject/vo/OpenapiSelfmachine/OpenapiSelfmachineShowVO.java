package com.wangxinenpu.springbootdemo.dataobject.vo.OpenapiSelfmachine;

import com.wangxinenpu.springbootdemo.dataobject.po.OpenapiSelfmachine;
import com.wangxinenpu.springbootdemo.dataobject.po.SelfMachineEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import java.io.Serializable;

@Data
@Accessors(chain = true)
public class OpenapiSelfmachineShowVO extends OpenapiSelfmachine implements Serializable {

    @ApiModelProperty("状态")
    @Column( name="statu")
    private SelfMachineEnum statu;



    @ApiModelProperty("机器型号")
    @Column( name="machine_type")
    private String machineType;

    @ApiModelProperty("机器编码")
    @Column( name="machine_code")
    private String machineCode;

    @ApiModelProperty("证书编号")
    @Column( name="certificate_code")
    private String certificateCode;

    @ApiModelProperty("完整地址")
    private String fullAddress;

    @ApiModelProperty("临时地址+详细地址")
    private String tempAddress;

}

