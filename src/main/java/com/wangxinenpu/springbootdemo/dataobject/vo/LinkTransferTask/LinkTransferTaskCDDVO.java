package com.wangxinenpu.springbootdemo.dataobject.vo.LinkTransferTask;

import com.wangxinenpu.springbootdemo.dataobject.po.linkTask.LinkTransferTask;
import com.wangxinenpu.springbootdemo.dataobject.po.linkTask.LinkTransferTaskRule;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class LinkTransferTaskCDDVO extends LinkTransferTask implements Serializable  {

    private List<LinkTransferTaskRule> linkTransferTaskRule;
}
