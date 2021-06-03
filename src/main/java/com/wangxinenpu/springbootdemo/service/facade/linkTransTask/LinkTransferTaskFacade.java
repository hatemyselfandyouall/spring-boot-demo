 
package com.wangxinenpu.springbootdemo.service.facade.linkTransTask;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.wangxinenpu.springbootdemo.dataobject.po.linkTask.LinkTransferTask;
import com.wangxinenpu.springbootdemo.dataobject.vo.LinkTransferTask.*;
import com.wangxinenpu.springbootdemo.dataobject.vo.root.ResultVo;

import java.util.List;


public interface LinkTransferTaskFacade{

	PageInfo<LinkTransferTaskShowVO> getLinkTransferTaskList(LinkTransferTaskListVO listVO);

    LinkTransferTask getLinkTransferTaskDetail(LinkTransferTaskDetailVO detailVO);

    Integer saveLinkTransferTask(LinkTransferTaskSaveVO saveVO, long userId, String userName);

    Integer deleteLinkTransferTask(LinkTransferTaskDeleteVO deleteVO);


    Integer setStatusLinkTransferTask(LinkTransferTaskSetStatusVO linkTransferTaskSetStatusVO);

    Integer manualStart(LinkTransferTaskManualStartVO linkTransferTaskManualStartVO, String userName) throws Exception;

    Integer taskStop(LinkTransferTaskManualStartVO linkTransferTaskManualStartVO) throws Exception;

    Integer taskRetry(LinkTransferTaskManualStartVO linkTransferTaskManualStartVO, String userName) throws Exception;

    ResultVo doTransfer(Long linkTaskId, String jobName, String userName) throws Exception;

    List<LinkTransferTask> getWaitingTaskList();

    void saveExceptionMsgForTask(Long linkTaskId, Exception e, String jobName);

    List<JSONObject> jobList() throws Exception;

    PageInfo<LinkTransferTaskHistoryShowVO> getLinkTransferTaskHistoryList(LinkTransferTaskHistoryListVO linkTransferTaskHistoryListVO);

    LinkTransferTaskHistoryStatistics getLinkTransferTaskHistoryStatistics();

    void init(List<LinkTransferTaskSaveVO> linkTransferTaskSaveVOS);

    List<LinkTransferTaskCDDVO> startCdc();
}

 
