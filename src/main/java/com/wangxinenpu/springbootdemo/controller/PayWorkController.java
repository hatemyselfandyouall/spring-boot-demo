package com.wangxinenpu.springbootdemo.controller;

import com.wangxinenpu.springbootdemo.dataobject.User;
import com.wangxinenpu.springbootdemo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(value = "pay")
@Slf4j
public class PayWorkController {



    @GetMapping(value = "test")
    public String mybtaisTest(){

        return "testFinal";
    }


}
