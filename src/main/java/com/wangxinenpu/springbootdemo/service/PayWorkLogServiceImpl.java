package com.wangxinenpu.springbootdemo.service;

import com.wangxinenpu.springbootdemo.dao.mapper.PayLogMapper;
import com.wangxinenpu.springbootdemo.dataobject.po.PayLog;
import lombok.Data;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PayWorkLogServiceImpl implements PayWorkLogService {
    @Autowired
    private  PayLogMapper payLogMapper;

    @Override
    public Integer insertLog(PayLog payLog) {
        return payLogMapper.insert(payLog);
    }
}
