package com.wangxinenpu.springbootdemo.service.impl;

import com.wangxinenpu.springbootdemo.service.facade.SysCheckConfigFacade;
import com.wangxinenpu.springbootdemo.dataobject.vo.*;
import com.wangxinenpu.springbootdemo.service.service.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import star.bizbase.exception.BizRuleException;
import star.util.DateUtil;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 审核配置facade服务实现类
 * 
 * @author Administrator
 *
 */
@Service
public class SysCheckConfigImpl implements SysCheckConfigFacade {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private SysCheckConfigService sysCheckConfigService;
	@Autowired
	private SysFunctionService sysFunctionService;
	@Autowired
	private SysCheckProcessService sysCheckProcessService;
	@Autowired
	private SysCheckInformationService sysCheckInformationService;
	@Autowired
	private SysCheckUserService sysCheckUserService;
	@Autowired
	private SysCheckRoleService sysCheckRoleService;
	@Autowired
	private SysUserRoleService sysUserRoleService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysCheckErrMsgService sysCheckErrMsgService;

	@Autowired
	private SysCheckSigningService sysCheckSigningService;
	@Autowired
	private SysUserOrgService sysUserOrgService;
	@Autowired
	private SysCheckChangelogService sysCheckChangelogService;

	@Override
	@Transactional
	public Long addSysCheckConfig(SysCheckConfigDTO po) throws BizRuleException {
		return sysCheckConfigService.addSysCheckConfig(po);
	}

	@Override
	public SysCheckConfigDTO getByPrimaryKey(Long id) {
		return sysCheckConfigService.getByPrimaryKey(id);
	}

	@Override
	@Transactional
	public int updatepo(SysCheckConfigDTO po) {
		return sysCheckConfigService.updatepo(po);
	}

	@Override
	public List<SysCheckConfigDTO> getListByWhere(HashMap<String, Object> searchMap, int start, int size) {
		return sysCheckConfigService.getListByWhere(searchMap, start, size);
	}

	@Override
	public List<SysCheckConfigDTO> getListByWhere(HashMap<String, Object> searchMap) {
		return sysCheckConfigService.getListByWhere(searchMap);
	}

	@Override
	public int getCountByWhere(HashMap<String, Object> searchMap) {
		return sysCheckConfigService.getCountByWhere(searchMap);
	}

	@Override
	@Transactional
	public void deleteByPrimaryKey(Long id) throws BizRuleException {
		 sysCheckConfigService.deleteById(id);
	}
	/**
	 * 查询是否配置通用审核
	 */
	@Override
	public boolean isCheckConfig(Long funId, Long busId, Long userId, List<SysCheckErrMsgDTO> checkErrMsgList) {
		return this.isCheckConfigs(funId, busId, userId, checkErrMsgList,null);
	}
	/**
	 * 查询是否配置通用审核
	 */
	@Override
	public boolean isCheckConfig(Long funId, Long busId, Long userId, List<SysCheckErrMsgDTO> checkErrMsgList, String hierarchy) {
		
		return this.isCheckConfigs(funId, busId, userId, checkErrMsgList,hierarchy);
	}
	
	/**
	 * 查询是否配置通用审核
	 * @param funId  --菜单id
	 * @param busId --业务id
	 * @param userId --用户id
	 * @param checkErrMsgList --异常信息
	 * @param hierarchy --审核层级。。（几级审核）
	 * @return
	 */
	public boolean isCheckConfigs(Long funId, Long busId, Long userId, List<SysCheckErrMsgDTO> checkErrMsgList, String hierarchy) {
		Long orgId = 0L;
		Date start = new Date();
		String dttempstr = DateUtil.dateFormate(start, DateUtil.FULL_STANDARD_PATTERN);
		//#########打印有重复数据比较多，有些调试日志没有必要打印，或改成debug级别
		logger.info("isCheckConfigs 调用方法开始 funId={},dttempstr={},userId={},busId={},hierarchy={}"
				,funId,dttempstr,userId,busId,hierarchy);
		//1.业务逻辑判断控制。
		if(funId==null || busId==null || userId==null) {
			//正常情况抛出异常
			logger.error("isCheckConfigs参数异常 funId={},busId={},userId={}",funId,busId,userId);
			return false;
		}
		//如果金额判断层级为0，直接返回，不需要审核，当成自动审核
		if("0".equals(hierarchy)){
			logger.error("isCheckConfigs hierarchy为0，不需要审核 funId={},busId={},userId={}",funId,busId,userId);
			return false;
		}
		
		//############此行代码可以移到else里，减少查询次数，机构id放到缓存中取
		SysUserDTO sysUser = sysUserService.getByPrimaryKey(userId);
		List<SysUserOrgDTO> userOrgList = sysUserOrgService.findByUserId(userId);
		if(userOrgList.size()>0){
			orgId = userOrgList.get(0).getOrgId();
		}else{
			orgId = sysUser.getOrgId();
//			logger.error("isCheckConfigs用户无关联机构funId={},sysUser={}",funId,sysUser);
//			return false;
		} 
		
		// 查询是否配置审核
		HashMap<String, Object> searchMap = new HashMap<>();
		searchMap.put("funId", funId);
		searchMap.put("orgId", orgId);
		List<SysCheckConfigDTO> checkConfigList = sysCheckConfigService.getListByWhere(searchMap);
		if(CollectionUtils.isEmpty(checkConfigList)) {
			//正常情况抛出异常
			logger.info("isCheckConfigs没有审核配置funId={},sysUser={},checkConfigList={}",funId,sysUser,checkConfigList);
			return false;
		}
//		ComOperateLogDTO oper = comOperateLogFacade.getByPrimaryKey(busId);
//		if(oper==null) {
//			logger.info("comOperateLogFacade.getByPrimaryKey dttempstr={},oper={}"
//					, DateUtil.dateFormate(new Date(), DateUtil.FULL_STANDARD_PATTERN),oper);
//			return false;
//		}
		//2.业务处理
		SysCheckConfigDTO config = checkConfigList.get(0);
		if(!"1".equals(config.getAuFlag()) ) {
			logger.info("isCheckConfigs 非手动配置审核  funId={},busId={},userId={}",funId,busId,userId);
			return false;
		}
		if("0".equals(config.getIsLeading()) ) {
			logger.info("isCheckConfigs 审核配置以柜面为准  funId={},busId={},userId={}",funId,busId,userId);
		}
		
		logger.info("isCheckConfigs funId={},config={}",funId,config);
		String arraignmentPeopleId = null;//获取提审人
		String projid = null;//事项流水号
//		if(null != oper){
//			arraignmentPeopleId = String.valueOf(oper.getOperateUserId());
//			projid = oper.getProjid();
//		}
		
		Long configId = config.getId();
		String isProRepeat = config.getIsProRepeat();
		List<SysCheckProcessDTO> proList = sysCheckProcessService.getListByConfigId(configId);
		if(CollectionUtils.isEmpty(proList)) {
			//正常情况抛出异常
			logger.info("isCheckConfigs没有审核配置流程信息funId={},sysUser={},proList={}",funId,sysUser,proList);
			return false;
		}
		logger.info("获取流程 configId={},proList={}",configId,proList);
		for(int pl=0; pl<proList.size(); pl++){
			SysCheckProcessDTO p = proList.get(pl);
			/* 获取指派人员 */
			List<SysUserDTO> userList = new ArrayList<>();
			List<String> userIds = new ArrayList<>();
			HashMap<String, Object> userMap = new HashMap<>();
			userMap.put("proId", p.getId());
			//##############减少for循环操作数据库，把需要查询的条件进行批量查询，对数据库进行操作（例如通过id批量查询，批量新增数据等）
			//减少在for循环里操作数据库的次数
			List<SysCheckUserDTO> uList = sysCheckUserService.getListByWhere(userMap);
			for (SysCheckUserDTO u : uList) {
//						if (!u.getUserId().equals(userId) && !u.getUserId().equals(arraignmentPeopleId)) {
//							userIds.add(u.getUserId());
//						}//上线前先允许提审人与审核人是同一人
				userIds.add(u.getUserId());
			}
			HashMap<String, Object> roleMap = new HashMap<>();
			roleMap.put("proId", p.getId());
			List<SysCheckRoleDTO> rList = sysCheckRoleService.getListByWhere(roleMap);
			for (SysCheckRoleDTO r : rList) {
				List<SysUserRoleDTO> urList = sysUserRoleService.findByRoleId(r.getRoleId());
				for (SysUserRoleDTO ur : urList) {
//							if (!ur.getUserId().equals(userId) && !ur.getUserId().equals(arraignmentPeopleId)) {
//								userIds.add(ur.getUserId());
//							}//上线前先允许提审人与审核人是同一人
					userIds.add(ur.getUserId());
				}
			}
			
			SysCheckInformationDTO checkInformation = new SysCheckInformationDTO();
			//说明为手动配置审核，有审核人员；否则以天正流程为准，没有审核人员
			if(userIds.size()>0){
				// set去重用户
				// List<String> listIds = new ArrayList<>(new HashSet<String>(userIds));
				String ids = "'" + StringUtils.join(userIds, "','") + "'";
				userList = sysUserService.getListByIds(ids);
				
				if(isProRepeat.equals("1")){//不允许重复
					if(!p.getStep().equals("0")){//初审不做判断
						HashMap<String, Object> infoMap = new HashMap<>();
						infoMap.put("busId", busId);
						List<SysCheckInformationDTO> infoList= sysCheckInformationService.getListByWhere(infoMap);
						if(infoList.size()>0){
							for(SysCheckInformationDTO i :infoList){
								//移除已指派过的人员
								userList=userList.stream().filter(j->!j.getId().equals(i.getCheckPeopleId())).collect(Collectors.toList());
							}
						}
					}
				}
				/***    ***/
				//添加环节审核信息
				if(p.getDispatchRule().equals("1")){//自主签领
					logger.info("isCheckConfigs 自主签领 userList={}",userList);
					for(SysUserDTO user: userList){
						SysCheckSigningDTO signin =  new SysCheckSigningDTO();
						signin.setProId(p.getId());
						signin.setBusId(busId);
						signin.setSigningUserId(user.getId().toString());
						signin.setProName(p.getName());
						sysCheckSigningService.addSysCheckSigning(signin);
					}
				} else if (p.getDispatchRule().equals("2")) {// 手动指派
					
				} else if (p.getDispatchRule().equals("3")) {// 系统轮询
					logger.info("isCheckConfigs 系统轮询 projid={}",projid);
					//获取审核人
					String flag = "false";
					if(StringUtils.isNotEmpty(projid)){
						 HashMap<String, Object> resMap =  getProPeopleId(projid,p.getStep());
						 flag = resMap.get("flag").toString();
						 if("true".equals(flag)){
							 checkInformation.setCheckPeopleId(resMap.get("checkPeopleId").toString());
							 checkInformation.setCheckPeopleName(resMap.get("checkPeopleName").toString());
						 }
					}
					//projid为空或其他流程环节都没指派过人
					if("false".equals(flag)){
						if(userList.size()>0){
							//可指派总人数
							int userNum = userList.size();
							//当前轮询数
							int pollingNum = p.getPollingNum();
							if(pollingNum == userNum){
								pollingNum = 0;
							}
							checkInformation.setCheckPeopleId(userList.get(pollingNum).getId().toString());
							checkInformation.setCheckPeopleName(userList.get(pollingNum).getDisplayName());

							//修改轮询数
							p.setPollingNum(pollingNum+1);
							sysCheckProcessService.updatepo(p);
						}
					}
				}else if(p.getDispatchRule().equals("4")){ //系统随机
					logger.info("isCheckConfigs 系统随机 projid={}",projid);
					//获取审核人
					String flag = "false";
					if(StringUtils.isNotEmpty(projid)){
						 HashMap<String, Object> resMap =  getProPeopleId(projid,p.getStep());
						 flag = resMap.get("flag").toString();
						 if("true".equals(flag)){
							 checkInformation.setCheckPeopleId(resMap.get("checkPeopleId").toString());
							 checkInformation.setCheckPeopleName(resMap.get("checkPeopleName").toString());
						 }
					}
					//projid为空或其他流程环节都没指派过人
					if("false".equals(flag)){
						if(userList.size()>0){
							Random random = new Random();
							int i = random.nextInt(userList.size());
							SysUserDTO user = userList.get(i);
							checkInformation.setCheckPeopleId(user.getId().toString());
							checkInformation.setCheckPeopleName(user.getDisplayName());
						}
					}
				}else if(p.getDispatchRule().equals("5")){//按任务数量最少，规则分配
					logger.info("isCheckConfigs 规则分配 projid={},userList={}",projid,userList);
					//获取审核人
					String flag = "false";
					if(StringUtils.isNotEmpty(projid)){
						 HashMap<String, Object> resMap =  getProPeopleId(projid,p.getStep());
						 flag = resMap.get("flag").toString();
						 if("true".equals(flag)){
							 checkInformation.setCheckPeopleId(resMap.get("checkPeopleId").toString());
							 checkInformation.setCheckPeopleName(resMap.get("checkPeopleName").toString());
						 }
					}
					//projid为空或其他流程环节都没指派过人
					if("false".equals(flag)){
						if(userList.size()>0){
							int i = 0;
							SysUserDTO user = null;
							for(SysUserDTO u : userList){
								HashMap<String, Object> map = new HashMap<>();
								map.put("checkPeopleId", u.getId());
								map.put("checkResult", "0");
								List<SysCheckInformationDTO> infoList = sysCheckInformationService.getListByCheckPeople(map);
								//根据业务ID分组
								Map<Long, List<SysCheckInformationDTO>> infoMap=infoList.stream().collect(Collectors.groupingBy(SysCheckInformationDTO::getBusId));
								infoList = new ArrayList<>();
								for(Long businessId:infoMap.keySet()){
									List<SysCheckInformationDTO> list =infoMap.get(businessId);
									SysCheckInformationDTO info = null;
									for(int f=0;f<list.size();f++){
										info = list.get(f);
										if(info.getCheckResult().equals("0")){
											break; 
										}
										if(info.getCheckResult().equals("1")){
											continue; 
										}
										if(info.getCheckResult().equals("2")){
											break; 
										}
									}
									infoList.add(info);
								}
								int count = infoList.size();
								for(int j = 0; j < infoList.size(); j++){
									SysCheckInformationDTO info = infoList.get(j);
									SysCheckProcessDTO pro = sysCheckProcessService.getByPrimaryKey(info.getProId());
									//还没到终审的环节不在列表展示
									int topStep = Integer.parseInt(pro.getStep()) - 1;
									searchMap.clear();
									searchMap.put("step", topStep);
									searchMap.put("configId", pro.getConfigId());
									searchMap.put("version", pro.getVersion());
									List<SysCheckProcessDTO> pList = sysCheckProcessService.getListByWhere(searchMap);
									if (pList.size() > 0) {
										searchMap.clear();
										searchMap.put("proId", pList.get(0).getId());
										searchMap.put("busId", info.getBusId());
										List<SysCheckInformationDTO> iList = sysCheckInformationService.getListByWhere(searchMap);
										if (iList.size() > 0) {
											String res = iList.get(0).getCheckResult();
											if (!"1".equals(res)) {
												//前环节没通，不做任务计算
												count --;
												continue;
											}
										}
									}
								}
								if(i==0){
									i=count;
									user = u;
								}
								if(count<i){//比前一个少
									i=count;
									user = u;
								}
							}
							checkInformation.setCheckPeopleId(user.getId().toString());
							checkInformation.setCheckPeopleName(user.getDisplayName());
						}
					}
				}
			}
			checkInformation.setBusId(busId);
			checkInformation.setProId(p.getId());
			checkInformation.setCheckResult("0");
			sysCheckInformationService.addSysCheckInformation(checkInformation);
			logger.info("addSysCheckInformation busId={},proId={},busId={},checkInformation={}"
					,busId,p.getId(),busId,checkInformation);
			
			
			//达到配置层级，后环节忽略
			if(StringUtils.isNotEmpty(hierarchy)){
				if(Integer.parseInt(hierarchy) == pl+1){
					break;
				}
			}
			logger.info("isCheckConfigs funId={},pl={},hierarchy={}",funId,pl,hierarchy);
		}
		
//		ComOperateLogDTO oplog = new ComOperateLogDTO();
//		oplog.setId(busId);
//		oplog.setHierarchy(hierarchy);
//		comOperateLogFacade.updateComOperateLog(oplog);
//		logger.info("isCheckConfigs修改层级 funId={},userId={},busId={},oplog={}"
//				,funId,userId,busId,oplog);
		
		
		//保存业务异常信息
		if(CollectionUtils.isNotEmpty(checkErrMsgList)){
			for(SysCheckErrMsgDTO em: checkErrMsgList){
				sysCheckErrMsgService.addSysCheckErrMsg(em);
			}
		}
		Date end = new Date();
		String endstr = DateUtil.dateFormate(end, DateUtil.FULL_STANDARD_PATTERN);
		logger.info("isCheckConfigs 调用结束 funId={},dttempstr={},userId={},busId={}"
				,funId,endstr,userId,busId);
		return true;
	}

	
	/**
	 * 获取同一事项的关联业务，同一流程环节配置的审核操作人员
	 * @param projid
	 * @param step
	 * @return
	 */
	public HashMap<String, Object> getProPeopleId(String projid, String step){
		HashMap<String, Object> resMap = new HashMap<>();
		HashMap<String, Object> map = new HashMap<>();
		map.put("projid", projid);
		map.put("valid", '1');//有效
//		List<ComOperateLogDTO> operList = comOperateLogFacade.getListByWhere(map);
//		if(operList.size()>0){
//			for(ComOperateLogDTO op : operList){
//				Long funId = op.getFunctionId();
//				Long busId = op.getId();
//				map.clear();
//				map.put("funId", funId);
//				// 查询是否配置审核
//				List<SysCheckConfigDTO> checkConfigList = sysCheckConfigService.getListByWhere(map);
//				if(checkConfigList.size()>0){
//					Long configId = checkConfigList.get(0).getId();
//					map.clear();
//					map.put("configId", configId);
//					map.put("step", step);
//					List<SysCheckProcessDTO> proList = sysCheckProcessService.getListByWhere(map);
//					for(SysCheckProcessDTO pro: proList){
//						map.put("busId", busId);
//						map.put("proId", pro.getId());
//						List<SysCheckInformationDTO> infoList= sysCheckInformationService.getListByWhere(map);
//						if(infoList.size()>0){
//							String checkPeopleId = infoList.get(0).getCheckPeopleId();
//							String checkPeopleName = infoList.get(0).getCheckPeopleName();
//							if(StringUtils.isNotEmpty(checkPeopleId)){
//								resMap.put("flag", true);
//								resMap.put("checkPeopleId", checkPeopleId);
//								resMap.put("checkPeopleName", checkPeopleName);
//								return resMap;
//							}
//						}
//					}
//				}
//			}
//		}
		resMap.put("flag", false);
		return resMap;
	}

	
	/**
	 * 查询财务审核是否配置通用审核
	 */
	@Override
	public boolean isFinanceCheckConfig(Long funId, Long busId, Long userId) {
		Date start = new Date();
		String dttempstr = DateUtil.dateFormate(start, DateUtil.FULL_STANDARD_PATTERN);
		logger.info("isFinanceCheckConfig 财务审核调用方法开始 funId={},dttempstr={},userId={},busId={}"
				,funId,dttempstr,userId,busId);
		//1.业务逻辑判断控制。
		if(funId==null || busId==null || userId==null) {
			logger.error("isFinanceCheckConfig 参数异常 funId={},busId={},userId={}",funId,busId,userId);
			return false;
		}
		Long orgId = 0L;
		SysUserDTO sysUser = sysUserService.getByPrimaryKey(userId);
		List<SysUserOrgDTO> userOrgList = sysUserOrgService.findByUserId(userId);
		if(userOrgList.size()>0){
			orgId = userOrgList.get(0).getOrgId();
		}else{
			orgId = sysUser.getOrgId();
//			logger.error("isCheckConfigs用户无关联机构funId={},sysUser={}",funId,sysUser);
//			return false;
		} 
		// 查询是否配置审核
		HashMap<String, Object> searchMap = new HashMap<>();
		searchMap.put("funId", funId);
		searchMap.put("orgId", orgId);
		List<SysCheckConfigDTO> checkConfigList = sysCheckConfigService.getListByWhere(searchMap);
		if(CollectionUtils.isEmpty(checkConfigList)) {
			//正常情况抛出异常
			logger.error("isFinanceCheckConfig 没有审核配置funId={},sysUser={},checkConfigList={}",funId,sysUser,checkConfigList);
			return false;
		}
//		ComOperateLogDTO oper = comOperateLogFacade.getByPrimaryKey(busId);
//		if(oper==null) {
//			logger.error("comOperateLogFacade.getByPrimaryKey dttempstr={},oper={}"
//					, DateUtil.dateFormate(new Date(), DateUtil.FULL_STANDARD_PATTERN),oper);
//			return false;
//		}
		//2.业务处理
		SysCheckConfigDTO config = checkConfigList.get(0);
		if(!"1".equals(config.getAuFlag()) ) {
			logger.info("isFinanceCheckConfig 非手动配置审核 funId={}",funId);
			return false;
		}
		logger.info("isFinanceCheckConfig funId={},config={}",funId,config);
			
		//获取提审人
		String arraignmentPeopleId = null;
//		if(null != oper){
//			arraignmentPeopleId = String.valueOf(oper.getOperateUserId());
//		}
//
		Long configId = checkConfigList.get(0).getId();
		String isProRepeat = checkConfigList.get(0).getIsProRepeat();
		List<SysCheckProcessDTO> proList = sysCheckProcessService.getListByConfigId(configId);
		logger.info("获取流程 configId={},proList={}",configId,proList);
		for(SysCheckProcessDTO p : proList){
			/* 获取指派人员 */
			List<SysUserDTO> userList = new ArrayList<>();
			List<String> userIds = new ArrayList<>();
			HashMap<String, Object> userMap = new HashMap<>();
			userMap.put("proId", p.getId());
			List<SysCheckUserDTO> uList = sysCheckUserService.getListByWhere(userMap);
			for (SysCheckUserDTO u : uList) {
//				if (!u.getUserId().equals(userId) && !u.getUserId().equals(arraignmentPeopleId)) {
//				userIds.add(u.getUserId());
//			}//上线前先允许提审人与审核人是同一人
				userIds.add(u.getUserId());
			}
			HashMap<String, Object> roleMap = new HashMap<>();
			roleMap.put("proId", p.getId());
			List<SysCheckRoleDTO> rList = sysCheckRoleService.getListByWhere(roleMap);
			for (SysCheckRoleDTO r : rList) {
				List<SysUserRoleDTO> urList = sysUserRoleService.findByRoleId(r.getRoleId());
				for (SysUserRoleDTO ur : urList) {
//					if (!ur.getUserId().equals(userId) && !ur.getUserId().equals(arraignmentPeopleId)) {
//					userIds.add(ur.getUserId());
//				}//上线前先允许提审人与审核人是同一人
					userIds.add(ur.getUserId());
				}
			}
			// set去重用户
			// List<String> listIds = new ArrayList<>(new HashSet<String>(userIds));
			String ids = "'" + StringUtils.join(userIds, "','") + "'";
			userList = sysUserService.getListByIds(ids);
			
			if(isProRepeat.equals("1")){//不允许重复
				if(!p.getStep().equals("0")){//初审不做判断
					HashMap<String, Object> infoMap = new HashMap<>();
					infoMap.put("busId", busId);
					List<SysCheckInformationDTO> infoList= sysCheckInformationService.getListByWhere(infoMap);
					if(infoList.size()>0){
						for(SysCheckInformationDTO i :infoList){
							//移除已指派过的人员
							userList=userList.stream().filter(j->!j.getId().equals(i.getCheckPeopleId())).collect(Collectors.toList());
						}
					}
				}
			}
			/***    ***/
			//添加环节审核信息
			SysCheckInformationDTO checkInformation = new SysCheckInformationDTO();
			if(p.getDispatchRule().equals("1")){//自主签领
				for(SysUserDTO user: userList){
					logger.info("isCheckConfigs 自主签领 userList={}",userList);
					SysCheckSigningDTO signin =  new SysCheckSigningDTO();
					signin.setProId(p.getId());
					signin.setBusId(busId);
					signin.setSigningUserId(user.getId().toString());
					signin.setProName(p.getName());
					//添加自主签领默认是待签领状态0
					signin.setState((long) 0);
					sysCheckSigningService.addSysCheckSigning(signin);
				}
			} else if (p.getDispatchRule().equals("2")) {// 手动指派
				
			} else if (p.getDispatchRule().equals("3")) {// 系统轮询
				logger.info("isCheckConfigs 系统轮询");
				if(userList.size()>0){
					//可指派总人数
					int userNum = userList.size();
					//当前轮询数
					int pollingNum = p.getPollingNum();
					if(pollingNum == userNum){
						pollingNum = 0;
					}
					checkInformation.setCheckPeopleId(userList.get(pollingNum).getId().toString());
					checkInformation.setCheckPeopleName(userList.get(pollingNum).getDisplayName());

					//修改轮询数
					p.setPollingNum(pollingNum+1);
					sysCheckProcessService.updatepo(p);
				}
			}else if(p.getDispatchRule().equals("4")){//系统随机
				logger.info("isCheckConfigs 系统随机 ");
				if(userList.size()>0){
					Random random = new Random();
					int i = random.nextInt(userList.size());
					SysUserDTO user = userList.get(i);
					checkInformation.setCheckPeopleId(user.getId().toString());
					checkInformation.setCheckPeopleName(user.getDisplayName());
				}
			}else if(p.getDispatchRule().equals("5")){//按任务数量最少，规则分配
				logger.info("isCheckConfigs 规则分配 userList={}",userList);
				if(userList.size()>0){
					int i = 0;
					SysUserDTO user = null;
					for(SysUserDTO u : userList){
						HashMap<String, Object> map = new HashMap<>();
						map.put("checkPeopleId", u.getId());
						map.put("checkResult", "0");
						map.put("GROUPLIST", "bus_id");
						List<SysCheckInformationDTO> infList= sysCheckInformationService.getListByWhere(searchMap);
						if(i==0){
							i=infList.size();
							user = u;
						}
						if(infList.size()<i){//比前一个少
							user = u;
						}
					}
					checkInformation.setCheckPeopleId(user.getId().toString());
					checkInformation.setCheckPeopleName(user.getDisplayName());
				}
			}
			checkInformation.setBusId(busId);
			checkInformation.setProId(p.getId());
			checkInformation.setCheckResult("0");
			
			sysCheckInformationService.addSysCheckInformation(checkInformation);
		}
		Date end = new Date();
		String endstr = DateUtil.dateFormate(end, DateUtil.FULL_STANDARD_PATTERN);
		logger.info("isFinanceCheckConfig 财务审核调用结束 funId={},dttempstr={},userId={},busId={}"
				,funId,endstr,userId,busId);
		return true;
	}

	@Override
	public Integer saveSysCheckChangelog(SysCheckChangelogDTO dto) throws BizRuleException {
		return sysCheckChangelogService.saveSysCheckChangelog(dto);
	}

	@Override
	public List<SysCheckChangelogDTO> getSysCheckChangelogList(Map<String, Object> map, int start, int size) throws BizRuleException {
		return sysCheckChangelogService.getSysCheckChangelogList(map,start,size);
	}
	
	/**
	 * 查询是否配置审核人员
	 * @throws BizRuleException 
	 */
	public boolean getCheckPeople(Long funId, Long orgId) throws BizRuleException{
		logger.info("getCheckPeople 查询是否配置审核人员 funId={},orgId={}",funId,orgId);
		if(null == funId || null == orgId){
			logger.error("isFinanceCheckConfig 请求参数为空 funId={},orgId={}",funId,orgId);
			throw new BizRuleException("请求参数为空");
		}else{
			// 查询是否配置审核
			HashMap<String, Object> searchMap = new HashMap<>();
			searchMap.put("funId", funId);
			searchMap.put("orgId", orgId);
			List<SysCheckConfigDTO> checkConfigList = sysCheckConfigService.getListByWhere(searchMap);
			if(CollectionUtils.isEmpty(checkConfigList)) {
				//正常情况抛出异常
				logger.error("isFinanceCheckConfig 没有审核配置  funId={},orgId={}",funId,orgId);
				throw new BizRuleException("没有配置审核");
			}
			SysCheckConfigDTO config = checkConfigList.get(0);
			if(!"1".equals(config.getAuFlag()) ) {
				logger.info("isFinanceCheckConfig 非手动配置审核 funId={}",funId);
				throw new BizRuleException("非手动配置审核");
			}
			List<SysCheckProcessDTO> proList = sysCheckProcessService.getListByConfigId(config.getId());
			logger.info("获取流程 configId={},proList={}",config.getId(),proList);
			List<String> userIds = new ArrayList<>();
			for(SysCheckProcessDTO p : proList){
				HashMap<String, Object> map = new HashMap<>();
				map.put("proId", p.getId());
				List<SysCheckUserDTO> uList = sysCheckUserService.getListByWhere(map);
				for (SysCheckUserDTO u : uList) {
					userIds.add(u.getUserId());
				}
				List<SysCheckRoleDTO> rList = sysCheckRoleService.getListByWhere(map);
				for (SysCheckRoleDTO r : rList) {
					List<SysUserRoleDTO> urList = sysUserRoleService.findByRoleId(r.getRoleId());
					for (SysUserRoleDTO ur : urList) {
						userIds.add(ur.getUserId());
					}
				}
			}
			if(CollectionUtils.isEmpty(userIds)){
				throw new BizRuleException("审核环节没有人员或角色配置");
			}
			return true;
		}
	}
	
	/**
	 * 批量修改审核配置
	 */
	@Override
	@Transactional
	public void updateByBatch(List<SysCheckConfigDTO> list){
		sysCheckConfigService.updateByBatch(list);
	}
	
	
}
