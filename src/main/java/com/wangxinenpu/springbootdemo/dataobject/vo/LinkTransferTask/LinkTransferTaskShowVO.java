package com.wangxinenpu.springbootdemo.dataobject.vo.LinkTransferTask;

import com.wangxinenpu.springbootdemo.dataobject.po.linkTask.LinkTransferTask;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class LinkTransferTaskShowVO extends LinkTransferTask implements Serializable {

    @ApiModelProperty("true在使用")
    private Boolean useStatus;
}
