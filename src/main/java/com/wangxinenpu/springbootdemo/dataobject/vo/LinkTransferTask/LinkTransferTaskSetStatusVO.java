package com.wangxinenpu.springbootdemo.dataobject.vo.LinkTransferTask;

import com.wangxinenpu.springbootdemo.dataobject.po.linkTask.LinkTransferTaskStatusEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

@Data
public class LinkTransferTaskSetStatusVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("接口id")
    @Column( name="id")
    private Long id;

    private LinkTransferTaskStatusEnum status;


}
