package com.wangxinenpu.springbootdemo.dataobject.vo.SiteMatters;

import com.wangxinenpu.springbootdemo.dataobject.vo.SiteBlock.SiteBlockSaveVO;
import com.wangxinenpu.springbootdemo.dataobject.vo.SiteMattersArea.SiteMattersAreaSaveVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.List;

@Data
public class SSMSiteMattersSaveVO implements Serializable {
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

    @ApiModelProperty("业务类型 1医保，2社保")
    @Column( name="buss_type")
    private Integer bussType;

    @ApiModelProperty("业务类型id")
    @Column( name="business_type_id")
    private Long siteBusinessType;

    @ApiModelProperty("创建人")
    @Column( name="operator_id")
    private String operatorId;

    @ApiModelProperty("修改人")
    @Column( name="modifier_id")
    private String modifierId;
    
    @ApiModelProperty("系统菜单id")
    @Column( name="sys_function_id")
    private String sysFunctionId;

    @ApiModelProperty("系统菜单名称")
    @Column( name="sys_function_name")
    private String sysFunctionName;

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

    private List<SiteMattersAreaSaveVO> siteMattersAreaSaveVOS;

    private SiteBlockSaveVO block;

}
