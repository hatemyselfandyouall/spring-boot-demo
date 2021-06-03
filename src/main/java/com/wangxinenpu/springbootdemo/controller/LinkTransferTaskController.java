package com.wangxinenpu.springbootdemo.controller;

import com.github.pagehelper.PageInfo;
import com.wangxinenpu.springbootdemo.dataobject.po.linkTask.LinkTransferTask;
import com.wangxinenpu.springbootdemo.dataobject.vo.DataListResultDto;
import com.wangxinenpu.springbootdemo.dataobject.vo.LinkTransferTask.*;
import com.wangxinenpu.springbootdemo.dataobject.vo.root.ResultVo;
import com.wangxinenpu.springbootdemo.service.facade.linkTransTask.LinkTransferTaskFacade;

import com.wangxinenpu.springbootdemo.util.dataSource.sqlParse.CDCCache.SQLSaver;
import com.wangxinenpu.springbootdemo.util.dataSource.sqlParse.CDCCache.TableStatusCache;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Date;
import java.util.List;
import java.util.Properties;


@RestController
@RequestMapping("linkTransferTask")
@Api(tags ="数据源抽取管理")
@Slf4j
public class LinkTransferTaskController  {

    @Autowired
    LinkTransferTaskFacade linkTransferTaskFacade;

    @Autowired
    DefaultMQProducer defaultMQProducer;
    static {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private Connection connection=null;

    private static  Boolean isWorking=false;
    @ApiOperation(value = "数据源抽取列表")
    @RequestMapping(value = "/list",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo<LinkTransferTaskShowVO> getLinkTransferTaskList(@RequestBody LinkTransferTaskListVO linkTransferTaskListVO){
        ResultVo resultVo=new ResultVo();
        try {
            PageInfo<LinkTransferTaskShowVO> linkTransferTaskList=linkTransferTaskFacade.getLinkTransferTaskList(linkTransferTaskListVO);
            if(linkTransferTaskList!=null){
                DataListResultDto<LinkTransferTaskShowVO> dataListResultDto=new DataListResultDto<>(linkTransferTaskList.getList(),(int)linkTransferTaskList.getTotal());
                resultVo.setResult(dataListResultDto);
                resultVo.setSuccess(true);
            }else {
                resultVo.setResultDes("分页数据缺失");
            }
        }catch (Exception e){
            resultVo.setResultDes("获取数据源抽取列表异常");
            log.error("获取数据源抽取列表异常",e);
        }
        return resultVo;
    }

    @ApiOperation(value = "数据源抽取详情")
    @RequestMapping(value = "/detail",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo<LinkTransferTask> getLinkTransferTaskDetail(@RequestBody LinkTransferTaskDetailVO linkTransferTaskDetailVO){
        ResultVo resultVo=new ResultVo();
        try {
        LinkTransferTask linkTransferTask=linkTransferTaskFacade.getLinkTransferTaskDetail(linkTransferTaskDetailVO);
        if(linkTransferTask!=null){
            resultVo.setResult(linkTransferTask);
            resultVo.setSuccess(true);
        }else {
            resultVo.setResultDes("获取详情失败");
        }
        } catch (Exception e){
        resultVo.setResultDes("获取数据源抽取详情异常");
        log.error("获取数据源抽取详情异常",e);
    }
        return resultVo;
    }

    @ApiOperation(value = "数据源抽取保存")
    @RequestMapping(value = "/save",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo<LinkTransferTask> saveLinkTransferTask(@RequestBody LinkTransferTaskSaveVO linkTransferTaskSaveVO){
        ResultVo resultVo=new ResultVo();
        try {
            Long userId=2l;
            String userName="Test";
            Integer flag = linkTransferTaskFacade.saveLinkTransferTask(linkTransferTaskSaveVO,userId,userName);
            if (1 == flag) {
                resultVo.setResultDes("数据源抽取保存成功");
                resultVo.setSuccess(true);
            } else {
                resultVo.setResultDes("数据源抽取保存失败");
            }
        }catch (Exception e){
                resultVo.setResultDes("接口保存异常"+e.getMessage());
                log.error("数据源抽取保存异常",e);
            }
        return resultVo;
    }

    @ApiOperation(value = "数据源抽取删除")
    @RequestMapping(value = "/delete",method = RequestMethod.DELETE,produces = {"application/json;charset=UTF-8"})
    public ResultVo<LinkTransferTask> deleteLinkTransferTask(@RequestBody LinkTransferTaskDeleteVO linkTransferTaskDeleteVO){
        ResultVo resultVo=new ResultVo();
        try {
            Integer flag = linkTransferTaskFacade.deleteLinkTransferTask(linkTransferTaskDeleteVO);
            if (1 == flag) {
                resultVo.setResultDes("数据源抽取删除成功");
                resultVo.setSuccess(true);
            } else {
                resultVo.setResultDes("数据源抽取删除失败");
            }
        }catch (Exception e){
            resultVo.setResultDes("数据源抽取删除异常");
            log.error("数据源抽取删除异常",e);
        }
        return resultVo;
    }


    @ApiOperation(value = "设置抽取任务状态")
    @RequestMapping(value = "/setStatus",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public synchronized ResultVo<LinkTransferTask>setStatusLinkTransferTask(@RequestBody LinkTransferTaskSetStatusVO linkTransferTaskSetStatusVO){
        ResultVo resultVo=new ResultVo();
        try {
            Integer flag = linkTransferTaskFacade.setStatusLinkTransferTask(linkTransferTaskSetStatusVO);
            if (1 == flag) {
                resultVo.setResultDes("设置抽取任务状态成功");
                resultVo.setSuccess(true);
            } else {
                resultVo.setResultDes("设置抽取任务状态删除失败");
            }
        }catch (Exception e){
            resultVo.setResultDes("设置抽取任务状态删除异常,异常为"+e.getMessage());
            log.error("设置抽取任务状态删除异常",e);
        }
        return resultVo;
    }

    @ApiOperation(value = "手动启动任务")
    @RequestMapping(value = "/manualStart",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo<LinkTransferTask>manualStart(@RequestBody LinkTransferTaskManualStartVO linkTransferTaskManualStartVO){
        ResultVo resultVo=new ResultVo();
        try {
            String userName="2";
            Integer flag = linkTransferTaskFacade.manualStart(linkTransferTaskManualStartVO,userName);
            if (1 == flag) {
                resultVo.setResultDes("手动启动任务成功,任务开始在后台执行");
                resultVo.setSuccess(true);
            } else {
                resultVo.setResultDes("手动启动任务失败");
            }
        }catch (Exception e){
            resultVo.setResultDes("手动启动任务异常,异常为"+e.getMessage());
            log.error("手动启动任务异常",e);
        }
        return resultVo;
    }

    @ApiOperation(value = "任务停用")
    @RequestMapping(value = "/taskStop",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo<LinkTransferTask>taskStop(@RequestBody LinkTransferTaskManualStartVO linkTransferTaskManualStartVO){
        ResultVo resultVo=new ResultVo();
        try {
//            Integer flag = linkTransferTaskFacade.taskStop(linkTransferTaskManualStartVO);
//            if (1 == flag) {
                resultVo.setResultDes("暂时不支持停止任务");
                resultVo.setSuccess(true);
//            } else {
//                resultVo.setResultDes("停止任务失败");
//            }
        }catch (Exception e){
            resultVo.setResultDes("停止任务异常,异常为"+e.getMessage());
            log.error("停止任务异常",e);
        }
        return resultVo;
    }

    @ApiOperation(value = "任务重试")
    @RequestMapping(value = "/taskRetry",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo<LinkTransferTask>taskRetry(@RequestBody LinkTransferTaskManualStartVO linkTransferTaskManualStartVO){
        ResultVo resultVo=new ResultVo();
        try {
            String userName="12";
            Integer flag = linkTransferTaskFacade.taskRetry(linkTransferTaskManualStartVO,userName);
            if (1 == flag) {
                resultVo.setResultDes("重试任务成功");
                resultVo.setSuccess(true);
            } else {
                resultVo.setResultDes("重试任务失败");
            }
        }catch (Exception e){
            resultVo.setResultDes("重试任务异常,原因为"+e);
            log.error("重试任务异常",e);
        }
        return resultVo;
    }
//    @ApiOperation(value = "initTasks")
//    @RequestMapping(value = "/initTasks",method = RequestMethod.GET,produces = {"application/json;charset=UTF-8"})
//    public ResultVo<LinkTransferTask>initTasks(){
//        ResultVo resultVo=new ResultVo();
//        try {
//            File file=new File("");
//            String excelPath="F:\\数据回流项目部署准备\\富阳数据回流清单反馈.xlsx";
//            ResultVo<List<TableInfo>> result= ExcelUtil.testConnect(excelPath);
//            TableInfo tableInfo=result.getResult().get(0);
//            List<LinkTransferTaskSaveVO> linkTransferTaskSaveVOS=new ArrayList<>();
//            for (JSONObject jsonObject:tableInfo.getTableDatas()){
//                String tableName=jsonObject.getString("表英文名");
//                String userName=jsonObject.getString("用户名");
//                String querySQL=jsonObject.getString("富阳过滤sql");
//                String fullTableName=userName+"."+tableName;
//                LinkTransferTaskSaveVO linkTransferTaskSaveVO=new LinkTransferTaskSaveVO();
//                linkTransferTaskSaveVO.setTargetTablesString(tableName);
//                linkTransferTaskSaveVO.setSegName(userName);
//                linkTransferTaskSaveVO.setFromDataLinkId(1l);
//                if ("aab301='330183'".equals(querySQL)){
//                    LinkTransferTaskRule linkTransferTaskRule=new LinkTransferTaskRule();
//                    linkTransferTaskRule.setColumnName("aab301").setColumnValue("330183")
//                            .setColumnRuleType(ColumnRuleTypeEnum.EQUALS)
//                            .setStatus(LinkTransferTaskRuleStatusEnum.IS_USE).setSegName(userName)
//                            .setTargetTablesString(tableName)
//                    .setFromDataLinkId(1l);
//                    linkTransferTaskSaveVO.setLinkTransferTaskRule(linkTransferTaskRule);
//                }
//                linkTransferTaskSaveVOS.add(linkTransferTaskSaveVO);
//            }
//            linkTransferTaskFacade.init(linkTransferTaskSaveVOS);
//        }catch (Exception e){
//            resultVo.setResultDes("重试任务异常,原因为"+e);
//            log.error("重试任务异常",e);
//        }
//        return resultVo;
//    }

    @ApiOperation(value = "startCdc")
    @RequestMapping(value = "/startCdc",method = RequestMethod.GET,produces = {"application/json;charset=UTF-8"})
    public ResultVo<LinkTransferTask>startCdc(@RequestParam("totalStartTime") Long totalStartTime){
        ResultVo resultVo=new ResultVo();
        try {
            if (isWorking){
                return resultVo;
            }else {
                isWorking=true;
                List<LinkTransferTaskCDDVO> linkTransferTasks=linkTransferTaskFacade.startCdc();
                CDCTask cdcTask=new CDCTask(totalStartTime,linkTransferTasks,defaultMQProducer);
                Thread thread=new Thread(cdcTask);
                thread.start();
            }
            //获取需要监听的表列表

            //根据列表进行数据增量入mq的工作


        }catch (Exception e){
            resultVo.setResultDes("重试任务异常,原因为"+e);
            log.error("重试任务异常",e);
        }
        return resultVo;
    }

    @ApiOperation(value = "startTableCDC")
    @RequestMapping(value = "/startTableCDC",method = RequestMethod.GET,produces = {"application/json;charset=UTF-8"})
        public ResultVo<LinkTransferTask>startTableCDC(@RequestParam("type")String type,@RequestParam("segName") String segName,@RequestParam("tableName")String tableName){
        ResultVo resultVo=new ResultVo();
        try {
            //获取需要监听的表列表
            if (connection==null||connection.isClosed()) {
                Class.forName("oracle.jdbc.OracleDriver");
                String toUrl = "jdbc:oracle:thin:@172.16.98.101:1521:ORCL";
                String toUserName = "SYNC";
                String toPassWord = "SYNC";
                Properties props = new Properties();
                props.put("user", toUserName);
                props.put("password", toPassWord);
                props.put("oracle.net.CONNECT_TIMEOUT", "10000000");
                 connection = DriverManager.getConnection(toUrl, props);
            }
            if (tableName.contains("?"))tableName=tableName.substring(0,tableName.length()-1);
            TableStatusCache.setStatus(segName,tableName, type, connection);
        }catch (Exception e){
            resultVo.setResultDes("重试任务异常,原因为"+e);
            log.error("重试任务异常",e);
        }
        return resultVo;
    }

    @ApiOperation(value = "getCacheStatus")
    @RequestMapping(value = "/getCacheStatus",method = RequestMethod.GET,produces = {"application/json;charset=UTF-8"})
    public ResultVo<String>getCacheStatus(){
        ResultVo resultVo=new ResultVo();
        try {
            String result="当前监听进程存活情况--";
            if (isWorking){
                result+="存活";
            }else {
                result+="不存活";
            }
            log.info(result);
            log.info(TableStatusCache.statusMap+"");
            log.info(SQLSaver.taskQueue+"");
            log.info(SQLSaver.tableCacheMap+"");
//            String
        }catch (Exception e){
            resultVo.setResultDes("重试任务异常,原因为"+e);
            log.error("重试任务异常",e);
        }
        return resultVo;
    }

    public static void main(String[] args) throws Exception{
//        String connectUrl="jdbc:oracle:thin:@//172.16.81.11:1521/hzrsrac";
//        String targetUserName="sjhl_fy";
//        String targetPassword="sjhl_pwdfy21";
//        Class.forName("oracle.jdbc.OracleDriver");
//        Connection targetConnection = DriverManager.getConnection(connectUrl, targetUserName, targetPassword);
//        targetConnection.createStatement().execute("BEGIN dbms_logmnr.start_logmnr(STARTTIME =>to_date('2021-06-02 10:13:00' , 'yyyy-mm-dd hh24:mi:ss'),options => DBMS_LOGMNR.DICT_FROM_ONLINE_CATALOG + DBMS_LOGMNR.CONTINUOUS_MINE + DBMS_LOGMNR.COMMITTED_DATA_ONLY);END;");
////            PreparedStatement preparedStatement=targetConnection.prepareStatement("SELECT * FROM v$logmnr_contents where  seg_owner = 'SYNC' and table_name" +
////                    " in ('AC85')  AND (operation IN ('INSERT','UPDATE','DELETE','DDL'))");
//        PreparedStatement preparedStatement=targetConnection.prepareStatement("SELECT count(*) FROM v$logmnr_contents where  "+
//                "  (operation IN ('INSERT','UPDATE','DELETE','DDL')) and ");
//        preparedStatement.setFetchSize(1);
//        ResultSet resultset=preparedStatement.executeQuery();
//        while (resultset.next()) {
//            System.out.println(resultset.getString("count(*)"));
//        }
        Class.forName("oracle.jdbc.OracleDriver");
        String toUrl = "jdbc:oracle:thin:@172.16.98.101:1521:ORCL";
        String toUserName = "SYNC";
        String toPassWord = "SYNC";
        Properties props = new Properties();
        props.put("user", toUserName);
        props.put("password", toPassWord);
        props.put("oracle.net.CONNECT_TIMEOUT", "10000000");
       Connection connection = DriverManager.getConnection(toUrl, props);
    }

}
