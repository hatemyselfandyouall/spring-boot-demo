 
package com.wangxinenpu.springbootdemo.dataobject.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Data
@Accessors(chain = true)
public class SysMenuChangeLog implements Serializable {


	//========== properties ==========

	@Id
	@GeneratedValue(generator="JDBC")
    @ApiModelProperty("")
    @Column( name="id")
    private Long id;

    @ApiModelProperty("")
    @Column( name="matter_id")
    private Long matterId;

    @ApiModelProperty("")
    @Column( name="chuange_json")
    private String chuangeJson;

    @ApiModelProperty("")
    @Column( name="editor_id")
    private String editorId;

    @ApiModelProperty("")
    @Column( name="create_time")
    private Date createTime;




}
