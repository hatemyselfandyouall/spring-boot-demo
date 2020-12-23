 
package com.wangxinenpu.springbootdemo.dataobject.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;


@Data
public class SiteBanner implements Serializable {


	//========== properties ==========

	@Id
	@GeneratedValue(generator="JDBC")
    @ApiModelProperty("id")
    @Column( name="id")
    private Long id;

    @ApiModelProperty("banner名")
    @Column( name="name")
    private String name;

    @ApiModelProperty("所属层id")
    @Column( name="floor_id")
    private Long floorId;

    @ApiModelProperty("跳转的url")
    @Column( name="jump_url")
    private String jumpUrl;

    @ApiModelProperty("banner图片")
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

    @ApiModelProperty("0未删除1已删除")
    @Column( name="is_delete")
    private Integer isDelete;

    @ApiModelProperty("")
    @Column( name="create_time")
    private Date createTime;

    @ApiModelProperty("")
    @Column( name="modify_time")
    private Date modifyTime;




}
