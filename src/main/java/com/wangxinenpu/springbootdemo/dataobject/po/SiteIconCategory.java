 
package com.wangxinenpu.springbootdemo.dataobject.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;


@Data
public class SiteIconCategory implements Serializable {


	//========== properties ==========

	@Id
	@GeneratedValue(generator="JDBC")
    @ApiModelProperty("类别id")
    @Column( name="id")
    private Long id;

    @ApiModelProperty("类别名称")
    @Column( name="name")
    private String name;

    @ApiModelProperty("排序序号")
    @Column( name="sort_no")
    private Integer sortNo;

    @ApiModelProperty("业务类型 1医保，2社保")
    @Column( name="buss_type")
    private Integer bussType;

    @ApiModelProperty("创建人")
    @Column( name="operator_id")
    private String operatorId;

    @ApiModelProperty("修改人")
    @Column( name="modifier_id")
    private String modifierId;

    @ApiModelProperty("创建时间")
    @Column( name="create_time")
    private Date createTime;

    @ApiModelProperty("修改时间")
    @Column( name="modify_time")
    private Date modifyTime;
    
    @ApiModelProperty("父节点id")
    @Column( name="parent_id")
    private Long parentId;
    
    @ApiModelProperty("是否显示（0，否；1，是）")
    @Column( name="is_show")
    private String isShow;
    
}
