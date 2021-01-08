package com.wangxinenpu.springbootdemo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wangxinenpu.springbootdemo.constant.DataConstant;
import com.wangxinenpu.springbootdemo.dao.mapper.selfmachine.OpenapiSelfmachineAddressMapper;
import com.wangxinenpu.springbootdemo.dataobject.po.OpenapiSelfmachineAddress;
import com.wangxinenpu.springbootdemo.dataobject.vo.OpenapiSelfmachineAddress.OpenapiSelfmachineAddressDeleteVO;
import com.wangxinenpu.springbootdemo.dataobject.vo.OpenapiSelfmachineAddress.OpenapiSelfmachineAddressDetailVO;
import com.wangxinenpu.springbootdemo.dataobject.vo.OpenapiSelfmachineAddress.OpenapiSelfmachineAddressListVO;
import com.wangxinenpu.springbootdemo.dataobject.vo.OpenapiSelfmachineAddress.OpenapiSelfmachineAddressSaveVO;
import com.wangxinenpu.springbootdemo.service.facade.OpenapiSelfmachineAddressFacade;
import com.wangxinenpu.springbootdemo.util.JSONUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class OpenapiSelfmachineAddressServiceImpl implements OpenapiSelfmachineAddressFacade {

    @Autowired
    OpenapiSelfmachineAddressMapper openapiSelfmachineAddressMapper;

    @Override
    public PageInfo<OpenapiSelfmachineAddress> getOpenapiSelfmachineAddressList(OpenapiSelfmachineAddressListVO openapiSelfmachineAddressListVO, Long userId) {
        if (openapiSelfmachineAddressListVO==null||openapiSelfmachineAddressListVO.getPageNum()==null||openapiSelfmachineAddressListVO.getPageSize()==null) {
            return null;
        }
        Example example=new Example(OpenapiSelfmachineAddress.class);
        Example.Criteria criteria=example.createCriteria();
        if (userId!=null){
            criteria.andEqualTo("userId",userId);
        }
        if (!StringUtils.isEmpty(openapiSelfmachineAddressListVO.getAddress())){
            criteria.andCondition("( address like ('%"+openapiSelfmachineAddressListVO.getAddress()+"%') " +
                    "or province like ('%"+openapiSelfmachineAddressListVO.getAddress()+"%') "
            +"or district like ('%"+openapiSelfmachineAddressListVO.getAddress()+"%') "
                    +"or city like ('%"+openapiSelfmachineAddressListVO.getAddress()+"%') )");
        }
        criteria.andEqualTo("isDelete", DataConstant.NO_DELETE);
        PageHelper.startPage(openapiSelfmachineAddressListVO.getPageNum().intValue(),openapiSelfmachineAddressListVO.getPageSize().intValue());
        List<OpenapiSelfmachineAddress> openapiSelfmachineAddressList=openapiSelfmachineAddressMapper.selectByExample(example);
        PageInfo<OpenapiSelfmachineAddress> openapiSelfmachineAddressPageInfo=new PageInfo<>(openapiSelfmachineAddressList);
        return openapiSelfmachineAddressPageInfo;
    }

    @Override
    public OpenapiSelfmachineAddress getOpenapiSelfmachineAddressDetail(OpenapiSelfmachineAddressDetailVO openapiSelfmachineAddressDetailVO) {
        if (openapiSelfmachineAddressDetailVO==null||openapiSelfmachineAddressDetailVO.getId()==null) {
            return null;
        };
        OpenapiSelfmachineAddress openapiSelfmachineAddress=openapiSelfmachineAddressMapper.selectByPrimaryKey(openapiSelfmachineAddressDetailVO.getId());
        return openapiSelfmachineAddress;
    }

    @Override
    public OpenapiSelfmachineAddress saveOpenapiSelfmachineAddress(OpenapiSelfmachineAddressSaveVO openapiSelfmachineAddressSaveVO, Long userId, String userName) {
        if (openapiSelfmachineAddressSaveVO==null){
            return null;
        }
        OpenapiSelfmachineAddress openapiSelfmachineAddress= JSONUtil.convert(openapiSelfmachineAddressSaveVO,OpenapiSelfmachineAddress.class);
        if (openapiSelfmachineAddress.getId()==null){
            openapiSelfmachineAddress.setUserId(userId).setCreatorName(userName);
            openapiSelfmachineAddressMapper.insertSelective(openapiSelfmachineAddress);
        }else {
            Example example=new Example(OpenapiSelfmachineAddress.class);
            example.createCriteria().andEqualTo("id",openapiSelfmachineAddress.getId());
            openapiSelfmachineAddressMapper.updateByExampleSelective(openapiSelfmachineAddress,example);
        }
        return openapiSelfmachineAddressMapper.selectByPrimaryKey(openapiSelfmachineAddress.getId());
    }

    @Override
    public Integer deleteOpenapiSelfmachineAddress(OpenapiSelfmachineAddressDeleteVO openapiSelfmachineAddressDeleteVO, Long userId) {
        if (openapiSelfmachineAddressDeleteVO==null||openapiSelfmachineAddressDeleteVO.getId()==null){
            return 0;
        }
        OpenapiSelfmachineAddress openapiSelfmachineAddress=new OpenapiSelfmachineAddress();
        openapiSelfmachineAddress.setIsDelete(openapiSelfmachineAddressDeleteVO.getIsDelete());
        Example example=new Example(OpenapiSelfmachineAddress.class);
        example.createCriteria().andEqualTo("id",openapiSelfmachineAddressDeleteVO.getId()).andEqualTo("userId",userId);
        return openapiSelfmachineAddressMapper.updateByExampleSelective(openapiSelfmachineAddress,example);
    }
}
