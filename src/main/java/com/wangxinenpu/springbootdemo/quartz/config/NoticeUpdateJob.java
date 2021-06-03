package com.wangxinenpu.springbootdemo.quartz.config;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

@Slf4j
public class NoticeUpdateJob implements Job {


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
//        Long modelId = (Long) jobExecutionContext.getJobDetail().getJobDataMap().get(TaskConstant.SERVICE_ID);
//        log.info("开始调用定时任务" + modelId);
//        try {
//            tableExtractFacade.dataExtarct(modelId);
//        } catch (Exception e) {
//            log.error("进行全量抽取报错", e);
//        }
    }
}
