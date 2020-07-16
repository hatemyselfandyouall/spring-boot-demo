 
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
    @ApiModelProperty("")
    @Column( name="user_id")
    private Long userId;

    @ApiModelProperty("")
    @Column( name="username")
    private String username;

    @ApiModelProperty("")
    @Column( name="password")
    private String password;

    @ApiModelProperty("")
    @Column( name="total_correct")
    private Integer totalCorrect;

    @ApiModelProperty("")
    @Column( name="total_answer")
    private Integer totalAnswer;



}
