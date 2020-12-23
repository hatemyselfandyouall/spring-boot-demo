package com.wangxinenpu.springbootdemo.dataobject.dto.sitematters;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

@Data
public class BlockListDTO  implements Serializable {
    private static final long serialVersionUID = -1L;

    @ApiModelProperty("平台码")
    @Column( name="platform_code")
    private String platformCode;

    @ApiModelProperty("地区码")
    @Column( name="area_code")
    private String areaCode;

}
