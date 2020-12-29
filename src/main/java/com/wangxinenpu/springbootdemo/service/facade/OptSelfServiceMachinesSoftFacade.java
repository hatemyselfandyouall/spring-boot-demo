package com.wangxinenpu.springbootdemo.service.facade;



import com.wangxinenpu.springbootdemo.dataobject.vo.OptSelfServiceMachinesSoftVo;

import java.util.HashMap;
import java.util.List;

/**
 * 自助机服务接口
 * 
 * @Author:ll
 * @since：2019年11月20日 下午1:41:37
 * @return:
 */
public interface OptSelfServiceMachinesSoftFacade {
	/**
	 * 新增自助机po信息
	 *
	 * @param po
	 * @return
	 */
	public Long addOptSelfServiceMachinesSoft(OptSelfServiceMachinesSoftVo po);


	/**
	 * 获取po信息
	 *
	 * @param id
	 * @return
	 */
	public OptSelfServiceMachinesSoftVo getByPrimaryKey(Long id);

	/**
	 * 更新po信息
	 *
	 * @param po
	 * @return
	 */
	public int updatePo(OptSelfServiceMachinesSoftVo po);

	/**
	 * 根据参数查询 获取自助机列表 带分页
	 *
	 * @param searchMap
	 * @param start
	 * @param size
	 * @return
	 */
	public List<OptSelfServiceMachinesSoftVo> getListByWhere(HashMap<String, Object> searchMap, int start, int size);

    /**
     * 保存和修改自助机
     * @param OptProject
     */
	int saveOptSelfServiceMachinesSoft(OptSelfServiceMachinesSoftVo OptProject);

	List<OptSelfServiceMachinesSoftVo> getListByWhere(HashMap<String, Object> searchMap);

	int getCountByWhere(HashMap<String, Object> searchMap);

    /**
     * 根据角色查询关联自助机
     * @param id
     * @return
     */
	OptSelfServiceMachinesSoftVo findProjectById(String id);

	/**
	 * 根据角色查询关联自助机
	 * @param id
	 * @return
	 */
	Integer deleteProjectById(Long id);


}
