package com.wangxinenpu.springbootdemo.dataobject.vo.OpenapiOrgShortname;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

@Data
public class OpenapiOrgShortnameDetailVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("")
    @Column( name="id")
    private Long id;

    @ApiModelProperty("")
    @Column( name="org_code")
    private String orgCode;

}
