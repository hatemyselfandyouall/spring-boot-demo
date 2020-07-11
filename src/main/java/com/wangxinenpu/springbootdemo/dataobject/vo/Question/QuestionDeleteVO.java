package com.wangxinenpu.springbootdemo.dataobject.vo.Question;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

@Data
public class QuestionDeleteVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("接口id")
    @Column( name="id")
    private Long id;

    @ApiModelProperty("0未删除1已删除")
    @Column( name="is_delete")
    private Integer isDelete;

}
