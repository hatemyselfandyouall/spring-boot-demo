 
package com.wangxinenpu.springbootdemo.dataobject.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;


@Data
public class OpenapiOrgInterface implements Serializable {


	//========== properties ==========
	
    @ApiModelProperty("")
    @Column( name="id")
    private Long id;

    @ApiModelProperty("")
    @Column( name="org_code")
    private String orgCode;

    @ApiModelProperty("")
    @Column( name="interface_id")
    private Long interfaceId;


    @ApiModelProperty("")
    @Column( name="create_time")
    private Date createTime;

    @ApiModelProperty("")
    @Column( name="modify_time")
    private Date modifyTime;




}
