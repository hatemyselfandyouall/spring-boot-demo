 
package com.wangxinenpu.springbootdemo.dataobject.dto.sitematters;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;


@Data
@Table
public class SiteIconCategoryAndMattersDto implements Serializable {


	//========== properties ==========

    @ApiModelProperty("事项分类集合")
    private List<SiteIconCategoryDto> siteIconCategoryDtos;

    @ApiModelProperty("事项集合")
    private List<SiteMattersInfoDto> siteMattersInfoDtos;
}
