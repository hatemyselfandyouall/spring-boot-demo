package com.wangxinenpu.springbootdemo.service;

import com.wangxinenpu.springbootdemo.dao.mapper.UserMapper;
import com.wangxinenpu.springbootdemo.dataobject.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;
    @Override
    public User getById(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }
}
