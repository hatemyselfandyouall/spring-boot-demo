package com.wangxinenpu.springbootdemo.dataobject.vo.SiteMattersArea;

import com.wangxinenpu.springbootdemo.dataobject.po.SiteBlock;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SiteMattersAreaRequest implements Serializable {

    private SiteBlock pcBlock;

    private SiteBlock appBlock;

    @ApiModelProperty("区域编码id")
    private Long SittersAreaId;

    @ApiModelProperty("事项id")
    private Long mattersId;

    @ApiModelProperty("区域id")
    private Long areaId;
}
