package com.wangxinenpu.springbootdemo.dataobject.vo.SiteMattersArea;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

@Data
public class SiteMattersAreaSaveVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("区域id")
    @Column( name="area_id")
    private Long areaId;

    @ApiModelProperty("区域路径")
    @Column( name="area_path")
    private String areaPath;

    @ApiModelProperty("市")
    private String city;

    @ApiModelProperty("区")
    private String region;

    @ApiModelProperty("账号类型id")
    @Column( name="account_type_id")
    private Long accountTypeId;

    @ApiModelProperty("事项id")
    @Column( name="matters_id")
    private Long mattersId;

    @ApiModelProperty("机器编码")
    private String machineNumber;

}
