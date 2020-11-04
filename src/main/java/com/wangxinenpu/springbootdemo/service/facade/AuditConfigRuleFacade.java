package com.wangxinenpu.springbootdemo.service.facade;



import com.wangxinenpu.springbootdemo.dataobject.po.AuditConfigRule;
import com.wangxinenpu.springbootdemo.dataobject.vo.*;

import java.util.List;

/**
 * 审核配置规则服务接口
 * 
 * @author Administrator
 *
 */
public interface AuditConfigRuleFacade {
	Integer getAuditConfigRuleDTOListCount(String funName, String funType, String ruleStruts, String isBus, String funLevel, String auFlag);
	List<AuditConfigRuleDTO> getAuditConfigRuleDTOListPage(String funName, String funType, String ruleStruts, String isBus, String funLevel, String auFlag, Integer page, Integer pageSize);
	AuditConfigRuleDTO getAuditConfigRuleInfo(Long id);
	Integer saveAuditConfigRuleInfo(AuditConfigRule auditConfigRule);
	Integer updAuditConfigRuleInfo(AuditConfigRule auditConfigRule);
	boolean getAuditConfigRuleYesOrNo(Long funId, String isBus, String funLevel, String auFlag, String isLeading, String regionCode);
}
