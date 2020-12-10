package com.wangxinenpu.springbootdemo.dataobject.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SWPTRefundResultVO {
    @ApiModelProperty("缴款单号（缴款单来源渠道编号+业务单号）")
    private String  NOTICENO;

    @ApiModelProperty("缴款单来源渠道编号")
    private String  CHANNELNO;

    @ApiModelProperty("业务单号（原缴款信息）")
    private String  ORIGINALNOTICENO;

    @ApiModelProperty("业务码（原缴款信息））")
    private String  YWCODE;

    @ApiModelProperty("支付单号（原缴款信息）")
    private String  PAYLISTNO;

    @ApiModelProperty("缴款凭证号（地区码3位+日期8位+顺序号6位）")
    private String  CERTIFICATENO;

    @ApiModelProperty("代收机构编号（原缴款信息）")
    private String  TRADECODE;

    @ApiModelProperty("代收机构名称（原缴款信息）")
    private String  TRADENAME;

    @ApiModelProperty("支付方式编码（原缴款信息）")
    private String  WAYCODE;

    @ApiModelProperty("交易流水号（原缴款信息）")
    private String  TRADENO;

    @ApiModelProperty("业务归属日期（代收机构认定，YYYYMMDD）")
    private String  TRADEDATE;

    @ApiModelProperty("校验码")
    private String  CHECKCODE;

    @ApiModelProperty("退款金额（原缴款金额，负数）")
    private String  PAYMONEY;

    @ApiModelProperty("退款日期")
    private String  PAYDATE;

    @ApiModelProperty("退款时间")
    private String  PAYTIME;
}
