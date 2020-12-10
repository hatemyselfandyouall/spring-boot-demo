package com.wangxinenpu.springbootdemo.dataobject.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SWPTPayVO {

    @ApiModelProperty("行政区划编码")
    private String REGICODE;

    @ApiModelProperty("缴款单号（缴款单来源渠道编号+业务单号）")
    private String NOTICENO;

    @ApiModelProperty("缴款单来源渠道编号")
    private String CHANNELNO;

    @ApiModelProperty("业务单号")
    private String ORIGINALNOTICENO;

    @ApiModelProperty("所属缴款人")
    private String PAYER;

    @ApiModelProperty("缴款人身份证号")
    private String PAYERSFZ;

    @ApiModelProperty("手机号（用于接收支付成功后的短信）")
    private String Mobile;

    @ApiModelProperty("制单日期（YYYYMMDD，如20140811")
    private String MAKEDATE;

    @ApiModelProperty("制单时间（hhmmss，如164733）")
    private String MAKETIME;

    @ApiModelProperty("合计金额")
    private String TOTALMONEY;

    @ApiModelProperty("业务码")
    private String YWCODE;

    @ApiModelProperty("执收单位编码")
    private String ENTERCODE;

    @ApiModelProperty("缴款单表现形式")
    private String NOTICEDIS;

    @ApiModelProperty("是否实名支付（0非实名（默认）， 1实名）")
    private String ISTRUENAME;

    @ApiModelProperty("截止缴款日期（YYYYMMDD，如20140811，如无传空值）")
    private String EXPIREDPAYDATE;

    @ApiModelProperty("是否由统一公共支付平台计算加收金额（1是，0否）")
    private String ISCOUNTOVERDUE;

    @ApiModelProperty("打印附加信息")
    private String PRINTINFO;

    @ApiModelProperty("附加名称1")
    private String FJ1;

    @ApiModelProperty("附加内容1")
    private String FJCONTENT1;

    @ApiModelProperty("附加名称2")
    private String FJ2;

    @ApiModelProperty("附加内容2")
    private String FJCONTENT2;

    @ApiModelProperty("附加名称3")
    private String FJ3;

    @ApiModelProperty("附加内容3")
    private String FJCONTENT3;

    @ApiModelProperty("附加名称4")
    private String FJ4;

    @ApiModelProperty("附加内容4")
    private String FJCONTENT4;

    @ApiModelProperty("附加名称5")
    private String FJ5;

    @ApiModelProperty("附加内容5")
    private String FJCONTENT5;
}
