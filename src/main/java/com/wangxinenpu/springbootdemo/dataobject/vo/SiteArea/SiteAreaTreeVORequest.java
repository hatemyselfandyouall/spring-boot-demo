package com.wangxinenpu.springbootdemo.dataobject.vo.SiteArea;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.List;

@Data
public class SiteAreaTreeVORequest implements Serializable {


    private static final long serialVersionUID = 1L;


    //========== properties ==========

    @Id
    @GeneratedValue(generator="JDBC")
    @ApiModelProperty("id")
    @Column( name="id")
    private Long value;

    @ApiModelProperty("上级id")
    @Column( name="parent_id")
    private Long parentId;

    @ApiModelProperty("区域名称")
    @Column( name="name")
    private String label;

    @ApiModelProperty("业务类型 1医保，2社保")
    @Column( name="buss_type")
    private String bussType;

    private List<SiteAreaTreeVORequest> children;

}
