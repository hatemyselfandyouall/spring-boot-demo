 
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
public class Exam implements Serializable{


	//========== properties ==========

	@Id
	@GeneratedValue(generator="JDBC")
    @ApiModelProperty("")
    @Column( name="id")
    private Long id;

    @ApiModelProperty("")
    @Column( name="tag")
    private String tag;

    @ApiModelProperty("")
    @Column( name="author")
    private String author;

    @ApiModelProperty("")
    @Column( name="year")
    private String year;

    @ApiModelProperty("")
    @Column( name="num_single")
    private Integer numSingle;

    @ApiModelProperty("")
    @Column( name="num_judge")
    private Integer numJudge;




}
