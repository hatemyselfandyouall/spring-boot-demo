 
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
public class ExamResult implements Serializable{


	//========== properties ==========

	@Id
	@GeneratedValue(generator="JDBC")
    @ApiModelProperty("")
    @Column( name="id")
    private Long id;

    @ApiModelProperty("")
    @Column( name="user_id")
    private Long userId;

    @ApiModelProperty("")
    @Column( name="exam_id")
    private Long examId;

    @ApiModelProperty("")
    @Column( name="done")
    private Integer done;

    @ApiModelProperty("")
    @Column( name="num_correct")
    private Integer numCorrect;

    @ApiModelProperty("")
    @Column( name="num_answered")
    private Integer numAnswered;

    @ApiModelProperty("")
    @Column( name="answers")
    private String answers;




}
