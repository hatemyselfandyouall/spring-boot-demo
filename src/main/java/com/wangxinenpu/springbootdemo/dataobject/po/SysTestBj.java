 
package com.wangxinenpu.springbootdemo.dataobject.po;

import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;


@Data
public class SysTestBj implements Serializable {


	//========== properties ==========

    @Column( name="title")
    private String title;

    @Column( name="user_id")
    private String userId;

    @Column( name="opseno")
    private String opseno;

    @Column( name="projid")
    private String projid;
}
