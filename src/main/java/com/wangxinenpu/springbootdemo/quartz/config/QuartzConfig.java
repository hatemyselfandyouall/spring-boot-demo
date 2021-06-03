package com.wangxinenpu.springbootdemo.quartz.config;


import com.wangxinenpu.springbootdemo.dataobject.po.linkTask.LinkTransferTask;
import com.wangxinenpu.springbootdemo.quartz.config.linkTrans.LinkTransJob;
import com.wangxinenpu.springbootdemo.service.facade.linkTransTask.LinkTransferTaskFacade;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * 启动项目时定时调用任务
 */
//@Service
@Slf4j
public class QuartzConfig {


//    @Autowired
//    DataModelFacade dataModelFacade;
    @Autowired
    Scheduler myScheduler;
    @Autowired
    LinkTransferTaskFacade linkTransferTaskFacade;


//    @PostConstruct
    private void startQuartzConfig() {
        log.info("开始启动定时任务");
        try {
            myScheduler.start();
        } catch (SchedulerException e) {
            log.error("启动定时调度器异常", e);
            return;
        }
        //获取未发布消息列表
//        List<ModelExtract> modelExtracts = dataModelFacade.getWaitingExtracts();
//        for (ModelExtract modelExtract : modelExtracts) {
//            //如果项目启动时消息已过时
//            createJob(modelExtract, myScheduler);
//        }
        //获取数据抽取任务列表
        List<LinkTransferTask> linkTransferTasks=linkTransferTaskFacade.getWaitingTaskList();
        for (LinkTransferTask linkTransferTask:linkTransferTasks){
            createLinkTransferJob(linkTransferTask, myScheduler);
        }
        log.info("虽然我可爱又迷人，但是我会带来死亡");
    }

//    public static void createJob(ModelExtract modelExtract, Scheduler myScheduler) {
//        try {
//            //创建一个定时任务,来进行发布消息工作，具体的逻辑在 NoticeUpdateJob里
//            JobDetail job = newJob(NoticeUpdateJob.class)
//                    .usingJobData(TaskConstant.SERVICE_ID, modelExtract.getModelId())
//                    .withIdentity(modelExtract.getId() + ":task", TaskConstant.GROUP_NAME)
//                    .build();
//            // 为上面的定时器创建一个触发器，在指定的发表时间执行一次，用以发表该消息
//            Trigger trigger = null;
//            if (modelExtract.getExtractStartDate() != null && modelExtract.getExtractEndDate() != null) {
//                trigger = newTrigger()
//                        .withIdentity(modelExtract.getId() + ":trigger", TaskConstant.GROUP_NAME)
//                        .startAt(futureDate(DataUtils.getTaskExcuteSecond(modelExtract.getExtractStartDate()), DateBuilder.IntervalUnit.SECOND))
//                        .endAt(futureDate(DataUtils.getTaskExcuteSecond(modelExtract.getExtractEndDate()), DateBuilder.IntervalUnit.SECOND))
//                        .withSchedule(getCronByModelExtract(modelExtract))
//                        .forJob(job.getKey())
//                        .build();
//            } else {
//                trigger = newTrigger()
//                        .withIdentity(modelExtract.getId() + ":trigger", TaskConstant.GROUP_NAME)
//                        .withSchedule(getCronByModelExtract(modelExtract))
//                        .forJob(job.getKey())
//                        .build();
//            }
//            //将以上的任务及触发器置于定时调度器的管理之下
//            myScheduler.scheduleJob(job, trigger);
//        } catch (Exception e) {
//            log.error("定时任务启动时出错！", e);
//        }
//    }

    public static TriggerKey createLinkTransferJob(LinkTransferTask linkTransferTask, Scheduler myScheduler) {
        try {
            //创建一个定时任务,来进行发布消息工作，具体的逻辑在 NoticeUpdateJob里
            log.info(linkTransferTask+"");
            String time=+System.currentTimeMillis()+"";
            JobDetail job = newJob(LinkTransJob.class)
                    .usingJobData(TaskConstant.LINK_TRANS_JOB_ID, linkTransferTask.getId())
                    .withIdentity(linkTransferTask.getId() + ":task"+"test"+time, TaskConstant.LINK_TRANS_JOB_GROUP+linkTransferTask.getId())
                    .build();
            // 为上面的定时器创建一个触发器，在指定的发表时间执行一次，用以发表该消息
            Trigger trigger = null;
            TriggerKey triggerKey=new TriggerKey(linkTransferTask.getId() + ":trigger"+time, TaskConstant.LINK_TRANS_JOB_GROUP+linkTransferTask.getId());
                trigger = newTrigger()
                        .withIdentity(triggerKey)
                        .withSchedule(CronScheduleBuilder.cronSchedule(linkTransferTask.getTimeCron()).withMisfireHandlingInstructionDoNothing())
                        .forJob(job.getKey())
                        .build();
            //将以上的任务及触发器置于定时调度器的管理之下
            myScheduler.scheduleJob(job, trigger);
            return triggerKey;
        } catch (Exception e) {
            log.error("定时任务启动时出错！", e);
            return null;
        }
    }

//    private static ScheduleBuilder getCronByModelExtract(ModelExtract modelExtract) throws Exception {
//        ScheduleBuilder scheduleBuilder = null;
//        if (modelExtract == null) {
//            throw new Exception("无法创建定时器");
//        }
//        if (DataConstant.IS.equals(modelExtract.getIsTemporaryExtract())) {
//            scheduleBuilder = SimpleScheduleBuilder.repeatSecondlyForTotalCount(1);
//        } else {
//            switch (modelExtract.getExtractTimeIntervalTypeCode()) {
//                case "MINUTE":
//                    scheduleBuilder = SimpleScheduleBuilder.repeatMinutelyForever(modelExtract.getExtractTimeInterval());
//                    break;
//                case "HOUR":
//                    scheduleBuilder = SimpleScheduleBuilder.repeatHourlyForever(modelExtract.getExtractTimeInterval());
//                    break;
//                case "DAY":
//                    scheduleBuilder = SimpleScheduleBuilder.repeatHourlyForever(modelExtract.getExtractTimeInterval() * 24);
//                    break;
//                case "WEEK":
//                    scheduleBuilder = SimpleScheduleBuilder.repeatHourlyForever(modelExtract.getExtractTimeInterval() * 24 * 7);
//                    break;
//                case "MONTH":
//                    scheduleBuilder = SimpleScheduleBuilder.repeatHourlyForever(modelExtract.getExtractTimeInterval() * 24 * 7 * 30);
//                    break;
//            }
//        }
//        return scheduleBuilder;
//    }
}
