package com.wangxinenpu.springbootdemo.service.service;

import com.wangxinenpu.springbootdemo.dataobject.vo.SysOrgDTO;
import com.wangxinenpu.springbootdemo.dao.mapper.SysOrgMapper;
import com.wangxinenpu.springbootdemo.dataobject.po.SysOrg;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import star.vo.BaseVo;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 系统机构service
 * @author Administrator
 *
 */
@Service
public class SysOrgService {
	private static Logger logger = LoggerFactory.getLogger(SysOrgService.class);
	@Resource
	private SysOrgMapper sysOrgMapper;

	/**
	 * 新增机构po信息
	 * @param dto
	 * @return
	 */
	public Long addSysOrg(SysOrgDTO dto){
		if(dto == null) {
			logger.info("sysOrgService.addSysOrg dto={}",dto);
			return 0L;
		}
		SysOrg org = dto.copyTo(SysOrg.class);
		sysOrgMapper.insertSysOrg(org);
		return org.getId();
	}

	/**
	 *  根据主键得到系统机构表记录
	 * @param id
	 * @return
	 */
	public SysOrgDTO getByPrimaryKey(Long id){
		if(id == null || id == 0) {
			logger.info("sysOrgService.getByPrimaryKey error:id={}",id);
			return null;
		}
		SysOrg po = sysOrgMapper.getByPrimaryKey(id);
		if(po==null) return null;
		return po.copyTo(SysOrgDTO.class);
	}

	/**
	 * 更新po信息
	 * @param dto
	 * @return
	 */
	public int updatepo(SysOrgDTO dto){
		if(dto == null || dto.getId() == null || dto.getId() == 0) {
			logger.info("sysOrgService.updatepo dto={}",dto);
			return 0;
		}
		return sysOrgMapper.updateSysOrg(dto.copyTo(SysOrg.class));//object转换 如果数量不多，尽量自己采用assember方法进行转换处理。
	}


	/**
	 * 根据参数查询 获取机构列表  带分页
	 * @param searchMap
	 * @param start
	 * @param size
	 * @return
	 */
	public List<SysOrgDTO> getListByWhere(HashMap<String, Object> searchMap, int start, int size){
		if(searchMap==null || searchMap.isEmpty() || size<1) {
			logger.info("sysOrgService.getListByWhere searchMap={}，start={},size={}",searchMap,start,size);
			return Collections.emptyList();
		}
		List<SysOrg> sysOrgList = sysOrgMapper.getListByWhere(searchMap,new RowBounds(start,size));
		if(CollectionUtils.isEmpty(sysOrgList)) return Collections.emptyList();
		return BaseVo.copyListTo(sysOrgList, SysOrgDTO.class);//列表转换 如果数量不多，尽量自己采用assember方法进行转换处理。
	}

	/**
	 * 根据参数查询机构列表
	 * @param searchMap
	 * @return
	 */
	public List<SysOrgDTO> getListByWhere(HashMap<String, Object> searchMap){
		if(searchMap==null || searchMap.isEmpty()) {
			logger.info("sysOrgService.getListByWhere searchMap={}",searchMap);
			return Collections.emptyList();
		}
		List<SysOrg> sysOrgList = sysOrgMapper.getListByWhere(searchMap);
		if(CollectionUtils.isEmpty(sysOrgList)) return Collections.emptyList();
		return BaseVo.copyListTo(sysOrgList, SysOrgDTO.class);//列表转换 如果数量不多，尽量自己采用assember方法进行转换处理。
	}


	/**
	 * 根据参数获取系统机构数量
	 * @param searchMap
	 * @return
	 */
	public int getCountByWhere(HashMap<String, Object> searchMap){
		if(searchMap==null || searchMap.isEmpty()) {
			return 0;
		}
		return sysOrgMapper.getCountByWhere(searchMap);
	}

	/**
	 * 根据区域ID查询机构列表
	 * @param regionCode
	 * @return
	 */
	public List<SysOrgDTO> findByRegioncode(String regionCode){
		List<SysOrg> sysOrgList = sysOrgMapper.findByRegioncode(regionCode);
		return BaseVo.copyListTo(sysOrgList, SysOrgDTO.class);
	}

	/**
	 * 根据id查询下级机构
	 * @param id
	 * @return
	 */
	public List<SysOrgDTO> findByParentId(Long id){
		List<SysOrg> sysOrgList = sysOrgMapper.findByParentId(id);
		return BaseVo.copyListTo(sysOrgList, SysOrgDTO.class);
	}

	public List<SysOrgDTO> queryOrgListAll(){
		List<SysOrg> sysOrgList = sysOrgMapper.queryOrgListAll();
		return BaseVo.copyListTo(sysOrgList, SysOrgDTO.class);
	}

	public List<SysOrgDTO> queryOrgListArea(HashMap<String, Object> searchMap){
		if(searchMap==null || searchMap.isEmpty()) {
			logger.info("sysOrgService.queryOrgListArea searchMap={}",searchMap);
			return Collections.emptyList();
		}
		List<SysOrg> sysOrgList = sysOrgMapper.queryOrgListArea(searchMap);
		if(CollectionUtils.isEmpty(sysOrgList)) return Collections.emptyList();
		return BaseVo.copyListTo(sysOrgList, SysOrgDTO.class);
	}
	
	public List<SysOrgDTO> queryOrgListShenbenji(HashMap<String, Object> searchMap){
		if(searchMap==null || searchMap.isEmpty()) {
			logger.info("sysOrgService.queryOrgListShenbenji searchMap={}",searchMap);
			return Collections.emptyList();
		}
		List<SysOrg> sysOrgList = sysOrgMapper.queryOrgListShenbenji(searchMap);
		if(CollectionUtils.isEmpty(sysOrgList)) return Collections.emptyList();
		return BaseVo.copyListTo(sysOrgList, SysOrgDTO.class);
	}

	/**
	 * 根据idpath查询下级机构
	 * @param idpath
	 * @return
	 */
	public List<SysOrgDTO> findByIdpath(String idpath){
		List<SysOrg> sysOrgList = sysOrgMapper.findByIdpath(idpath);
		return BaseVo.copyListTo(sysOrgList, SysOrgDTO.class);
	}

	/**
	 * 根据机构名称，机构代码查询是否存在
	 * @param orgName
	 * @param orgenterCode
	 * @return
	 */
	public List<SysOrgDTO> checkOrgNameOrgenterCode(String orgName, String orgenterCode){
		List<SysOrg> sysOrgList = sysOrgMapper.checkOrgNameOrgenterCode(orgName, orgenterCode);
		return BaseVo.copyListTo(sysOrgList, SysOrgDTO.class);
	}

	/**
	 * 根据机构名称查询
	 * @param orgName
	 * @return
	 */
	public SysOrgDTO findByName(String orgName){
		SysOrg po = sysOrgMapper.findByName(orgName);
		if(po==null) return null;
		return po.copyTo(SysOrgDTO.class);
	}

	/**
	 * 根据机构名称模糊查询
	 * @param orgName
	 * @return
	 */
	public List<SysOrgDTO> findByNameLike(String orgName){
		List<SysOrg> orgList= sysOrgMapper.findByNameLike(orgName);

		if(CollectionUtils.isEmpty(orgList)) return Collections.emptyList();
		return BaseVo.copyListTo(orgList, SysOrgDTO.class);
	}

	/**
	 * 根据主键删除
	 * @param id
	 */
	public void deleteByPrimaryKey(Long id){
		sysOrgMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 根据天正一体化id删除机构
	 * @param uuid
	 */
	public void deleteByUUid(String uuid){
		sysOrgMapper.deleteByUUid(uuid);
	}

	/**
	 * 通过行政区和险种获取机构信息
	 * @param areaId
	 * @param insId
	 * @return
	 */
	public SysOrgDTO findByAreaIdAndInsId(String areaId, String insId){
		HashMap<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("areaId", areaId);
		searchMap.put("insId", insId);
		List<SysOrg> sysOrgList = sysOrgMapper.findByAreaIdAndInsId(searchMap);
		SysOrg po = null;
		if(sysOrgList.size()>0){
			po = sysOrgList.get(0);
			return po.copyTo(SysOrgDTO.class);
		}
		return null;

	}

	/**
	 * 获取所有市
	 * @param regionCode
	 * @return
	 */
	public List<SysOrgDTO> findCityByRegionCode(String regionCode){
		List<SysOrg> sysOrgList = sysOrgMapper.findCityByRegionCode(regionCode);
		if(CollectionUtils.isEmpty(sysOrgList)) return Collections.emptyList();
		return BaseVo.copyListTo(sysOrgList, SysOrgDTO.class);
	}

	/**
     * 根据orgIds查询机构树
     * @param orgIds
     * @return
     */
	public List<SysOrgDTO> queryOrgListByOrgIds(String orgIds){
		List<SysOrg> sysOrgList = sysOrgMapper.queryOrgListByOrgIds(orgIds);
		if(CollectionUtils.isEmpty(sysOrgList)) return Collections.emptyList();
		return BaseVo.copyListTo(sysOrgList, SysOrgDTO.class);
	}

	public SysOrgDTO queryOrgNameByOrgCode(String orgCode){
		SysOrg sysOrg = sysOrgMapper.queryOrgNameByOrgCode(orgCode);
		if (sysOrg==null) return null;
		return sysOrg.copyTo(SysOrgDTO.class);
	}
    public List<SysOrgDTO> getListWithParent(Long orgId) {
		List<SysOrg> sysOrgs=IteraGetList(orgId);
		return BaseVo.copyListTo(sysOrgs, SysOrgDTO.class);
    }

    private List<SysOrg> IteraGetList(Long orgId){
		List<SysOrg> sysOrgs=new ArrayList<>();
		SysOrg org=sysOrgMapper.getByPrimaryKey(orgId);
		if (org==null){
			return null;
		}else {
			sysOrgs.add(org);
			List<SysOrg> parents=IteraGetList(org.getParentId());
			if (!CollectionUtils.isEmpty(parents)){
			sysOrgs.addAll(parents);
			}
			return sysOrgs.stream().filter(i->i!=null).collect(Collectors.toList());
		}
	}

	public List<SysOrgDTO> queryOrgListAllForArea(String areaId) {
		List<SysOrg> sysOrg=sysOrgMapper.getByRegionCode(areaId);
		List<SysOrg> sysOrgs=sysOrg.stream().map(i->sysOrgMapper.findCityByParentId(i.getId())).reduce(new ArrayList<>(),(a, b)->{
			a.addAll(b);
			return a;
		});
		List<SysOrg> orgs=sysOrgs.parallelStream().filter(i->i.getType()==1).collect(Collectors.toList());
		List<SysOrg> areas=sysOrgs.parallelStream().filter(i->i.getType()==0).collect(Collectors.toList());
		if (!CollectionUtils.isEmpty(areas)){
			iteratorGetOrgs(orgs,areas);
		}
		return BaseVo.copyListTo(orgs, SysOrgDTO.class);
	}

	private void iteratorGetOrgs(List<SysOrg> orgs, List<SysOrg> areas) {
		for (SysOrg i:areas){
			List<SysOrg> sysOrgs=sysOrgMapper.findCityByParentId(i.getId());
			if (CollectionUtils.isEmpty(sysOrgs)){
				return;
			}
			List<SysOrg> subArea=sysOrgs.parallelStream().filter(j->j.getType()==0).collect(Collectors.toList());
			orgs.addAll(sysOrgs.parallelStream().filter(j->j.getType()==1).collect(Collectors.toList()));
			if (!CollectionUtils.isEmpty(subArea)){
				iteratorGetOrgs(orgs,subArea);
			}
		}
	}

    public List<SysOrgDTO> synchOrgList(String modifyTime) {
        int count = sysOrgMapper.getCountByMaxOrgId( modifyTime);
        if (count == 0) {
            return null;
        }
        List<SysOrg> sysOrgList = sysOrgMapper.synchOrgListByMaxOrgId(modifyTime);
        return BaseVo.copyListTo(sysOrgList, SysOrgDTO.class);
    }


	public SysOrgDTO getOrgByRegionCode(String areaId){
		if(StringUtils.isEmpty(areaId)){
			logger.info("sysOrgService.getOrgByRegionCode error:areaId={}",areaId);
			return null;
		}
		List<SysOrg> sysOrg=sysOrgMapper.getByRegionCode(areaId);
		if(CollectionUtils.isEmpty(sysOrg)) return null;
		return sysOrg.get(0).copyTo(SysOrgDTO.class);
	}

	public List<SysOrgDTO> getlistByOrgUuids(String startTime, String endTime){
		return sysOrgMapper.getlistByOrgUuids(startTime, endTime);
	}


}
