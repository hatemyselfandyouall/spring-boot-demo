package com.wangxinenpu.springbootdemo.dataobject.vo.OpenapiSelfmachineRequest;

import com.wangxinenpu.springbootdemo.dataobject.po.SelfMachineEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = true)
public class OpenapiSelfmachineRequestDeleteVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("自助机id")
    @Column( name="id")
    private List<Long> ids;

    @ApiModelProperty("校验状态")
    @Column( name="statu")
    private SelfMachineEnum statu;

}
