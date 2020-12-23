 
package com.wangxinenpu.springbootdemo.dataobject.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


@Data
@Table
@Accessors(chain = true)
public class SiteSidMatters implements Serializable {


	//========== properties ==========

	@Id
	@GeneratedValue(generator="JDBC")
    @ApiModelProperty("事项id")
    @Column( name="id")
    private Long id;

    @ApiModelProperty("事项名称")
    @Column( name="matters_id")
    private Long mattersId;

    @ApiModelProperty("事项简称")
    @Column( name="basic_code")
    private String basicCode;

    @ApiModelProperty("事项简称")
    @Column( name="inside_code")
    private String insideCode;

    @ApiModelProperty("事项类型id")
    @Column( name="region_code")
    private String regionCode;

    @ApiModelProperty("业务类型 1医保，2社保")
    @Column( name="buss_type")
    private Integer bussType;

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

    @ApiModelProperty("天正关联id")
    private String sxxxId;

}
