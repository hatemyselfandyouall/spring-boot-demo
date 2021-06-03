package com.wangxinenpu.springbootdemo.dataobject.vo.LinkTransferTask;



import com.wangxinenpu.springbootdemo.dataobject.po.linkTask.LinkTransferTaskExecuteStatusEnum;
import com.wangxinenpu.springbootdemo.dataobject.po.linkTask.LinkTransferTaskStatusEnum;
import com.wangxinenpu.springbootdemo.dataobject.vo.root.PageVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import java.io.Serializable;

@Data
@Accessors(chain = true)
public class LinkTransferTaskListVO extends PageVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("")
    @Column( name="name")
    private String name;

    @ApiModelProperty("dataSourceName")
    private String dataSourceName;

    @ApiModelProperty("执行状态")
    @Column( name="execute_status")
    private LinkTransferTaskExecuteStatusEnum executeStatus;

    private String startSearchTime;

    private String endSearchTime;

    private LinkTransferTaskStatusEnum status;
}
