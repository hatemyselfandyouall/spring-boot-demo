package com.wangxinenpu.springbootdemo.dataobject.dto.sitematters;

import com.wangxinenpu.springbootdemo.dataobject.vo.root.PageVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

@Data
public class FloorListDTO extends PageVO implements Serializable {
    private static final long serialVersionUID = -1L;

    @ApiModelProperty("系统码")
    @Column( name="system_code")
    private String systemCode;

    @ApiModelProperty("地区码")
    @Column( name="area_code")
    private String areaCode;

}
