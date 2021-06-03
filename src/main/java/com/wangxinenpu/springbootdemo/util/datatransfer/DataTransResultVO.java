package com.wangxinenpu.springbootdemo.util.datatransfer;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;

@Data
@Accessors(chain = true)
public class DataTransResultVO {

    private String lastScn;

    private String lastTime;

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
}
