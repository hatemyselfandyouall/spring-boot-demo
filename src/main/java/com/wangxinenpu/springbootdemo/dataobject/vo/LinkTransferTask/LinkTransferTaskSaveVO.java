package com.wangxinenpu.springbootdemo.dataobject.vo.LinkTransferTask;


import com.wangxinenpu.springbootdemo.dataobject.po.linkTask.LinkTransferTaskRule;
import com.wangxinenpu.springbootdemo.dataobject.po.linkTask.LinkTransferTaskStatusEnum;
import com.wangxinenpu.springbootdemo.dataobject.po.linkTask.LinkTransferTaskTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

@Data
public class LinkTransferTaskSaveVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("")
    @Column( name="id")
    private Long id;

    @ApiModelProperty("")
    @Column( name="name")
    private String name;

    @ApiModelProperty("")
    @Column( name="status")
    private LinkTransferTaskStatusEnum status;

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

    @ApiModelProperty("增量类型")
    @Column( name="cdc_type_code")
    private String cdcTypeCode;

    @ApiModelProperty("增量同步起始scn")
    @Column( name="cdc_start_scn")
    private String cdcStartScn;

    @ApiModelProperty("1表示使用离线模式，会将文件下载到本地分析")
    @Column( name="is_offline_mode")
    private Integer isOfflineMode;
    @Column( name="seg_name")
    private String segName;

    private LinkTransferTaskRule linkTransferTaskRule;
}
