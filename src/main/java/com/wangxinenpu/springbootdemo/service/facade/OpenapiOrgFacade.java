 
package com.wangxinenpu.springbootdemo.service.facade;


import com.github.pagehelper.PageInfo;
import com.wangxinenpu.springbootdemo.dataobject.dto.GetSelfMachineDTO;
import com.wangxinenpu.springbootdemo.dataobject.dto.sitematters.SelfMachineOrgDTO;
import com.wangxinenpu.springbootdemo.dataobject.po.OpenapiOrg;
import com.wangxinenpu.springbootdemo.dataobject.vo.OpenapiApp.ResetAppSecretVO;
import com.wangxinenpu.springbootdemo.dataobject.vo.OpenapiOrg.*;
import com.wangxinenpu.springbootdemo.dataobject.vo.OpenapiSelfmachineRequest.OpenapiSelfmachineRequestSaveVO;


import java.util.List;


public interface OpenapiOrgFacade {

    PageInfo<OpenapiOrgShowVO> getOpenapiOrgList(OpenapiOrgListVO listVO, Long userId);

    OpenapiOrg getOpenapiOrgDetail(OpenapiOrgDetailVO detailVO);

    Integer saveOpenapiOrg(OpenapiOrgSaveVO saveVO, Long userId, String userName) throws Exception;

    Integer deleteOpenapiOrg(OpenapiOrgDeleteVO deleteVO);

    OpenapiOrg resetAppSecret(ResetAppSecretVO resetAppSecretVO);

    PageInfo<SelfMachineOrgDTO> getSelfMachine(GetSelfMachineDTO getSelfMachineDTO);

    OpenapiOrg checkCertificate(String certificate, OpenapiSelfmachineRequestSaveVO openapiSelfmachineRequestSaveVO);

    /**
     *机构下一共有多少自助机
     * @param orgCode
     * @return
     */
    Integer getSelfMachineCountByOrgCode(String orgCode);

    /**
     *根据机器码 获取机构信息
     * @return
     */
    OpenapiOrg getOrgByMachineCode(String machineCode);

    Integer auditOpenapiOrg(OpenapiOrgAuditVO openapiOrgDeleteVO, String auditName);

    Integer auditBackOpenapiOrg(OpenapiOrgAuditVO openapiOrgAuditVO, String auditName);

    /**
     * 查询操作日志id不为空的数据
     * @return
     */
    List<OpenapiOrg> getOpenapiOrgListByOpenso();

    Integer saveCheckConfigs(OpenapiOrgSaveVO saveVO, Long userId, String userName) throws Exception;

    void importExcels(List<OpenapiSelfmachineRequestSaveVO> openapiSelfmachineRequestSaveVOS, OpenapiOrg openapiOrg);
}

 
