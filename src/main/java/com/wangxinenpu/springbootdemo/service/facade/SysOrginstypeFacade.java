package com.wangxinenpu.springbootdemo.service.facade;


import com.wangxinenpu.springbootdemo.dataobject.vo.SysOrginstypeDTO;
import star.bizbase.exception.BizRuleException;

import java.util.HashMap;
import java.util.List;

/**
 * 机构险种服务接口
 * 
 * @author Administrator
 *
 */
public interface SysOrginstypeFacade {
    
    /**
     * 新增机构险种
     * @param po
     * @return
     * @throws BizRuleException
     */
    public int addSysOrginstype(SysOrginstypeDTO po) throws BizRuleException;
    
    /**
     * 根据机构id查询险种
     * @param orgId
     * @return
     */
    public List<SysOrginstypeDTO> findByOrgId(Long orgId);
    
    /**
     * 根据机构id删除
     * @param orgId
     * @throws BizRuleException
     */
    public void deleteByOrgId(Long orgId) throws BizRuleException;
    
    /**
     * 根据参数查询列表
     * @param searchMap
     * @return
     */
    public List<SysOrginstypeDTO> getListByWhere(HashMap<String, Object> searchMap);

}
