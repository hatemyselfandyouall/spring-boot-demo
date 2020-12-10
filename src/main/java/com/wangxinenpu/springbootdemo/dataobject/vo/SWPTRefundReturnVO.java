package com.wangxinenpu.springbootdemo.dataobject.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SWPTRefundReturnVO {
    @ApiModelProperty("结果：\n" +
            "00：支付成功 \n" +
            "03：缴款单已缴款\n" +
            "07：超时\n" +
            "08：支付失败\n" +
            "09：支付状态未知\n" +
            "10：支付中85：有多个商户信息\n" +
            "92: 指定渠道未开通相应商户\n" +
            "97：XML格式错误\n" +
            "99：其他错误\n")
    private String code;

    private boolean isSuccess;

    @ApiModelProperty("错误说明")
    private String ERRMSG;

    private SWPTRefundVO swptRefundVO;
}
