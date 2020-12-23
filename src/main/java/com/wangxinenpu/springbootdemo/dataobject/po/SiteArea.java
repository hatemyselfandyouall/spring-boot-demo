 
package com.wangxinenpu.springbootdemo.dataobject.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;


@Data
public class SiteArea implements Serializable {


	//========== properties ==========

	@Id
	@GeneratedValue(generator="JDBC")
    @ApiModelProperty("id")
    @Column( name="id")
    private Long id;

    @ApiModelProperty("上级id")
    @Column( name="parent_id")
    private Long parentId;

    @ApiModelProperty("区域名称")
    @Column( name="name")
    private String name;

    @ApiModelProperty("业务类型 1医保，2社保")
    @Column( name="buss_type")
    private String bussType;

    @Transient
    @ApiModelProperty("区域路径")
    @Column( name="area_path")
    private String areaPath;

}
