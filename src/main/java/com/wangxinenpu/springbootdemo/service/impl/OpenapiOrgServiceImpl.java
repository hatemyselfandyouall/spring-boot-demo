package com.wangxinenpu.springbootdemo.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taobao.diamond.extend.DynamicProperties;
import com.wangxinenpu.springbootdemo.constant.DataConstant;
import com.wangxinenpu.springbootdemo.dao.mapper.selfmachine.*;
import com.wangxinenpu.springbootdemo.dataobject.po.*;
import com.wangxinenpu.springbootdemo.dataobject.vo.SysUserDTO;
import com.wangxinenpu.springbootdemo.dataobject.vo.SysUserOrgDTO;
import com.wangxinenpu.springbootdemo.service.facade.*;
import com.wangxinenpu.springbootdemo.util.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.noggit.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import com.wangxinenpu.springbootdemo.dataobject.dto.GetSelfMachineDTO;
import com.wangxinenpu.springbootdemo.dataobject.dto.sitematters.SelfMachineOrgDTO;
import com.wangxinenpu.springbootdemo.dataobject.vo.OpenapiApp.ResetAppSecretVO;
import com.wangxinenpu.springbootdemo.dataobject.vo.OpenapiOrg.*;
import com.wangxinenpu.springbootdemo.dataobject.vo.OpenapiSelfmachineRequest.OpenapiSelfmachineRequestSaveVO;
import com.wangxinenpu.springbootdemo.dao.mapper.selfmachine.OpenapiOrgMapper;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OpenapiOrgServiceImpl implements OpenapiOrgFacade {

    @Autowired
    OpenapiOrgMapper OpenapiOrgMapper;
    @Autowired
    OpenapiSelfmachineMapper openapiSelfmachineMapper;
    @Autowired
    OpenapiSelfmachineRequestMapper openapiSelfmachineRequestMapper;
    @Autowired
    SysUserFacade sysUserFacade;
    @Autowired
    SysOrgFacade sysOrgFacade;
    @Autowired
    SysCheckConfigFacade sysCheckConfigFacade;
    @Autowired
    OpenapiOrgShortnameMapper openapiOrgShortnameMapper;
    @Autowired
    OpenapiSelfmachineFacade openapiSelfmachineFacade;
    @Autowired
    SysAreaFacade sysAreaFacade;

    private static Logger logger = LoggerFactory.getLogger(OpenapiOrgServiceImpl.class);
    public static final String URL= DynamicProperties.staticProperties.getProperty("oss.download.http.txt.url");
    @Override
    public PageInfo<OpenapiOrgShowVO> getOpenapiOrgList(OpenapiOrgListVO OpenapiOrgListVO, Long userId) {
        if (OpenapiOrgListVO==null||OpenapiOrgListVO.getPageNum()==null||OpenapiOrgListVO.getPageSize()==null) {
            return null;
        }
        Example example=new Example(OpenapiOrg.class);
        Example.Criteria criteria=example.createCriteria();
        if (OpenapiOrgListVO.getAreaId()!=null){
            List<String> orgIdList = sysAreaFacade.findAllByParentId(OpenapiOrgListVO.getAreaId()+"");
            logger.info(orgIdList+"areaIdS");
            criteria.andIn("areaId",orgIdList);
        }
        if (OpenapiOrgListVO.getOpseno()!=null){
            criteria.andEqualTo("opseno",OpenapiOrgListVO.getOpseno());
        }
        if (!StringUtils.isEmpty(OpenapiOrgListVO.getOrgCode())){
            criteria.andEqualTo("orgCode",OpenapiOrgListVO.getOrgCode());
        }
        if (!CollectionUtils.isEmpty(OpenapiOrgListVO.getStatusEnumList())){
            criteria.andIn("status",OpenapiOrgListVO.getStatusEnumList());
        }
        if (!CollectionUtils.isEmpty(OpenapiOrgListVO.getAuditResults())){
            criteria.andIn("auditResult",OpenapiOrgListVO.getAuditResults());
        }
        if (!StringUtils.isEmpty(OpenapiOrgListVO.getAuditorName())){
            criteria.andEqualTo("auditorName",OpenapiOrgListVO.getAuditorName());
        }
        if (!StringUtils.isEmpty(OpenapiOrgListVO.getCreatorName())){
            criteria.andEqualTo("creatorName",OpenapiOrgListVO.getCreatorName());
        }
        if (!StringUtils.isEmpty(OpenapiOrgListVO.getCreateTimeBegin())&&!StringUtils.isEmpty(OpenapiOrgListVO.getCreateTimeEnd())){
            criteria.andBetween("createTime",OpenapiOrgListVO.getCreateTimeBegin(),OpenapiOrgListVO.getCreateTimeEnd());
        }
        if(OpenapiOrgListVO.getOrderFlag()!=null&&OpenapiOrgListVO.getOrderFlag()==1){
            example.setOrderByClause("modify_time desc");
        }else {
            example.setOrderByClause("create_time asc");
        }
//        if (!StringUtils.isEmpty(OpenapiOrgListVO.getCreateTimeEnd())){
//            criteria.andEqualTo("auditorName",OpenapiOrgListVO.getAuditorName());
//        }
        String orgCode=null;
        if (userId!=null){
            List<SysUserOrgDTO> orgList=sysUserFacade.queryUserOrg(userId);
            if (orgList!=null&&!orgList.isEmpty()){
                orgCode=sysOrgFacade.getByPrimaryKey(orgList.get(0).getOrgId()).getOrgenterCode();
            }
            criteria.andEqualTo("orgCode",orgCode);
        }
        PageHelper.startPage(OpenapiOrgListVO.getPageNum().intValue(),OpenapiOrgListVO.getPageSize().intValue());
        Page<OpenapiOrg> openapiOrgList= (Page<OpenapiOrg>) OpenapiOrgMapper.selectByExample(example);
        List<OpenapiOrgShowVO> openapiOrgShowVOS=openapiOrgList.stream().map(i->{
            OpenapiOrgShowVO openapiOrgShowVO= new OpenapiOrgShowVO();
            BeanUtils.copyProperties(i,openapiOrgShowVO);
            openapiOrgShowVO.setCertificateKey(URL+i.getCertificateKey());
            openapiOrgShowVO.setMachineCount(openapiSelfmachineRequestMapper.selectCount(new OpenapiSelfmachineRequest().setOrgId(i.getId()).setStatu(SelfMachineEnum.WHITE)));
            return openapiOrgShowVO;
        }).collect(Collectors.toList());
        PageInfo<OpenapiOrgShowVO> OpenapiOrgPageInfo=new PageInfo<>(openapiOrgShowVOS);
        OpenapiOrgPageInfo.setTotal(openapiOrgList.getTotal());
        return OpenapiOrgPageInfo;
    }

    @Override
    public OpenapiOrg getOpenapiOrgDetail(OpenapiOrgDetailVO OpenapiOrgDetailVO) {
        if (OpenapiOrgDetailVO==null||OpenapiOrgDetailVO.getId()==null) {
            return null;
        };
        OpenapiOrg OpenapiOrg=OpenapiOrgMapper.selectByPrimaryKey(OpenapiOrgDetailVO.getId());
        return OpenapiOrg;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer saveOpenapiOrg(OpenapiOrgSaveVO OpenapiOrgSaveVO, Long userId, String userName)throws Exception {
        if (OpenapiOrgSaveVO==null){
            return 0;
        }
        SysUserDTO user = sysUserFacade.getByPrimaryKey(userId);
        OpenapiOrg openapiOrg= new OpenapiOrg();
        BeanUtils.copyProperties(openapiOrg,OpenapiOrgSaveVO);
        if (openapiOrg.getId()==null){
            String appKey= UUID.randomUUID().toString().replaceAll("-","");
            String appSecret= UUID.randomUUID().toString().replaceAll("-","");
            String certificate= MD5Util.MD5Encode(appKey+openapiOrg.getOrgCode()+openapiOrg.getCreateTime(),"UTF-8");
            if (!StringUtils.isEmpty(openapiOrg.getSelfmachineValidateCode())){
                certificate=certificate+"|"+openapiOrg.getSelfmachineValidateCode();
            }
            String key= HttpUtil.upload(certificate);
            if(OpenapiOrgStatusEnum.AUDIT_PASSED.equals(openapiOrg.getStatus())){
                openapiOrg.setAuditorName(userName);
                openapiOrg.setStatus(OpenapiOrgStatusEnum.AUDIT_PASSED) ;
                openapiOrg.setAuditResult(OpenapiOrgAuditResultEnum.AUDIT_PASSED);
            }
            openapiOrg.setCreatorId(userId);
            openapiOrg.setModifyId(userId);
            openapiOrg.setCreatorName(userName);
            openapiOrg.setModifyName(userName);
            openapiOrg.setCertificate(certificate);
            openapiOrg.setCertificateKey(key);
            openapiOrg.setAppKey(appKey);
            openapiOrg.setAppSecret(appSecret);
            openapiOrg.setSortNumber(getCertCodeNumber(openapiOrg.getOrgCode()));
            openapiOrg.setCertificateCode(openapiOrg.getOrgCode()+ DateUtils.getStringCurrentDate()+ NumbersUtil.getSortNumber(openapiOrg.getSortNumber(),2));
            if (!StringUtils.isEmpty(openapiOrg.getOrgCode())){
                OpenapiOrgShortname openapiOrgShortname= MybatisUtil.SelectOne(new OpenapiOrgShortname().setOrgCode(openapiOrg.getOrgCode()),openapiOrgShortnameMapper);
                if (openapiOrgShortname!=null){
                    openapiOrg.setAbbreviation(openapiOrgShortname.getShortName());
                }
            }
            return OpenapiOrgMapper.insertSelective(openapiOrg);
        }else {
            if (!StringUtils.isEmpty(openapiOrg.getOrgCode())){
                OpenapiOrgShortname openapiOrgShortname= MybatisUtil.SelectOne(new OpenapiOrgShortname().setOrgCode(openapiOrg.getOrgCode()),openapiOrgShortnameMapper);
                if (openapiOrgShortname!=null){
                    openapiOrg.setAbbreviation(openapiOrgShortname.getShortName());
                }
            }
            openapiOrg.setModifyId(userId);
            openapiOrg.setModifyName(userName);
            openapiOrg.setModifyTime(new Date());
            Example example=new Example(OpenapiOrg.class);
            example.createCriteria().andEqualTo("id",openapiOrg.getId());
            return OpenapiOrgMapper.updateByExampleSelective(openapiOrg,example);
        }
    }

    private Integer getCertCodeNumber(String orgCode) {
        List<OpenapiOrg> openapiOrgs=OpenapiOrgMapper.select(new OpenapiOrg().setOrgCode(orgCode));
        Optional<Integer> number=openapiOrgs.stream().filter(i->i.getSortNumber()!=null).max(Comparator.comparingInt(OpenapiOrg::getSortNumber)).map(OpenapiOrg::getSortNumber);
        return number.orElse(0)+1;
    }

    @Override
    public Integer deleteOpenapiOrg(OpenapiOrgDeleteVO OpenapiOrgDeleteVO) {
        if (OpenapiOrgDeleteVO==null||OpenapiOrgDeleteVO.getId()==null){
            return 0;
        }
        OpenapiOrg OpenapiOrg=new OpenapiOrg();
        OpenapiOrg.setModifyTime(new Date());
        OpenapiOrg.setStatus(OpenapiOrgStatusEnum.IS_DELETE);
        Example example=new Example(OpenapiOrg.class);
        example.createCriteria().andEqualTo("id",OpenapiOrgDeleteVO.getId());
        return OpenapiOrgMapper.updateByExampleSelective(OpenapiOrg,example);
    }

    @Override
    public Integer getSelfMachineCountByOrgCode(String orgCode) {
        List<OpenapiOrg> openapiOrgs= OpenapiOrgMapper.select(new OpenapiOrg().setOrgCode(orgCode).setStatus(OpenapiOrgStatusEnum.AUDIT_PASSED));
        List<Long> ids=openapiOrgs.stream().map(OpenapiOrg::getId).collect(Collectors.toList());
        if (ids==null||ids.isEmpty()){
            return 0;
        }else {
            Example example=new Example(OpenapiSelfmachine.class);
            example.createCriteria().andIn("orgId",ids).andEqualTo("idDelete", DataConstant.NO_DELETE);
            return openapiSelfmachineMapper.selectCountByExample(example);
        }
    }

    @Override
    public OpenapiOrg getOrgByMachineCode(String machineCode) {
        List<OpenapiSelfmachineRequest> openapiSelfmachineRequest=openapiSelfmachineRequestMapper.select(new OpenapiSelfmachineRequest().setMachineCode(machineCode));
        if (openapiSelfmachineRequest==null||openapiSelfmachineRequest.isEmpty()) {
            return null;
        }
        Long id=openapiSelfmachineRequest.get(0).getOrgId();
        OpenapiOrg openapiOrg=OpenapiOrgMapper.selectByPrimaryKey(id);
        return openapiOrg;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer auditOpenapiOrg(OpenapiOrgAuditVO openapiOrgAuditVO, String auditName) {
        if (openapiOrgAuditVO==null||openapiOrgAuditVO.getId()==null){
            return 0;
        }
        OpenapiOrg OpenapiOrg=new OpenapiOrg();
        OpenapiOrg.setModifyTime(new Date());
        OpenapiOrg.setStatus(openapiOrgAuditVO.getStatus());
        OpenapiOrg.setAuditRemark(openapiOrgAuditVO.getAuditRemark());
        OpenapiOrg.setAuditorName(auditName);
        Example example=new Example(OpenapiOrg.class);
        example.createCriteria().andEqualTo("id",openapiOrgAuditVO.getId());
        if (OpenapiOrgStatusEnum.AUDIT_PASSED.equals(openapiOrgAuditVO.getStatus())) {
            setAuditResult(openapiOrgAuditVO.getId(), OpenapiOrgAuditResultEnum.AUDIT_PASSED);
        }
        if(OpenapiOrgStatusEnum.AUDIT_FAILED.equals(openapiOrgAuditVO.getStatus())){
            setAuditResult(openapiOrgAuditVO.getId(), OpenapiOrgAuditResultEnum.AUDIT_FAILED);
        }
        if(OpenapiOrgStatusEnum.AUDIT_CANCELED.equals(openapiOrgAuditVO.getStatus())){
            setAuditResult(openapiOrgAuditVO.getId(), OpenapiOrgAuditResultEnum.WAIT_AUDIT);
        }
        return OpenapiOrgMapper.updateByExampleSelective(OpenapiOrg,example);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer auditBackOpenapiOrg(OpenapiOrgAuditVO openapiOrgAuditVO, String auditName) {
        if (openapiOrgAuditVO==null||openapiOrgAuditVO.getId()==null){
            return 0;
        }
        OpenapiOrg OpenapiOrg=new OpenapiOrg();
        OpenapiOrg.setModifyTime(new Date());
        OpenapiOrg.setAuditRefuseReason(openapiOrgAuditVO.getAuditRefuseReason());
        OpenapiOrg.setStatus(OpenapiOrgStatusEnum.WAIT_AUDIT);
        OpenapiOrg.setAuditorName(auditName);
        Example example=new Example(OpenapiOrg.class);
        example.createCriteria().andEqualTo("id",openapiOrgAuditVO.getId());
        setAuditResult(openapiOrgAuditVO.getId(),OpenapiOrgAuditResultEnum.WAIT_AUDIT);
        return OpenapiOrgMapper.updateByExampleSelective(OpenapiOrg,example);
    }

    @Override
    public List<OpenapiOrg> getOpenapiOrgListByOpenso() {
        Example example=new Example(OpenapiOrg.class);
        Example.Criteria criteria=example.createCriteria();
        criteria.andIsNotNull("opseno");
        List<OpenapiOrg> openapiOrgList=OpenapiOrgMapper.selectByExample(example);
        return openapiOrgList;
    }

    private Integer setAuditResult(Long orgId, OpenapiOrgAuditResultEnum openapiOrgAuditResultEnum){
        OpenapiOrg openapiOrg=new OpenapiOrg();
        openapiOrg.setModifyTime(new Date());
        openapiOrg.setAuditResult(openapiOrgAuditResultEnum);
        Example example=new Example(OpenapiOrg.class);
        example.createCriteria().andEqualTo("id",orgId);
        return OpenapiOrgMapper.updateByExampleSelective(openapiOrg,example);
    }

    @Override
    public OpenapiOrg resetAppSecret(ResetAppSecretVO resetAppSecretVO) {
        if (resetAppSecretVO==null||resetAppSecretVO.getId()==null){
            return null;
        }
        OpenapiOrg openapiOrg=new OpenapiOrg();
        openapiOrg.setModifyTime(new Date());
        openapiOrg.setAppSecret(UUID.randomUUID().toString().replaceAll("-",""));
        Example example=new Example(OpenapiOrg.class);
        example.createCriteria().andEqualTo("id",resetAppSecretVO.getId());
        OpenapiOrgMapper.updateByExampleSelective(openapiOrg,example);
        return OpenapiOrgMapper.selectByPrimaryKey(resetAppSecretVO.getId());
    }

    @Override
    public PageInfo<SelfMachineOrgDTO> getSelfMachine(GetSelfMachineDTO getSelfMachineDTO) {
        if (getSelfMachineDTO==null||getSelfMachineDTO.getPageNum()==null||getSelfMachineDTO.getPageSize()==null) {
            return null;
        }
        PageHelper.startPage(getSelfMachineDTO.getPageNum().intValue(),getSelfMachineDTO.getPageSize().intValue());
        List<SelfMachineOrgDTO> selfMachineOrgDTOS=OpenapiOrgMapper.getSelfMachine(getSelfMachineDTO.getName());
        selfMachineOrgDTOS.forEach(i->{
          String orgCode=i.getOrgCode();
          List<Long> ids=OpenapiOrgMapper.select(new OpenapiOrg().setOrgCode(orgCode).setStatus(OpenapiOrgStatusEnum.AUDIT_PASSED)).stream().map(OpenapiOrg::getId).collect(Collectors.toList());
          Integer count;
          if (CollectionUtils.isEmpty(ids)){
              count=0;
          }else {
              Example example=new Example(OpenapiSelfmachineRequest.class);
              example.createCriteria().andIn("orgId", ids).andEqualTo("statu", SelfMachineEnum.WHITE);
              count=openapiSelfmachineRequestMapper.selectCountByExample(example);
          }
          i.setCount(count);
        });
        PageInfo<SelfMachineOrgDTO> OpenapiOrgPageInfo=new PageInfo<>(selfMachineOrgDTOS);
        return OpenapiOrgPageInfo;
    }

    @Override
    public OpenapiOrg checkCertificate(String certificate, OpenapiSelfmachineRequestSaveVO openapiSelfmachineRequestSaveVO) {
        OpenapiOrg openapiOrg=OpenapiOrgMapper.selectOne(new OpenapiOrg().setCertificate(certificate).setStatus(OpenapiOrgStatusEnum.AUDIT_PASSED));
        return openapiOrg;
    }

    @Override
    public Integer saveCheckConfigs(OpenapiOrgSaveVO saveVO, Long userId, String userName) throws Exception {
        //查询是否配置通用审核(true配置审核，false没有配置审核)
        boolean flag = sysCheckConfigFacade.isCheckConfig(saveVO.getFunctionId(),saveVO.getOpseno(),userId,null);
        logger.info("是否配置通用审核saveCheckConfigs flag={}",flag);
        if(flag == true){
            saveVO.setStatus(OpenapiOrgStatusEnum.WAIT_AUDIT);
        }else{
            saveVO.setStatus(OpenapiOrgStatusEnum.AUDIT_PASSED);
        }
        return saveOpenapiOrg(saveVO,userId,userName);
    }

    @Override
    public void importExcels(List<OpenapiSelfmachineRequestSaveVO> openapiSelfmachineRequestSaveVOS, OpenapiOrg openapiOrg) {
        openapiSelfmachineRequestSaveVOS.forEach(i->openapiSelfmachineFacade.saveSelfMachineWithPermission(i,openapiOrg));
    }
}
