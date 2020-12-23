package com.wangxinenpu.springbootdemo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.wangxinenpu.springbootdemo.constant.DataConstant;
import com.wangxinenpu.springbootdemo.dao.mapper.selfmachine.OpenapiSelfmachineTypeMapper;
import com.wangxinenpu.springbootdemo.service.facade.OpenapiSelfmachineTypeFacade;
import org.apache.commons.lang.StringUtils;
import org.noggit.JSONUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import com.wangxinenpu.springbootdemo.dataobject.po.OpenapiSelfmachineType;
import com.wangxinenpu.springbootdemo.dataobject.vo.OpenapiSelfmachineType.OpenapiSelfmachineTypeDeleteVO;
import com.wangxinenpu.springbootdemo.dataobject.vo.OpenapiSelfmachineType.OpenapiSelfmachineTypeDetailVO;
import com.wangxinenpu.springbootdemo.dataobject.vo.OpenapiSelfmachineType.OpenapiSelfmachineTypeListVO;
import com.wangxinenpu.springbootdemo.dataobject.vo.OpenapiSelfmachineType.OpenapiSelfmachineTypeSaveVO;
import java.util.List;

@Service
public class OpenapiSelfmachineTypeServiceImpl implements OpenapiSelfmachineTypeFacade {

    @Autowired
    OpenapiSelfmachineTypeMapper openapiSelfmachineTypeMapper;


    @Override
    public PageInfo<OpenapiSelfmachineType> getOpenapiSelfmachineTypeList(OpenapiSelfmachineTypeListVO openapiSelfmachineTypeListVO, Long userId) {
        if (openapiSelfmachineTypeListVO==null||openapiSelfmachineTypeListVO.getPageNum()==null||openapiSelfmachineTypeListVO.getPageSize()==null) {
            return null;
        }
        Example example=new Example(OpenapiSelfmachineType.class);
        Example.Criteria criteria=example.createCriteria();
        if (userId!=null){
            criteria.andEqualTo("creatorId",userId);
        }
        if (!StringUtils.isEmpty(openapiSelfmachineTypeListVO.getName())){
            criteria.andLike("name","%"+openapiSelfmachineTypeListVO.getName()+"%");
        }
        criteria.andEqualTo("isDelete", DataConstant.NO_DELETE);
        PageHelper.startPage(openapiSelfmachineTypeListVO.getPageNum().intValue(),openapiSelfmachineTypeListVO.getPageSize().intValue());
        List<OpenapiSelfmachineType> openapiSelfmachineTypeList=openapiSelfmachineTypeMapper.selectByExample(example);
        PageInfo<OpenapiSelfmachineType> openapiSelfmachineTypePageInfo=new PageInfo<>(openapiSelfmachineTypeList);
        return openapiSelfmachineTypePageInfo;
    }

    @Override
    public OpenapiSelfmachineType getOpenapiSelfmachineTypeDetail(OpenapiSelfmachineTypeDetailVO openapiSelfmachineTypeDetailVO) {
        if (openapiSelfmachineTypeDetailVO==null||openapiSelfmachineTypeDetailVO.getId()==null) {
            return null;
        };
        OpenapiSelfmachineType openapiSelfmachineType=openapiSelfmachineTypeMapper.selectByPrimaryKey(openapiSelfmachineTypeDetailVO.getId());
        return openapiSelfmachineType;
    }

    @Override
    public Integer saveOpenapiSelfmachineType(OpenapiSelfmachineTypeSaveVO openapiSelfmachineTypeSaveVO, Long userId, String userName) {
        if (openapiSelfmachineTypeSaveVO==null){
            return 0;
        }
        OpenapiSelfmachineType openapiSelfmachineType=new OpenapiSelfmachineType();
        BeanUtils.copyProperties(openapiSelfmachineTypeSaveVO,openapiSelfmachineType);
        if (openapiSelfmachineType.getId()==null){
            openapiSelfmachineType.setCreatorId(userId).setCreatorName(userName);
            return openapiSelfmachineTypeMapper.insertSelective(openapiSelfmachineType);
        }else {
            Example example=new Example(OpenapiSelfmachineType.class);
            example.createCriteria().andEqualTo("id",openapiSelfmachineType.getId());
            return openapiSelfmachineTypeMapper.updateByExampleSelective(openapiSelfmachineType,example);
        }
    }

    @Override
    public Integer deleteOpenapiSelfmachineType(OpenapiSelfmachineTypeDeleteVO openapiSelfmachineTypeDeleteVO, Long userId) {
        if (openapiSelfmachineTypeDeleteVO==null||openapiSelfmachineTypeDeleteVO.getId()==null){
            return 0;
        }
        OpenapiSelfmachineType openapiSelfmachineType=new OpenapiSelfmachineType();
        openapiSelfmachineType.setIsDelete(openapiSelfmachineTypeDeleteVO.getIsDelete());
        Example example=new Example(OpenapiSelfmachineType.class);
        example.createCriteria().andEqualTo("id",openapiSelfmachineTypeDeleteVO.getId()).andEqualTo("creatorId",userId);
        return openapiSelfmachineTypeMapper.updateByExampleSelective(openapiSelfmachineType,example);
    }



    @Override
    public List<OpenapiSelfmachineType> getAllTypes() {
        List<OpenapiSelfmachineType> openapiSelfmachineTypes=openapiSelfmachineTypeMapper.select(new OpenapiSelfmachineType().setIsDelete(DataConstant.NO_DELETE));
        return openapiSelfmachineTypes;
    }
}
