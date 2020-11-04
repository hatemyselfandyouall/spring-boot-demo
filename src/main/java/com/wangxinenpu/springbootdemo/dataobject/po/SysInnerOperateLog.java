 
package com.wangxinenpu.springbootdemo.dataobject.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Data
@Accessors(chain = true)
public class SysInnerOperateLog implements Serializable {


	//========== properties ==========

	@Id
	@GeneratedValue(generator="JDBC")
    @ApiModelProperty("")
    @Column( name="id")
    private Long id;

    @ApiModelProperty("方法英文名")
    @Column( name="method_name")
    private String methodName;

    @ApiModelProperty("方法中文名")
    @Column( name="method_ch_name")
    private String methodChName;

    @ApiModelProperty("操作人id")
    @Column( name="operator_id")
    private Long operatorId;

    @ApiModelProperty("操作人姓名")
    @Column( name="operator_name")
    private String operatorName;

    @ApiModelProperty("操作人ip")
    @Column( name="operator_ip")
    private String operatorIp;

    @ApiModelProperty("请求入参")
    @Column( name="operate_detail_json")
    private String operateDetailJson;

    @ApiModelProperty("返回值")
    @Column( name="return_value")
    private String returnValue;

    @ApiModelProperty("创建时间")
    @Column( name="create_time")
    private Date createTime;

    @ApiModelProperty("系统")
    @Column( name="system_name")
    private String systemName;

    @ApiModelProperty("请求url")
    @Column( name="url")
    private String url;


}
