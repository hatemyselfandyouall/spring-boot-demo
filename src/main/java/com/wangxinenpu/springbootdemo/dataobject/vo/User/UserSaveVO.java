package com.wangxinenpu.springbootdemo.dataobject.vo.User;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Data
public class UserSaveVO implements Serializable {

    private static final long serialVersionUID = 1L;



    @ApiModelProperty("")
    @Column( name="username")
    private String username;

    @ApiModelProperty("")
    @Column( name="password")
    private String password;




}
