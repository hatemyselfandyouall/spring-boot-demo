 
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
public class LinkTransferTaskHistory implements Serializable{

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
    @Column( name="task_id")
    private Long taskId;

    @ApiModelProperty("")
    @Column( name="execute_status")
    private LinkTransferTaskExecuteStatusEnum executeStatus;

    @ApiModelProperty("最近一次执行详情")
    @Column( name="last_execute_detail_msg")
    private String lastExecuteDetailMsg;

    @ApiModelProperty("最近一次执行详情")
    @Column( name="current_job_name")
    private String currentJobName;

    @ApiModelProperty("")
    @Column( name="last_execute_exception_msg")
    private String lastExecuteExceptionMsg;


    @ApiModelProperty("")
    @Column( name="from_data_link_name")
    private String fromDataLinkName;

    @ApiModelProperty("")
    @Column( name="from_data_link_id")
    private Long fromDataLinkId;

    @ApiModelProperty("")
    @Column( name="to_data_link_name")
    private String toDataLinkName;

    @ApiModelProperty("")
    @Column( name="to_data_link_id")
    private Long toDataLinkId;

    @ApiModelProperty("目标表，以逗号分隔")
    @Column( name="target_tables_string")
    private String targetTablesString;

    @ApiModelProperty("")
    @Column( name="task_end_time")
    private Date taskEndTime;

    @ApiModelProperty("")
    @Column( name="name")
    private String name;

    @ApiModelProperty("")
    @Column( name="success_trans_row_count")
    private Long successTransRowCount;

    @ApiModelProperty("")
    @Column( name="fail_trans_row_count")
    private Long failTransRowCount;

    @ApiModelProperty("")
    @Column( name="execute_time")
    private String executeTime;

    @ApiModelProperty("")
    @Column( name="execute_time_second")
    private Long executeTimeSecond;

    @ApiModelProperty("")
    @Column( name="executor_name")
    private String executorName;
}
