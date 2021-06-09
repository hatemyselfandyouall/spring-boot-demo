 
package com.wangxinenpu.springbootdemo.dataobject.po.linkTask;

import java.io.Serializable;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.persistence.*;

import java.lang.String;
import java.lang.Integer;
import java.util.Date;
import java.util.List;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
public class LinkTransferTaskTotal implements Serializable{


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

    @ApiModelProperty("增量同步起始时间")
    @Column( name="now_parse_scn")
    private Long nowParseScn;

    @ApiModelProperty("增量同步结束时间")
    @Column( name="now_parse_time")
    private String nowParseTime;

    @ApiModelProperty("增量同步起始scn")
    @Column( name="cdc_start_scn")
    private String cdcStartScn;




}
