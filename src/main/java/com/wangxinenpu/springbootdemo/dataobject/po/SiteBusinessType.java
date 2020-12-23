 
package com.wangxinenpu.springbootdemo.dataobject.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;


@Data
public class SiteBusinessType implements Serializable {


	//========== properties ==========

	@Id
	@GeneratedValue(generator="JDBC")
    @ApiModelProperty("业务类型id")
    @Column( name="id")
    private Long id;

    @ApiModelProperty("业务类型名称")
    @Column( name="name")
    private String name;

    @ApiModelProperty("当事人类别(1单位，2个人)")
    @Column( name="type")
    private String type;

}
