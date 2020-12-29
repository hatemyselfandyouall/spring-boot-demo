package com.wangxinenpu.springbootdemo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.wangxinenpu.springbootdemo.config.MyCacheConfig;
import com.wangxinenpu.springbootdemo.constant.OpenapiCacheEnum;
import com.wangxinenpu.springbootdemo.dao.mapper.selfmachine.OpenapiOrgMapper;
import com.wangxinenpu.springbootdemo.dao.mapper.selfmachine.OpenapiSelfmachineMapper;
import com.wangxinenpu.springbootdemo.dao.mapper.selfmachine.*;
import com.wangxinenpu.springbootdemo.dataobject.dto.sitematters.SelfMachineOrgDTO;
import com.wangxinenpu.springbootdemo.dataobject.po.*;
import com.wangxinenpu.springbootdemo.dataobject.vo.OpenapiSelfmachine.OpenapiSelfmachineDetailVO;
import com.wangxinenpu.springbootdemo.dataobject.vo.OpenapiSelfmachineRequest.*;
import com.wangxinenpu.springbootdemo.service.facade.OpenapiSelfmachineFacade;
import com.wangxinenpu.springbootdemo.service.facade.OpenapiSelfmachineRequestFacade;
import com.wangxinenpu.springbootdemo.util.DateUtils;
import com.wangxinenpu.springbootdemo.util.JSONUtil;
import com.wangxinenpu.springbootdemo.util.MybatisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import star.bizbase.util.StringUtils;
import star.bizbase.util.constant.SysCacheTimeDMO;
import star.modules.cache.CachesKeyService;
import star.modules.cache.CachesService;
import tk.mybatis.mapper.entity.Example;

import java.util.*;


@Slf4j
@Service
public class OpenapiSelfmachineRequestServiceImpl implements OpenapiSelfmachineRequestFacade {

    @Autowired
    OpenapiSelfmachineRequestMapper openapiSelfmachineRequestMapper;
    @Autowired
    CachesKeyService cachesKeyService;
    @Autowired
    CachesService cachesService;
    @Autowired
    OpenapiOrgMapper openapiOrgMapper;
    @Autowired
    OpenapiSelfmachineMapper openapiSelfmachineMapper;
    @Autowired
    OpenapiSelfmachineTypeMapper openapiSelfmachineTypeMapper;
    @Autowired
    OpenapiSelfmachineFacade openapiSelfmachineFacade;

    @Override
    public PageInfo<OpenapiSelfmachineRequest> getOpenapiSelfmachineRequestList(OpenapiSelfmachineRequestListVO openapiSelfmachineRequestListVO) {
        if (openapiSelfmachineRequestListVO == null || openapiSelfmachineRequestListVO.getPageNum() == null || openapiSelfmachineRequestListVO.getPageSize() == null) {
            return null;
        }
        Example example = new Example(OpenapiSelfmachineRequest.class);
        Example.Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(openapiSelfmachineRequestListVO.getKeyWord())) {
            criteria.andCondition("( ip like '%" + openapiSelfmachineRequestListVO.getKeyWord() + "%' or unique_code like '%"
                    + openapiSelfmachineRequestListVO.getKeyWord() + "%' or mac_address like '%" + openapiSelfmachineRequestListVO.getKeyWord() + "%' )");
        }
        if (!StringUtils.isEmpty(openapiSelfmachineRequestListVO.getMachineType())) {
            criteria.andEqualTo("machineType", openapiSelfmachineRequestListVO.getMachineType());
        }
        if (openapiSelfmachineRequestListVO.getPreliminaryCalibration() != null) {
            criteria.andEqualTo("preliminaryCalibration", openapiSelfmachineRequestListVO.getPreliminaryCalibration());
        }
        criteria.andEqualTo("statu", SelfMachineEnum.NOT_YET);
        example.setOrderByClause("request_time desc");
        PageHelper.startPage(openapiSelfmachineRequestListVO.getPageNum().intValue(), openapiSelfmachineRequestListVO.getPageSize().intValue());
        List<OpenapiSelfmachineRequest> openapiSelfmachineRequestList = openapiSelfmachineRequestMapper.selectByExample(example);
        openapiSelfmachineRequestList.forEach(i -> {
            OpenapiSelfmachine openapiSelfmachine = null;
            List<OpenapiSelfmachine> openapiSelfmachines = openapiSelfmachineMapper.select(new OpenapiSelfmachine().setUniqueCode(i.getUniqueCode()));
            if (!CollectionUtils.isEmpty(openapiSelfmachines)) {
                openapiSelfmachine = openapiSelfmachines.get(0);
            }
            if (openapiSelfmachine != null && openapiSelfmachine.getMachineTypeId() != null) {
                OpenapiSelfmachineType openapiSelfmachineType = openapiSelfmachineTypeMapper.selectByPrimaryKey(openapiSelfmachine.getMachineTypeId());
                i.setMachineType(openapiSelfmachineType != null ? openapiSelfmachineType.getName() : "");
            }
        });
        PageInfo<OpenapiSelfmachineRequest> openapiSelfmachineRequestPageInfo = new PageInfo<>(openapiSelfmachineRequestList);
        return openapiSelfmachineRequestPageInfo;
    }

    @Override
    public OpenapiSelfmachineRequest getOpenapiSelfmachineRequestDetail(OpenapiSelfmachineRequestDetailVO openapiSelfmachineRequestDetailVO) {
        if (openapiSelfmachineRequestDetailVO == null) {
            return null;
        }
        ;
        List<OpenapiSelfmachineRequest> openapiSelfmachineRequest = openapiSelfmachineRequestMapper.select(JSONUtil.convert(openapiSelfmachineRequestDetailVO, OpenapiSelfmachineRequest.class));
        if (!CollectionUtils.isEmpty(openapiSelfmachineRequest)) {
            return openapiSelfmachineRequest.get(0);
        } else {
            return null;
        }
    }

    @Override
    public Integer saveOpenapiSelfmachineRequest(OpenapiSelfmachineRequestSaveVO openapiSelfmachineRequestSaveVO) {
        if (openapiSelfmachineRequestSaveVO == null) {
            return 0;
        }
        OpenapiSelfmachineRequest openapiSelfmachineRequest = JSONUtil.convert(openapiSelfmachineRequestSaveVO, OpenapiSelfmachineRequest.class);
        if (openapiSelfmachineRequest.getId() == null) {
            return openapiSelfmachineRequestMapper.insertSelective(openapiSelfmachineRequest);
        } else {
            Example example = new Example(OpenapiSelfmachineRequest.class);
            example.createCriteria().andEqualTo("id", openapiSelfmachineRequest.getId());
            return openapiSelfmachineRequestMapper.updateByExampleSelective(openapiSelfmachineRequest, example);
        }
    }

    @Override
    public Integer deleteOpenapiSelfmachineRequest(OpenapiSelfmachineRequestDeleteVO openapiSelfmachineRequestDeleteVO) {
        if (openapiSelfmachineRequestDeleteVO == null || CollectionUtils.isEmpty(openapiSelfmachineRequestDeleteVO.getIds())) {
            return 0;
        }
        OpenapiSelfmachineRequest openapiSelfmachineRequest = new OpenapiSelfmachineRequest();
        openapiSelfmachineRequest.setStatu(openapiSelfmachineRequestDeleteVO.getStatu());
        Example example = new Example(OpenapiSelfmachineRequest.class);
        example.createCriteria().andIn("id", openapiSelfmachineRequestDeleteVO.getIds());
        return openapiSelfmachineRequestMapper.updateByExampleSelective(openapiSelfmachineRequest, example);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OpenapiSelfmachineRequest createToken(OpenapiSelfmachineRequest openapiSelfmachineRequest, OpenapiOrg openapiOrg) {
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        //初始化的情况
        if (openapiSelfmachineRequest.getToken() == null && openapiSelfmachineRequest.getOldToken() == null) {
            openapiSelfmachineRequest.setToken(token);
            openapiSelfmachineRequestMapper.updateByPrimaryKeySelective(openapiSelfmachineRequest);
             MyCacheConfig.putInCache(OpenapiCacheEnum.REQUEST_TOKEN, openapiSelfmachineRequest.getToken(), openapiSelfmachineRequest, SysCacheTimeDMO.CACHETIMEOUT_30M);
        }
        //token过期的情况
        if ( MyCacheConfig.getFromCache(OpenapiCacheEnum.REQUEST_TOKEN, openapiSelfmachineRequest.getToken()) == null) {
            openapiSelfmachineRequest.setOldToken(openapiSelfmachineRequest.getToken());
            openapiSelfmachineRequest.setToken(token);
            openapiSelfmachineRequestMapper.updateByPrimaryKeySelective(openapiSelfmachineRequest);
             MyCacheConfig.putInCache(OpenapiCacheEnum.REQUEST_TOKEN, openapiSelfmachineRequest.getOldToken(), openapiSelfmachineRequest, SysCacheTimeDMO.CACHETIMEOUT_1H);
             MyCacheConfig.putInCache(OpenapiCacheEnum.REQUEST_TOKEN, openapiSelfmachineRequest.getToken(), openapiSelfmachineRequest, SysCacheTimeDMO.CACHETIMEOUT_30M);
        }
        openapiSelfmachineFacade.todayOpen(openapiSelfmachineRequest.getUniqueCode());
        return openapiSelfmachineRequest;
    }

    @Override
    public Boolean checkTokenExit(String token) {
        Boolean flag = false;
        if (StringUtils.isEmpty(token)) {
            return flag;
        }
        OpenapiSelfmachineRequest openapiSelfmachineRequest = null;
        Example example = new Example(OpenapiSelfmachineRequest.class);
        example.createCriteria().andCondition("token = '" + token + "' or old_token ='" + token + "'");
        List<OpenapiSelfmachineRequest> openapiSelfmachines = openapiSelfmachineRequestMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(openapiSelfmachines)) {
            openapiSelfmachineRequest = openapiSelfmachines.get(0);
        }
        if (openapiSelfmachineRequest == null) {
            return flag;
        }
        OpenapiSelfmachine openapiSelfmachine = openapiSelfmachineFacade.getOpenapiSelfmachineDetail(new OpenapiSelfmachineDetailVO().setUniqueCode(openapiSelfmachineRequest.getUniqueCode()));
        if (openapiSelfmachine == null || OpenapiSelfmachineEnum.CANCEL.equals(openapiSelfmachine.getActiveStatu())) {
            return flag;
        } else {
            return true;
        }

//        if (  MyCacheConfig.getFromCache(OpenapiCacheEnum.REQUEST_TOKEN,openapiSelfmachineRequest.getToken())!=null||
//                 MyCacheConfig.getFromCache(OpenapiCacheEnum.REQUEST_TOKEN,openapiSelfmachineRequest.getOldToken())!=null){
//            flag=  true;
//        }
//        return flag;
    }

    @Override
    public SelfMachineOrgDTO getOrgByToken(String token) {
        SelfMachineOrgDTO selfMachineOrgDTO = null;
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        OpenapiSelfmachineRequest openapiSelfmachineRequest = null;
        List<OpenapiSelfmachineRequest> openapiSelfmachines = openapiSelfmachineRequestMapper.select(new OpenapiSelfmachineRequest().setToken(token));
        if (!CollectionUtils.isEmpty(openapiSelfmachines)) {
            openapiSelfmachineRequest = openapiSelfmachines.get(0);
        }
        if (openapiSelfmachineRequest == null) {
            return null;
        }
        OpenapiSelfmachineRequest openapiSelfmachineRequest1 = (OpenapiSelfmachineRequest) MyCacheConfig.getFromCache(OpenapiCacheEnum.REQUEST_TOKEN, openapiSelfmachineRequest.getUniqueCode());
        if (openapiSelfmachineRequest1 != null && token.equals(openapiSelfmachineRequest1.getToken())) {
            Long orgId = openapiSelfmachineRequest1.getOrgId();
            OpenapiOrg openapiOrg = openapiOrgMapper.selectByPrimaryKey(orgId);
            if (openapiOrg == null) {
                return null;
            }
            selfMachineOrgDTO = new SelfMachineOrgDTO().setOrgCode(openapiOrg.getOrgCode()).setOrgName(openapiOrg.getOrgName());
        }
        return selfMachineOrgDTO;
    }

    @Override
    public OpenapiSelfmachineDetailShowVO getDetailByToken(String token) {
        Example example = new Example(OpenapiSelfmachineRequest.class);
        example.createCriteria().andCondition("token = '" + token + "' or old_token ='" + token + "'");
        OpenapiSelfmachineRequest openapiSelfmachineRequest = MybatisUtil.SelectOne(example, openapiSelfmachineRequestMapper);
        if (openapiSelfmachineRequest == null) {
            log.info("无此自助机");
            return null;
        }
        OpenapiOrg openapiOrg = MybatisUtil.SelectOne(new OpenapiOrg().setId(openapiSelfmachineRequest.getOrgId()), openapiOrgMapper);
        OpenapiSelfmachine openapiSelfmachine = MybatisUtil.SelectOne(new OpenapiSelfmachine().setUniqueCode(openapiSelfmachineRequest.getUniqueCode()), openapiSelfmachineMapper);
        if (openapiOrg == null) {
            log.info("不能找到对应机构");
            return null;
        }
        if (openapiSelfmachine == null) {
            log.info("无此自助机1");
            return null;
        }
        OpenapiSelfmachineDetailShowVO openapiSelfmachineDetailShowVO = new OpenapiSelfmachineDetailShowVO();
        OpenapiSelfmachineType openapiSelfmachineType = openapiSelfmachineTypeMapper.selectByPrimaryKey(openapiSelfmachine.getMachineTypeId());
        openapiSelfmachineDetailShowVO.setOrgCode(openapiOrg.getOrgCode())
                .setOrgName(openapiOrg.getOrgName())
                .setAreaId(openapiOrg.getAreaId())
                .setAreaName(openapiOrg.getAreaName())
                .setMachineTypeId(openapiSelfmachine.getMachineTypeId())
                .setMachineTypeName(openapiSelfmachineType != null ? openapiSelfmachineType.getName() : "");
//        MybatisUtil.SelectOne(new OpenapiSelfmachineRequest(),openapiSelfmachineRequestMapper);
        return openapiSelfmachineDetailShowVO;
    }


    public String getInitMachineCode(OpenapiSelfmachineRequest openapiSelfmachineRequest, OpenapiOrg openapiOrg) {
        Long orgId = openapiOrg.getId();
        List<OpenapiSelfmachineRequest> openapiSelfmachineRequests = openapiSelfmachineRequestMapper.select(new OpenapiSelfmachineRequest().setOrgId(orgId));
        Optional<Integer> number = openapiSelfmachineRequests.stream().filter(i -> i.getNumber() != null).max(Comparator.comparingInt(OpenapiSelfmachineRequest::getNumber)).map(OpenapiSelfmachineRequest::getNumber);
        openapiSelfmachineRequest.setNumber(number.orElse(0) + 1);
        if (openapiSelfmachineRequest.getNumber()<10){
            return openapiOrg.getAbbreviation() + "00" + openapiSelfmachineRequest.getNumber();
        }
        if (openapiSelfmachineRequest.getNumber()<100){
            return openapiOrg.getAbbreviation() + "0" + openapiSelfmachineRequest.getNumber();
        }
        return openapiOrg.getAbbreviation()  + openapiSelfmachineRequest.getNumber();
    }

    public static void main(String[] args) {
        System.out.println(DateUtils.parseDate(new Date(1574126664000l), "yyyy-MM-dd HH:mm:ss"));
    }
}
