package com.wangxinenpu.springbootdemo.service;

import com.wangxinenpu.springbootdemo.dataobject.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("client1")
public interface UserService {
    @RequestMapping(value = "/test/mybtaisTest",method = RequestMethod.GET)
    User getById(@RequestParam("id") Integer id);
}
