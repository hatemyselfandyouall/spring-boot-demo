package com.wangxinenpu.springbootdemo.dataobject.vo.Exam;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

@Data
public class SaveExamResultVO implements Serializable {

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
