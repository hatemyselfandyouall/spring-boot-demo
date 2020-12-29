package com.wangxinenpu.springbootdemo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.wangxinenpu.springbootdemo.dao.mapper.selfmachine.*;
import com.wangxinenpu.springbootdemo.dataobject.dto.OptSelfServiceMachinesSoftModelRefDTO;
import com.wangxinenpu.springbootdemo.dataobject.dto.OptSelfServiceMachinesSoftOrgRefDTO;
import com.wangxinenpu.springbootdemo.dataobject.dto.OptSelfServiceMachinesSoftRegionRefDTO;
import com.wangxinenpu.springbootdemo.dataobject.po.*;
import com.wangxinenpu.springbootdemo.dataobject.vo.OptSelfServiceMachinesSoftVo;
import com.wangxinenpu.springbootdemo.service.facade.OptSelfServiceMachinesSoftFacade;
import com.wangxinenpu.springbootdemo.util.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import star.bizbase.exception.BizRuleException;
import star.vo.BaseVo;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 自助机服务实现类
 *
 * @Author:ll
 * @since：2019年11月20日 下午1:46:43
 * @return:
 */
@Service
public class OptSelfServiceMachinesSoftServiceImpl implements OptSelfServiceMachinesSoftFacade {

    private static Logger logger = LoggerFactory.getLogger(OptSelfServiceMachinesSoftServiceImpl.class);
    @Resource
    private OptSelfServiceMachinesSoftMapper optSelfServiceMachinesSoftMapper;
    @Resource
    private OptSelfServiceMachinesSoftModelRefMapper optSoftModelRefMapper;
    @Resource
    private OptSelfServiceMachinesSoftOrgRefMapper optSoftOrgRefMapper;
    @Resource
    private OptSelfServiceMachinesSoftRegionRefMapper optSoftRegionRefMapper;

    @Resource
    private OptResourceClusterMapper optClusterMapper;

    /**
     * 新增自助机vo信息
     *
     * @param vo
     * @return
     */
    @Override
    public Long addOptSelfServiceMachinesSoft(OptSelfServiceMachinesSoftVo vo) {
        if (vo == null) {
            logger.info("OptSelfServiceMachinesSoftService.addOptSelfServiceMachinesSoft OptSelfServiceMachinesSoftVo={}", vo.toString());
            return 0L;
        }
        vo.setCreateTime(new Date());
        vo.setModifyTime(new Date());
//        vo.setIsValid(Short.parseShort("1"));
        OptSelfServiceMachinesSoft pro = new OptSelfServiceMachinesSoft();
        BeanUtils.copy(vo,pro);
       //新增自助机客户端
        optSelfServiceMachinesSoftMapper.insertSelective(pro);
        //添加机型关系
        if(vo.getSoftModelList().size()>0) {
            OptSelfServiceMachinesSoftModelRefExample exp = new OptSelfServiceMachinesSoftModelRefExample();
            exp.createCriteria().andSelfServiceIdEqualTo(pro.getId());
            optSoftModelRefMapper.deleteByExample(exp);
        }
        if(vo.getSoftModelList().size()>0){
            List<OptSelfServiceMachinesSoftModelRefDTO> resList =vo.getSoftModelList();
            for (OptSelfServiceMachinesSoftModelRefDTO r: resList) {
                OptSelfServiceMachinesSoftModelRef Ref=new OptSelfServiceMachinesSoftModelRef();
                Ref.setSelfServiceId(pro.getId());
                Ref.setModelId(r.getModelId());
                Ref.setCreateTime(new Date());
                Ref.setModifyTime(new Date());
                optSoftModelRefMapper.insert(Ref);
            }
        }

        //添加机构关系
        if(vo.getSoftOrgRefList().size()>0) {
            OptSelfServiceMachinesSoftOrgRefExample exp = new OptSelfServiceMachinesSoftOrgRefExample();
            exp.createCriteria().andSelfServiceIdEqualTo(pro.getId());
            optSoftOrgRefMapper.deleteByExample(exp);
        }
        if(vo.getSoftOrgRefList().size()>0){
            List<OptSelfServiceMachinesSoftOrgRefDTO> resList =vo.getSoftOrgRefList();
            for (OptSelfServiceMachinesSoftOrgRefDTO r: resList) {
                OptSelfServiceMachinesSoftOrgRef Ref=new OptSelfServiceMachinesSoftOrgRef();
                Ref.setSelfServiceId(pro.getId());
                Ref.setOrgId(r.getOrgId());
                Ref.setCreateTime(new Date());
                Ref.setModifyTime(new Date());
                optSoftOrgRefMapper.insert(Ref);
            }
        }

        //添加地区关系
        if(vo.getSoftRegionRefList().size()>0) {
            OptSelfServiceMachinesSoftRegionRefExample exp = new OptSelfServiceMachinesSoftRegionRefExample();
            exp.createCriteria().andSelfServiceIdEqualTo(pro.getId());
            optSoftRegionRefMapper.deleteByExample(exp);
        }
        if(vo.getSoftRegionRefList().size()>0){
            List<OptSelfServiceMachinesSoftRegionRefDTO> resList =vo.getSoftRegionRefList();
            for (OptSelfServiceMachinesSoftRegionRefDTO r: resList) {
                OptSelfServiceMachinesSoftRegionRef Ref=new OptSelfServiceMachinesSoftRegionRef();
                Ref.setSelfServiceId(pro.getId());
                Ref.setRegionId(r.getRegionId());
                Ref.setCreateTime(new Date());
                Ref.setModifyTime(new Date());
                optSoftRegionRefMapper.insert(Ref);
            }
        }
        return pro.getId();
    }

    /**
     * 根据主键得到系统自助机表记录
     *
     * @param id
     * @return
     */
    @Override
    public OptSelfServiceMachinesSoftVo getByPrimaryKey(Long id) {
        if (id == null) {
            logger.info("OptSelfServiceMachinesSoftService.selectByPrimaryKey error:id={}", id);
            return null;
        }
        OptSelfServiceMachinesSoft po = optSelfServiceMachinesSoftMapper.selectByPrimaryKey(id);
        if (po == null) return null;
        OptSelfServiceMachinesSoftVo vo= po.copyTo(OptSelfServiceMachinesSoftVo.class);

        OptSelfServiceMachinesSoftModelRefExample exp = new OptSelfServiceMachinesSoftModelRefExample();
        exp.createCriteria().andSelfServiceIdEqualTo(vo.getId());
        List<OptSelfServiceMachinesSoftModelRef> softModelList = optSoftModelRefMapper.selectByExample(exp);

        OptSelfServiceMachinesSoftOrgRefExample expOrg = new OptSelfServiceMachinesSoftOrgRefExample();
        expOrg.createCriteria().andSelfServiceIdEqualTo(vo.getId());
        List<OptSelfServiceMachinesSoftOrgRef> softOrgRefList = optSoftOrgRefMapper.selectByExample(expOrg);

        OptSelfServiceMachinesSoftRegionRefExample expRegion = new OptSelfServiceMachinesSoftRegionRefExample();
        expRegion.createCriteria().andSelfServiceIdEqualTo(vo.getId());
        List<OptSelfServiceMachinesSoftRegionRef> softRegionRefList = optSoftRegionRefMapper.selectByExample(expRegion);
        String reStr= "";
        for (OptSelfServiceMachinesSoftModelRef ref: softModelList) {
//            OptResource res =optResourceMapper.selectByPrimaryKey(ref.getResourceId());
//
//            resourceList.add(res);

            reStr = reStr + ref.getModelId() + ",";

        }
        if(reStr.indexOf(",")>-1) {
            reStr = reStr.substring(0, reStr.length() - 1);
        }
        vo.setSoftModelList(BaseVo.copyListTo(softModelList, OptSelfServiceMachinesSoftModelRefDTO.class));;
        vo.setSoftOrgRefList(BaseVo.copyListTo(softOrgRefList, OptSelfServiceMachinesSoftOrgRefDTO.class));
        vo.setSoftRegionRefList(BaseVo.copyListTo(softRegionRefList, OptSelfServiceMachinesSoftRegionRefDTO.class));
        vo.setOptSelfServiceMachinesSoftModelRefStr(reStr);
        return vo;
    }

    /**
     * 更新po信息
     *
     * @param vo
     * @return
     */
    @Override
    public int updatePo(OptSelfServiceMachinesSoftVo vo) {
        if (vo == null || vo.getId() == null) {
            logger.info("OptSelfServiceMachinesSoftService.updatepo OptSelfServiceMachinesSoftVo={}", vo.toString());
            return 0;
        }
        vo.setModifyTime(new Date());
        OptSelfServiceMachinesSoft optSelfServiceMachinesSoft=new OptSelfServiceMachinesSoft();
        BeanUtils.copy(vo,optSelfServiceMachinesSoft);
        int n =optSelfServiceMachinesSoftMapper.updateByPrimaryKeySelective(optSelfServiceMachinesSoft);//object转换 如果数量不多，尽量自己采用assember方法进行转换处理。

        //添加机型关系
        if(vo.getSoftModelList().size()>0) {
            OptSelfServiceMachinesSoftModelRefExample exp = new OptSelfServiceMachinesSoftModelRefExample();
            exp.createCriteria().andSelfServiceIdEqualTo(vo.getId());
            optSoftModelRefMapper.deleteByExample(exp);
        }
        if(vo.getSoftModelList().size()>0){
            List<OptSelfServiceMachinesSoftModelRefDTO> resList =vo.getSoftModelList();
            for (OptSelfServiceMachinesSoftModelRefDTO r: resList) {
                OptSelfServiceMachinesSoftModelRef Ref=new OptSelfServiceMachinesSoftModelRef();
                Ref.setSelfServiceId(vo.getId());
                Ref.setModelId(r.getModelId());
                Ref.setCreateTime(new Date());
                Ref.setModifyTime(new Date());
                optSoftModelRefMapper.insert(Ref);
            }
        }

        //添加机构关系
        if(vo.getSoftOrgRefList().size()>0) {
            OptSelfServiceMachinesSoftOrgRefExample exp = new OptSelfServiceMachinesSoftOrgRefExample();
            exp.createCriteria().andSelfServiceIdEqualTo(vo.getId());
            optSoftOrgRefMapper.deleteByExample(exp);
        }
        if(vo.getSoftOrgRefList().size()>0){
            List<OptSelfServiceMachinesSoftOrgRefDTO> resList =vo.getSoftOrgRefList();
            for (OptSelfServiceMachinesSoftOrgRefDTO r: resList) {
                OptSelfServiceMachinesSoftOrgRef Ref=new OptSelfServiceMachinesSoftOrgRef();
                Ref.setSelfServiceId(vo.getId());
                Ref.setOrgId(r.getOrgId());
                Ref.setCreateTime(new Date());
                Ref.setModifyTime(new Date());
                optSoftOrgRefMapper.insert(Ref);
            }
        }

        //添加地区关系
        if(vo.getSoftRegionRefList().size()>0) {
            OptSelfServiceMachinesSoftRegionRefExample exp = new OptSelfServiceMachinesSoftRegionRefExample();
            exp.createCriteria().andSelfServiceIdEqualTo(vo.getId());
            optSoftRegionRefMapper.deleteByExample(exp);
        }
        if(vo.getSoftRegionRefList().size()>0){
            List<OptSelfServiceMachinesSoftRegionRefDTO> resList =vo.getSoftRegionRefList();
            for (OptSelfServiceMachinesSoftRegionRefDTO r: resList) {
                OptSelfServiceMachinesSoftRegionRef Ref=new OptSelfServiceMachinesSoftRegionRef();
                Ref.setSelfServiceId(vo.getId());
                Ref.setRegionId(r.getRegionId());
                Ref.setCreateTime(new Date());
                Ref.setModifyTime(new Date());
                optSoftRegionRefMapper.insert(Ref);
            }
        }
        return n;
    }


    /**
     * 根据参数查询 获取自助机列表  带分页
     *
     * @param searchMap
     * @param start
     * @param size
     * @return
     */
    @Override
    public List<OptSelfServiceMachinesSoftVo> getListByWhere(HashMap<String, Object> searchMap, int start, int size) {
        if (searchMap == null || searchMap.isEmpty() || size < 1) {
            logger.info("OptSelfServiceMachinesSoftService.getListByWhere searchMap={}，start={},size={}", searchMap, start, size);
            return Collections.emptyList();
        }
        PageHelper.startPage(start,size);
        List<OptSelfServiceMachinesSoft> OptSelfServiceMachinesSoftVoList = optSelfServiceMachinesSoftMapper.getListByWhere(searchMap);
        PageInfo<OptSelfServiceMachinesSoft> optProjectServiceVoPage=new PageInfo<>(OptSelfServiceMachinesSoftVoList);
        OptSelfServiceMachinesSoftVoList = optProjectServiceVoPage.getList();
//        if (CollectionUtils.isEmpty(OptSelfServiceMachinesSoftVoList)) return Collections.emptyList();
//        return BaseVo.copyListTo(OptSelfServiceMachinesSoftVoList, OptSelfServiceMachinesSoftVo.class);//列表转换 如果数量不多，尽量自己采用assember方法进行转换处理。
        if (CollectionUtils.isEmpty(OptSelfServiceMachinesSoftVoList)) return Collections.emptyList();
        List<OptSelfServiceMachinesSoftVo> voList= OptSelfServiceMachinesSoftVoList.stream().map(i->{
            OptSelfServiceMachinesSoftVo optSelfServiceMachinesSoftVo=new OptSelfServiceMachinesSoftVo();
            BeanUtils.copy(i,optProjectServiceVoPage);
            return optSelfServiceMachinesSoftVo;
        }).collect(Collectors.toList());//列表转换 如果数量不多，尽量自己采用assember方法进行转换处理。
        List<OptSelfServiceMachinesSoftVo> OptSelfServiceMachinesSoftListNew =new ArrayList<>();

        for (OptSelfServiceMachinesSoftVo  proVo: voList) {

            String softModelRefStr ="";
            if(proVo.getId()!=null){
                OptSelfServiceMachinesSoftModelRefExample exp = new OptSelfServiceMachinesSoftModelRefExample();
                exp.createCriteria().andSelfServiceIdEqualTo(proVo.getId());
                List<OptSelfServiceMachinesSoftModelRef> softModelList = optSoftModelRefMapper.selectByExample(exp);

//                List<OptResourceServerVo>  resourceServerVoList = optResourceService.getResourceClusterByProServiceId(proVo.getId());
                if(softModelList.size()>0) {
                    for (OptSelfServiceMachinesSoftModelRef ref: softModelList) {

                        softModelRefStr = softModelRefStr + ref.getModelId()+",";
                    }
                    if(softModelRefStr.indexOf(",")>-1) {
                        softModelRefStr = softModelRefStr.substring(0, softModelRefStr.length() - 1);
                    }
                    proVo.setOptSelfServiceMachinesSoftModelRefStr(softModelRefStr);
                }
            }
            OptSelfServiceMachinesSoftListNew.add(proVo);
        }

        return OptSelfServiceMachinesSoftListNew;
    }

    /**
     * 根据参数查询自助机列表
     *
     * @param searchMap
     * @return
     */
    @Override
    public List<OptSelfServiceMachinesSoftVo> getListByWhere(HashMap<String, Object> searchMap) {
        if (searchMap == null || searchMap.isEmpty()) {
            logger.info("OptSelfServiceMachinesSoftService.getListByWhere searchMap={}", searchMap);
            return Collections.emptyList();
        }
        List<OptSelfServiceMachinesSoft> OptSelfServiceMachinesSoftList = optSelfServiceMachinesSoftMapper.getListByWhere(searchMap);

        if (CollectionUtils.isEmpty(OptSelfServiceMachinesSoftList)) return Collections.emptyList();
        List<OptSelfServiceMachinesSoftVo> voList=OptSelfServiceMachinesSoftList.stream().map(i->{
            OptSelfServiceMachinesSoftVo optSelfServiceMachinesSoftVo=new OptSelfServiceMachinesSoftVo();
            BeanUtils.copy(i,optSelfServiceMachinesSoftVo);
            return optSelfServiceMachinesSoftVo;
        }).collect(Collectors.toList());//列表转换 如果数量不多，尽量自己采用assember方法进行转换处理。
        List<OptSelfServiceMachinesSoftVo> OptSelfServiceMachinesSoftListNew =new ArrayList<>();
        for (OptSelfServiceMachinesSoftVo  proVo: voList) {
            String softModelRefStr ="";
            if(proVo.getId()!=null){
                OptSelfServiceMachinesSoftModelRefExample exp = new OptSelfServiceMachinesSoftModelRefExample();
                exp.createCriteria().andSelfServiceIdEqualTo(proVo.getId());
                List<OptSelfServiceMachinesSoftModelRef> softModelList = optSoftModelRefMapper.selectByExample(exp);

//                List<OptResourceServerVo>  resourceServerVoList = optResourceService.getResourceClusterByProServiceId(proVo.getId());
                if(softModelList.size()>0) {
                    for (OptSelfServiceMachinesSoftModelRef ref: softModelList) {

                        softModelRefStr = softModelRefStr + ref.getModelId()+",";
                    }
                    if(softModelRefStr.indexOf(",")>-1) {
                        softModelRefStr = softModelRefStr.substring(0, softModelRefStr.length() - 1);
                    }
                    proVo.setOptSelfServiceMachinesSoftModelRefStr(softModelRefStr);
                }
            }
            OptSelfServiceMachinesSoftListNew.add(proVo);
        }

        return OptSelfServiceMachinesSoftListNew;
    }


    /**
     * 根据参数获取系统自助机数量
     *
     * @param searchMap
     * @return
     */
    @Override
    public int getCountByWhere(HashMap<String, Object> searchMap) {
        if (searchMap == null || searchMap.isEmpty()) {
            return 0;
        }
        return optSelfServiceMachinesSoftMapper.getCountByWhere(searchMap);
    }

    /**
     * 自助机保存和修改
     */
    @Override
    @Transactional
    public int saveOptSelfServiceMachinesSoft(OptSelfServiceMachinesSoftVo vo) {
        Long OptSelfServiceMachinesSoftVoId = null;
        int n = 0;
        if (vo.getId() != null) {
            //修改用户
            vo.setModifyTime(new Date());
            n = updatePo(vo);
        } else {

            vo.setCreateTime(new Date());
            vo.setModifyTime(new Date());
            //保存自助机
            OptSelfServiceMachinesSoftVoId = addOptSelfServiceMachinesSoft(vo);
            if (OptSelfServiceMachinesSoftVoId != 0L) {
                n = 1;
            }
        }
        return n;

    }

    @Override
    public OptSelfServiceMachinesSoftVo findProjectById(String id) {
        return getByPrimaryKey(Long.parseLong(id));
    }

    /**
     * 根据角色查询关联自助机
     *
     * @param id
     * @return
     * @throws BizRuleException
     */
    public Integer deleteProjectById(Long id) {
        return optSelfServiceMachinesSoftMapper.deleteByPrimaryKey(id);
    }

}
