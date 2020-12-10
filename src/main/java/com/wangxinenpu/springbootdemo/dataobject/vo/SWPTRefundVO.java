package com.wangxinenpu.springbootdemo.dataobject.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SWPTRefundVO {

    @ApiModelProperty("缴款单号（缴款单来源渠道编号+业务单号）")
    private String  NOTICENO;

    @ApiModelProperty("缴款单来源渠道编号")
    private String  CHANNELNO;

    @ApiModelProperty("业务单号")
    private String  ORIGINALNOTICENO;

    @ApiModelProperty("业务码")
    private String  YWCODE;
}
