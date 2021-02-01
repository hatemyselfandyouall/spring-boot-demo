 
package com.wangxinenpu.springbootdemo.dao.mapper;

import com.wangxinenpu.springbootdemo.dataobject.po.SysOrgDepartment;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.HashMap;
import java.util.List;


public interface SysOrgDepartmentMapper extends Mapper<SysOrgDepartment>{

    /**
     * 根据机构和科室获取记录
     *
     * @hucheng
     */
    Long findByWhere(HashMap<String, Object> searchMap);

    Integer insertDepartmentReturnId(SysOrgDepartment sysOrgDepartment);

    /**
     * 根据多个科室id科室列表
     * @param departmentIds
     * @return
     */
     List<SysOrgDepartment> queryDepartmentListByDepartmentIds(@Param("departmentIds") String departmentIds);
}
