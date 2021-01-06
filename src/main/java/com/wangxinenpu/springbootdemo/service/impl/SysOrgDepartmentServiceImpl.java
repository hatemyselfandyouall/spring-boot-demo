package com.wangxinenpu.springbootdemo.service.impl;

import com.wangxinenpu.springbootdemo.service.facade.SysOrgDepartmentFacade;
import com.wangxinenpu.springbootdemo.dataobject.po.SysOrgDepartment;
import com.wangxinenpu.springbootdemo.dataobject.vo.SysOrgDepartment.SysOrgDepartmentDeleteVO;
import com.wangxinenpu.springbootdemo.dataobject.vo.SysOrgDepartment.SysOrgDepartmentDetailVO;
import com.wangxinenpu.springbootdemo.dataobject.vo.SysOrgDepartment.SysOrgDepartmentListVO;
import com.wangxinenpu.springbootdemo.dataobject.vo.SysOrgDepartment.SysOrgDepartmentSaveVO;
import com.wangxinenpu.springbootdemo.dao.mapper.SysOrgDepartmentMapper;
import com.wangxinenpu.springbootdemo.dao.mapper.SysUserMapper;
import com.wangxinenpu.springbootdemo.service.service.SysUserService;
import com.wangxinenpu.springbootdemo.dataobject.po.SysUser;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import star.util.BeanUtils;
import star.vo.result.ResultVo;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Service
public class SysOrgDepartmentServiceImpl implements SysOrgDepartmentFacade {

    @Autowired
    SysOrgDepartmentMapper sysOrgDepartmentMapper;
    @Autowired
    SysUserMapper sysUserMapper;
    @Autowired
    private SysUserService sysUserService;
    @Override
    public List<SysOrgDepartment> getSysOrgDepartmentList(SysOrgDepartmentListVO sysOrgDepartmentListVO) {
        if (sysOrgDepartmentListVO==null) {
            return null;
        }
       // PageHelper.startPage(sysOrgDepartmentListVO.getPageNum().intValue(),sysOrgDepartmentListVO.getPageSize().intValue());
        SysOrgDepartment exampleObeject=new SysOrgDepartment();
        exampleObeject.setOrgId(sysOrgDepartmentListVO.getOrgId());
        exampleObeject.setName(sysOrgDepartmentListVO.getName());
        exampleObeject.setUuid(sysOrgDepartmentListVO.getUuid());
        List<SysOrgDepartment> sysOrgDepartmentList=sysOrgDepartmentMapper.select(exampleObeject);
        //PageInfo<SysOrgDepartment> sysOrgDepartmentPageInfo=new PageInfo<>(sysOrgDepartmentList);
        return sysOrgDepartmentList;
    }

    @Override
    public SysOrgDepartment getSysOrgDepartmentDetail(SysOrgDepartmentDetailVO sysOrgDepartmentDetailVO) {
        if (sysOrgDepartmentDetailVO==null||sysOrgDepartmentDetailVO.getId()==null) {
            return null;
        };
        SysOrgDepartment sysOrgDepartment=sysOrgDepartmentMapper.selectByPrimaryKey(sysOrgDepartmentDetailVO.getId());
        return sysOrgDepartment;
    }

    @Override
    public ResultVo saveSysOrgDepartment(SysOrgDepartmentSaveVO sysOrgDepartmentSaveVO) {
        ResultVo resultVo=new ResultVo();
        if (sysOrgDepartmentSaveVO==null){
            resultVo.setResultDes("参数不能为空！");
            resultVo.setSuccess(false);
            return resultVo;
        }
        SysOrgDepartment sysOrgDepartment=new SysOrgDepartment();
        BeanUtils.copyProperties(sysOrgDepartment,sysOrgDepartmentSaveVO);
        SysOrgDepartment exampleObeject=new SysOrgDepartment();
        exampleObeject.setOrgId(sysOrgDepartmentSaveVO.getOrgId());
        exampleObeject.setName(sysOrgDepartmentSaveVO.getName());
        List<SysOrgDepartment> sysOrgDepartmentList=sysOrgDepartmentMapper.select(exampleObeject);
        if (sysOrgDepartment.getId()==null){
            if(sysOrgDepartmentList != null && sysOrgDepartmentList.size()>0){
                resultVo.setResultDes("科室名称重复，请修改！");
                resultVo.setSuccess(false);
                return resultVo;
            }
            sysOrgDepartment.setCreateTime(new Date());
            sysOrgDepartment.setModifyTime(new Date());
            sysOrgDepartmentMapper.insertSelective(sysOrgDepartment);
            resultVo.setResultDes("保存成功！");
            resultVo.setSuccess(true);
        }else {
            if(sysOrgDepartmentList != null && sysOrgDepartmentList.size()>0 && !sysOrgDepartmentList.get(0).getId().equals(sysOrgDepartmentSaveVO.getId())){
                resultVo.setResultDes("科室名称重复，请修改！");
                resultVo.setSuccess(false);
                return resultVo;
            }
            sysOrgDepartment.setModifyTime(new Date());
            Example example=new Example(SysOrgDepartment.class);
            example.createCriteria().andEqualTo("id",sysOrgDepartment.getId());
            sysOrgDepartmentMapper.updateByExampleSelective(sysOrgDepartment,example);
            resultVo.setResultDes("修改成功！");
            resultVo.setSuccess(true);
        }
        return resultVo;
    }

    @Override
    public ResultVo deleteSysOrgDepartment(SysOrgDepartmentDeleteVO sysOrgDepartmentDeleteVO) {
        ResultVo resultVo=new ResultVo();
        if (sysOrgDepartmentDeleteVO==null||sysOrgDepartmentDeleteVO.getId()==null||sysOrgDepartmentDeleteVO.getOrgId() == null){
            resultVo.setResultDes("参数不能为空");
            resultVo.setSuccess(false);
        }
        List<SysUser> sysUsers = sysUserMapper.queryUserListByDeparment(sysOrgDepartmentDeleteVO.getId()+"",null);
        if(sysUsers.size()>0){
            resultVo.setResultDes("科室使用中，不能删除!");
            resultVo.setSuccess(false);
        }else{
            if(StringUtils.isNotEmpty(sysOrgDepartmentDeleteVO.getUuid())){
                SysOrgDepartment department = new SysOrgDepartment();
                department.setUuid(sysOrgDepartmentDeleteVO.getUuid());
                sysOrgDepartmentMapper.delete(department);
                resultVo.setCode("0");
                resultVo.setSuccess(true);
                resultVo.setResultDes("根据uuid删除成功!");
                return resultVo;
            }else {
                int fla = sysOrgDepartmentMapper.deleteByPrimaryKey(sysOrgDepartmentDeleteVO.getId());
                resultVo.setCode("0");
                resultVo.setSuccess(true);
                resultVo.setResultDes("删除成功!");
                return resultVo;
            }
        }
        return resultVo;
    }

}
