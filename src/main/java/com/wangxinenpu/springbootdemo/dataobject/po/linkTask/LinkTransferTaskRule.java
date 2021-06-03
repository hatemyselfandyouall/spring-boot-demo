 
package com.wangxinenpu.springbootdemo.dataobject.po.linkTask;

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
public class LinkTransferTaskRule implements Serializable{


	//========== properties ==========

	@Id
	@GeneratedValue(generator="JDBC")
    @ApiModelProperty("")
    @Column( name="id")
    private Long id;

    @ApiModelProperty("")
    @Column( name="create_time")
    private Date createTime;

    @ApiModelProperty("")
    @Column( name="modify_time")
    private Date modifyTime;

    @ApiModelProperty("")
    @Column( name="name")
    private String name;

    @ApiModelProperty("")
    @Column( name="status")
    private LinkTransferTaskRuleStatusEnum status;

    @ApiModelProperty("")
    @Column( name="from_data_link_name")
    private String fromDataLinkName;

    @ApiModelProperty("")
    @Column( name="from_data_link_id")
    private Long fromDataLinkId;

    @ApiModelProperty("目标表，以逗号分隔")
    @Column( name="target_tables_string")
    private String targetTablesString;

    @ApiModelProperty("目标字段名")
    @Column( name="column_name")
    private String columnName;

    @ApiModelProperty("")
    @Column( name="column_rule_type")
    private ColumnRuleTypeEnum columnRuleType;

    @ApiModelProperty("目标字段值")
    @Column( name="column_value")
    private String columnValue;

    @ApiModelProperty("")
    @Column( name="creator_id")
    private Long creatorId;

    @ApiModelProperty("")
    @Column( name="creator_name")
    private String creatorName;

    @ApiModelProperty("")
    @Column( name="creator_org_code")
    private String creatorOrgCode;

    @ApiModelProperty("")
    @Column( name="cdc_type_code")
    private String cdcTypeCode;


    @Column( name="seg_name")
    private String segName;

}
