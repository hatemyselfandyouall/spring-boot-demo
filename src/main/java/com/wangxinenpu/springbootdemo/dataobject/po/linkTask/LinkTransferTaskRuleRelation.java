 
package com.wangxinenpu.springbootdemo.dataobject.po.linkTask;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;


@Data
@Accessors(chain = true)
public class LinkTransferTaskRuleRelation implements Serializable{


	//========== properties ==========

    @Column( name="id")
    private String id;

    @ApiModelProperty("")
    @Column( name="create_time")
    private Date createTime;

    @ApiModelProperty("")
    @Column( name="modify_time")
    private Date modifyTime;

    @ApiModelProperty("")
    @Column( name="status")
    private LinkTransferTaskRuleStatusEnum status;

    @ApiModelProperty("")
    @Column( name="rule_id")
    private Long ruleId;

    @ApiModelProperty("")
    @Column( name="task_id")
    private Long taskId;




}
