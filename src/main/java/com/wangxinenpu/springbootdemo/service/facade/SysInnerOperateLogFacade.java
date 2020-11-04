 
package com.wangxinenpu.springbootdemo.service.facade;

import com.github.pagehelper.PageInfo;
import com.wangxinenpu.springbootdemo.dataobject.po.SysInnerOperateLog;
import com.wangxinenpu.springbootdemo.dataobject.po.SysInnerOperateLogMenu;
import com.wangxinenpu.springbootdemo.dataobject.vo.sysInnerOperateLog.SysInnerOperateLogListVO;


import java.util.List;


public interface SysInnerOperateLogFacade{

	PageInfo<SysInnerOperateLog> getSysInnerOperateLogList(SysInnerOperateLogListVO listVO);


    void saveOpearte(SysInnerOperateLog operateLog);

    List<SysInnerOperateLogMenu> getSysInnerOperateLogMenus();
}

 
