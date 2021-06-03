//package com.wangxinenpu.springbootdemo.controller;
//
//import com.github.pagehelper.PageInfo;
//import com.wangxinenpu.springbootdemo.config.component.LoginComponent;
//import com.wangxinenpu.springbootdemo.dataobject.vo.DataListResultDto;
//import com.wangxinenpu.springbootdemo.dataobject.vo.LinkTransferTask.LinkTransferTaskHistoryListVO;
//import com.wangxinenpu.springbootdemo.dataobject.vo.LinkTransferTask.LinkTransferTaskHistoryShowVO;
//import com.wangxinenpu.springbootdemo.dataobject.vo.LinkTransferTask.LinkTransferTaskHistoryStatistics;
//import com.wangxinenpu.springbootdemo.service.facade.linkTransTask.LinkTransferTaskFacade;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//import star.vo.result.ResultVo;
//
//
//@RestController
//@RequestMapping("linkTransferTaskHistory")
//@Api(tags ="数据源抽取历史记录")
//@Slf4j
//public class LinkTransferTaskHistoryController {
//
//    @Autowired
//    LinkTransferTaskFacade linkTransferTaskFacade;
//    @Autowired
//    LoginComponent loginComponent;
//
//    @ApiOperation(value = "数据源抽取历史列表")
//    @RequestMapping(value = "/list",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
//    public ResultVo<LinkTransferTaskHistoryShowVO> getLinkTransferTaskHistoryList(@RequestBody LinkTransferTaskHistoryListVO linkTransferTaskHistoryListVO){
//        ResultVo resultVo=new ResultVo();
//        try {
//            PageInfo<LinkTransferTaskHistoryShowVO> linkTransferTaskList=linkTransferTaskFacade.getLinkTransferTaskHistoryList(linkTransferTaskHistoryListVO);
//            if(linkTransferTaskList!=null){
//                DataListResultDto<LinkTransferTaskHistoryShowVO> dataListResultDto=new DataListResultDto<>(linkTransferTaskList.getList(),(int)linkTransferTaskList.getTotal());
//                resultVo.setResult(dataListResultDto);
//                resultVo.setSuccess(true);
//            }else {
//                resultVo.setResultDes("分页数据缺失");
//            }
//        }catch (Exception e){
//            resultVo.setResultDes("数据源抽取历史列表异常");
//            log.error("数据源抽取历史列表异常",e);
//        }
//        return resultVo;
//    }
//
//    @ApiOperation(value = "数据源抽取历史统计数据")
//    @RequestMapping(value = "/statistics",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
//    public ResultVo<LinkTransferTaskHistoryStatistics> getLinkTransferTaskHistoryStatistics(){
//        ResultVo<LinkTransferTaskHistoryStatistics> resultVo=new ResultVo();
//        try {
//            LinkTransferTaskHistoryStatistics linkTransferTaskHistoryStatistics=linkTransferTaskFacade.getLinkTransferTaskHistoryStatistics();
//            if(linkTransferTaskHistoryStatistics!=null){
//                resultVo.setResult(linkTransferTaskHistoryStatistics);
//                resultVo.setSuccess(true);
//            }else {
//                resultVo.setResultDes("统计失败");
//            }
//        }catch (Exception e){
//            resultVo.setResultDes("数据源抽取历史列表异常");
//            log.error("数据源抽取历史列表异常",e);
//        }
//        return resultVo;
//    }
//
//}
