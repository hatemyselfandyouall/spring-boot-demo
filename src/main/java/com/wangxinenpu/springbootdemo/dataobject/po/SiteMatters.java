 
package com.wangxinenpu.springbootdemo.dataobject.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Data
@Table
public class SiteMatters implements Serializable {


	//========== properties ==========

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

    @ApiModelProperty("事项类型名称")
    @Column( name="iconCategoryName")
    @Transient
    private String iconCategoryName;

    @ApiModelProperty("业务类型 1医保，2社保")
    @Column( name="buss_type")
    private Integer bussType;

    @ApiModelProperty("业务类型id")
    @Column( name="business_type_id")
    private Long siteBusinessType;

    @ApiModelProperty("网上办图标id")
    @Column( name="block_pc_id")
    private Long blockPcId;

    @ApiModelProperty("掌上办图标id")
    @Column( name="block_app_id")
    private Long blockAppId;

    @ApiModelProperty("自助机图标id")
    @Column( name="block_ssm_id")
    private Long blockSsmId;

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

    @Transient
    @ApiModelProperty("創建人")
    private String creatorName;;
    @Transient
    @ApiModelProperty("修改人")
    private String modifierName;
    
    @ApiModelProperty("系统菜单id")
    @Column( name="sys_function_id")
    private String sysFunctionId;

    @ApiModelProperty("系统菜单名称")
    @Column( name="sys_function_name")
    private String sysFunctionName;

    @ApiModelProperty("是否生效（1，生效；2，失效 默认1生效）")
    @Column( name="is_takes_effect")
    private String isTakesEffect;

    @ApiModelProperty("是否推荐（0，不推荐；1，推荐 默认0不推荐）")
    @Column( name="is_recommend")
    private String isRecommend;

    @ApiModelProperty("事项编号")
    @Column( name="matter_number")
    private String matterNumber;

    @ApiModelProperty("事项编码")
    @Column( name="matters_coding")
    private String mattersCoding;

    @ApiModelProperty("统筹区类型")
    @Column( name="coordination_area_type")
    private String coordinationAreaType;

    @ApiModelProperty("打印类型")
    @Column( name="print_type")
    private String printType;

    @ApiModelProperty("业务编码")
    @Column( name="business_number")
    private String businessNumber;

    @ApiModelProperty("管理编码")
    @Column( name="manage_code")
    private String manageCode;
    
    @ApiModelProperty("是否需要二次验证（0，否；1，是）")
    @Column( name="is_verification")
    private String isVerification;
    
    @ApiModelProperty("是否为网报事项（0，否；1，是）")
    @Column( name="is_network")
    private String isNetwork;
    
}
