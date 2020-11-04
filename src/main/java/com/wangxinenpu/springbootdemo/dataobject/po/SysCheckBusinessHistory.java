 
package com.wangxinenpu.springbootdemo.dataobject.po;

import lombok.Data;
import star.vo.BaseVo;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;


@Data
public class SysCheckBusinessHistory extends BaseVo implements Serializable {


	//========== properties ==========

	@Id
	@GeneratedValue(generator="JDBC")
    @Column( name="id")
    private Long id;

    @Column( name="bus_id")
    private Long busId;

    @Column( name="cancel_people")
    private String cancelPeople;

    @Column( name="cancel_time")
    private Date cancelTime;

    @Column( name="create_time")
    private Date createTime;

    @Column( name="modify_time")
    private Date modifyTime;




}
