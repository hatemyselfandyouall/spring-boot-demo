 
package com.wangxinenpu.springbootdemo.dao.mapper.selfmachine;


import com.wangxinenpu.springbootdemo.dataobject.dto.sitematters.SelfMachineOrgDTO;
import com.wangxinenpu.springbootdemo.dataobject.po.OpenapiOrg;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


public interface OpenapiOrgMapper extends Mapper<OpenapiOrg>{


    List<SelfMachineOrgDTO> getSelfMachine(@Param("name") String name);
}
