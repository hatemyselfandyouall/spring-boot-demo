package com.wangxinenpu.springbootdemo.dataobject.vo.sysInnerOperateLog;


import com.wangxinenpu.springbootdemo.dataobject.vo.root.PageVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

@Data
public class SysInnerOperateLogListVO extends PageVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("方法英文名")
    @Column( name="method_name")
    private String methodName;

    @ApiModelProperty("操作人姓名")
    @Column( name="operator_name")
    private String operatorName;

    @ApiModelProperty("创建时间起始")
    private String createTimeStart;

    @ApiModelProperty("创建时间结束")
    private String createTimeEnd;

    @ApiModelProperty("关键字查询：请求入参/返回值")
    private String keyWord;

    @ApiModelProperty("模块名称")
    @Column( name="method_ch_name")
    private String methodChName;

    @ApiModelProperty("请求url")
    @Column( name="url")
    private String url;

}
