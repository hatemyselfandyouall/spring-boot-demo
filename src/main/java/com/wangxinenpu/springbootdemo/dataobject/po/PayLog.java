 
package com.wangxinenpu.springbootdemo.dataobject.po;

import java.io.Serializable;

import com.wangxinenpu.springbootdemo.dataobject.enums.PayStatusEnum;
import com.wangxinenpu.springbootdemo.dataobject.enums.PayWayStatusEnum;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.persistence.*;

import java.lang.String;
import java.lang.Integer;
import java.util.Date;
import java.util.List;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
public class PayLog implements Serializable{


	//========== properties ==========

	@Id
	@GeneratedValue(generator="JDBC")
    @ApiModelProperty("")
    @Column( name="id")
    private Long id;

    @ApiModelProperty("")
    @Column( name="create_time")
    private Date createTime;

    @ApiModelProperty("回调时间")
    @Column( name="callback_time")
    private Date callbackTime;

    @ApiModelProperty("支付状态")
    @Column( name="status")
    private PayStatusEnum status;

    @ApiModelProperty("支付的发起信息，原样留底")
    @Column( name="send_msg")
    private String sendMsg;

    @ApiModelProperty("支付的返回信息")
    @Column( name="retur_msg")
    private String returMsg;

    @ApiModelProperty("支付的回调信息")
    @Column( name="call_back_msg")
    private String callBackMsg;

    @ApiModelProperty("")
    @Column( name="amount_of_payment")
    private Double amountOfPayment;

    @ApiModelProperty("支付编码，各类型支付均应有此编码")
    @Column( name="pay_code")
    private String payCode;

    @ApiModelProperty("支付方式，目前只有省本级啦")
    @Column( name="pay_way")
    private PayWayStatusEnum payWay;




}
