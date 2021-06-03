package com.wangxinenpu.springbootdemo.dataobject.vo.LinkTransferTask;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class LinkTransferTaskHistoryStatistics implements Serializable {

    private Integer totalTaskCount;

    private Integer successTaskCount;

    private Integer failTaskCount;

    private String totalTaskTime;

    private String avaTaskTime;
}
