package com.wangxinenpu.springbootdemo.dataobject.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Data
public class SiteHomeConfig implements Serializable {


    @Id
    @GeneratedValue(generator="JDBC")
    @ApiModelProperty("层id")
    @Column( name="id")
    private Long id;

//    @ApiModelProperty("事项关联Id")
//    @Column( name="site_matters_area_id")
//    private Long siteMattersAreaId;
    @ApiModelProperty("排序序号")
    @Column( name="sort_number")
    private Integer sortNumber;

    @ApiModelProperty("事项id")
    @Column( name="matters_id")
    private Long mattersId;

    @ApiModelProperty("区域id")
    @Column( name="area_id")
    private Long areaId;


    @ApiModelProperty("图标id")
    @Column( name="block_app_id")
    private Long blockAppId;


    @ApiModelProperty("状态 1 单位 2 个人")
    @Column( name="status_type")
    private Long statusType;

}
