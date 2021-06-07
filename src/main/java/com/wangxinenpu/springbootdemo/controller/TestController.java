package com.wangxinenpu.springbootdemo.controller;

import com.wangxinenpu.springbootdemo.dataobject.User;
import com.wangxinenpu.springbootdemo.redismq.MessagePublisher;
import com.wangxinenpu.springbootdemo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(value = "test")
@Slf4j
public class TestController {

    @Autowired
    UserService userService;


    @Autowired
    RedisTemplate redisTemplate;

    @Resource
    private MessagePublisher messagePublisher;



    @RequestMapping("hello")
    public String hello(){
        log.info("hello log");
        for(int i = 0;i<1000;i++){
            messagePublisher.publish("hello,world"+i);

        }


        return "hello Spring";
    }

    @RequestMapping(value = "mybtaisTest",method = RequestMethod.GET)
    public User mybtaisTest(@RequestParam(value = "id")Integer id){
        User sysUser=userService.getById(id);
        return sysUser;
    }

    @RequestMapping(value = "doCurl",method = RequestMethod.GET)
    public ResponseEntity<String>  doCurl(@RequestParam("url")String url){
        RestTemplate templater=new RestTemplate();
        ResponseEntity<String> responseEntity=templater.getForEntity(url,String.class);
        return responseEntity;
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
        ResponseEntity<String> responseEntity=restTemplate.getForEntity("http://localhost:8080/test/doCurl?url=http://www.baidu.com",String.class);
        System.out.println(responseEntity);
    }
}
