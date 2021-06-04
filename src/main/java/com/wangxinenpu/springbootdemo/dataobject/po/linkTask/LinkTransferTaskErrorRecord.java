 
package com.wangxinenpu.springbootdemo.dataobject.po.linkTask;

import java.io.Serializable;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.persistence.*;

import java.lang.String;
import java.util.Date;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
public class LinkTransferTaskErrorRecord implements Serializable{


	//========== properties ==========

	@Id
	@GeneratedValue(generator="JDBC")
    @ApiModelProperty("")
    @Column( name="id")
    private Long id;

    @ApiModelProperty("")
    @Column( name="sql")
    private String sql;

    @ApiModelProperty("")
    @Column( name="exception_trace")
    private String exceptionTrace;

    @ApiModelProperty("")
    @Column( name="exception_msg")
    private String exceptionMsg;

    @ApiModelProperty("")
    @Column( name="create_time")
    private Date createTime;




}
