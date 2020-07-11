 
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
public class Question implements Serializable{


	//========== properties ==========

	@Id
	@GeneratedValue(generator="JDBC")
    @ApiModelProperty("")
    @Column( name="id")
    private Long id;

    @ApiModelProperty("examId")
    @Column( name="exam_id")
	private Long examId;

    @ApiModelProperty("")
    @Column( name="tag")
    private String tag;

    @ApiModelProperty("")
    @Column( name="type")
    private Integer type;

    @ApiModelProperty("")
    @Column( name="author")
    private String author;

    @ApiModelProperty("")
    @Column( name="year")
    private String year;

    @ApiModelProperty("")
    @Column( name="difficulty")
    private String difficulty;

    @ApiModelProperty("")
    @Column( name="title")
    private String title;

    @ApiModelProperty("")
    @Column( name="image")
    private String image;

    @ApiModelProperty("")
    @Column( name="options")
    private String options;

    @ApiModelProperty("")
    @Column( name="answer")
    private String answer;

    @ApiModelProperty("")
    @Column( name="solution")
    private String solution;




}
