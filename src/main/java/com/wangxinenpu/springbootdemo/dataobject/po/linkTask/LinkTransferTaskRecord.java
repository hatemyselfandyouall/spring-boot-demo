 
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
public class LinkTransferTaskRecord implements Serializable{


	//========== properties ==========

	@Id
	@GeneratedValue(generator="JDBC")
    @ApiModelProperty("")
    @Column( name="id")
    private Long id;

    @ApiModelProperty("")
    @Column( name="save_sql")
    private String saveSql;

    @ApiModelProperty("")
    @Column( name="table_name")
    private String tableName;

    @ApiModelProperty("")
    @Column( name="seg_owner")
    private String segOwner;

    @ApiModelProperty("")
    @Column( name="scn")
    private Long scn;

    @ApiModelProperty("")
    @Column( name="create_time")
    private Date createTime;




}
