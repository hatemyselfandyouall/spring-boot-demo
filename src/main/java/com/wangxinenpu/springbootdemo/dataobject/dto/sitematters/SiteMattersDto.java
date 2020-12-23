 
package com.wangxinenpu.springbootdemo.dataobject.dto.sitematters;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


@Data
@Table
public class SiteMattersDto implements Serializable {


	//========== properties ==========

    @ApiModelProperty("事项id")
    private Long id;

    @ApiModelProperty("事项名称")
    private String name;

    @ApiModelProperty("事项简称")
    private String shortName;

    @ApiModelProperty("事项类型id")
    private Long iconCategoryId;

    @ApiModelProperty("事项类型名称")
    private String iconCategoryName;

    @ApiModelProperty("业务类型 1医保，2社保")
    private Integer bussType;

    @ApiModelProperty("业务类型id")
    private Long siteBusinessType;

    @ApiModelProperty("网上办图标id")
    private Long blockPcId;

    @ApiModelProperty("掌上办图标id")
    private Long blockAppId;

    @ApiModelProperty("自助机图标id")
    private Long blockSsmId;

    @ApiModelProperty("创建人")
    private String operatorId;

    @ApiModelProperty("修改人")
    private String modifierId;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("修改时间")
    private Date modifyTime;

    @ApiModelProperty("創建人")
    private String creatorName;

    @ApiModelProperty("修改人")
    private String modifierName;
    
    @ApiModelProperty("系统菜单id")
    private String sysFunctionId;

    @ApiModelProperty("系统菜单名称")
    private String sysFunctionName;

    @ApiModelProperty("insideCode")
    private String insideCode;

    @ApiModelProperty("是否生效（1，生效；2，失效 默认1生效）")
    private String isTakesEffect;

    @ApiModelProperty("是否推荐（0，不推荐；1，推荐 默认0不推荐）")
    private String isRecommend;

    @ApiModelProperty("事项编号")
    private String matterNumber;

    @ApiModelProperty("事项编码")
    private String mattersCoding;

    @ApiModelProperty("统筹区类型")
    private String coordinationAreaType;

    @ApiModelProperty("打印类型")
    private String printType;

    @ApiModelProperty("业务编码")
    private String businessNumber;

    @ApiModelProperty("天正关联id")
    private String sxxxId;

    @ApiModelProperty("管理编码")
    private String manageCode;
    
    @ApiModelProperty("是否需要二次验证（0，否；1，是）")
    private String isVerification;
    
    @ApiModelProperty("是否为网报事项（0，否；1，是）")
    private String isNetwork;
}
