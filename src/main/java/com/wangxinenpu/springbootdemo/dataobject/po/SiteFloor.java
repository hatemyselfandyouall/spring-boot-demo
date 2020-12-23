 
package com.wangxinenpu.springbootdemo.dataobject.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;


@Data
public class SiteFloor implements Serializable {


	//========== properties ==========

	@Id
	@GeneratedValue(generator="JDBC")
    @ApiModelProperty("层id")
    @Column( name="id")
    private Long id;

    @ApiModelProperty("层名称")
    @Column( name="name")
    private String name;

    @ApiModelProperty("类型0banner1block2其他")
    @Column( name="type")
    private Integer type;

    @ApiModelProperty("code")
    @Column( name="code")
    private String code;

    @ApiModelProperty("所属地区码")
    @Column( name="area_code")
    private String areaCode;

    @ApiModelProperty("所属平台码")
    @Column( name="platform_code")
    private String platformCode;

    @ApiModelProperty("0未删除1已删除")
    @Column( name="is_delete")
    private Integer isDelete;

    @ApiModelProperty("")
    @Column( name="create_time")
    private Date createTime;

    @ApiModelProperty("")
    @Column( name="modify_time")
    private Date modifyTime;




}
