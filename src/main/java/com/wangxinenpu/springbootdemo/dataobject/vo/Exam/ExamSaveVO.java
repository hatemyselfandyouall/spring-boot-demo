package com.wangxinenpu.springbootdemo.dataobject.vo.Exam;

import com.wangxinenpu.springbootdemo.dataobject.Question;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.List;

@Data
public class ExamSaveVO implements Serializable {

    private static final long serialVersionUID = 1L;


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

    List<Question> questions;

}
