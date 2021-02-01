package com.wangxinenpu.springbootdemo.service.service;

import com.wangxinenpu.springbootdemo.dataobject.vo.SystemIconDTO;
import com.wangxinenpu.springbootdemo.dao.mapper.SystemIconMapper;
import com.wangxinenpu.springbootdemo.dataobject.po.SystemIcon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import star.vo.BaseVo;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class SystemIconService {

    @Autowired
    private SystemIconMapper systemIconMapper;


    public int insert (SystemIconDTO systemIcon){
        return systemIconMapper.insert(systemIcon.copyTo(SystemIcon.class));
    }
    public boolean checkName (String name){
        Example example = new Example(SystemIcon.class);
        example.createCriteria().andEqualTo("iconName",name);
        List<SystemIcon> systemIcons = systemIconMapper.selectByExample(example);
        return systemIcons.size()>0 ? false : true;
    }

    public List<SystemIconDTO> selectAll (){
       return BaseVo.copyListTo(systemIconMapper.selectAll(),SystemIconDTO.class);
    }
}
