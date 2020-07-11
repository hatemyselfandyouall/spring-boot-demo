package com.wangxinenpu.springbootdemo.dataobject.vo.User;

import com.wangxinenpu.springbootdemo.dataobject.Exam;
import com.wangxinenpu.springbootdemo.dataobject.ExamResult;
import com.wangxinenpu.springbootdemo.dataobject.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = true)
public class UserDetailShowVO implements Serializable {

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

    private List<ExamResult> examResults;

}
