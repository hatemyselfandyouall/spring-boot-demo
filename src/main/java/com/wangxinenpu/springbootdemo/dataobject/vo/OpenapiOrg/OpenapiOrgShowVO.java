package com.wangxinenpu.springbootdemo.dataobject.vo.OpenapiOrg;

import com.wangxinenpu.springbootdemo.dataobject.po.OpenapiOrg;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class OpenapiOrgShowVO extends OpenapiOrg implements Serializable {

    @ApiModelProperty("自助机数量")
    private Integer machineCount;
}
