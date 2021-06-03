package com.wangxinenpu.springbootdemo.dataobject.vo.LinkTransferTask;

import com.wangxinenpu.springbootdemo.dataobject.po.linkTask.LinkTransferTaskHistory;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class LinkTransferTaskHistoryShowVO extends LinkTransferTaskHistory implements Serializable {


}
