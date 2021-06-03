package com.wangxinenpu.springbootdemo.dataobject.vo.LinkTransferTask;


import com.wangxinenpu.springbootdemo.dataobject.vo.LinkTransferTask.Link.LinkSaveVO;
import com.wangxinenpu.springbootdemo.dataobject.vo.LinkTransferTask.LinkDetail.LinkDetailSaveVO;
import lombok.Data;

import java.io.Serializable;

@Data
public class LinkSaveTotalVO implements Serializable {

    private LinkSaveVO linkSaveVO;

    private LinkDetailSaveVO linkDetailSaveVO;



}
