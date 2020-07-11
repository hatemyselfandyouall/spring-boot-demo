package com.wangxinenpu.springbootdemo.dataobject.vo.Exam;

import com.wangxinenpu.springbootdemo.dataobject.Question;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = true)
public class ExamDetailShowVO implements Serializable {
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

    private List<Question> questionList;
}
