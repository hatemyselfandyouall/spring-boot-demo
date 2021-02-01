package com.wangxinenpu.springbootdemo.service.facade;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageInfo;
import com.wangxinenpu.springbootdemo.dataobject.po.*;
import com.wangxinenpu.springbootdemo.dataobject.vo.*;
import com.wangxinenpu.springbootdemo.dataobject.vo.systemManage.SystemAreaTree;
import com.wangxinenpu.springbootdemo.dataobject.vo.systemManage.SystemInsertVo;
import com.wangxinenpu.springbootdemo.dataobject.vo.systemManage.SystemManageListVo;
import com.wangxinenpu.springbootdemo.dataobject.vo.systemManage.SystemManageQueryVo;

import java.util.List;

public interface SystemManageFacade {

    /*
    *
    * 查询系统展示分页列表
    */
    PageInfo<SystemManageListVo> selectByMap(SystemManageQueryVo systemManageQueryVo);

    int deleteById(String id);

    int updateAndInsert(SystemInsertVo systemInsertVo);

    SystemManageListVo selectById(String id);

    SystemManageListVo selectBychannelCode(String channelCode);

    /*
    根据统筹区查询系统
    * */
    JSONArray selectByCode(String areaId);

    /*
  根据统筹区查询系统
  * */
    List<SystemAreaTree> selectByTree(String areaId);



    /**
     * 根据机构id查询系统
     * @param orgId
     *
     * @return List<SysOrgDTO>
     */
     List<SystemManageListVo> getListByOrgId(Long orgId, String channelCode);


     int insert(SystemIconDTO systemIcon);
     boolean checkName (String name);
     List<SystemIconDTO> selectAll();
}
