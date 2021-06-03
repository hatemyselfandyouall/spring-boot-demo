
package com.wangxinenpu.springbootdemo.service.facade.linkTransTask;

import com.wangxinenpu.springbootdemo.dataobject.vo.LinkTransferTask.Link.LinkDetailVO;
import com.wangxinenpu.springbootdemo.dataobject.vo.LinkTransferTask.Link.LinkShowVO;


public interface DataLinkFacade {


    LinkShowVO getDataLinkDetail(LinkDetailVO detailVO) throws Exception;

}

 
