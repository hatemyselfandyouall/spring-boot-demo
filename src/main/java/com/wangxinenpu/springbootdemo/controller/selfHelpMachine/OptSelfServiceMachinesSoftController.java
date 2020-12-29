package com.wangxinenpu.springbootdemo.controller.selfHelpMachine;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.ImmutableSet;
import com.taobao.diamond.extend.DynamicProperties;
import com.wangxinenpu.springbootdemo.config.component.LoginComponent;
import com.wangxinenpu.springbootdemo.constant.BaseConstant;
import com.wangxinenpu.springbootdemo.constant.DataConstant;
import com.wangxinenpu.springbootdemo.controller.root.BasicController;
import com.wangxinenpu.springbootdemo.dataobject.dto.OptSelfServiceMachinesSoftOrgRefDTO;
import com.wangxinenpu.springbootdemo.dataobject.po.OpenapiSelfmachineType;
import com.wangxinenpu.springbootdemo.dataobject.vo.*;
import com.wangxinenpu.springbootdemo.dataobject.vo.OpenapiSelfmachineType.OpenapiSelfmachineTypeListVO;
import com.wangxinenpu.springbootdemo.service.facade.*;
import com.wangxinenpu.springbootdemo.util.FileUploadUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import star.bizbase.vo.result.Results;
import star.fw.web.util.ServletAttributes;
import star.util.StringUtil;
import star.vo.result.ResultVo;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 自助机服务管理
 *
 * @Author:ll
 * @since：2019年11月20日
 *
 */
@Slf4j
@Api(value="opt/selfServiceMachines", tags="自助机服务管理")
@RestController
@RequestMapping("opt/selfServiceMachines")
public class OptSelfServiceMachinesSoftController extends BasicController {


	@Autowired
	private OptSelfServiceMachinesSoftFacade optSelfService;
	@Autowired
	private SysUserFacade sysUserFacade;
	@Autowired
	private SysOrgFacade sysOrgFacade;

	@Autowired
	private LoginComponent loginComonent;

	@Autowired
	private OpenapiSelfmachineTypeFacade openapiSelfMachineTypeFacade;

	@Autowired
    RestTemplate restTemplate;

	public static final String downloadUrl= DynamicProperties.staticProperties.getProperty("oss.download.http.txt.url");
	public static final String downloadUrlHttp= DynamicProperties.staticProperties.getProperty("oss.download.http.zip.url");
	public static final String downloadUrlClient= DynamicProperties.staticProperties.getProperty("oss.download.http.exe.url");
	public static final String uploadUrl= DynamicProperties.staticProperties.getProperty("oss.fileUpload.http.txt.url");

	/**
     * 分页查询数据
     *
     * @param vo
     * @return
     */
    @ApiOperation(value="分页查询数据", notes = "分页查询数据", httpMethod="POST")
    @RequestMapping(value = { "/queryTable" })
    public ResultVo<PageListVo> queryTable(@RequestBody OptSelfServiceMachinesSoftVo vo) {
        ResultVo<PageListVo> result = Results.newResultVo();
        try {
            log.info("分页查询，入参 OptSelfServiceMachinesSoftVo={}", JSON.toJSONString(vo));

            HashMap<String, Object> searchMap = new HashMap<>();
            Integer pageSize = (int)(long)vo.getPageSize();
			if(pageSize == null) {
				pageSize = 10;
            }
            Integer pageNum =(int)(long) vo.getPageNum();
            if(pageNum == null) {
                pageNum = 1;
            }

            searchMap.put("searchName", vo.getSearchName());
            searchMap.put("id", vo.getId());
            searchMap.put("isValid", vo.getIsValid());
			searchMap.put("softName", vo.getSoftName());
            int fromIndex =pageNum;// pageSize * (pageNum - 1);

            List<OptSelfServiceMachinesSoftVo> list = optSelfService.getListByWhere(searchMap,fromIndex,pageSize);
            int count = optSelfService.getCountByWhere(searchMap);
			List<OptSelfServiceMachinesSoftVo> listNew =new ArrayList<>();

			for (OptSelfServiceMachinesSoftVo vo1: list) {
				OptSelfServiceMachinesSoftVo vNew = new OptSelfServiceMachinesSoftVo();
				BeanUtils.copyProperties(vo1,vNew);
				//				String dUrl = downloadUrl+ vNew.getPathKey();
				String dUrl = "";
				String pathKey = vNew.getPathKey();
				String softType = vNew	.getSoftName();
				if(DataConstant.SOFTTYPE_MONITOR_SERVER.equals(softType)) {
					dUrl = downloadUrl + pathKey;
				}else if(DataConstant.SOFTTYPE_HTTP.equals(softType)) {
					dUrl = downloadUrlHttp + pathKey;
				}else if(DataConstant.SOFTTYPE_MONITOR_CLIENT.equals(softType)) {
					dUrl = downloadUrlClient + pathKey;
				}else{
					dUrl = downloadUrlClient + pathKey;
				}
				vNew.setDownloadUrl(dUrl);

                //转类型
                OpenapiSelfmachineTypeListVO var1 = new OpenapiSelfmachineTypeListVO();
                var1.setPageNum(1L);
                var1.setPageSize(10000L);
                PageInfo<OpenapiSelfmachineType> pageInfo=openapiSelfMachineTypeFacade.getOpenapiSelfmachineTypeList(var1,null);
                String softModelRefNewStr  = "";
                try {
                    for (OpenapiSelfmachineType a : pageInfo.getList()) {

                        String softModelRefStr  = vNew.getOptSelfServiceMachinesSoftModelRefStr();
                        String[] softModelRefArr = softModelRefStr.split(",");
                        for (int i = 0; i < softModelRefArr.length; i++) {
                           Long softModel = Long.parseLong(softModelRefArr[i].toString());
                            if(softModel.equals(a.getId())) {
                                softModelRefNewStr =softModelRefNewStr + a.getName() +",";
                            }
                        }

                    }
                    if(softModelRefNewStr.indexOf(",")>-1) {
                        softModelRefNewStr = softModelRefNewStr.substring(0, softModelRefNewStr.length() - 1);
                    }
                } catch (Exception e) {
                    softModelRefNewStr ="";
                }
                vNew.setOptSelfServiceMachinesSoftModelRefStr(softModelRefNewStr);
				listNew.add(vNew);
			}
            result = setPageResultSuccessed(listNew, count);
            log.info("分页查询，返回ResultVo<PageListVo>={}", JSON.toJSONString(result));
        } catch (Exception e) {
            result = setPageResultError( "0000",e.getMessage());
            log.error("分页查询，错误信息 error-info={}", e.getMessage());

        }
        return result;
    }


	/**
	 * 查询区域信息
	 *
	 *
	 * @return
	 */
	@ApiOperation(value = "查询区域信息", notes = "查询区域", httpMethod="POST" )
	@RequestMapping(value = {"/queryArea"})
	public ResultVo<List> queryArea(SysUserDTO sysUser) {
		ResultVo<List> result = Results.newResultVo();
		result.setSuccess(false);
		try {
			log.info("开始查询");
			List<Map<String, String>> listMr = new ArrayList<>();
			List<SysAreaDTO> list=sysUserFacade.queryAreaNodes(sysUser);

//			for (SysAreaDTO a : list) {
//				Map<String, String> mr = new HashMap<>();
//				mr.put("areaId", a.getId().toString());
//				mr.put("areaName", a.getAreaName());
//				listMr.add(mr);
//			}
			result.setResult(list);
			result.setCode("0");
			result.setSuccess(true);
			log.info("查询区域信息，返回ResultVo<PageListVo>={}", JSON.toJSONString(result));
		} catch (Exception e) {
			result.setResultDes("查询区域信息，失败原因：" + e.getMessage());
			log.error("查询区域信息，错误信息 error-info={}", e.getMessage());

		}
		return result;
	}

	/**
	 * 查询区域信息
	 *
	 *
	 * @return
	 */
	@ApiOperation(value = "根据统筹区编码获取机构", notes = "根据统筹区编码获取机构", httpMethod="GET" )
	@RequestMapping(value = {"/queryOrg"})
	public ResultVo<List> queryOrg(String areaId) {
		ResultVo<List> result = Results.newResultVo();
		result.setSuccess(false);
		try {
			log.info("开始查询");
			List<Map<String, String>> listMr = new ArrayList<>();
			List<SysOrgDTO> orgList= sysOrgFacade.queryOrgNodes(areaId);

			result.setResult(orgList);
			result.setCode("0");
			result.setSuccess(true);
			log.info("根据统筹区编码获取机构，返回ResultVo<PageListVo>={}", JSON.toJSONString(result));
		} catch (Exception e) {
			result.setResultDes("根据统筹区编码获取机构，失败原因：" + e.getMessage());
			log.error("根据统筹区编码获取机构，错误信息 error-info={}", e.getMessage());

		}
		return result;
	}

	/**
	 * 获取机构树
	 *
	 *
	 * @return
	 */
	@ApiOperation(value = "获取机构树", notes = "获取机构树", httpMethod="POST" )
	@RequestMapping(value = {"/queryOrgTree"})
	public ResultVo<List> queryOrgTree() {
		ResultVo<List> result = Results.newResultVo();
		result.setSuccess(false);
		try {
			log.info("开始查询");
			List<Map<String, String>> listMr = new ArrayList<>();

			HashMap<String, Object> searchMap = new HashMap<>();
			searchMap.put("orgState", DataConstant.YES.toString());
			List<SysOrgDTO> orgList = sysOrgFacade.getListByWhere(searchMap);
			List<Map<String, Object>> parentNodeList = new ArrayList<Map<String, Object>>();
			for (SysOrgDTO org : orgList) {
				if (org.getParentId() == null) {
					Map<String, Object> parentNode = new HashMap<String, Object>();
					parentNode.put("orgId", org.getId());
					parentNode.put("orgName", org.getOrgName());

					getOrgChild(orgList, parentNodeList, org, parentNode);
				}
			}
			result.setResult(parentNodeList);
			result.setCode("0");
			result.setSuccess(true);
			log.info("根据统筹区编码获取机构，返回ResultVo<PageListVo>={}", JSON.toJSONString(result));

		} catch (Exception e) {
			result.setResultDes("根据统筹区编码获取机构，失败原因：" + e.getMessage());
			log.error("根据统筹区编码获取机构，错误信息 error-info={}", e.getMessage());

		}
		return result;
	}

	private void getOrgChild(List<SysOrgDTO> orgList, List<Map<String, Object>> parentNodeList, SysOrgDTO org, Map<String, Object> parentNode) {
		List<Map<String, Object>> parentNodeChildList = new ArrayList<Map<String, Object>>();
		for (SysOrgDTO orgChild : orgList) {
			if (orgChild.getParentId() != null) {
				if (orgChild.getParentId().equals(org.getId())) {
					Map<String, Object> parentNodeChild = new HashMap<String, Object>();
					parentNodeChild.put("orgId", orgChild.getId());
					parentNodeChild.put("orgName", orgChild.getOrgName());
					//迭代tree
					getOrgChild(orgList, new ArrayList<Map<String, Object>>(), orgChild, parentNodeChild);

					parentNodeChildList.add(parentNodeChild);
				}
			}
		}
		if (parentNodeChildList != null && parentNodeChildList.size() > 0) {
			parentNode.put("children", parentNodeChildList);
		}
		parentNodeList.add(parentNode);
	}

	/**
	 * 保存自助机
	 * @param vo
	 * @return
	 */
	@ApiOperation(value="保存自助机", notes = "保存自助机", httpMethod="POST")
	@ResponseBody
	@RequestMapping(value = { "/doSave" })
	public ResultVo<Integer> save(@RequestBody OptSelfServiceMachinesSoftVo vo){
		ResultVo<Integer> result = Results.newResultVo();
		int n = 0;
		try {
			log.info("保存自助机，入参 OptSelfServiceMachinesSoftVo={}", JSON.toJSONString(vo));

			//参数校验
			if(StringUtils.isBlank(vo.getSoftName())){
				result = setIntResultError(BaseConstant.SYSTEM_PARAM_ERROR,"客户端名称不可为空："+vo.getSoftName());
				return result;
			}
			HashMap<String, Object> searchMap = new HashMap<>();
			searchMap.put("softVersion", vo.getSoftVersion());
            searchMap.put("softName", vo.getSoftName());
			searchMap.put("isValid", DataConstant.YES);
			List<OptSelfServiceMachinesSoftVo> list = optSelfService.getListByWhere(searchMap);
			if (list.size()>0) {
				result = setIntResultError(BaseConstant.INFO_IS_EXIT,"该软件版本号："+vo.getSoftVersion()+"已存在,请调整版本！");
				return result;
			}
			vo.setManagerId(loginComonent.getLoginUserId());
			vo.setManagerName(loginComonent.getLoginUserName());
			Long id = optSelfService.addOptSelfServiceMachinesSoft(vo);

			if(id!=0)
			{
				n = 1;
			}
			result = setIntResultSuccessed(n);
			log.info("保存自助机，返回ResultVo<Integer>={}", JSON.toJSONString(result));
		} catch (Exception e) {
			result = setIntResultError( "0000","保存失败，失败原因："+e.getMessage());
			log.error("保存自助机，错误信息 error-info={}", e.getMessage());

		}

		return result;
	}



	/**
	 *	修改自助机信息
	 *
	 * @param vo
	 * @return
	 */
	@ApiOperation(value="修改自助机信息", notes = "修改自助机信息", httpMethod="POST")
	@RequestMapping("/updateSelfService")
	@ResponseBody
	public ResultVo<Integer> updateSelfService(@RequestBody @ApiParam(name="修改自助机信息",value="传入json格式",required=true) OptSelfServiceMachinesSoftVo vo) {
		ResultVo<Integer> result = Results.newResultVo();
		result.setSuccess(false);
		Integer n = 0;
		try {
			log.info("修改自助机信息，入参 OptSelfServiceMachinesSoftVo={}", JSON.toJSONString(vo));

			if(StringUtils.isBlank(vo.getSoftName())){
				result = setIntResultError(BaseConstant.SYSTEM_PARAM_ERROR,"客户端名称不可为空："+vo.getSoftName());
				return result;
			}
			/*HashMap<String, Object> searchMap = new HashMap<>();
			searchMap.put("softVersion", vo.getSoftVersion());
			searchMap.put("isValid", 1);
			List<OptSelfServiceMachinesSoftVo> list = optSelfService.getListByWhere(searchMap);
			if (list.size()>0) {
				result = setIntResultError(BaseConstant.INFO_IS_EXIT,"版本号："+vo.getSoftVersion()+"已存在,请调整版本！");
				return result;
			}*/
			vo.setManagerId(loginComonent.getLoginUserId());
			vo.setManagerName(loginComonent.getLoginUserName());
			n = optSelfService.updatePo(vo);
			if(n>0) {
				result = setIntResultSuccessed(n);
			}
			log.info("修改自助机信息，返回ResultVo<Integer>={}", JSON.toJSONString(result));

		} catch (Exception e) {
			result = setIntResultError( "0000","保存失败，失败原因："+e.getMessage());
			log.error("修改自助机信息，错误信息 error-info={}", e.getMessage());

		}
		return result;
	}

	/**
	 *根据Id查询信息
	 * @param id
	 * @return
	 */
	@ApiOperation(value="根据id查询信息", notes = "根据id查询信息", httpMethod="GET")
	@ApiImplicitParam(name="id",value="自助机ID",dataType="string", paramType = "query")
	@RequestMapping(value = { "/querySelfServiceById" })
	@ResponseBody
    public ResultVo<OptSelfServiceMachinesSoftVo> querySelfServiceById(String id){
		ResultVo<OptSelfServiceMachinesSoftVo> result = Results.newResultVo();
		result.setSuccess(false);
		OptSelfServiceMachinesSoftVo sysUser = new OptSelfServiceMachinesSoftVo();
		try{
			log.info("根据id查询信息，入参 id={}", id);

			sysUser = optSelfService.getByPrimaryKey(Long.parseLong(id));
			result.setResult(sysUser);
			result.setCode("0");
			result.setSuccess(true);
			log.info("根据projectId查询信息，返回ResultVo<OptSelfServiceMachinesSoftVo>={}", JSON.toJSONString(result));

		} catch (Exception e) {
			result.setResultDes("信息查询失败，失败原因："+e.getMessage());
			log.error("根据projectId查询信息，错误信息 error-info={}", e.getMessage());

		}

        return result;
    }

	/**
	 *根据projectId删除信息
	 * @param id
	 * @return
	 */
	@ApiOperation(value="根据id删除信息", notes = "根据id删除信息", httpMethod="POST")
	@ApiImplicitParam(name="id",value="自助机ID",dataType="string", paramType = "query")
	@RequestMapping(value = { "/deleteSelfServiceById" })
	@ResponseBody
	public ResultVo<Integer> deleteSelfServiceById(String id){
		ResultVo<Integer> result = Results.newResultVo();
		OptSelfServiceMachinesSoftVo vo = new OptSelfServiceMachinesSoftVo();
		Integer n = 0;
		try{
			log.info("根据id删除信息，入参 id={}", id);
			OptSelfServiceMachinesSoftVo self = optSelfService.getByPrimaryKey(Long.parseLong(id));

			if(DataConstant.YES.toString().equals(self.getIsValid().toString())){
				result.setSuccess(false);
				result.setResultDes("有效状态的软件不能删除！请置为无效！");
				return result;
			}

//			vo.setId(Long.parseLong(id));
//			vo.setIsValid(Short.parseShort("0"));
			n = optSelfService.deleteProjectById(Long.parseLong(id));
			//修改用户
//			vo.setModifyTime(new Date());
//			n = optSelfService.updatePo(vo);
			result = setIntResultSuccessed(n);

			log.info("根据id删除信息，返回ResultVo<Integer>={}", JSON.toJSONString(result));

		} catch (Exception e) {
			result = setIntResultError( "0000","信息删除失败，失败原因："+e.getMessage());
			log.error("根据id删除信息，错误信息 error-info={}", e.getMessage());

		}

		return result;
	}
	/**
	 * 查询自助机型号
	 *
	 *
	 * @return
	 */
	@ApiOperation(value = "查询自助机型号", notes = "查询自助机型号", httpMethod="POST" )
	@RequestMapping(value = {"/querySelfMachineType"})
	public ResultVo<List> querySelfMachineType() {
		ResultVo<List> result = Results.newResultVo();
		result.setSuccess(false);
		try {
			log.info("开始查询");
			List<Map<String, Object>> listMr = new ArrayList<>();
			OpenapiSelfmachineTypeListVO var1 = new OpenapiSelfmachineTypeListVO();
			var1.setPageNum(1L);
			var1.setPageSize(10000L);
			PageInfo<OpenapiSelfmachineType> pageInfo=openapiSelfMachineTypeFacade.getOpenapiSelfmachineTypeList(var1,null);

			for (OpenapiSelfmachineType a : pageInfo.getList()) {
				Map<String, Object> mr = new HashMap<>();
				mr.put("typeId", a.getId());
				mr.put("typeName", a.getName());
				listMr.add(mr);
			}
			result.setResult(listMr);
			result.setCode("0");
			result.setSuccess(true);
			log.info("查询自助机型号，返回ResultVo<PageListVo>={}", JSON.toJSONString(result));

		} catch (Exception e) {
			result.setResultDes("查询自助机型号，失败原因：" + e.getMessage());
			log.error("查询自助机型号，错误信息 error-info={}", e.getMessage());

		}
		return result;
	}
	@ApiOperation(value = "查询软件列表", notes = "查询软件列表", httpMethod="POST" )
	@RequestMapping(value = {"/querySoftList"})
	public ResultVo<List<Map<String, String>>> querySoftList() {
		ResultVo<List<Map<String, String>>> result = Results.newResultVo();
		result.setSuccess(false);
		try {
			log.info("查询软件列表");
			List<Map<String, String>> listMr = new ArrayList<>();
			HashMap<String, Object> searchMap = new HashMap<>();
			HashSet<String> hashSet = new HashSet<>();
            searchMap.put("isValid", DataConstant.YES);
			List<OptSelfServiceMachinesSoftVo> list = optSelfService.getListByWhere(searchMap);
//			String addr = "";
			for (OptSelfServiceMachinesSoftVo s : list) {
				String softPath= s.getAddr();
				String name = "";
				String url ="";
//				if(softPath.lastIndexOf("/")>-1){
//					name = softPath.substring(softPath.lastIndexOf("/")+1);
//					url = softPath.substring(0,softPath.lastIndexOf("/"));
//				}
				name = softPath;
//				url = downloadUrl + s.getPathKey();
				String pathKey = s.getPathKey();
				String softType = s.getSoftName();
				if(DataConstant.SOFTTYPE_MONITOR_SERVER.equals(softType)) {
					url = downloadUrl + pathKey;
				}else if(DataConstant.SOFTTYPE_HTTP.equals(softType)) {
					url = downloadUrlHttp + pathKey;
				}else if(DataConstant.SOFTTYPE_MONITOR_CLIENT.equals(softType)) {
					url = downloadUrlClient + pathKey;
				}else{
					url = downloadUrlClient + pathKey;
				}

				Map<String, String> mr = new HashMap<>();
				mr.put("name", name);
				mr.put("path", url);
				mr.put("pathKey", pathKey);
				if(hashSet.add(name)) {
					listMr.add(mr);
				}
			}
			result.setResult(listMr);
			result.setCode("0");
			result.setSuccess(true);
			log.info("查询软件列表，返回ResultVo<PageListVo>={}", JSON.toJSONString(result));

		} catch (Exception e) {
			result.setResultDes("查询软件列表，失败原因：" + e.getMessage());
			log.error("查询软件列表，错误信息 error-info={}", e.getMessage());

		}
		return result;
	}

	@ApiOperation(value = "查询软件路径通过选择的值", notes = "查询软件路径通过选择的值", httpMethod="POST" )
	@RequestMapping(value = {"/querySoftPathByAddr"})
	public ResultVo<List<Map<String, String>>> querySoftPathByAddr(@RequestBody OptSelfServiceMachinesSoftVo vo ) {
		ResultVo<List<Map<String, String>>> result = Results.newResultVo();
		result.setSuccess(false);
		try {
			log.info("查询软件路径通过选择的值");
			List<Map<String, String>> listMr = new ArrayList<>();
			HashMap<String, Object> searchMap = new HashMap<>();
			searchMap.put("addr", vo.getAddr());
            searchMap.put("isValid", DataConstant.YES);
			List<OptSelfServiceMachinesSoftVo> list = optSelfService.getListByWhere(searchMap);
			if (list.size()>0) {
				String softPath= list.get(0).getAddr();
				String name = "";
				String url ="";
//				if(softPath.lastIndexOf("/")>-1){
//					name = softPath.substring(softPath.lastIndexOf("/")+1);
//					url = softPath.substring(0,softPath.lastIndexOf("/"));
//				}
				name = softPath;
//				url = downloadUrl + list.get(0).getPathKey();
				String pathKey = list.get(0).getPathKey();
				String softType = list.get(0).getSoftName();
				if(DataConstant.SOFTTYPE_MONITOR_SERVER.equals(softType)) {
					url = downloadUrl + pathKey;
				}else if(DataConstant.SOFTTYPE_HTTP.equals(softType)) {
					url = downloadUrlHttp + pathKey;
				}else if(DataConstant.SOFTTYPE_MONITOR_CLIENT.equals(softType)) {
					url = downloadUrlClient + pathKey;
				}else{
					url = downloadUrlClient + pathKey;
				}
				Map<String, String> mr = new HashMap<>();
				mr.put("name", name);
				mr.put("path", url);
				mr.put("pathKey", pathKey);
				listMr.add(mr);
			}
			result.setResult(listMr);
			result.setCode("0");
			result.setSuccess(true);
			log.info("查询软件路径通过选择的值，返回ResultVo<PageListVo>={}", JSON.toJSONString(result));

		} catch (Exception e) {
			result.setResultDes("查询软件列表，失败原因：" + e.getMessage());
			log.error("查询软件路径通过选择的值，错误信息 error-info={}", e.getMessage());

		}
		return result;
	}

	/**
	 * 查询版本
	 *
	 *
	 * @return
	 */
	@ApiOperation(value = "查询版本", notes = "查询版本", httpMethod="POST" )
	@RequestMapping(value = {"/querySelfServiceMachinesSoft"})
	public ResultVo<HashMap<String, Object>> querySelfServiceMachinesSoft(String oldVersion) {
		ResultVo<HashMap<String, Object>> result = Results.newResultVo();
		result.setSuccess(false);
		try {
			log.info("开始查询");
			List<Map<String, Object>> vList = new ArrayList<>();
			HashMap<String, Object> searchMap = new HashMap<>();

            searchMap.put("isValid", DataConstant.YES);
			searchMap.put("softName", "2");

			List<OptSelfServiceMachinesSoftVo> list = optSelfService.getListByWhere(searchMap);

			HashMap<String, Object> returnMap = new HashMap<>();
			for (OptSelfServiceMachinesSoftVo a : list) {
				if(a.getSoftVersion()!=null) {
					if (compareVersion(a.getSoftVersion(), oldVersion) == 1) {
						String softPath = a.getAddr();
						String pathKey = a.getPathKey();
						String name = "";
						String url = "";

						if (StringUtils.isNotBlank(softPath) && softPath.lastIndexOf("/") > -1) {
							name = softPath.substring(softPath.lastIndexOf("/") + 1);
//							url = softPath.substring(0, softPath.lastIndexOf("/"));
						}
						url = downloadUrlClient + pathKey;
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");//初始化Formatter的转换格式。
//                    if (softName.equals(name)) {
						Map<String, Object> mr = new HashMap<>();
						mr.put("file_name", name);
						mr.put("file_desc", url);
						mr.put("sys_version_name", a.getSoftVersion());
						mr.put("updateStrategy", a.getUpdateStrategy());
                        mr.put("startUpdateTime", a.getStartUpdateTime()!=null?simpleDateFormat.format( a.getStartUpdateTime()):null);
                        mr.put("updateTime", a.getUpdateTime()!=null?formatter.format( a.getUpdateTime()):null);
						vList.add(mr);
//                    }
					}
				}
			}
			returnMap.put("content",vList);
			result.setResult(returnMap);
			result.setCode("0");
			result.setSuccess(true);
			log.info("查询版本，返回ResultVo<PageListVo>={}", JSON.toJSONString(result));

		} catch (Exception e) {
			result.setResultDes("查询版本，失败原因：" + e.getMessage());
			log.error("查询版本，错误信息 error-info={}", e.getMessage());

		}
		return result;
	}

	/**
	 * 查询版本
	 *
	 *
	 * @return
	 */
	@ApiOperation(value = "查询版本", notes = "查询版本", httpMethod="POST" )
	@RequestMapping(value = {"/querySelfServiceMachinesMoreSoft"})
	public ResultVo<HashMap<String, Object>> querySelfServiceMachinesMoreSoft(String softType, String oldVersion) {
		ResultVo<HashMap<String, Object>> result = Results.newResultVo();
		result.setSuccess(false);
		try {
			log.info("开始查询");
			List<Map<String, Object>> vList = new ArrayList<>();
			HashMap<String, Object> searchMap = new HashMap<>();

            searchMap.put("isValid", DataConstant.YES);
			searchMap.put("softName", softType);
			List<OptSelfServiceMachinesSoftVo> list = optSelfService.getListByWhere(searchMap);

			HashMap<String, Object> returnMap = new HashMap<>();
			for (OptSelfServiceMachinesSoftVo a : list) {
				if(a.getSoftVersion()!=null) {
					if (compareVersion(a.getSoftVersion(), oldVersion) == 1) {
						getUpSoftInfo(softType, vList, a);
					}
				}
			}
			returnMap.put("content",vList);
			result.setResult(returnMap);
			result.setCode("0");
			result.setSuccess(true);
			log.info("查询版本，返回ResultVo<PageListVo>={}", JSON.toJSONString(result));

		} catch (Exception e) {
			result.setResultDes("查询版本，失败原因：" + e.getMessage());
			log.error("查询版本，错误信息 error-info={}", e.getMessage());

		}
		return result;
	}


	/**
	 * 查询版本
	 *
	 *
	 * @return
	 */
	@ApiOperation(value = "查询升级版本通过机构，老版本，软件类型和自助机类型", notes = "查询版本", httpMethod="POST" )
	@RequestMapping(value = {"/querySelfServiceMachinesMoreSoftByType"})
	public ResultVo<HashMap<String, Object>> querySelfServiceMachinesMoreSoftByType(String softType, String oldVersion, String machineTypeId, String orgId) {
		ResultVo<HashMap<String, Object>> result = Results.newResultVo();
		result.setSuccess(false);
		try {
			log.info("开始查询");
			List<Map<String, Object>> vList = new ArrayList<>();
			HashMap<String, Object> searchMap = new HashMap<>();

			searchMap.put("isValid", DataConstant.YES);
			searchMap.put("softName", softType);

			List<OptSelfServiceMachinesSoftVo> list = optSelfService.getListByWhere(searchMap);

			HashMap<String, Object> returnMap = new HashMap<>();
			for (OptSelfServiceMachinesSoftVo a : list) {
				if(a.getSoftVersion()!=null) {
				    //验证版本
					if (compareVersion(a.getSoftVersion(), oldVersion) == 1) {
                        //验证机构
                        for (OptSelfServiceMachinesSoftOrgRefDTO org: a.getSoftOrgRefList()) {
                            if(org.getOrgId().equals(Long.parseLong(orgId))){
                                //验证机型
                                String softModelRefStr  = a.getOptSelfServiceMachinesSoftModelRefStr();
                                String[] softModelRefArr = softModelRefStr.split(",");
                                for (int i = 0; i < softModelRefArr.length; i++) {
                                    String softModel = softModelRefArr[i].toString();
                                    if(softModel.equals(machineTypeId)) {
                                        //获取封装升级软件信息
                                        getUpSoftInfo(softType, vList, a);
                                    }
                                }
                            }
                        }

					}
				}
			}
			returnMap.put("content",vList);
			result.setResult(returnMap);
			result.setCode("0");
			result.setSuccess(true);
			log.info("查询版本，返回ResultVo<PageListVo>={}", JSON.toJSONString(result));

		} catch (Exception e) {
			result.setResultDes("查询版本，失败原因：" + e.getMessage());
			log.error("查询版本，错误信息 error-info={}", e.getMessage());

		}
		return result;
	}

	private void getUpSoftInfo(String softType, List<Map<String, Object>> vList, OptSelfServiceMachinesSoftVo a) {
		String softPath = a.getAddr();
		String pathKey = a.getPathKey();
		String name = "";
		String url = "";

		if (StringUtils.isNotBlank(softPath) && softPath.lastIndexOf("/") > -1) {
			name = softPath.substring(softPath.lastIndexOf("/") + 1);
		}
		//拿取软件类型
		if (DataConstant.SOFTTYPE_MONITOR_SERVER.equals(softType)) {
			url = downloadUrl + pathKey;
		} else if (DataConstant.SOFTTYPE_HTTP.equals(softType)) {
			url = downloadUrlHttp + pathKey;
		} else if (DataConstant.SOFTTYPE_MONITOR_CLIENT.equals(softType)) {
			url = downloadUrlClient + pathKey;
		} else {
			url = downloadUrlClient + pathKey;
		}
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");//初始化Formatter的转换格式。
		Map<String, Object> mr = new HashMap<>();
		mr.put("file_name", name);
		mr.put("file_desc", url);
		mr.put("sys_version_name", a.getSoftVersion());
		mr.put("updateStrategy", a.getUpdateStrategy());
		mr.put("startUpdateTime", a.getStartUpdateTime()!=null?simpleDateFormat.format( a.getStartUpdateTime()):null);
        mr.put("updateTime", a.getUpdateTime()!=null?formatter.format( a.getUpdateTime()):null);
		vList.add(mr);
	}

	/**
	 * 查询版本
	 *
	 *
	 * @return
	 */
	@ApiOperation(value = "查询版本", notes = "查询版本", httpMethod="POST" )
	@RequestMapping(value = {"/queryV"})
	public ResultVo<List> queryV() {
		ResultVo<List> result = Results.newResultVo();
		result.setSuccess(false);
		try {
			log.info("开始查询");
			List<String> vList = new ArrayList<>();
			HashMap<String, Object> searchMap = new HashMap<>();

            searchMap.put("isValid", DataConstant.YES);

			List<OptSelfServiceMachinesSoftVo> list = optSelfService.getListByWhere(searchMap);

			for (OptSelfServiceMachinesSoftVo a : list) {
//				Map<String, String> mr = new HashMap<>();
//				mr.put("areaId", a.getId().toString());
//				mr.put("areaName", a.getAreaName());
				vList.add(a.getSoftVersion());
			}
			result.setResult(vList);
			result.setCode("0");
			result.setSuccess(true);
			log.info("查询版本，返回ResultVo<PageListVo>={}", JSON.toJSONString(result));
		} catch (Exception e) {
			result.setResultDes("查询版本，失败原因：" + e.getMessage());
			log.error("查询版本，错误信息 error-info={}", e.getMessage());

		}
		return result;
	}


	/**
	 * 版本号比较
	 *
	 * @param v1
	 * @param v2
	 * @return 0代表相等，1代表左边大，-1代表右边大
	 * Utils.compareVersion("1.0.358_20180820090554","1.0.358_20180820090553")=1
	 */
	public static int compareVersion(String v1, String v2) {
		if(StringUtils.isNotBlank(v1)&&StringUtils.isNotBlank(v2)) {
			if (v1.equals(v2)) {
				return 0;
			}

			String[] version1Array = v1.split("[._]");
			String[] version2Array = v2.split("[._]");

			int index = 0;
			int minLen = Math.min(version1Array.length, version2Array.length);
			long diff = 0;

			while (index < minLen
					&& (diff = Long.parseLong(version1Array[index])
					- Long.parseLong(version2Array[index])) == 0) {
				index++;
			}
			if (diff == 0) {
				for (int i = index; i < version1Array.length; i++) {
					if (Long.parseLong(version1Array[i]) > 0) {
						return 1;
					}
				}

				for (int i = index; i < version2Array.length; i++) {
					if (Long.parseLong(version2Array[i]) > 0) {
						return -1;
					}
				}
				return 0;
			} else {
				return diff > 0 ? 1 : -1;
			}
		}else{
			return 0;
		}
	}


	//存放上传文件的路径
//	private static String LOCAL_IMG_DIR = Config.getRootPath() + "/upload/";
	private static final ImmutableSet<String> DEFAULT_ALLOW_FILE_TYPE = ImmutableSet.of(".jpg",".jpeg",".gif",".png",".xls",".xlsx", ".webp", ".exe", ".zip", ".tar");
	private static final Long MAX_FILE_SIZE = 1024*1024*20L;//最多20MB文件

	@RequestMapping(value = { "/upload" }, method = RequestMethod.POST)
	@ResponseBody
	public ResultVo<JSONObject> uploadProcess(@RequestParam("file") MultipartFile file, String path) {
		ResultVo<JSONObject> result = new ResultVo<JSONObject>();
		String LOCAL_IMG_DIR = this.getClass().getResource("/").getPath() + "/upload/";
		if(file == null || StringUtil.isEmpty(file.getOriginalFilename()) || StringUtil.isEmpty(path)){
			result.setSuccess(false);
			result.setCode("1000");
			result.setResultDes("上传的文件未获取到或者参数错误，请刷新重试！");
			return result;
		}

//		if(file.getSize()>MAX_FILE_SIZE){
//			result.setSuccess(false);
//			result.setCode("1001");
//			result.setResultDes("上传的文件太大，不允许上传！");
//			return result;
//		}

		if(!file.getOriginalFilename().contains(".") || ! DEFAULT_ALLOW_FILE_TYPE.contains(
				file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")))){
			result.setSuccess(false);
			result.setCode("1002");
			result.setResultDes("上传的文件类型不允许！");
			return result;
		}

		try{

			//获取文件存储路径
			FileUploadPathVo pathVo = FileUploadUtil.getHttpSrcFilePathVo(file.getOriginalFilename(),path);

			//保存原始图片
			File destFile = new File(LOCAL_IMG_DIR+pathVo.getPath());
			if(!destFile.getParentFile().exists()){
				destFile.getParentFile().mkdirs();
			}
			FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);
			Map<String, Object> stringStringMap = new HashMap<>();
			stringStringMap.put("file", file);
			stringStringMap.put("isPublic", 0);

			PostMethod filePost = new PostMethod(uploadUrl);
			HttpClient client = new HttpClient();

			Part[] parts = { new FilePart("file", destFile),new StringPart("isPublic", "0") };
			filePost.setRequestEntity(new MultipartRequestEntity(parts, filePost.getParams()));
			client.getHttpConnectionManager().getParams().setConnectionTimeout(5000);

			int status = client.executeMethod(filePost);
			JSONObject dataLast = new JSONObject();
			if (status == HttpStatus.SC_OK) {
				String response = new String(filePost.getResponseBodyAsString().getBytes("utf-8"));;
				JSONObject responJSON= JSONObject.parseObject(response);
				log.info("上传成功response："+response);
				String key= responJSON.getJSONObject("result").getString("key");
				String pathRes= responJSON.getJSONObject("result").getString("path");
				dataLast.put("addr", pathRes);
				dataLast.put("key", key);
			}

			dataLast.put("path", pathVo.getPath());

			result.setSuccess(true);
			result.setResult(dataLast );
			result.setResultDes("上传的文件成功！");
		}catch(Exception e){
			result.setCode("1003");
			result.setSuccess(false);
			result.setResultDes("上传文件过程中发生异常，请联系管理员！"+e.getMessage());
			return result;
		}
		return result;
	}
}

