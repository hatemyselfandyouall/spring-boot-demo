 
package com.wangxinenpu.springbootdemo.dataobject.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;


@Data
@Accessors(chain = true)
public class SiteMattersPermissionArea implements Serializable {


	//========== properties ==========

	@Id
	@GeneratedValue(generator="JDBC")
    @ApiModelProperty("主键id")
    @Column( name="id")
    private Long id;

    @ApiModelProperty("区域id")
    @Column( name="area_id")
    private Long areaId;

    @ApiModelProperty("")
    @Column( name="area_path")
    private String areaPath;

    @ApiModelProperty("事项id")
    @Column( name="matters_id")
    private Long mattersId;

    @ApiModelProperty("操作员id")
    @Column( name="operator_id")
    private Long operatorId;

    @ApiModelProperty("修改人id")
    @Column( name="modifier_id")
    private Long modifierId;

    @ApiModelProperty("创建时间")
    @Column( name="create_time")
    private Date createTime;

    @ApiModelProperty("修改时间")
    @Column( name="modify_time")
    private Date modifyTime;

    @ApiModelProperty("")
    @Column( name="remark")
    private String remark;

    @ApiModelProperty("0未删除1已删除")
    @Column( name="is_delete")
    private Integer isDelete;




}
