package com.wangxinenpu.springbootdemo.dataobject.vo.SiteMatters;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Data
public class SiteMattersSaveVoRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(generator="JDBC")
    @ApiModelProperty("事项id")
    @Column( name="id")
    private Long id;

    @ApiModelProperty("事项名称")
    @Column( name="name")
    private String name;

    @ApiModelProperty("事项简称")
    @Column( name="short_name")
    private String shortName;

    @ApiModelProperty("事项类型id")
    @Column( name="icon_category_id")
    private Long iconCategoryId;
}
