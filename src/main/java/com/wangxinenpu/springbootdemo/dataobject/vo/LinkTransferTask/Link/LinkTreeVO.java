package com.wangxinenpu.springbootdemo.dataobject.vo.LinkTransferTask.Link;

import com.wangxinenpu.springbootdemo.dataobject.po.linkTask.Link;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class LinkTreeVO extends Link implements Serializable {


    @ApiModelProperty("type为1表示文件夹。为2表示接口")
    private Integer type;

}
