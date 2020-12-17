package com.wangxinenpu.springbootdemo.dataobject.vo.getBatchPayInfoDTO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ResponsePayProjectDTO {

    @ApiModelProperty("执收项目编码")
    private String CHITCODE;

    @ApiModelProperty("执收项目名称")
    private String CHITNAME;

    @ApiModelProperty("计量单位")
    private String METRICUNIT;

    @ApiModelProperty("数量")
    private String CHCOUNT;

    @ApiModelProperty("标准")
    private String CHARGESTANDARD;

    @ApiModelProperty("金额")
    private String CHMONEY;

    @ApiModelProperty("是否会产生加收金额（1是，0否）")
    private String ISEXISTSOVERDUE;

    @ApiModelProperty("加收金额上限（0为不限制）")
    private String MAXPAYMENT;

    @ApiModelProperty("加收金额比例（每日）")
    private String PAYMENTRADIO;

    @ApiModelProperty("开始计算日期（YYYYMMDD，如20140811）")
    private String STARTCALCDATE;

    @ApiModelProperty("加收金额所属执收项目编码")
    private String BELONGCHITCODE;

    @ApiModelProperty("加收金额所属执收项目名称")
    private String BELONGCHITNAME;
}
