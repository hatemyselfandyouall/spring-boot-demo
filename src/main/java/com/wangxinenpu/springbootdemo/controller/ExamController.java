package com.wangxinenpu.springbootdemo.controller;

import com.github.pagehelper.PageInfo;
import com.wangxinenpu.springbootdemo.dataobject.Exam;
import com.wangxinenpu.springbootdemo.dataobject.vo.Exam.*;
import com.wangxinenpu.springbootdemo.dataobject.vo.root.PageVO;
import com.wangxinenpu.springbootdemo.dataobject.vo.root.ResultVo;
import com.wangxinenpu.springbootdemo.service.ExamFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.wangxinenpu.springbootdemo.dataobject.Exam;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("exam")
@Api(tags ="试卷")
@Slf4j
public class ExamController  {

    @Autowired
    ExamFacade examFacade;

    @ApiOperation(value = "列表")
    @RequestMapping(value = "/list",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo<ExamDetailShowVO> getExamList(@RequestBody PageVO pageVO){
        ResultVo resultVo=new ResultVo();
        try {
            PageInfo<ExamDetailShowVO> examList=examFacade.getExamList(pageVO);
            if(examList!=null){
                resultVo.setResult(examList);
                resultVo.setSuccess(true);
            }else {
                resultVo.setResultDes("获取列表失败");
            }
        }catch (Exception e){
            resultVo.setResultDes("获取列表异常");
            log.error("获取列表异常",e);
        }
        return resultVo;
    }


    @ApiOperation(value = "详情")
    @RequestMapping(value = "/list",method = RequestMethod.GET,produces = {"application/json;charset=UTF-8"})
    public ResultVo<ExamDetailShowVO> getExamList(@RequestParam("id") Long id){
        ResultVo resultVo=new ResultVo();
        try {
            ExamDetailShowVO exam=examFacade.getExamDetail(id);
            if(exam!=null){
                resultVo.setResult(exam);
                resultVo.setSuccess(true);
            }else {
                resultVo.setResultDes("获取详情失败");
            }
        }catch (Exception e){
            resultVo.setResultDes("获取详情异常");
            log.error("获取详情异常",e);
        }
        return resultVo;
    }

    @ApiOperation(value = "考试结果保存")
    @RequestMapping(value = "/saveExamResult",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo saveExamResult(@RequestBody SaveExamResultVO saveExamResultVO){
        ResultVo resultVo=new ResultVo();
        try {
            Integer flag =examFacade.saveExamResult(saveExamResultVO);
            if(1==flag){
                resultVo.setResultDes("考试结果保存成功");
                resultVo.setSuccess(true);
            }else {
                resultVo.setResultDes("考试结果保存失败");
            }
        }catch (Exception e){
            resultVo.setResultDes("考试结果保存异常");
            log.error("考试结果保存异常",e);
        }
        return resultVo;
    }


    @ApiOperation(value = "试卷导入")
    @RequestMapping(value = "/importFile",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo importFile(@RequestPart("file")MultipartFile file){
        ResultVo resultVo=new ResultVo();
        try {
            Integer flag =examFacade.importExample(file);
            if(1==flag){
                resultVo.setResultDes("试卷导入成功");
                resultVo.setSuccess(true);
            }else {
                resultVo.setResultDes("试卷导入失败");
            }
        }catch (Exception e){
            resultVo.setResultDes("试卷导入异常");
            log.error("试卷导入异常",e);
        }
        return resultVo;
    }



}
