package com.wangxinenpu.springbootdemo.dataobject.vo.SiteBlock;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

@Data
public class SiteBlockSaveVO implements Serializable {

    private static final long serialVersionUID = 1L;



    @ApiModelProperty("block名")
    @Column( name="name")
    private String name;

    @ApiModelProperty("所属层id")
    @Column( name="floor_id")
    private Long floorId;

    @ApiModelProperty("跳转的url")
    @Column( name="jump_url")
    private String jumpUrl;

    @ApiModelProperty("block图片url")
    @Column( name="pic_url")
    private String picUrl;

    @ApiModelProperty("所属地区码")
    @Column( name="area_code")
    private String areaCode;

    @ApiModelProperty("所属平台码")
    @Column( name="platform_code")
    private String platformCode;

    @ApiModelProperty("上角标id")
    @Column( name="up_corner_marker_id")
    private Long upCornerMarkerId;

    @ApiModelProperty("下角标id")
    @Column( name="down_corner_marker_id")
    private Long downCornerMarkerId;

    @ApiModelProperty("类别id")
    @Column( name="icon_category_id")
    private Integer iconCategoryId;

    @ApiModelProperty("是不是首页展示")
    @Column( name="is_home")
    private String isHome;

    @ApiModelProperty("1未生效2已生效")
    @Column( name="status")
    private Integer status;

    @ApiModelProperty("排序序号")
    @Column( name="sort_number")
    private Integer sortNumber;

}
