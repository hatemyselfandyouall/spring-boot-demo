package com.wangxinenpu.springbootdemo.quartz.config.linkTrans;

import com.wangxinenpu.springbootdemo.quartz.config.TaskConstant;
import com.wangxinenpu.springbootdemo.service.facade.linkTransTask.LinkTransferTaskFacade;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@DisallowConcurrentExecution
public class LinkTransJob implements Job{

    @Autowired
    LinkTransferTaskFacade linkTransferTaskFacade;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Long linkTaskId = (Long) jobExecutionContext.getJobDetail().getJobDataMap().get(TaskConstant.LINK_TRANS_JOB_ID);
        String triggerName=jobExecutionContext.getTrigger().getKey().getName();
        log.info("开始执行数据库迁移" + linkTaskId);
        try {
            linkTransferTaskFacade.doTransfer(linkTaskId,triggerName,"系统定时");
        } catch (Exception e) {
            log.error("进行全量抽取报错", e);
            linkTransferTaskFacade.saveExceptionMsgForTask(linkTaskId,e,triggerName);
        }
    }
}
