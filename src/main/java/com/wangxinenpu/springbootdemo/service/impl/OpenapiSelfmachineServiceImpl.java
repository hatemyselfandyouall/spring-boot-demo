package com.wangxinenpu.springbootdemo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wangxinenpu.springbootdemo.constant.DataConstant;
import com.wangxinenpu.springbootdemo.dao.mapper.selfmachine.*;
import com.wangxinenpu.springbootdemo.dataobject.po.*;
import com.wangxinenpu.springbootdemo.dataobject.vo.OpenapiSelfmachine.*;
import com.wangxinenpu.springbootdemo.dataobject.vo.OpenapiSelfmachineRequest.OpenapiSelfmachineRequestDeleteVO;
import com.wangxinenpu.springbootdemo.dataobject.vo.OpenapiSelfmachineRequest.OpenapiSelfmachineRequestSaveVO;
import com.wangxinenpu.springbootdemo.dataobject.vo.SysOrgDTO;
import com.wangxinenpu.springbootdemo.dataobject.vo.SysUserOrgDTO;
import com.wangxinenpu.springbootdemo.service.facade.OpenapiSelfmachineFacade;
import com.wangxinenpu.springbootdemo.service.facade.OpenapiSelfmachineRequestFacade;
import com.wangxinenpu.springbootdemo.service.facade.SysOrgFacade;
import com.wangxinenpu.springbootdemo.service.facade.SysUserFacade;
import com.wangxinenpu.springbootdemo.util.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import star.bizbase.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
public class OpenapiSelfmachineServiceImpl implements OpenapiSelfmachineFacade {

    @Autowired
    OpenapiSelfmachineMapper openapiSelfmachineMapper;
    @Autowired
    OpenapiSelfmachineAddressMapper openapiSelfmachineAddressMapper;
    @Autowired
    OpenapiSelfmachineRequestMapper openapiSelfmachineRequestMapper;
    @Autowired
    OpenapiSelfmachineRequestFacade openapiSelfmachineRequestFacade;
    @Autowired
    SysUserFacade sysUserFacade;
    @Autowired
    SysOrgFacade sysOrgFacade;
    @Autowired
    OpenapiSelfmachineTypeMapper openapiSelfmachineTypeMapper;



    @Override
    public PageInfo<OpenapiSelfmachineShowVO> getOpenapiSelfmachineList(OpenapiSelfmachineListVO openapiSelfmachineListVO, Long userId) {
        if (openapiSelfmachineListVO==null||openapiSelfmachineListVO.getPageNum()==null||openapiSelfmachineListVO.getPageSize()==null) {
            return null;
        }
        String orgCode=null;
        if (userId!=null){
            List<SysUserOrgDTO> orgList=sysUserFacade.queryUserOrg(userId);
            if (orgList!=null&&!orgList.isEmpty()){
                orgCode=sysOrgFacade.getByPrimaryKey(orgList.get(0).getOrgId()).getOrgenterCode();
                openapiSelfmachineListVO.setOrgCode(orgCode);
            }
        }
        if (!ObjectUtils.isEmpty(openapiSelfmachineListVO.getOrgName())){
            List<SysOrgDTO> sysOrgDTOS= sysOrgFacade.getListByAll();
            List<SysOrgDTO> tempNameList=sysOrgDTOS.parallelStream().filter(i->openapiSelfmachineListVO.getOrgName().equals(i.getOrgName())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(tempNameList)){
                List<Long> orgIds= sysOrgFacade.getOrgIdsForParent(tempNameList.get(0).getId()+"");
                List<String> nameSearchString =sysOrgDTOS.stream().filter(i->orgIds.contains(i.getId())).map(i->i.getOrgName()).collect(Collectors.toList());
                openapiSelfmachineListVO.setOrgNames(nameSearchString);
                openapiSelfmachineListVO.setOrgName(null);
            }
        }
        PageHelper.startPage(openapiSelfmachineListVO.getPageNum().intValue(),openapiSelfmachineListVO.getPageSize().intValue());
        List<OpenapiSelfmachineShowVO> openapiSelfmachineRequestList=openapiSelfmachineMapper.getOpenapiSelfmachineList(openapiSelfmachineListVO);
        openapiSelfmachineRequestList.forEach(i->{
            if (i.getMachineTypeId()!=null){
                OpenapiSelfmachineType openapiSelfmachineType=openapiSelfmachineTypeMapper.selectByPrimaryKey(i.getMachineTypeId());
                i.setMachineType(openapiSelfmachineType!=null?openapiSelfmachineType.getName():"");
            }
                OpenapiSelfmachineAddress openapiSelfmachineAddress=openapiSelfmachineAddressMapper.selectByPrimaryKey(i.getMachineAddressId());
                String temp=openapiSelfmachineAddress!=null?openapiSelfmachineAddress.getProvince()+openapiSelfmachineAddress.getCity()+openapiSelfmachineAddress.getDistrict()+openapiSelfmachineAddress.getAddress():"";
                temp=temp+" "+(i.getMachineAddress()!=null?i.getMachineAddress():"");
                i.setFullAddress(temp);
                temp=openapiSelfmachineAddress!=null?openapiSelfmachineAddress.getAddress():"";
            temp=temp+" "+(i.getMachineAddress()!=null?i.getMachineAddress():"");
            i.setTempAddress(temp);
        });
        PageInfo<OpenapiSelfmachineShowVO> openapiSelfmachineRequestPageInfo=new PageInfo<>(openapiSelfmachineRequestList);
        return openapiSelfmachineRequestPageInfo;
    }

    @Override
    public OpenapiSelfmachine getOpenapiSelfmachineDetail(OpenapiSelfmachineDetailVO openapiSelfmachineDetailVO) {
        if (openapiSelfmachineDetailVO==null) {
            return null;
        };
        OpenapiSelfmachine openapiSelfmachine=new OpenapiSelfmachine();
        BeanUtils.copyProperties(openapiSelfmachine,openapiSelfmachineDetailVO);
        List<OpenapiSelfmachine> openapiSelfmachines=openapiSelfmachineMapper.select(openapiSelfmachine);
        if (!CollectionUtils.isEmpty(openapiSelfmachines)){
            openapiSelfmachine=openapiSelfmachines.get(0);
        }
        return openapiSelfmachine;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OpenapiSelfmachine saveOpenapiSelfmachine(OpenapiSelfmachineSaveVO openapiSelfmachineSaveVO) {
        if (openapiSelfmachineSaveVO==null){
            return null;
        }
        OpenapiSelfmachine openapiSelfmachine=new OpenapiSelfmachine();
        BeanUtils.copyProperties(openapiSelfmachine,openapiSelfmachineSaveVO);
        if (openapiSelfmachine.getId()==null){
            openapiSelfmachineMapper.insertSelective(openapiSelfmachine);
        }else {
            Example example=new Example(OpenapiSelfmachine.class);
            example.createCriteria().andEqualTo("id",openapiSelfmachine.getId());
            openapiSelfmachineMapper.updateByExampleSelective(openapiSelfmachine,example);
        }
        if (openapiSelfmachine.getMachineAddressId()!=null){
            Example example=new Example(OpenapiSelfmachineAddress.class);
            example.createCriteria().andEqualTo("userId",0);
            openapiSelfmachineAddressMapper.updateByExampleSelective(new OpenapiSelfmachineAddress().setIsLastUsed(DataConstant.NOT_LAST_USED),example);
            example.clear();
            example.createCriteria().andEqualTo("id",openapiSelfmachine.getMachineAddressId());
            openapiSelfmachineAddressMapper.updateByExampleSelective(new OpenapiSelfmachineAddress().setIsLastUsed(DataConstant.IS_LAST_USED),example);
        }
        return getOpenapiSelfmachineDetail(new OpenapiSelfmachineDetailVO().setId(openapiSelfmachine.getId()));
    }

    @Override
    public Integer deleteOpenapiSelfmachine(OpenapiSelfmachineDeleteVO openapiSelfmachineDeleteVO) {
        if (openapiSelfmachineDeleteVO==null||openapiSelfmachineDeleteVO.getId()==null){
            return 0;
        }
        OpenapiSelfmachine openapiSelfmachine=new OpenapiSelfmachine();
        Example example=new Example(OpenapiSelfmachine.class);
        example.createCriteria().andEqualTo("id",openapiSelfmachineDeleteVO.getId());
        return openapiSelfmachineMapper.updateByExampleSelective(openapiSelfmachine,example);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public synchronized String saveSelfMachine(OpenapiSelfmachineRequestSaveVO openapiSelfmachineRequestSaveVO, OpenapiOrg openapiOrg) {
        OpenapiSelfmachine openapiSelfmachine= JSONUtil.convert(openapiSelfmachineRequestSaveVO,OpenapiSelfmachine.class);
        openapiSelfmachine.setOrgName(openapiOrg.getOrgName());
        openapiSelfmachineMapper.insertSelective(openapiSelfmachine.setOrgId(openapiOrg.getId()));
        OpenapiSelfmachineRequest openapiSelfmachineRequest=JSONUtil.convert(openapiSelfmachineRequestSaveVO,OpenapiSelfmachineRequest.class);
        if (openapiSelfmachineRequest.getMachineCode()==null){
            openapiSelfmachineRequest.setMachineCode(openapiSelfmachineRequestFacade.getInitMachineCode(openapiSelfmachineRequest,openapiOrg));
        }
        openapiSelfmachineRequest.setOrgName(openapiOrg.getOrgName()).setAppKey(openapiOrg.getAppKey());
        openapiSelfmachineRequestMapper.insertSelective(openapiSelfmachineRequest.setOrgId(openapiOrg.getId()));
        return openapiSelfmachineRequest.getMachineCode();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public synchronized String saveSelfMachineWithPermission(OpenapiSelfmachineRequestSaveVO openapiSelfmachineRequestSaveVO, OpenapiOrg openapiOrg) {
        OpenapiSelfmachine openapiSelfmachine=JSONUtil.convert(openapiSelfmachineRequestSaveVO,OpenapiSelfmachine.class);
        openapiSelfmachine.setOrgName(openapiOrg.getOrgName());
        openapiSelfmachine.setActiveStatu(OpenapiSelfmachineEnum.ACTIVE);
        openapiSelfmachineMapper.insertSelective(openapiSelfmachine.setOrgId(openapiOrg.getId()));
        OpenapiSelfmachineRequest openapiSelfmachineRequest=JSONUtil.convert(openapiSelfmachineRequestSaveVO,OpenapiSelfmachineRequest.class);
        openapiSelfmachineRequest.setStatu(SelfMachineEnum.WHITE);
        if (openapiSelfmachineRequest.getMachineCode()==null){
            openapiSelfmachineRequest.setMachineCode(openapiSelfmachineRequestFacade.getInitMachineCode(openapiSelfmachineRequest,openapiOrg));
        }
        openapiSelfmachineRequest.setOrgName(openapiOrg.getOrgName()).setAppKey(openapiOrg.getAppKey());
        openapiSelfmachineRequestMapper.insertSelective(openapiSelfmachineRequest.setOrgId(openapiOrg.getId()));
        return openapiSelfmachineRequest.getMachineCode();
    }
    @Override
    public Integer setStatu(OpenapiSelfmachineDeleteVO openapiSelfmachineDeleteVO) {
        if (openapiSelfmachineDeleteVO==null|| openapiSelfmachineDeleteVO.getId()==null){
            return 0;
        }
        OpenapiSelfmachine openapiSelfmachine=openapiSelfmachineMapper.selectByPrimaryKey(openapiSelfmachineDeleteVO.getId());
        if (openapiSelfmachine==null){
            return 0;
        }
        OpenapiSelfmachineRequest openapiSelfmachineRequest=null;
        List<OpenapiSelfmachineRequest> openapiSelfmachines=openapiSelfmachineRequestMapper.select(new OpenapiSelfmachineRequest().setUniqueCode(openapiSelfmachine.getUniqueCode()));
        if (!CollectionUtils.isEmpty(openapiSelfmachines)){
            openapiSelfmachineRequest=openapiSelfmachines.get(0);
        }
        if (openapiSelfmachineRequest==null){
            return 0;
        }
        return openapiSelfmachineRequestFacade.deleteOpenapiSelfmachineRequest(new OpenapiSelfmachineRequestDeleteVO().setIds(Arrays.asList(openapiSelfmachineRequest.getId())).setStatu(openapiSelfmachineDeleteVO.getStatu()));
    }

    @Override
    public Integer setActiveStatus(OpenapiSelfmachineSetActiveStatusVO openapiSelfmachineSetActiveStatusVO) {
        if (openapiSelfmachineSetActiveStatusVO==null|| CollectionUtils.isEmpty(openapiSelfmachineSetActiveStatusVO.getIds())){
            return 0;
        }
        OpenapiSelfmachine openapiSelfmachine=new OpenapiSelfmachine().setActiveStatu(openapiSelfmachineSetActiveStatusVO.getActiveStatu()).setRemark(openapiSelfmachineSetActiveStatusVO.getRemark());
        Example example=new Example(OpenapiSelfmachine.class);
        example.createCriteria().andIn("id",openapiSelfmachineSetActiveStatusVO.getIds());
        openapiSelfmachineMapper.updateByExampleSelective(openapiSelfmachine,example);
        List<String> requestUnionCodess=openapiSelfmachineMapper.selectByExample(example)
                .stream().map(OpenapiSelfmachine::getUniqueCode).collect(Collectors.toList());
        OpenapiSelfmachineRequest openapiSelfmachineRequest=new OpenapiSelfmachineRequest().setStatu(SelfMachineEnum.NOT_YET);
        example=new Example(OpenapiSelfmachineRequest.class);
        example.createCriteria().andIn("uniqueCode",requestUnionCodess);
        openapiSelfmachineRequestMapper.updateByExampleSelective(openapiSelfmachineRequest,example);
        return 1;
    }

    @Override
    public Integer setOrg(OpenapiSelfmachineSetOrgVO openapiSelfmachineSetOrgVO) {
        if (openapiSelfmachineSetOrgVO==null|| CollectionUtils.isEmpty(openapiSelfmachineSetOrgVO.getIds())){
            return 0;
        }
        OpenapiSelfmachine openapiSelfmachine=new OpenapiSelfmachine().setOrgId(openapiSelfmachineSetOrgVO.getOrgId()).setRemark(openapiSelfmachineSetOrgVO.getRemark());
        Example example=new Example(OpenapiSelfmachine.class);
        example.createCriteria().andIn("id",openapiSelfmachineSetOrgVO.getIds());
        return openapiSelfmachineMapper.updateByExampleSelective(openapiSelfmachine,example);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSelfMachine(OpenapiSelfmachineRequestSaveVO openapiSelfmachineRequestSaveVO,OpenapiSelfmachineRequest openapiSelfmachine) {
        openapiSelfmachine.setClientVersion(openapiSelfmachineRequestSaveVO.getClientVersion()).
                setIpSegment(openapiSelfmachineRequestSaveVO.getIpSegment()).setIp(openapiSelfmachineRequestSaveVO.getIp());
        OpenapiSelfmachine tempSelfMachine=null;
        List<OpenapiSelfmachine> openapiSelfmachines=openapiSelfmachineMapper.select(new OpenapiSelfmachine().setUniqueCode(openapiSelfmachine.getUniqueCode()));
        if (!CollectionUtils.isEmpty(openapiSelfmachines)){
            tempSelfMachine=openapiSelfmachines.get(0);
        }
        tempSelfMachine.setClientVersion(openapiSelfmachineRequestSaveVO.getClientVersion()).setSystemCode(openapiSelfmachineRequestSaveVO.getSystemCode())
                .setIp(openapiSelfmachineRequestSaveVO.getIp()).setHttpVersion(openapiSelfmachineRequestSaveVO.getHttpVersion()).setQtVersion(openapiSelfmachineRequestSaveVO.getQtVersion());
        openapiSelfmachineRequestMapper.updateByPrimaryKeySelective(openapiSelfmachine);
        openapiSelfmachineMapper.updateByPrimaryKeySelective(tempSelfMachine);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String reActivSelfMachine(OpenapiSelfmachine openapiSelfmachine, OpenapiSelfmachineRequest openapiSelfmachineRequest, OpenapiOrg openapiOrg){
        openapiSelfmachine.setActiveStatu(OpenapiSelfmachineEnum.ACTIVE);
        openapiSelfmachineMapper.updateByPrimaryKeySelective(openapiSelfmachine);
        openapiSelfmachineRequest.setMachineCode(openapiSelfmachineRequestFacade.getInitMachineCode(openapiSelfmachineRequest,openapiOrg))
        .setStatu(SelfMachineEnum.NOT_YET);
        openapiSelfmachineRequestMapper.updateByPrimaryKeySelective(openapiSelfmachineRequest);
        return openapiSelfmachineRequest.getMachineCode();
    }

    @Override
    public void todayOpen(String uniqueCode) {
        if (StringUtils.isEmpty(uniqueCode)){
            return;
        }
        OpenapiSelfmachine openapiSelfmachine=new OpenapiSelfmachine().setIsOpenToday(1);
        Example example=new Example(OpenapiSelfmachine.class);
        example.createCriteria().andEqualTo("uniqueCode",uniqueCode);
        openapiSelfmachineMapper.updateByExampleSelective(openapiSelfmachine,example);
    }

    @Override
    public void clearOpenStatu() {
        OpenapiSelfmachine openapiSelfmachine=new OpenapiSelfmachine().setIsOpenToday(0);
        Example example=new Example(OpenapiSelfmachine.class);
        openapiSelfmachineMapper.updateByExampleSelective(openapiSelfmachine,example);
    }

}
