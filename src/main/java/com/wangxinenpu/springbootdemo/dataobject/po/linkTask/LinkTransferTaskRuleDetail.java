 
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
public class LinkTransferTaskRuleDetail implements Serializable{


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
    @Column( name="rule_id")
    private Long ruleId;

    @ApiModelProperty("目标字段名")
    @Column( name="column_name")
    private String columnName;

    @ApiModelProperty("")
    @Column( name="column_rule_type")
    private ColumnRuleTypeEnum columnRuleType;

    @ApiModelProperty("目标字段值")
    @Column( name="column_value")
    private String columnValue;




}
