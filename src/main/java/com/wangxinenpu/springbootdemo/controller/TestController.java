package com.wangxinenpu.springbootdemo.controller;

import com.wangxinenpu.springbootdemo.dataobject.User;
import com.wangxinenpu.springbootdemo.service.UserService;
import com.wangxinenpu.springbootdemo.util.ExcelHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(value = "test")
@Slf4j
public class TestController {

    @Autowired
    UserService userService;


    @Autowired
    RedisTemplate redisTemplate;


    @RequestMapping("hello")
    public String hello(){
        log.info("hello log");
        return "hello Spring";
    }

    @RequestMapping(value = "mybtaisTest",method = RequestMethod.GET)
    public User mybtaisTest(@RequestParam(value = "id")Integer id){
        User sysUser=userService.getById(id);
        return sysUser;
    }

    @RequestMapping(value = "doCurl",method = RequestMethod.GET)
    public Integer  doCurl(@RequestParam("url")String url){
        try {
            RestTemplate templater=new RestTemplate();
            ResponseEntity<String> responseEntity=templater.getForEntity(url,String.class);
            return responseEntity.getStatusCode().value();
        }catch (Exception e){
           return  0;
        }
    }

    //    @RequestMapping(value = "redisTest",method = RequestMethod.GET)
//    public SysUser redisTest(){
////        for (int i=0;i<10;i++) {
////            redisTemplate.opsForValue().set(i+"","缓存测试"+i);
////        }
//        for (int i=0;i<10;i++){
//            log.info(redisTemplate.opsForValue().get(i+"")+"");
//        }
//        return null;
//    }

    public static void main(String[] args) {
        RestTemplate restTemplate=new RestTemplate();
        String testUrl65="";
        String testUrl68="";
        List<Map<String, String>> codeMaps= ExcelHelper.getFunctionListByExcel("C:\\Users\\epsoft\\Documents\\WeChat Files\\wxid_03wcfsqvw1nb22\\FileStorage\\File\\2020-08\\无标题.xlsx");
        for (Map<String, String> codeMap:codeMaps){
            String url=codeMap.get("url");
            String test65result=restTemplate.getForEntity(testUrl65+url,String.class).getBody();
            String test68result=restTemplate.getForEntity(testUrl68+url,String.class).getBody();
            codeMap.put("test65result",test65result);
            codeMap.put("test68result",test68result);
            if (!test65result.equals(test68result)){
                System.out.println(codeMap);
            }
        }
//        ResponseEntity<String> responseEntity=restTemplate.getForEntity("http://localhost:8080/test/doCurl?url=http://www.baidu.com1",String.class);
//        System.out.println(responseEntity.getBody());
    }
}
