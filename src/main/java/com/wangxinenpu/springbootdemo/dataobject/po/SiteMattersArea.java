 
package com.wangxinenpu.springbootdemo.dataobject.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;


@Data
@Accessors(chain = true)
public class SiteMattersArea implements Serializable {


	//========== properties ==========

    @ApiModelProperty("事项区域关联表id")
    @Column( name="id")
    private Long id;

    @ApiModelProperty("区域id")
    @Column( name="area_id")
    private Long areaId;

    @ApiModelProperty("区域路径")
    @Column( name="area_path")
    private String areaPath;

    @ApiModelProperty("账号类型id")
    @Column( name="account_type_id")
    private Long accountTypeId;

    @ApiModelProperty("网上办图标id")
    @Column( name="block_pc_id")
    private Long blockPcId;

    @ApiModelProperty("掌上办图标id")
    @Column( name="block_app_id")
    private Long blockAppId;

    @ApiModelProperty("机器编码")
    @Column( name="machine_number")
    private String machineNumber;

    @ApiModelProperty("事项id")
    @Column( name="matters_id")
    private Long mattersId;

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

    @ApiModelProperty("0未删除1已删除")
    @Column( name="is_delete")
    private Integer isDelete;
}
