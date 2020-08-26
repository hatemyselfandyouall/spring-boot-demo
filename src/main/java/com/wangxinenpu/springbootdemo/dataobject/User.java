 
package com.wangxinenpu.springbootdemo.dataobject;

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
public class User implements Serializable{


	//========== properties ==========

	@Id
	@GeneratedValue(generator="JDBC")
    @ApiModelProperty("用户id")
    @Column( name="user_id")
    private Long userId;

    @ApiModelProperty("用户名")
    @Column( name="username")
    private String username;

    @ApiModelProperty("用户密码")
    @Column( name="password")
    private String password;

    @ApiModelProperty("创建时间")
    @Column( name="create_time")
    private Date createTime;




}
