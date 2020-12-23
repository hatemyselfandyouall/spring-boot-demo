 
package com.wangxinenpu.springbootdemo.dataobject.dto.sitematters;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


@Data
public class SiteIconCategoryDto implements Serializable {


	//========== properties ==========


    @ApiModelProperty("类别id")
    private Long id;

    @ApiModelProperty("类别名称")
    private String name;

    @ApiModelProperty("排序序号")
    private Integer sortNo;

    @ApiModelProperty("事项数量")
    private Integer mattersCount;
    
    @ApiModelProperty("子类数量")
    private Integer categoryCount;
    
    @ApiModelProperty("父节点ID")
    private Long parentId;
}
