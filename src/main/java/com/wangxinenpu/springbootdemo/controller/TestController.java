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
import org.springframework.web.multipart.MultipartFile;

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
    @RequestMapping(value = "testShell",method = RequestMethod.POST)
    public Integer  doCurl(@RequestPart MultipartFile file){
        try {
            RestTemplate restTemplate=new RestTemplate();
            String testUrl65="http://10.87.0.65:8090/test/doCurl?url=";
            String testUrl68="http://10.87.0.68:8090/test/doCurl?url=";
            List<Map<String, String>> codeMaps= ExcelHelper.getFunctionListByExcel(file);
            for (Map<String, String> codeMap:codeMaps){
                String url=codeMap.get("url");
                String test65result=restTemplate.getForEntity(testUrl65+url,String.class).getBody();
                String test68result=restTemplate.getForEntity(testUrl68+url,String.class).getBody();
                codeMap.put("test65result",test65result);
                codeMap.put("test68result",test68result);
                if (!test65result.equals(test68result)){
                    log.info(codeMap+"");
                }
            }
            return 1;
        }catch (Exception e){
            return  0;
        }
    }
    public static void main(String[] args) {

//        ResponseEntity<String> responseEntity=restTemplate.getForEntity("http://10.85.159.203:7019/test/doCurl?url=http://www.baidu.com1",String.class);
//        System.out.println(responseEntity.getBody());
    }
}
