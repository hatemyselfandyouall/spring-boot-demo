 
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
public class LinkTransferTask implements Serializable{


	//========== properties ==========

	@Id
	@GeneratedValue(generator="JDBC")
    @ApiModelProperty("")
    @Column( name="id")
    private Long id;

    @ApiModelProperty("")
    @Column( name="name")
    private String name;

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
    @Column( name="create_time")
    private Date createTime;

    @ApiModelProperty("")
    @Column( name="modify_time")
    private Date modifyTime;

    @ApiModelProperty("状态")
    @Column( name="status")
    private LinkTransferTaskStatusEnum status;

    @ApiModelProperty("执行状态")
    @Column( name="execute_status")
    private  LinkTransferTaskExecuteStatusEnum executeStatus;

    @ApiModelProperty("最近一次执行详情")
    @Column( name="last_execute_detail_msg")
    private String lastExecuteDetailMsg;

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

    @ApiModelProperty("full表示全量,cdc表示增量")
    @Column( name="transfer_type")
    private LinkTransferTaskTypeEnum transferType;

    @ApiModelProperty("时间周期，以cron表达式形式存在")
    @Column( name="time_cron")
    private String timeCron;

    @ApiModelProperty("增量同步起始时间")
    @Column( name="cdc_start_time")
    private String cdcStartTime;

    @ApiModelProperty("增量同步结束时间")
    @Column( name="cdc_end_time")
    private String cdcEndTime;

    @ApiModelProperty("增量同步起始scn")
    @Column( name="cdc_start_scn")
    private String cdcStartScn;


    @ApiModelProperty("增量类型")
    @Column( name="cdc_type_code")
    private String cdcTypeCode;

    @ApiModelProperty("最近一次执行详情")
    @Column( name="current_job_name")
    private String currentJobName;

    @ApiModelProperty("1表示使用离线模式，会将文件下载到本地分析")
    @Column( name="is_offline_mode")
    private Integer isOfflineMode;

    @Column( name="seg_name")
    private String segName;
}
