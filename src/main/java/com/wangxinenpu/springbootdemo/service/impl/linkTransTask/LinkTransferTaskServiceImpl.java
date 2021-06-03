package com.wangxinenpu.springbootdemo.service.impl.linkTransTask;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wangxinenpu.springbootdemo.dao.mapper.LinkTransferTaskHistoryMapper;
import com.wangxinenpu.springbootdemo.dao.mapper.LinkTransferTaskMapper;
import com.wangxinenpu.springbootdemo.dao.mapper.LinkTransferTaskRuleMapper;
import com.wangxinenpu.springbootdemo.dataobject.po.linkTask.*;
import com.wangxinenpu.springbootdemo.dataobject.vo.LinkTransferTask.Link.LinkDetailVO;
import com.wangxinenpu.springbootdemo.dataobject.vo.LinkTransferTask.Link.LinkShowVO;
import com.wangxinenpu.springbootdemo.dataobject.vo.LinkTransferTask.*;
import com.wangxinenpu.springbootdemo.dataobject.vo.root.ResultVo;
import com.wangxinenpu.springbootdemo.quartz.config.QuartzConfig;
import com.wangxinenpu.springbootdemo.quartz.config.TaskConstant;
import com.wangxinenpu.springbootdemo.service.facade.linkTransTask.DataLinkFacade;
import com.wangxinenpu.springbootdemo.service.facade.linkTransTask.LinkTransferTaskFacade;
import com.wangxinenpu.springbootdemo.util.datatransfer.CDCUtil;
import com.wangxinenpu.springbootdemo.util.datatransfer.DataTransResultVO;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Slf4j
@Service
public class LinkTransferTaskServiceImpl implements LinkTransferTaskFacade {

    @Autowired
    LinkTransferTaskMapper linkTransferTaskMapper;
    @Autowired
    DataLinkFacade dataLinkFacade;
    @Autowired
    LinkTransferTaskHistoryMapper linkTransferTaskHistoryMapper;
    @Autowired
    LinkTransferTaskRuleMapper linkTransferTaskRuleMapper;
    @Autowired
    Scheduler myScheduler;


    @Override
    public PageInfo<LinkTransferTaskShowVO> getLinkTransferTaskList(LinkTransferTaskListVO linkTransferTaskListVO) {
        if (linkTransferTaskListVO==null||linkTransferTaskListVO.getPageNum()==null||linkTransferTaskListVO.getPageSize()==null) {
            return null;
        }
        Example example=new Example(LinkTransferTask.class);
        Example.Criteria criteria=example.createCriteria();
        if (!StringUtils.isEmpty(linkTransferTaskListVO.getDataSourceName())){
            criteria.andCondition("from_data_link_name like ('%"+linkTransferTaskListVO.getDataSourceName()+"') or from_data_link_name like ('%"+linkTransferTaskListVO.getDataSourceName()+"')");
        }
        if (linkTransferTaskListVO.getStatus()!=null){
            criteria.andEqualTo("status",linkTransferTaskListVO.getStatus());
        }
        if (linkTransferTaskListVO.getExecuteStatus()!=null){
            criteria.andEqualTo("executeStatus",linkTransferTaskListVO.getExecuteStatus());
        }
        if (!StringUtils.isEmpty(linkTransferTaskListVO.getName())){
            criteria.andLike("name",linkTransferTaskListVO.getName());
        }
        if (!StringUtils.isEmpty(linkTransferTaskListVO.getStartSearchTime())&&!StringUtils.isEmpty(linkTransferTaskListVO.getEndSearchTime())){
            criteria.andBetween("modifyTime",linkTransferTaskListVO.getStartSearchTime(),linkTransferTaskListVO.getEndSearchTime());
        }
        PageHelper.startPage(linkTransferTaskListVO.getPageNum().intValue(),linkTransferTaskListVO.getPageSize().intValue());
        List<LinkTransferTask> linkTransferTaskList=linkTransferTaskMapper.selectByExample(example);
        List<LinkTransferTaskShowVO> linkTransferTaskShowVOS=linkTransferTaskList.stream().map(i->{
            LinkTransferTaskShowVO linkTransferTaskShowVO=new LinkTransferTaskShowVO();
            BeanUtils.copyProperties(i,linkTransferTaskShowVO);
            if (LinkTransferTaskStatusEnum.IS_USE.equals(i.getStatus())){
                linkTransferTaskShowVO.setUseStatus(true);
            }else {
                linkTransferTaskShowVO.setUseStatus(false);
            }
            return linkTransferTaskShowVO;
        }).collect(Collectors.toList());
        PageInfo<LinkTransferTaskShowVO> linkTransferTaskPageInfo=new PageInfo<>(linkTransferTaskShowVOS);
        return linkTransferTaskPageInfo;
    }

    @Override
    public LinkTransferTask getLinkTransferTaskDetail(LinkTransferTaskDetailVO linkTransferTaskDetailVO) {
        if (linkTransferTaskDetailVO==null||linkTransferTaskDetailVO.getId()==null) {
            return null;
        };
        LinkTransferTask linkTransferTask=linkTransferTaskMapper.selectByPrimaryKey(linkTransferTaskDetailVO.getId());
        return linkTransferTask;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer saveLinkTransferTask(LinkTransferTaskSaveVO linkTransferTaskSaveVO, long userId, String userName) {
        if (linkTransferTaskSaveVO==null){
            return 0;
        }
        LinkTransferTask linkTransferTask= new LinkTransferTask();
        Example example=new Example(LinkTransferTask.class);
        Example.Criteria criteria=example.createCriteria().andEqualTo("name",linkTransferTaskSaveVO.getName()).andNotEqualTo("status", LinkTransferTaskStatusEnum.IS_DELETE);
        if (linkTransferTaskSaveVO.getId()!=null){
            criteria.andNotEqualTo("id",linkTransferTaskSaveVO.getId());
        }
        Integer sameNameCount=linkTransferTaskMapper.selectCountByExample(example);
        if (sameNameCount>0){
            throw new RuntimeException("已有同名任务！");
        }
        if (StringUtils.isEmpty(linkTransferTaskSaveVO.getTimeCron()))throw new RuntimeException("未配置正则表达式");
        if (!CronExpression.isValidExpression(linkTransferTaskSaveVO.getTimeCron())) throw new RuntimeException("未配置合法的正则表达式");
        BeanUtils.copyProperties(linkTransferTaskSaveVO,linkTransferTask);
        if (linkTransferTask.getId()==null){
            Integer flag=linkTransferTaskMapper.insertSelective(linkTransferTask);
            QuartzConfig.createLinkTransferJob(linkTransferTask, myScheduler);
            return flag;
        }else {
            linkTransferTask.setModifyTime(new Date());
            example=new Example(LinkTransferTask.class);
            example.createCriteria().andEqualTo("id",linkTransferTask.getId());
            return linkTransferTaskMapper.updateByExampleSelective(linkTransferTask,example);
        }
    }

    @Override
    public Integer deleteLinkTransferTask(LinkTransferTaskDeleteVO linkTransferTaskDeleteVO) {
        if (linkTransferTaskDeleteVO==null||linkTransferTaskDeleteVO.getId()==null){
            return 0;
        }
        LinkTransferTask linkTransferTask=new LinkTransferTask();
        linkTransferTask.setModifyTime(new Date());
        linkTransferTask.setStatus(LinkTransferTaskStatusEnum.IS_DELETE);
        Example example=new Example(LinkTransferTask.class);
        example.createCriteria().andEqualTo("id",linkTransferTaskDeleteVO.getId());
        return linkTransferTaskMapper.updateByExampleSelective(linkTransferTask,example);
    }

    @Override
    public Integer setStatusLinkTransferTask(LinkTransferTaskSetStatusVO linkTransferTaskSetStatusVO) {
        if (linkTransferTaskSetStatusVO==null||linkTransferTaskSetStatusVO.getId()==null||linkTransferTaskSetStatusVO.getStatus()==null){
            return 0;
        }
        LinkTransferTask linkTransferTask=new LinkTransferTask();
        linkTransferTask.setModifyTime(new Date());
        linkTransferTask.setStatus(linkTransferTaskSetStatusVO.getStatus());
        if (LinkTransferTaskStatusEnum.IS_USE.equals(linkTransferTaskSetStatusVO.getStatus())) {
            linkTransferTask.setExecuteStatus(LinkTransferTaskExecuteStatusEnum.WAIT_START);
        }else {
            linkTransferTask.setExecuteStatus(LinkTransferTaskExecuteStatusEnum.EXECUTE_STOPED);
        }
        Example example=new Example(LinkTransferTask.class);
        example.createCriteria().andEqualTo("id",linkTransferTaskSetStatusVO.getId());
        LinkTransferTask oldLinkTask=linkTransferTaskMapper.selectByPrimaryKey(linkTransferTaskSetStatusVO.getId());
        if (LinkTransferTaskStatusEnum.IS_USE.equals(oldLinkTask.getStatus())&& LinkTransferTaskStatusEnum.NOT_USE.equals(linkTransferTaskSetStatusVO.getStatus())){
            try {
//                myScheduler.pauseTriggers(GroupMatcher.triggerGroupEquals(TaskConstant.LINK_TRANS_JOB_GROUP+linkTransferTaskSetStatusVO.getId()));
                Set<TriggerKey> set= myScheduler.getTriggerKeys(GroupMatcher.triggerGroupEquals(TaskConstant.LINK_TRANS_JOB_GROUP+linkTransferTaskSetStatusVO.getId()));
                for (TriggerKey triggerKey:set){
                    myScheduler.pauseTrigger(triggerKey);
                }
            } catch (Exception e) {
                //停不住无妨吧
                log.error("设置状态时停止任务失败",e);
            }
        }
        Integer flag=linkTransferTaskMapper.updateByExampleSelective(linkTransferTask,example);
        if (flag==1){
            if (LinkTransferTaskStatusEnum.IS_USE.equals(linkTransferTaskSetStatusVO.getStatus())) {
                Set<TriggerKey> set=new HashSet<>();
                try {
                    set = myScheduler.getTriggerKeys(GroupMatcher.triggerGroupEquals(TaskConstant.LINK_TRANS_JOB_GROUP + linkTransferTaskSetStatusVO.getId()));
                }catch (Exception e){
                    //do nothind
                }
                if (CollectionUtils.isEmpty(set)){
                    QuartzConfig.createLinkTransferJob(oldLinkTask, myScheduler);
                }else {
                    try {
                        myScheduler.resumeTrigger(set.iterator().next());
                    }catch (Exception e){
                        QuartzConfig.createLinkTransferJob(oldLinkTask, myScheduler);
                    }
                }
//                if(triggerKey!=null) {
//                    try {
//                        myScheduler.resumeTrigger(triggerKey);
//                    }catch (Exception e){
//                        log.error("唤醒triggerKey失败",e);
//
//                    }
//                }
            }
            if (LinkTransferTaskStatusEnum.IS_DELETE.equals(linkTransferTaskSetStatusVO.getStatus())|| LinkTransferTaskStatusEnum.NOT_USE.equals(linkTransferTaskSetStatusVO.getStatus())) {
                try {
                    Set<TriggerKey> set= myScheduler.getTriggerKeys(GroupMatcher.triggerGroupEquals(TaskConstant.LINK_TRANS_JOB_GROUP+linkTransferTaskSetStatusVO.getId()));
                    for (TriggerKey triggerKey:set){
                        myScheduler.pauseTrigger(triggerKey);
                    }
                } catch (Exception e) {
                    //删不掉也无妨吧
                    log.error("设置状态时删除任务失败",e);
                }
            }
        }
        return flag;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer manualStart(LinkTransferTaskManualStartVO linkTransferTaskManualStartVO, String userName) throws Exception {
        if (linkTransferTaskManualStartVO==null||linkTransferTaskManualStartVO.getId()==null){
            return 0;
        }
        LinkTransferTask linkTransferTask=linkTransferTaskMapper.selectByPrimaryKey(linkTransferTaskManualStartVO.getId());
        if (linkTransferTask==null){
            return 0;
        }
        if(LinkTransferTaskExecuteStatusEnum.IS_EXECUTING.equals(linkTransferTask.getExecuteStatus()))throw new Exception("执行中的任务不能手动执行");
        String currentTime=""+System.currentTimeMillis();
//        ResultVo resultVo=doTransfer(linkTransferTaskManualStartVO.getId(),"mannal|"+currentTime);
        Executors.newSingleThreadExecutor().submit(()->doTransfer(linkTransferTaskManualStartVO.getId(),"mannal|"+currentTime,userName));
//        if (resultVo.isSuccess()){
            return 1;
//        }else {
//            return 0;
//        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer taskStop(LinkTransferTaskManualStartVO linkTransferTaskManualStartVO) throws Exception {
        if (linkTransferTaskManualStartVO==null||linkTransferTaskManualStartVO.getId()==null){
            return 0;
        }
        LinkTransferTask linkTransferTask=linkTransferTaskMapper.selectByPrimaryKey(linkTransferTaskManualStartVO.getId());
        if (linkTransferTask==null){
            return 0;
        }
//        if (!LinkTransferTaskExecuteStatusEnum.IS_EXECUTING.equals(linkTransferTask.getExecuteStatus()))throw new RuntimeException("只有执行中的任务才能停止");算了算了
        myScheduler.pauseTriggers(GroupMatcher.triggerGroupEquals(TaskConstant.LINK_TRANS_JOB_GROUP+linkTransferTaskManualStartVO.getId()));
         linkTransferTask=new LinkTransferTask();
        linkTransferTask.setModifyTime(new Date());
        linkTransferTask.setExecuteStatus(LinkTransferTaskExecuteStatusEnum.EXECUTE_STOPED);
        Example example=new Example(LinkTransferTask.class);
        example.createCriteria().andEqualTo("id",linkTransferTaskManualStartVO.getId());
        return linkTransferTaskMapper.updateByExampleSelective(linkTransferTask,example);
    }

    @Override
    public synchronized Integer taskRetry(LinkTransferTaskManualStartVO linkTransferTaskManualStartVO, String userName) throws Exception {
        if (linkTransferTaskManualStartVO==null||linkTransferTaskManualStartVO.getId()==null){
            return 0;
        }
        LinkTransferTask linkTransferTask=linkTransferTaskMapper.selectByPrimaryKey(linkTransferTaskManualStartVO.getId());
        if (linkTransferTask==null){
            return 0;
        }
        if (!LinkTransferTaskExecuteStatusEnum.EXECUTE_STOPED.equals(linkTransferTask.getExecuteStatus())
                &&!LinkTransferTaskExecuteStatusEnum.EXECUTE_FAIL.equals(linkTransferTask.getExecuteStatus())
                &&!LinkTransferTaskExecuteStatusEnum.EXECUTE_SUCCESS.equals(linkTransferTask.getExecuteStatus()))throw new RuntimeException("只有停止、成功和失败的任务才能重启");
        String triggerName=linkTransferTask.getCurrentJobName();
        Date nextExecuteDate=getNextTriggerTime(linkTransferTask.getTimeCron(),new Date());
        if (LinkTransferTaskStatusEnum.IS_USE.equals(linkTransferTask.getExecuteStatus())&&nextExecuteDate.getTime()-new Date().getTime()<600000)throw new Exception("十分钟内就会重新发起的任务不能重试");
//        myScheduler.getTrigger().mayFireAgain()
//        String currentTime=""+System.currentTimeMillis();
//        ResultVo resultVo=doTransfer(linkTransferTaskManualStartVO.getId(),"mannalRetry|"+currentTime);
//        log.info("重试返回为"+resultVo);
//        if (resultVo.isSuccess()){
        String currentTime=""+System.currentTimeMillis();
        linkTransferTask=new LinkTransferTask();
        linkTransferTask.setModifyTime(new Date());
        linkTransferTask.setExecuteStatus(LinkTransferTaskExecuteStatusEnum.IS_EXECUTING);
        Example example=new Example(LinkTransferTask.class);
        example.createCriteria().andEqualTo("id",linkTransferTaskManualStartVO.getId());
        linkTransferTaskMapper.updateByExampleSelective(linkTransferTask,example);
        Executors.newSingleThreadExecutor().submit(()->doTransfer(linkTransferTaskManualStartVO.getId(),"mannal|"+currentTime,userName));
        return 1;
//        }else {
//            throw new Exception(resultVo.getResultDes());
//        }
    }

    @Override
//    @Transactional(rollbackFor = Exception.class)
    public ResultVo doTransfer(Long linkTaskId, String jobName, String userName) throws Exception{
        ResultVo<DataTransResultVO> resultVo=new ResultVo();
        //step1 首先，我们要确定可行性对伐
        if (linkTaskId==null) throw new Exception("任务id未传入!");
        LinkTransferTask linkTransferTask=linkTransferTaskMapper.selectByPrimaryKey(linkTaskId);
        checkLinkTransferTaskCando(linkTransferTask);
//        if (linkTransferTask.getExecuteStatus().equals(LinkTransferTaskExecuteStatusEnum.IS_EXECUTING)){
//            //执行中的任务不能再执行
//            return resultVo;
//        }
        LinkShowVO fromLink =dataLinkFacade.getDataLinkDetail(new LinkDetailVO().setId(linkTransferTask.getFromDataLinkId()));
        if (fromLink==null) throw new Exception("源数据连接不存在");
        LinkShowVO toLink =dataLinkFacade.getDataLinkDetail(new LinkDetailVO().setId(linkTransferTask.getToDataLinkId()));
        if (toLink==null) throw new Exception("目标数据连接不存在");
        checkLinkTransferTaskLinkCando(fromLink,toLink);
        //step2.然后，我们得判断是全量还是增量,执行前还要改变执行状态
        setTaskStatusToStart(linkTransferTask,jobName,userName);
        if (LinkTransferTaskTypeEnum.FULL.equals(linkTransferTask.getTransferType())){
             resultVo=doFullTrans(linkTransferTask,fromLink,toLink);
        }else {
             resultVo=doCDCTrans(linkTransferTask,fromLink,toLink);
        }
        //step3 根据执行的结果更新状态
        if (resultVo==null)throw new Exception("未获取执行结果");
        if (resultVo.isSuccess()){
            setTaskSuccess(linkTaskId,resultVo.getResultDes(),jobName,resultVo.getResult());
        }else {
            setTaskFail(linkTaskId,resultVo.getResultDes(),jobName,resultVo.getResult());
        }
        return resultVo;
    }

    @Override
    public List<LinkTransferTask> getWaitingTaskList() {
        Example example=new Example(LinkTransferTask.class);
        example.createCriteria().andEqualTo("status", LinkTransferTaskStatusEnum.IS_USE);
        return linkTransferTaskMapper.selectByExample(example);
    }

    @Override
    public void saveExceptionMsgForTask(Long linkTaskId, Exception e, String jobName) {
        LinkTransferTask linkTransferTask=linkTransferTaskMapper.selectByPrimaryKey(linkTaskId);
        if (linkTransferTask==null)return;
//        if (!LinkTransferTaskExecuteStatusEnum.IS_EXECUTING.equals(linkTransferTask.getExecuteStatus())){
//            return;
//        }
        linkTransferTask=new LinkTransferTask();
        linkTransferTask.setExecuteStatus(LinkTransferTaskExecuteStatusEnum.EXECUTE_FAIL);
        linkTransferTask.setLastExecuteDetailMsg(e.getMessage()).setLastExecuteDetailMsg(e.getMessage());
        linkTransferTask.setModifyTime(new Date());
        Example example=new Example(LinkTransferTask.class);
        example.createCriteria().andEqualTo("id",linkTaskId);
        linkTransferTaskMapper.updateByExampleSelective(linkTransferTask,example);
        LinkTransferTaskHistory linkTransferTaskHistory=new LinkTransferTaskHistory();
        linkTransferTaskHistory.setExecuteStatus(LinkTransferTaskExecuteStatusEnum.EXECUTE_FAIL);
        linkTransferTaskHistory.setLastExecuteDetailMsg(e.getMessage()).setLastExecuteDetailMsg(e.getMessage());
        example=new Example(LinkTransferTaskHistory.class);
        example.createCriteria().andEqualTo("currentJobName",jobName);
        linkTransferTaskHistoryMapper.updateByExampleSelective(linkTransferTaskHistory,example);
    }

    @Override
    public List<JSONObject> jobList() throws Exception {
        List<JobExecutionContext> list= myScheduler.getCurrentlyExecutingJobs();
        return list.stream().map(i-> JSONObject.parseObject(JSONObject.toJSONString(i))).collect(Collectors.toList());
    }

    @Override
    public PageInfo<LinkTransferTaskHistoryShowVO> getLinkTransferTaskHistoryList(LinkTransferTaskHistoryListVO linkTransferTaskHistoryListVO) {
        if (linkTransferTaskHistoryListVO==null||linkTransferTaskHistoryListVO.getPageNum()==null||linkTransferTaskHistoryListVO.getPageSize()==null) {
            return null;
        }
        Example example=new Example(LinkTransferTaskHistory.class);
        Example.Criteria criteria=example.createCriteria();
        if (!StringUtils.isEmpty(linkTransferTaskHistoryListVO.getDataSourceName())){
            criteria.andCondition("from_data_link_name like ('%"+linkTransferTaskHistoryListVO.getDataSourceName()+"') or from_data_link_name like ('%"+linkTransferTaskHistoryListVO.getDataSourceName()+"')");
        }
        if (linkTransferTaskHistoryListVO.getExecuteStatus()!=null){
            criteria.andEqualTo("executeStatus",linkTransferTaskHistoryListVO.getExecuteStatus());
        }
        if (!StringUtils.isEmpty(linkTransferTaskHistoryListVO.getName())){
            criteria.andLike("name",linkTransferTaskHistoryListVO.getName());
        }
        if (!StringUtils.isEmpty(linkTransferTaskHistoryListVO.getStartSearchTime())&&!StringUtils.isEmpty(linkTransferTaskHistoryListVO.getEndSearchTime())){
            criteria.andBetween("createTime",linkTransferTaskHistoryListVO.getStartSearchTime(),linkTransferTaskHistoryListVO.getEndSearchTime());
        }
        PageHelper.startPage(linkTransferTaskHistoryListVO.getPageNum().intValue(),linkTransferTaskHistoryListVO.getPageSize().intValue());
        List<LinkTransferTaskHistory> linkTransferTaskList=linkTransferTaskHistoryMapper.selectByExample(example);
        PageInfo<LinkTransferTaskHistory> linkTransferTaskListPage=new PageInfo<>(linkTransferTaskList);
        List<LinkTransferTaskHistoryShowVO> linkTransferTaskShowVOS=linkTransferTaskList.stream().map(i->{
            LinkTransferTaskHistoryShowVO linkTransferTaskShowVO=new LinkTransferTaskHistoryShowVO();
            BeanUtils.copyProperties(i,linkTransferTaskShowVO);
            return linkTransferTaskShowVO;
        }).collect(Collectors.toList());
        PageInfo<LinkTransferTaskHistoryShowVO> linkTransferTaskPageInfo=new PageInfo<>(linkTransferTaskShowVOS);
        linkTransferTaskPageInfo.setTotal(linkTransferTaskListPage.getTotal());
        return linkTransferTaskPageInfo;
    }

    @Override
    public LinkTransferTaskHistoryStatistics getLinkTransferTaskHistoryStatistics() {
        Integer totalCount=linkTransferTaskHistoryMapper.selectCount(new LinkTransferTaskHistory());
        Integer successCount=linkTransferTaskHistoryMapper.selectCount(new LinkTransferTaskHistory().setExecuteStatus(LinkTransferTaskExecuteStatusEnum.EXECUTE_SUCCESS));
        Integer failCount=linkTransferTaskHistoryMapper.selectCount(new LinkTransferTaskHistory().setExecuteStatus(LinkTransferTaskExecuteStatusEnum.EXECUTE_FAIL));
        return new LinkTransferTaskHistoryStatistics().setAvaTaskTime("20s").setTotalTaskTime("20000s").setTotalTaskCount(totalCount).setSuccessTaskCount(successCount).setFailTaskCount(failCount);
    }

    @Override
    public void init(List<LinkTransferTaskSaveVO> linkTransferTaskSaveVOS) {
        for (LinkTransferTaskSaveVO linkTransferTaskSaveVO:linkTransferTaskSaveVOS){
            LinkTransferTask linkTransferTask=new LinkTransferTask();
            BeanUtils.copyProperties(linkTransferTaskSaveVO,linkTransferTask);
            linkTransferTaskMapper.insertSelective(linkTransferTask);
            if (linkTransferTaskSaveVO.getLinkTransferTaskRule()!=null){
                linkTransferTaskRuleMapper.insertSelective(linkTransferTaskSaveVO.getLinkTransferTaskRule());
            }
        }
    }

    @Override
    public List<LinkTransferTaskCDDVO> startCdc() {
        List<LinkTransferTask> linkTransferTasks=linkTransferTaskMapper.selectAll();
        linkTransferTasks=linkTransferTasks.stream().filter(i->i.getStatus().equals(LinkTransferTaskStatusEnum.IS_USE)).collect(Collectors.toList());
        List<LinkTransferTaskCDDVO> linkTransferTaskCDDVOS=linkTransferTasks.stream().map(i->{
            LinkTransferTaskCDDVO linkTransferTaskCDDVO=new LinkTransferTaskCDDVO();
            BeanUtils.copyProperties(i,linkTransferTaskCDDVO);
            linkTransferTaskCDDVO.setLinkTransferTaskRule(linkTransferTaskRuleMapper.select(new LinkTransferTaskRule()
            .setSegName(i.getSegName())
            .setTargetTablesString(i.getTargetTablesString())
                    .setStatus(LinkTransferTaskRuleStatusEnum.IS_USE).setFromDataLinkId(i.getToDataLinkId())));
            return linkTransferTaskCDDVO;
        }).collect(Collectors.toList());
        return linkTransferTaskCDDVOS;
    }

    private void setTaskStatusToStart(LinkTransferTask oldTask, String jobName, String userName) {
        LinkTransferTask linkTransferTask=new LinkTransferTask();
        linkTransferTask.setExecuteStatus(LinkTransferTaskExecuteStatusEnum.IS_EXECUTING);
        linkTransferTask.setModifyTime(new Date());
        linkTransferTask.setCurrentJobName(jobName);
        Example example=new Example(LinkTransferTask.class);
        example.createCriteria().andEqualTo("id",oldTask.getId());
        linkTransferTaskMapper.updateByExampleSelective(linkTransferTask,example);
        linkTransferTaskHistoryMapper.insertSelective(new LinkTransferTaskHistory().setExecutorName(userName)
                .setExecuteStatus(LinkTransferTaskExecuteStatusEnum.IS_EXECUTING).setCreateTime(new Date()).setTaskId(oldTask.getId()).setCurrentJobName(jobName)
        .setFromDataLinkId(oldTask.getFromDataLinkId()).setToDataLinkId(oldTask.getToDataLinkId()).setName(oldTask.getName())
                .setFromDataLinkName(oldTask.getFromDataLinkName()).setToDataLinkName(oldTask.getToDataLinkName()).setTargetTablesString(oldTask.getTargetTablesString()));
    }



    private void setTaskSuccess(Long linkTaskId, String des, String jobname, DataTransResultVO result) {
        LinkTransferTask linkTransferTask=new LinkTransferTask();
        linkTransferTask.setExecuteStatus(LinkTransferTaskExecuteStatusEnum.EXECUTE_SUCCESS);
        linkTransferTask.setLastExecuteDetailMsg(des);
        linkTransferTask.setModifyTime(new Date());
        if (result!=null) {
            if (!StringUtils.isEmpty(result.getLastScn())) {
                linkTransferTask.setCdcStartScn(result.getLastScn());
            }
            if (!StringUtils.isEmpty(result.getLastTime())) {
                linkTransferTask.setCdcStartTime(result.getLastTime());
            }
        }
        Example example=new Example(LinkTransferTask.class);
        example.createCriteria().andEqualTo("id",linkTaskId);
        linkTransferTaskMapper.updateByExampleSelective(linkTransferTask,example);
                LinkTransferTaskHistory linkTransferTaskHistory=new LinkTransferTaskHistory();
        linkTransferTaskHistory.setExecuteStatus(LinkTransferTaskExecuteStatusEnum.EXECUTE_SUCCESS);
        linkTransferTaskHistory.setTaskEndTime(new Date());
        linkTransferTaskHistory.setSuccessTransRowCount(result.getSuccessTransRowCount()).setFailTransRowCount(result.getFailTransRowCount())
                .setExecuteTime(result.getExecuteTime()).setExecuteTimeSecond(result.getExecuteTimeSecond());
        example=new Example(LinkTransferTaskHistory.class);
        example.createCriteria().andEqualTo("currentJobName",jobname);
        linkTransferTaskHistoryMapper.updateByExampleSelective(linkTransferTaskHistory,example);
    }

    private void setTaskFail(Long linkTaskId, String des, String jobname, DataTransResultVO result) {
        LinkTransferTask linkTransferTask=new LinkTransferTask();
        linkTransferTask.setExecuteStatus(LinkTransferTaskExecuteStatusEnum.EXECUTE_FAIL);
        linkTransferTask.setLastExecuteDetailMsg(des);
        linkTransferTask.setModifyTime(new Date());
        Example example=new Example(LinkTransferTask.class);
        example.createCriteria().andEqualTo("id",linkTaskId);
        linkTransferTaskMapper.updateByExampleSelective(linkTransferTask,example);
        LinkTransferTaskHistory linkTransferTaskHistory=new LinkTransferTaskHistory();
        linkTransferTaskHistory.setExecuteStatus(LinkTransferTaskExecuteStatusEnum.EXECUTE_FAIL);
        linkTransferTaskHistory.setLastExecuteDetailMsg(des);
        linkTransferTaskHistory.setTaskEndTime(new Date());
        if (result!=null){
            linkTransferTaskHistory.setSuccessTransRowCount(result.getSuccessTransRowCount()).setFailTransRowCount(result.getFailTransRowCount())
                    .setExecuteTime(result.getExecuteTime()).setExecuteTimeSecond(result.getExecuteTimeSecond());
        }
        example=new Example(LinkTransferTaskHistory.class);
        example.createCriteria().andEqualTo("currentJobName",jobname);
        linkTransferTaskHistoryMapper.updateByExampleSelective(linkTransferTaskHistory,example);
    }

    private ResultVo<DataTransResultVO> doCDCTrans(LinkTransferTask linkTransferTask, LinkShowVO fromLink, LinkShowVO toLink)throws Exception {
        if (!"ORACLE".equals(fromLink.getLink().getLinkTypeCode().toUpperCase())&&!"MYSQL".equals(toLink.getLink().getLinkTypeCode().toUpperCase()))throw new Exception("目前只支持mysql与oracle的迁移");
        if (!fromLink.getLink().getLinkTypeCode().equals(toLink.getLink().getLinkTypeCode()))throw new Exception("目前只支持相同类型数据源的迁移");
        String tableString=linkTransferTask.getTargetTablesString();
        if (StringUtils.isEmpty(tableString)) throw new Exception("未设定迁移表");
        ResultVo<DataTransResultVO> resultVo= CDCUtil.doTrans(fromLink.getLinkDetail().getUrl(),fromLink.getLinkDetail().getPort(),fromLink.getLinkDetail().getOracleSid(),fromLink.getLinkDetail().getOracleServerName(),fromLink.getLinkDetail().getUsername(),fromLink.getLinkDetail().getPassword(),
                toLink.getLinkDetail().getUrl(),toLink.getLinkDetail().getUsername(),toLink.getLinkDetail().getPassword(),toLink.getLinkDetail().getPort(),toLink.getLinkDetail().getOracleServerName(),toLink.getLinkDetail().getOracleSid(),fromLink.getLinkDetail().getSchemaName()
                ,"'INSERT','UPDATE','DELETE'",linkTransferTask.getTargetTablesString(),linkTransferTask.getCdcStartTime(),linkTransferTask.getCdcEndTime(),
                linkTransferTask.getCdcStartScn(),fromLink.getLink().getLinkTypeCode(),fromLink,toLink,linkTransferTask.getIsOfflineMode());
        return resultVo;
    }

    private ResultVo<DataTransResultVO> doFullTrans(LinkTransferTask linkTransferTask, LinkShowVO fromLink, LinkShowVO toLink)throws Exception {
        //todo 暂时不做全量
//        linkTransferTask.setCdcStartTime(null);
//        linkTransferTask.setCdcStartScn(null);
//        linkTransferTask.setCdcEndTime(null);
        return doCDCTrans(linkTransferTask, fromLink, toLink);
    }

    private void checkLinkTransferTaskLinkCando(LinkShowVO fromLink, LinkShowVO toLink)throws Exception {
        if (fromLink.getTableInfos()==null)throw new Exception("未取得源数据库中的表数据");
        if (toLink.getTableInfos()==null)throw new Exception("未取得目标数据库中的表数据");
    }

    private void checkLinkTransferTaskCando(LinkTransferTask linkTransferTask)throws Exception{
        if (linkTransferTask==null) throw new Exception("任务不存在");
        if (linkTransferTask.getFromDataLinkId()==null)throw new Exception("不存在的源数据库id");
        if (linkTransferTask.getToDataLinkId()==null)throw new Exception("不存在的目标数据库id");
        if (linkTransferTask.getTransferType()==null) throw new Exception("未配置迁移类型");
    }

    public static Date getNextTriggerTime(String cron, Date start) {
        CronTrigger trigger = getCronTrigger(cron);
        if (start == null) {
            start = trigger.getStartTime();
        }
        return trigger.getFireTimeAfter(start);
    }
    public static CronTrigger getCronTrigger(String cron) {
        if (!CronExpression.isValidExpression(cron)) {
            throw new RuntimeException("cron :" + cron + "表达式解析错误");
        }
        return TriggerBuilder.newTrigger().withIdentity("Caclulate Date").withSchedule(CronScheduleBuilder.cronSchedule(cron)).build();

    }

    public static void main(String[] args) {
        System.out.println(getNextTriggerTime("59 * * * * ? *",new Date()));
    }
}
