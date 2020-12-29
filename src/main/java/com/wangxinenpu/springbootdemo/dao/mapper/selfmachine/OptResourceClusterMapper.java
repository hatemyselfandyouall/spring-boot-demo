package com.wangxinenpu.springbootdemo.dao.mapper.selfmachine;

import com.wangxinenpu.springbootdemo.dataobject.po.OptResourceCluster;
import com.wangxinenpu.springbootdemo.dataobject.po.OptResourceClusterExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.HashMap;
import java.util.List;

public interface OptResourceClusterMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table opt_resource_cluster
     *
     * @mbggenerated
     */
    int countByExample(OptResourceClusterExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table opt_resource_cluster
     *
     * @mbggenerated
     */
    int deleteByExample(OptResourceClusterExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table opt_resource_cluster
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table opt_resource_cluster
     *
     * @mbggenerated
     */
    int insert(OptResourceCluster record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table opt_resource_cluster
     *
     * @mbggenerated
     */
    int insertSelective(OptResourceCluster record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table opt_resource_cluster
     *
     * @mbggenerated
     */
    List<OptResourceCluster> selectByExample(OptResourceClusterExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table opt_resource_cluster
     *
     * @mbggenerated
     */
    OptResourceCluster selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table opt_resource_cluster
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") OptResourceCluster record, @Param("example") OptResourceClusterExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table opt_resource_cluster
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") OptResourceCluster record, @Param("example") OptResourceClusterExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table opt_resource_cluster
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(OptResourceCluster record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table opt_resource_cluster
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(OptResourceCluster record);

    /**
     * 搜索系统用户表列表，带分页
     *
     * @haoxz11MyBatis
     */
    List<OptResourceCluster> getListByWhere(HashMap<String, Object> searchMap, RowBounds rowBounds);


    /**
     * 搜索系统用户表列表
     *
     */
    List<OptResourceCluster> getListByWhere(HashMap<String, Object> searchMap);

    /**
     * 得到搜索系统用户表的记录数量
     *
     */
    int getCountByWhere(HashMap<String, Object> searchMap);

}