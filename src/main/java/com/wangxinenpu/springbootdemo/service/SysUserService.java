package com.wangxinenpu.springbootdemo.service;

import com.wangxinenpu.springbootdemo.dataobject.SysUser;
import org.springframework.stereotype.Service;

public interface SysUserService {

    SysUser getById(Integer id);

    SysUser findUser(SysUser sysUser);
}
