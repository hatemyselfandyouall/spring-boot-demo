package com.wangxinenpu.springbootdemo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wangxinenpu.springbootdemo.constant.SysbaseCacheEnum;
import com.wangxinenpu.springbootdemo.service.facade.SysInnerOperateLogFacade;
import com.wangxinenpu.springbootdemo.dataobject.po.SysInnerOperateLog;
import com.wangxinenpu.springbootdemo.dataobject.po.SysInnerOperateLogMenu;
import com.wangxinenpu.springbootdemo.dataobject.vo.sysInnerOperateLog.SysInnerOperateLogListVO;
import com.wangxinenpu.springbootdemo.dao.mapper.SysInnerOperateLogMapper;
import com.wangxinenpu.springbootdemo.dao.mapper.SysInnerOperateLogMenuMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import star.bizbase.util.constant.SysCacheTimeDMO;
import star.modules.cache.CacheKeyLock;
import star.modules.cache.CachesKeyService;
import star.modules.cache.enumerate.BaseCacheEnum;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class SysInnerOperateLogServiceImpl implements SysInnerOperateLogFacade {

    @Autowired
    private SysInnerOperateLogMapper sysInnerOperateLogMapper;
    @Autowired
    private SysInnerOperateLogMenuMapper sysInnerOperateLogMenuMapper;
    @Autowired
    private CachesKeyService cachesKeyService;

    @Override
    public PageInfo<SysInnerOperateLog> getSysInnerOperateLogList(SysInnerOperateLogListVO sysInnerOperateLogListVO) {
        if (sysInnerOperateLogListVO == null || sysInnerOperateLogListVO.getPageNum() == null || sysInnerOperateLogListVO.getPageSize() == null) {
            return null;
        }
        PageHelper.startPage(sysInnerOperateLogListVO.getPageNum().intValue(), sysInnerOperateLogListVO.getPageSize().intValue());
        Example example = new Example(SysInnerOperateLog.class);
        Example.Criteria criteria = example.createCriteria();
            if (StringUtils.isNotEmpty(sysInnerOperateLogListVO.getMethodName())) {
                criteria.andCondition("(method_name like '%" + sysInnerOperateLogListVO.getMethodName() + "%' or method_ch_name like '%" + sysInnerOperateLogListVO.getMethodName() + "%')");
            }
            if (StringUtils.isNotEmpty(sysInnerOperateLogListVO.getUrl())){
                criteria.andLike("url",sysInnerOperateLogListVO.getUrl());
            }
            if (StringUtils.isNotEmpty(sysInnerOperateLogListVO.getKeyWord())) {
                criteria.andCondition("(operate_detail_json like '%" + sysInnerOperateLogListVO.getKeyWord() + "%'" + "or return_value like '%"
                        + sysInnerOperateLogListVO.getKeyWord() + "%')");
            }
            if (StringUtils.isNotEmpty(sysInnerOperateLogListVO.getOperatorName())) {
                criteria.andLike("operatorName", sysInnerOperateLogListVO.getOperatorName());
            }
            if (StringUtils.isNotEmpty(sysInnerOperateLogListVO.getCreateTimeStart()) && StringUtils.isNotEmpty(sysInnerOperateLogListVO.getCreateTimeEnd())) {
                criteria.andBetween("createTime", sysInnerOperateLogListVO.getCreateTimeStart(), sysInnerOperateLogListVO.getCreateTimeEnd());
            }
            example.setOrderByClause("create_time desc");
            List<SysInnerOperateLog> sysInnerOperateLogList = sysInnerOperateLogMapper.selectByExample(example);
            PageInfo<SysInnerOperateLog> sysInnerOperateLogPageInfo = new PageInfo<>(sysInnerOperateLogList);
            return sysInnerOperateLogPageInfo;

    }

    @Override
    public void saveOpearte (SysInnerOperateLog operateLog) {
        sysInnerOperateLogMapper.insertSelective(operateLog);
    }

    @Override
    public List<SysInnerOperateLogMenu> getSysInnerOperateLogMenus() {
        String cacheKey="getSysInnerOperateLogMenus";
        return new CacheKeyLock(cachesKeyService, SysCacheTimeDMO.CACHETIMEOUT_30M) {
            @Override
            protected Object doGetList(BaseCacheEnum baseCacheEnum, String s) {
                return sysInnerOperateLogMenuMapper.selectAll();
            }//缓存1tian
        }.getCache(SysbaseCacheEnum.SYSINNEROPERATE_LOGMENUS, cacheKey);

    }


}
