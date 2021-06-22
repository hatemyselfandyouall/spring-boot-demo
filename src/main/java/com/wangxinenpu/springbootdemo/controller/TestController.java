package com.wangxinenpu.springbootdemo.controller;

import com.wangxinenpu.springbootdemo.dataobject.User;
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





    @RequestMapping("hello")
    public String hello(){
        log.info("hello log");
        for(int i = 0;i<1000;i++){

        }


        return "hello Spring";
    }

    @RequestMapping("getRedisList")
    public String hello(@RequestParam("listName")String listName){
        listName="big:queue:EMPQUERY|AC05";
        log.info("列表"+listName+"共有"+redisTemplate.opsForList().size(listName)+"条数据");
        return "列表"+listName+"共有"+redisTemplate.opsForList().size(listName)+"条数据";
    }

    @RequestMapping("setRedisValue")
    public String setRedisValue(@RequestParam("redisValue")String redisMapName,String redisValue){
        redisTemplate.opsForValue().set("big:queue:table:9091","OTHERQUERY|RB01_SJZ,OTHERQUERY|RC01_SJZ,OTHERQUERY|RF02,OTHERQUERY|RF08,OTHERQUERY|BB10,OTHERQUERY|MV_BC15,EMPQUERY|AA02,EMPQUERY|AA03,EMPQUERY|AA05,EMPQUERY|AA06,EMPQUERY|AA17,EMPQUERY|AB01,EMPQUERY|AB02,EMPQUERY|AB07,EMPQUERY|AC01,EMPQUERY|AC02,EMPQUERY|AC05,EMPQUERY|AC20,EMPQUERY|AC35,OTHERQUERY|AC43,EMPQUERY|AC50,EMPQUERY|AC51,EMPQUERY|AC53,EMPQUERY|AC60,EMPQUERY|AC61,EMPQUERY|AC62,EMPQUERY|AC63,EMPQUERY|AC66,EMPQUERY|AC67,EMPQUERY|AC77");
        redisTemplate.opsForValue().set("big:queue:table:9092","EMPQUERY|AC82,EMPQUERY|AC83,EMP//        listName=\"big:queue:EMPQUERY|AC05\";\nQUERY|AC92,EMPQUERY|AC97,EMPQUERY|ACD8,EMPQUERY|AD27,EMPQUERY|AE23,EMPQUERY|AE28,EMPQUERY|AE29,EMPQUERY|AE53,EMPQUERY|AF02,EMPQUERY|BA04,EMPQUERY|BA08,EMPQUERY|MV_BB02,EMPQUERY|BB08,EMPQUERY|MV_BC02,EMPQUERY|MV_BC12,EMPQUERY|BC20,EMPQUERY|MV_BC60,EMPQUERY|BC65,EMPQUERY|MV_BC95,EMPQUERY|MV_BE03_DW,EMPQUERY|MV_BE03_RY,EMPQUERY|MV_BE03_JZXM,EMPQUERY|BF41,EMPQUERY|EXT6802,EMPQUERY|EXT6803,EMPQUERY|IC05,EMPQUERY|IC07,EMPQUERY|IC08");
        redisTemplate.opsForValue().set("big:queue:table:9093","EMPQUERY|IC09,EMPQUERY|IC10,EMPQUERY|IC13,EMPQUERY|IC20,EMPQUERY|IC87,EMPQUERY|IC88,EMPQUERY|IC89,EMPQUERY|MV_IC90,EMPQUERY|MV_IC91,EMPQUERY|IC92,EMPQUERY|IC93,EMPQUERY|MV_IC94,EMPQUERY|IC95,EMPQUERY|IC96,EMPQUERY|MV_IC97,EMPQUERY|MV_IC98,EMPQUERY|LA02,EMPQUERY|LA06,EMPQUERY|LB01,EMPQUERY|LB02,EMPQUERY|LC01,EMPQUERY|LC03,EMPQUERY|LC04,EMPQUERY|LC17,EMPQUERY|LC31,EMPQUERY|LC37,EMPQUERY|LC45,EMPQUERY|LC46,EMPQUERY|LC47,EMPQUERY|LC48");
        redisTemplate.opsForValue().set("big:queue:table:9094","EMPQUERY|LC56,EMPQUERY|LC57,EMPQUERY|LC58,EMPQUERY|LC59,EMPQUERY|LC68,EMPQUERY|LC69,EMPQUERY|LCB2,OTHERQUERY|AD07,EMPQUERY|BC43,EMPQUERY|AA10,EMPQUERY|AC90,EMPQUERY|AA26,VILQUERY|AA02,VILQUERY|AA03,VILQUERY|AA05,VILQUERY|AA17,VILQUERY|AC01,VILQUERY|AC02,VILQUERY|AC05,VILQUERY|AC20,VILQUERY|AC43,VILQUERY|AC50,VILQUERY|AC51,VILQUERY|AC53,VILQUERY|AC60,VILQUERY|AC61,VILQUERY|AC63,VILQUERY|AC66,VILQUERY|AC67,VILQUERY|AC77");
        redisTemplate.opsForValue().set("big:queue:table:9095","VILQUERY|AC82,VILQUERY|AC83,VILQUERY|MV_AC93,VILQUERY|AC97,VILQUERY|ACD8,OTHERQUERY|AD07,VILQUERY|AE23,VILQUERY|AF02,VILQUERY|BA04,VILQUERY|BA06,VILQUERY|MV_BC02,VILQUERY|MV_BE03_CSQ,VILQUERY|MV_BE03_RY,VILQUERY|IC05,VILQUERY|IC30,VILQUERY|AA10,VILQUERY|AC90,VILQUERY|AA26,INJURY|LC30,INJURY|LC51,INJURY|LCE1,INJURY|LCE3,INJURY|LCE4");

        //        log.info("列表"+listName+"共有"+redisTemplate.opsForList().size(listName)+"条数据");
//        return "列表"+listName+"共有"+redisTemplate.opsForList().size(listName)+"条数据";
    return "success";
    }

    @RequestMapping("getRedisValue")
    public String getRedisValue(){
        log.info(redisTemplate.opsForValue().get("big:queue:table:9091")+"");
        log.info(redisTemplate.opsForValue().get("big:queue:table:9092")+"");
        log.info(redisTemplate.opsForValue().get("big:queue:table:9093")+"");
        log.info(redisTemplate.opsForValue().get("big:queue:table:9094")+"");
        log.info(redisTemplate.opsForValue().get("big:queue:table:9095")+"");
        return "success";
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
