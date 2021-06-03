package com.wangxinenpu.springbootdemo.dataobject.vo.LinkTransferTask.Link;

import com.wangxinenpu.springbootdemo.dataobject.po.linkTask.Link;
import com.wangxinenpu.springbootdemo.dataobject.po.linkTask.LinkDetail;
import com.wangxinenpu.springbootdemo.dataobject.vo.LinkTransferTask.table.TableInfo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class LinkShowVO implements Serializable {

    private Link link;

    private LinkDetail linkDetail;

    List<TableInfo> tableInfos;



}
