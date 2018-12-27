package com.wangxinenpu.springbootdemo.service;

import com.wangxinenpu.springbootdemo.dao.mapper.SysUserMapper;
import com.wangxinenpu.springbootdemo.dataobject.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    SysUserMapper sysUserMapper;

    @Override
    public SysUser getById(Integer id) {
        SysUser sysUser=new SysUser();
        sysUser.setUserid(id);
        return sysUserMapper.selectOne(sysUser);
    }

    @Override
    public SysUser findUser(SysUser sysUser) {
        return sysUserMapper.selectOne(sysUser);
    }
}
