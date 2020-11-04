//package com.wangxinenpu.springbootdemo.service.impl;
//
//
//import com.wangxinenpu.springbootdemo.service.facade.AuditConfigRuleFacade;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import tk.mybatis.mapper.entity.Example;
//
//import java.util.List;
//
///**
// * 审核配置规则facade服务实现类
// */
//public class AuditConfigRuleImpl implements AuditConfigRuleFacade {
//	protected Logger logger = LoggerFactory.getLogger(getClass());
//
//	@Autowired
//	private AuditConfigRuleMapper auditConfigRuleMapper;
//
//	@Autowired
//	private SysAreaMapper sysAreaMapper;
//
//	@Override
//	public Integer getAuditConfigRuleDTOListCount(String funName, String funType, String ruleStruts, String isBus, String funLevel, String auFlag) {
//		return auditConfigRuleMapper.getAuditConfigRuleDTOListCount(funName,funType,ruleStruts,isBus,funLevel,auFlag);
//	}
//
//	@Override
//	public List<AuditConfigRuleDTO> getAuditConfigRuleDTOListPage(String funName, String funType, String ruleStruts, String isBus, String funLevel, String auFlag, Integer page, Integer pageSize) {
//		page = (page - 1) * pageSize;
//		return auditConfigRuleMapper.getAuditConfigRuleDTOListPage(funName,funType,ruleStruts,isBus,funLevel,auFlag,page,pageSize);
//	}
//
//	@Override
//	public AuditConfigRuleDTO getAuditConfigRuleInfo(Long id) {
//		AuditConfigRuleDTO auditConfigRuleDTO = new AuditConfigRuleDTO();
//		AuditConfigRule auditConfigRule = new AuditConfigRule();
//		auditConfigRule.setId(id);
//		auditConfigRule = auditConfigRuleMapper.selectOne(auditConfigRule);
//		BeanUtils.copyProperties(auditConfigRule,auditConfigRuleDTO);
//		if(auditConfigRule!=null&& StringUtils.isNotEmpty(auditConfigRule.getRegionCode())) {
//			SysArea sysArea = sysAreaMapper.getByPrimaryKey(Long.parseLong(auditConfigRule.getRegionCode()));
//			if(sysArea!=null) {
//				auditConfigRuleDTO.setAreaName(sysArea.getAreaName());
//			}
//		}
//		return auditConfigRuleDTO;
//	}
//
//	@Override
//	public Integer saveAuditConfigRuleInfo(AuditConfigRule auditConfigRule) {
//		return auditConfigRuleMapper.insert(auditConfigRule);
//	}
//
//	@Override
//	public Integer updAuditConfigRuleInfo(AuditConfigRule auditConfigRule) {
//		return auditConfigRuleMapper.updateByPrimaryKeySelective(auditConfigRule);
//	}
//
//	@Override
//	public boolean getAuditConfigRuleYesOrNo(Long funId, String isBus, String funLevel, String auFlag, String isLeading , String regionCode) {
//		Example example = new Example(AuditConfigRule.class);
//		Example.Criteria criteria= example.createCriteria();
//		criteria.andEqualTo("funId",funId);
//		if(StringUtils.isNotEmpty(regionCode)){
//			criteria.andEqualTo("regionCode",regionCode);
//		}else{
//			criteria.andIsNull("regionCode");
//		}
//		AuditConfigRule auditConfigRule = auditConfigRuleMapper.selectOneByExample(example);
//		logger.info("getAuditConfigRuleYesOrNo:auditConfigRule="+auditConfigRule);
//		boolean auflg = false;
//		if(auditConfigRule!=null){
//			if("1".equals(auditConfigRule.getRuleStruts())){
//				if(StringUtils.isNotEmpty(isBus)){//判断菜单
//					logger.info("isBus:"+isBus+"==auditConfigRule.getIsBus()="+auditConfigRule.getIsBus());
//					if(isBus.equals(auditConfigRule.getIsBus())){
//						auflg=true;
//					}else{
//						logger.info("isBus:...................");
//						return false;
//					}
//				}
//				if(StringUtils.isNotEmpty(funLevel)){//判断菜单
//					logger.info("funLevel:"+auFlag+"==auditConfigRule.getFunLevel()="+auditConfigRule.getFunLevel());
//					if(funLevel.equals(auditConfigRule.getFunLevel())){
//						auflg=true;
//					}else {
//						logger.info("funLevel:...................");
//						return false;
//					}
//				}
//				if(StringUtils.isNotEmpty(auFlag)){//判断菜单
//					logger.info("auFlag:"+auFlag+"==auditConfigRule.getAuFlag()="+auditConfigRule.getAuFlag());
//					if(auFlag.equals(auditConfigRule.getAuFlag())){
//						auflg=true;
//					}else {
//						logger.info("auFlag:...................");
//						return false;
//					}
//				}
//				if(StringUtils.isNotEmpty(isLeading)){//判断菜单
//					logger.info("isLeading:"+isLeading+"==auditConfigRule.getIsLeading()="+auditConfigRule.getIsLeading());
//					if(isLeading.equals(auditConfigRule.getIsLeading())){
//						auflg=true;
//					}else {
//						logger.info("isLeading:...................");
//						return false;
//					}
//				}
//			}else{
//				auflg=true;
//			}
//		}else{
//			auflg=true;
//		}
//		return auflg;
//	}
//}
