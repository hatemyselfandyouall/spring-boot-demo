package com.wangxinenpu.springbootdemo.controller.user;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.io.ByteStreams;
import com.wangxinenpu.springbootdemo.config.component.CheckComponent;
import com.wangxinenpu.springbootdemo.config.component.CheckRedisComponent;
import com.wangxinenpu.springbootdemo.config.component.LoginComponent;
import com.wangxinenpu.springbootdemo.dataobject.vo.*;

import com.wangxinenpu.springbootdemo.dataobject.vo.orgSystem.SysOrgSystemDto;
import com.wangxinenpu.springbootdemo.service.facade.SysFunctionFacade;
import com.wangxinenpu.springbootdemo.service.facade.SysOrgFacade;
import com.wangxinenpu.springbootdemo.service.facade.SysRoleFacade;
import com.wangxinenpu.springbootdemo.service.facade.*;
import com.wangxinenpu.springbootdemo.util.ExcelUtil;
import com.wangxinenpu.springbootdemo.util.JavaBeanUtils;
import com.wangxinenpu.springbootdemo.util.TreeUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import star.bizbase.exception.BizRuleException;
import star.bizbase.vo.result.Results;
import star.util.ExceptionUtil;
import star.vo.result.ResultVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.file.Paths;
import java.util.*;

/**
 * 系统角色 管理
 *
 * @author Administrator
 */
@Controller
@RequestMapping("sys/roleV2")
@SuppressWarnings("all")
public class SysRoleV2Controller  {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private SysRoleFacade sysRoleFacade;
    @Autowired
    private SysFunctionFacade sysFunctionFacade;
    @Autowired
    private LoginComponent loginComonent;
    @Autowired
    private SysUserFacade sysUserFacade;
    @Autowired
    private CheckComponent checkComponent;
    @Autowired
    private CheckRedisComponent checkRedisComponent;
    @Autowired
    private SysOrgFacade sysOrgFacade;
    @Autowired
    private SysOrgSystemFacade sysOrgSystemFacade;
    @Autowired
    private SystemManageFacade systemManageFacade;

    /**
     * 查询角色详情
     *
     * @param request
     * @param roleId
     * @return
     * @throws BizRuleException
     */
    @ResponseBody
    @RequestMapping(value = {"/detail"})
    public ResultVo<SysRoleDTO> detail(HttpServletRequest request, String roleId) throws BizRuleException {
        ResultVo<SysRoleDTO> result = Results.newResultVo();
        SysRoleDTO sysRole = sysRoleFacade.getByPrimaryKey(roleId);
        SysOrgDTO org = sysOrgFacade.getByPrimaryKey(sysRole.getOrgId());
        sysRole.setIdpath(org.getIdpath());
        result.setSuccess(true);
        result.setCode("0");
        result.setResult(sysRole);
        return result;

    }

    /**
     * 分页查询数据
     *
     * @param request
     * @param roleName
     * @param roleDesc
     * @param roleType
     * @param page
     * @param size
     * @return
     * @throws BizRuleException
     */
    @ResponseBody
    @RequestMapping(value = {"/queryTable"})
    public Map<String, Object> queryTable(HttpServletRequest request,
                                          @RequestParam(name = "roleName") String roleName,
                                          @RequestParam(name = "roleDesc") String roleDesc,
                                          @RequestParam(name = "roleType") String roleType,
                                          @RequestParam(name = "orgId") String orgId,
                                          @RequestParam(name = "page") Integer page,
                                          @RequestParam(name = "size") Integer size) {
        Map<String, Object> mapResult = new HashMap<>();
        ResultVo<List<SysRoleDTO>> result = Results.newResultVo();
        HashMap<String, Object> searchMap = new HashMap<>();
        searchMap.put("roleName", roleName);
        searchMap.put("orgId", orgId);
        searchMap.put("roleDesc", roleDesc);
        searchMap.put("active", "1");
        //从缓存获取用户信息
        Long userId = loginComonent.getLoginUserId();
        SysUserDTO user = sysUserFacade.getCacheByPrimaryKey(userId);
        String userType = user.getUserType();
        if (null != userType && !"".equals(userType)) {
            if ("1".equals(userType)) {// 超级管理员
                searchMap.put("roleType", roleType);
            }
            if ("2".equals(userType)) {// 行政区管理员
                searchMap.put("areaId", user.getAreaId());
            }
            if ("3".equals(userType)) {// 机构管理员
//				searchMap.put("orgId", user.getOrgId());
                List<SysUserOrgDTO> uoList = sysUserFacade.queryUserOrg(userId);
                Set<Long> s = new HashSet<>();
                for (SysUserOrgDTO uo : uoList) {
                    s.add(uo.getOrgId());
                }
                String orgIds = "'" + StringUtils.join(s, "','") + "'";
                searchMap.put("orgIds", orgIds);
                if (null != roleType && !"".equals(roleType)) {
                    searchMap.put("roleType", roleType);
                } else {
                    searchMap.put("roleType", "4");
                }
            }
            // 计算当前页
            page = (page - 1) * size;
            List<SysRoleDTO> list = sysRoleFacade.getListByWhere(searchMap, page, size);
            List<SysRoleDTO> roleList = new ArrayList<>();
            for (SysRoleDTO r : list) {
                SysOrgDTO org = sysOrgFacade.getByPrimaryKey(r.getOrgId());
                if (null != org) {
                    r.setOrgName(org.getOrgName());
                }
                roleList.add(r);
            }
            int count = sysRoleFacade.getCountByWhere(searchMap);
            result.setCode("0");
            result.setSuccess(true);
            result.setResult(roleList);
            mapResult.put("count", count);
        } else {
            result.setResultDes("非管理员角色，不能看到任何角色");
            result.setCode("-1");
        }
        mapResult.put("pageList", result);
        return mapResult;
    }

    /**
     * 修改角色信息
     *
     * @param request
     * @param sysRole
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"/updateRole"}, method = RequestMethod.POST)
    public ResultVo<SysRoleDTO> updateRole(@RequestBody SysRoleDTO sysRole) {
        ResultVo<SysRoleDTO> result = Results.newResultVo();
        boolean bool = true;// 校验是否通过
        bool = sysRoleFacade.checkRoleName(sysRole.getRoleName(), sysRole.getId(), sysRole.getOrgId());
        if (bool) {
            result.setResultDes("当前机构下该角色名已存在");
            result.setSuccess(false);
        } else {
            sysRoleFacade.updatepo(sysRole);
            result.setCode("0");
            result.setSuccess(true);
            result.setResultDes("修改成功");
            result.setResult(sysRole);
        }
        return result;

    }

    /**
     * 新增角色信息
     *
     * @param request
     * @param sysRole
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"/addSysRole"}, method = RequestMethod.POST)
    public ResultVo<Map<String, Object>> addSysRole(@RequestBody SysRoleDTO sysRole) {
        ResultVo<Map<String, Object>> result = Results.newResultVo();
        result.setSuccess(false);
        //生成主键ID
        String uuid = UUID.randomUUID().toString();
        String id = uuid.replace("-", "");
        sysRole.setId(id);

        //从缓存获取用户信息
        Long userId = loginComonent.getLoginUserId();
//		SysUserDTO sysUser = sysUserFacade.getCacheByPrimaryKey(userId);
//		//机构管理员获取自身区域和机构
//		if(!"1".equals(sysUser.getUserType())){
//			long areaId = sysUser.getAreaId();
//			long orgId = sysUser.getOrgId();
//			sysRole.setAreaId(areaId);
//			sysRole.setOrgId(orgId);
//		}
        sysRole.setCreatorId(userId.toString());
        sysRole.setActive("1");

        try {
            boolean bool = true;// 校验是否通过
            bool = sysRoleFacade.checkRoleName(sysRole.getRoleName(), null, sysRole.getOrgId());
            if (bool) {
                result.setResultDes("当前机构下该角色名已存在");
                result.setSuccess(false);
            } else {
                String location = "sys/role/addSysRole";
                HashMap<String, Object> map = checkComponent.createCheckBusiness(location, "新增角色信息");
                logger.info("addSysRole 是否配置审核 map={}", map);
                boolean flag = (boolean) map.get("flag");
                String opseno = "";
                if (null != map.get("opseno")) {
                    opseno = map.get("opseno").toString();
                }

                String msg = "";
                if (flag) {//有审核配置
                    String busId = map.get("busId").toString();
                    Map<String, Object> checkMap = new HashMap<>();
                    checkMap.put("type", "add");
                    checkMap.put("object", sysRole);
                    checkMap.put("bus", "role");
                    checkRedisComponent.putRedis(busId, checkMap);
                    msg = "新增成功，审核通过后数据生效";
                } else {
                    sysRoleFacade.addSysRole(sysRole);
                    msg = "新增成功";
                }
                Map<String, Object> retmap = new HashMap<>();
                retmap.put("opseno", opseno);
                result.setResultDes(msg);
                result.setResult(retmap);
                result.setCode("0");
                result.setSuccess(true);
            }
        } catch (BizRuleException e) {
            result.setResultDes("新增失败，失败原因：" + e.getMessage());
        }
        return result;

    }

    /**
     * 角色删除
     *
     * @param request
     * @param roleId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"/deleteSysRole"})
    public ResultVo<SysRoleDTO> addSysRole(HttpServletRequest request, String roleId) {
        ResultVo<SysRoleDTO> result = Results.newResultVo();
        SysRoleDTO sysRole = sysRoleFacade.getByPrimaryKey(roleId);
        if (null == sysRole) {
            result.setResultDes("角色不存在");
            result.setSuccess(false);
        } else {
            try {
                sysRoleFacade.deleteByPrimaryKey(roleId);
                result.setResultDes("删除成功");
                result.setCode("0");
                result.setSuccess(true);
            } catch (BizRuleException e) {
                result.setSuccess(false);
                result.setResultDes("删除失败，失败原因：" + e.getMessage());
            }

        }
        return result;
    }

    /**
     * 查询菜单树和已授权菜单
     *
     * @param roleType
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"/queryTree"})
    public Map<String, Object> queryTree(String roleType, String roleId) {
        Map<String, Object> mapResult = new HashMap<>();
        ResultVo<JSONArray> result = Results.newResultVo();
        if (StringUtils.isEmpty(roleType) || StringUtils.isEmpty(roleId)) {
            result.setCode("-1");
            result.setSuccess(false);
            result.setResultDes("参数缺失");
            mapResult.put("result", result);
            return mapResult;
        }
        JSONArray outData = new JSONArray();
        List<SysFunctionDTO> functionList = new ArrayList<SysFunctionDTO>();
        SysRoleDTO sysRoleDto = sysRoleFacade.getByPrimaryKey(roleId);
        List<SysOrgSystemDto> orgsystemList = sysOrgSystemFacade.findByOrgId(sysRoleDto.getOrgId());
        //从缓存获取用户信息
        Long userId = loginComonent.getLoginUserId();
        String userType = sysUserFacade.getByPrimaryKey(userId).getUserType();
        //admin账户给机构管理员分配权限
        if("1".equals(userType)) {
            SysOrgSystemDto sysOrgSystemDto = new SysOrgSystemDto();
            sysOrgSystemDto.setSystemId(1l);
            sysOrgSystemDto.setSystemName("管理系统");
            orgsystemList.add(sysOrgSystemDto);
        }
        for (SysOrgSystemDto f : orgsystemList) {
             //只展示已授权的系统
                functionList = sysFunctionFacade.findByFunTypeList(f.getSystemId().toString());
                JSONArray jsonArray = TreeUtil.listToTree(JSONArray.parseArray(JSONArray.toJSONString(functionList)), "id",
                        "parentId", "children");
                JSONObject out = new JSONObject();
                out.put("systemId",f.getSystemId());
                out.put("systemName",f.getSystemName());
                out.put("children", jsonArray);
                outData.add(out);
        }
        //查询角色拥有的功能列表
        List<SysRoleFunctionDTO> rfList = sysFunctionFacade.findByRoleId(roleId);
        result.setCode("0");
        result.setSuccess(true);
        result.setResult(outData);
        mapResult.put("rfList", rfList);
        mapResult.put("result", result);
        return mapResult;
    }


    /**
     * 查询菜单树和已授权菜单
     *
     * @param roleType
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"/queryTreeGroup"})
    public Map<String, Object> queryTreeGroup(String roleType, String roleId) {
        Map<String, Object> objectMap = new HashMap<>();
        ResultVo<JSONArray> result = Results.newResultVo();
        if (StringUtils.isEmpty(roleType) || StringUtils.isEmpty(roleId)) {
            result.setCode("-1");
            result.setSuccess(false);
            result.setResultDes("参数缺失");
            objectMap.put("result", result);
            return objectMap;
        }
        Map<String, HashMap> mapArray = new HashMap<>();
        JSONArray outData = new JSONArray();

        List<SysFunctionDTO> functionList = new ArrayList<SysFunctionDTO>();
        SysRoleDTO sysRoleDto = sysRoleFacade.getByPrimaryKey(roleId);
        List<SysOrgSystemDto> orgsystemList = sysOrgSystemFacade.findByOrgId(sysRoleDto.getOrgId());
        for (SysOrgSystemDto f : orgsystemList) {
            HashMap<String,JSONArray> groupList = new HashMap<>();
            //只展示已授权的系统
            functionList = sysFunctionFacade.findByFunTypeList(f.getSystemId().toString());
            JSONArray jsonArrayGroup = TreeUtil.groupBy(JSONArray.parseArray(JSONArray.toJSONString(functionList)),"groupName","id");
           for (Object o :jsonArrayGroup){
               JSONObject out = (JSONObject) o;
               JSONArray list = out.getJSONArray("data");
               JSONArray jsonArray = TreeUtil.listToTree(list, "id",
                       "parentId", "children");
               groupList.put(out.getString("groupName"),jsonArray);
           }
           JSONObject param = new JSONObject();
           param.put("systemId",f.getSystemId());
            param.put("systemName",f.getSystemName());
            param.put("children", groupList);
            outData.add(param);
        }
        //查询角色拥有的功能列表
        List<SysRoleFunctionDTO> rfList = sysFunctionFacade.findByRoleId(roleId);
        result.setCode("0");
        result.setSuccess(true);
        result.setResult(outData);
        objectMap.put("rfList", rfList);
        objectMap.put("result", result);
        return objectMap;
    }


    /**
     * 授权
     *
     * @param jsonObject
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"/addRoleRef"}, method = RequestMethod.POST)
    public ResultVo<String> addRoleRef(@RequestBody JSONObject jsonObject) {
        ResultVo<String> result = Results.newResultVo();
        String roleId = (String) jsonObject.get("roleId");
        // 判断角色有效性
        SysRoleDTO role = sysRoleFacade.getByPrimaryKey(roleId);
        JSONArray jsonArray = null;
        try {
            if (null != role) {
                jsonArray = jsonObject.getJSONArray("treeInfo");
//				JSONObject jsonObject1 = null;
                List<SysRoleFunctionDTO> list = new ArrayList<>();
                for (int i = 0; i < jsonArray.size(); i++) {
                    SysRoleFunctionDTO sysRoleFunction = JavaBeanUtils.pageElementToBean((JSONObject) jsonArray.get(i), SysRoleFunctionDTO.class);
//					jsonObject1 = (JSONObject) JSONObject.parse(jsonArray.get(i).toString());
//					Long functionId = jsonObject1.getLong("functionId");
//					list.add(functionId);
                    list.add(sysRoleFunction);
                }
                sysRoleFacade.deleteRoleFunctionRefAndAddNewRef(roleId, list);
                result.setSuccess(true);
                result.setResultDes("授权成功");
            } else {
                result.setSuccess(false);
                result.setResultDes("角色不存在");
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResultDes("授权失败，失败原因：" + e.getMessage());
        }

        return result;
    }

    /**
     * 下载模板
     */
    @ResponseBody
    @RequestMapping(value = "/downloadTemplate", produces = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public void downloadTemplate(HttpServletRequest request, HttpServletResponse response) {
        try {
            String realPath = request.getServletContext().getRealPath("/");
            String fileSeperator = File.separator;
            realPath += fileSeperator + "template" + fileSeperator;
            String localFileName = "角色信息导入模板.xlsx";
            String localFilePath = realPath + localFileName;
            File file = Paths.get(localFilePath).toFile();

            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8");
            String fileName = URLEncoder.encode(localFileName, "UTF-8");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName);
            OutputStream stream = response.getOutputStream();
            stream.write(ByteStreams.toByteArray(new FileInputStream(file)));
            stream.flush();
            stream.close();
        } catch (Exception e) {
            logger.error("userId={},error={}", loginComonent.getLoginUserId(), ExceptionUtil.getMessage(e));//打印全部异常日志
        }
    }

    /**
     * 角色信息导入
     *
     * @param file
     * @param orgId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/import", method = RequestMethod.POST)
    public ResultVo<Object> doImport(@RequestParam("file") MultipartFile file, Long orgId) {
        ResultVo<Object> resultVo = Results.newResultVo();
        resultVo.setSuccess(false);

        if (orgId == null) {
            resultVo.setCode("-1");
            resultVo.setResultDes("导入失败：机构id为空");
            return resultVo;
        }
        //从缓存获取用户信息
        Long userId = loginComonent.getLoginUserId();
        SysUserDTO user = sysUserFacade.getCacheByPrimaryKey(userId);

        String roleName = null;
        String roleDesc = null;
        String position = null;

        SysRoleDTO sysRole = new SysRoleDTO();
        List<SysRoleDTO> roleList = new ArrayList<>();
        try {
            List<String[][]> excel_list = ExcelUtil.getData(file, 1);
            if (excel_list == null || excel_list.isEmpty()) {
                resultVo.setCode("-1");
                resultVo.setResultDes("导入失败：传入的文件内容为空");
                return resultVo;
            }

            for (String[][] Sheets : excel_list) {//第一个循环遍历Sheet
                for (String[] rows : Sheets) {//第二个循环遍历row
                    if (rows != null && rows.length > 1) {
                        roleName = rows[1];
                        roleDesc = rows[2];
                        position = rows[3];
                        if (roleName.equals("角色名称") || roleDesc.equals("角色描述")) {
                            resultVo.setCode("-1");
                            resultVo.setResultDes("导入失败：传入的文件内容为空");
                            return resultVo;
                        }
                        sysRole = new SysRoleDTO();
                        //生成主键ID
                        String uuid = UUID.randomUUID().toString();
                        String id = uuid.replace("-", "");
                        sysRole.setId(id);
                        sysRole.setOrgId(orgId);
                        sysRole.setCreatorId(userId.toString());
                        sysRole.setActive("1");
                        if (user.getUserType().equals("1")) {
                            sysRole.setRoleType("3");
                        } else {
                            sysRole.setRoleType("4");
                        }
                        sysRole.setRoleName(roleName);
                        sysRole.setRoleDesc(roleDesc);
                        sysRole.setPosition(position);

                        boolean bool = true;// 校验是否通过
                        bool = sysRoleFacade.checkRoleName(roleName, null, orgId);
                        if (bool) {
                            resultVo.setCode("-1");
                            resultVo.setResultDes("导入失败：当前机构下该角色名已存在,角色名：" + roleName);
                            return resultVo;
                        }
                        roleList.add(sysRole);
                    }
                }
            }
            sysRoleFacade.insertByBatch(roleList);
        } catch (Exception e) {
            resultVo.setCode("-1");
            resultVo.setResultDes("导入失败：" + e.getLocalizedMessage());
            e.printStackTrace();
            return resultVo;
        }

        resultVo.setCode("0");
        resultVo.setResultDes("导入成功");
        resultVo.setSuccess(true);
        return resultVo;
    }
}
