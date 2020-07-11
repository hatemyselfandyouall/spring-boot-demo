package com.wangxinenpu.springbootdemo.controller;

import com.github.pagehelper.PageInfo;
import com.wangxinenpu.springbootdemo.dataobject.vo.Exam.ExamDetailShowVO;
import com.wangxinenpu.springbootdemo.dataobject.vo.User.*;
import com.wangxinenpu.springbootdemo.dataobject.vo.root.PageVO;
import com.wangxinenpu.springbootdemo.dataobject.vo.root.ResultVo;
import com.wangxinenpu.springbootdemo.service.UserFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.wangxinenpu.springbootdemo.dataobject.User;


@RestController
@RequestMapping("user")
@Api(tags ="用户")
@Slf4j
public class UserController  {

    @Autowired
    UserFacade userFacade;

    @ApiOperation(value = "注册")
    @RequestMapping(value = "/register",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo<User> register(@RequestBody UserSaveVO userSaveVOu){
        ResultVo resultVo=new ResultVo();
        try {
           Integer flag=userFacade.saveUser(userSaveVOu);
            if(1!=flag){
                resultVo.setSuccess(true);
            }else {
                resultVo.setResultDes("注册成功");
            }
        }catch (Exception e){
            resultVo.setResultDes("注册异常");
            log.error("注册异常",e);
        }
        return resultVo;
    }


    @ApiOperation(value = "登录")
    @RequestMapping(value = "/login",produces = {"application/json;charset=UTF-8"})
    public ResultVo<UserDetailShowVO> login(@RequestParam("userName") String userName,@RequestParam("passWord") String passWord){
        ResultVo resultVo=new ResultVo();
        try {
            resultVo=userFacade.login(userName,passWord);
        }catch (Exception e){
            resultVo.setResultDes("登录异常");
            log.error("登录异常",e);
        }
        return resultVo;
    }

    @ApiOperation(value = "用户排行")
    @RequestMapping(value = "/userRanking",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public ResultVo<UserDetailShowVO> userRanking(PageVO pageVO){
        ResultVo resultVo=new ResultVo();
        try {
            PageInfo<UserDetailShowVO> examList=userFacade.userRanking(pageVO);
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

}
