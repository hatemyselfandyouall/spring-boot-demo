 
package com.wangxinenpu.springbootdemo.dao.mapper.selfmachine;

import com.wangxinenpu.springbootdemo.dataobject.po.OpenapiSelfmachine;
import com.wangxinenpu.springbootdemo.dataobject.vo.OpenapiSelfmachine.OpenapiSelfmachineListVO;
import com.wangxinenpu.springbootdemo.dataobject.vo.OpenapiSelfmachine.OpenapiSelfmachineShowVO;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


public interface OpenapiSelfmachineMapper extends Mapper<OpenapiSelfmachine>{


    List<OpenapiSelfmachineShowVO> getOpenapiSelfmachineList(@Param("param") OpenapiSelfmachineListVO openapiSelfmachineListVO);
}
