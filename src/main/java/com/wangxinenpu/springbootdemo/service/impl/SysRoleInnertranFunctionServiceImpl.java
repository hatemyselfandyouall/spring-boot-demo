package com.wangxinenpu.springbootdemo.service.impl;

import com.wangxinenpu.springbootdemo.service.facade.SysRoleInnertranFunctionFacade;
import com.wangxinenpu.springbootdemo.dao.mapper.SysRoleInnertranFunctionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysRoleInnertranFunctionServiceImpl implements SysRoleInnertranFunctionFacade {

    @Autowired
    SysRoleInnertranFunctionMapper sysRoleInnertranFunctionMapper;


}
