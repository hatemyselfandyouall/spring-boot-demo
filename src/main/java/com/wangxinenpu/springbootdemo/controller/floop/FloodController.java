package com.wangxinenpu.springbootdemo.controller.floop;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("floopTest")
@Api(tags = "飞洛链测试")
@Slf4j
public class FloodController {



    @ApiOperation(value = "创建账号")
    @RequestMapping(value = {"/createAccount"}, method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public Map createAccount() {
        Map<String,Object> resultMap=new HashMap<>();
        try {

        }catch (Exception e){
            log.error("创建账号异常",e);
            resultMap.put("result","创建账号异常");
        }
        return resultMap;
    }
}
