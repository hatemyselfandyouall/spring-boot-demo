 
package com.wangxinenpu.springbootdemo.dataobject.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;


@Data
@Accessors(chain = true)
public class OpenapiSelfmachineAddress implements Serializable {


	//========== properties ==========

	@Id
	@GeneratedValue(generator="JDBC")
    @ApiModelProperty("")
    @Column( name="id")
    private Long id;

    @ApiModelProperty("")
    @Column( name="user_id")
    private Long userId;

    @ApiModelProperty("")
    @Column( name="org_code")
    private String orgCode;


    @ApiModelProperty("省")
    @Column( name="province")
    private String province;

    @ApiModelProperty("市")
    @Column( name="city")
    private String city;

    @ApiModelProperty("区")
    @Column( name="district")
    private String district;

    @ApiModelProperty("")
    @Column( name="address")
    private String address;

    @ApiModelProperty("最近一次使用为1，其他0")
    @Column( name="is_last_used")
    private Integer isLastUsed;

    @ApiModelProperty("")
    @Column( name="create_time")
    private Date createTime;

    @ApiModelProperty("")
    @Column( name="creator_name")
    private String creatorName;

    @ApiModelProperty("删除为1，未删除0")
    @Column( name="is_delete")
    private Integer isDelete;




}
