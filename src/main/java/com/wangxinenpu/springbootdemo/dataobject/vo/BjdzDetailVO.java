package com.wangxinenpu.springbootdemo.dataobject.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;

@Data
public class BjdzDetailVO implements Serializable {

    private Long id;

    @ApiModelProperty("任务id")
    @Column( name="projid")
    private String projid;

    private String bjjg;
    private String bjjgms;
    private Date bjsj;
    private String bjzt;
    private Long opseno;
    private String state;
    private Date createTime;
    private Date modifyTime;

    private String opeartorName;

    private Long orgCode;

    private String orgName;

    private String compareState;

    private String titileName;
}
