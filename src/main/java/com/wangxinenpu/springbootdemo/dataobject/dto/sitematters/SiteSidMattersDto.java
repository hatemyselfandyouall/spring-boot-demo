 
package com.wangxinenpu.springbootdemo.dataobject.dto.sitematters;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


@Data
public class SiteSidMattersDto implements Serializable {
	//========== properties ==========
    @ApiModelProperty("事项名称")
    private String name;

    @ApiModelProperty("事项编码")
    private String basicCode;

    @ApiModelProperty("事项内码")
    private String insideCode;

    @ApiModelProperty("区域")
    private String regionCode;
}
