package com.wangxinenpu.springbootdemo.dataobject.dto.SiteArea;


import com.wangxinenpu.springbootdemo.dataobject.vo.root.PageVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

@Data
public class SiteAreaListVO extends PageVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("业务类型 1医保，2社保")
    @Column( name="buss_type")
    private String bussType;

}
