 
package com.wangxinenpu.springbootdemo.service.facade;

import com.github.pagehelper.PageInfo;
import com.wangxinenpu.springbootdemo.dataobject.po.OpenapiSelfmachineType;
import com.wangxinenpu.springbootdemo.dataobject.vo.OpenapiSelfmachineType.OpenapiSelfmachineTypeDeleteVO;
import com.wangxinenpu.springbootdemo.dataobject.vo.OpenapiSelfmachineType.OpenapiSelfmachineTypeDetailVO;
import com.wangxinenpu.springbootdemo.dataobject.vo.OpenapiSelfmachineType.OpenapiSelfmachineTypeListVO;
import com.wangxinenpu.springbootdemo.dataobject.vo.OpenapiSelfmachineType.OpenapiSelfmachineTypeSaveVO;


import java.util.List;


public interface OpenapiSelfmachineTypeFacade {

	PageInfo<OpenapiSelfmachineType> getOpenapiSelfmachineTypeList(OpenapiSelfmachineTypeListVO listVO, Long userId);

    OpenapiSelfmachineType getOpenapiSelfmachineTypeDetail(OpenapiSelfmachineTypeDetailVO detailVO);

    Integer saveOpenapiSelfmachineType(OpenapiSelfmachineTypeSaveVO saveVO, Long userId, String userName);

    Integer deleteOpenapiSelfmachineType(OpenapiSelfmachineTypeDeleteVO deleteVO, Long userId);


    List<OpenapiSelfmachineType> getAllTypes();

    Integer checkDelete(OpenapiSelfmachineTypeDeleteVO openapiSelfmachineTypeDeleteVO);

}

 
