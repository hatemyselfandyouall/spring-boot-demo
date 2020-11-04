package com.wangxinenpu.springbootdemo.config.component;


import com.wangxinenpu.springbootdemo.dataobject.vo.*;
import com.wangxinenpu.springbootdemo.service.facade.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import star.bizbase.exception.BizRuleException;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 创建审核业务公共类
 * 
 * @author Administrator
 *
 */
@Component
public class CheckComponent {

	protected Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private SysFunctionFacade sysFunctionFacade;
	@Autowired
	private LoginComponent loginComonent;
	@Autowired
	private SysUserFacade sysUserFacade;
	@Autowired
	private SysCheckConfigFacade sysCheckConfigFacade;
	@Autowired
	private SysCheckBusinessFacade sysCheckBusinessFacade;
	@Autowired
	private SysCheckProcessFacade sysCheckProcessFacade;
	@Autowired
	private SysCheckInformationFacade sysCheckInformationFacade;
	@Autowired
	private SysCheckRoleFacade sysCheckRoleFacade;
	@Autowired
	private SysCheckUserFacade sysCheckUserFacade;


	/**
	 * 创建审核业务
	 * 
	 * @param location
	 * @param operatioName
	 */
	public HashMap<String, Object> createCheckBusiness(String location, String operatioName) {
		HashMap<String, Object> retMap = new HashMap<>();
		try {
			// 从缓存获取用户信息
			Long userId = loginComonent.getLoginUserId();
			SysUserDTO sysUser = sysUserFacade.getByPrimaryKey(userId);
			List<SysFunctionDTO> functionList = sysFunctionFacade.findByLocation(location);
			if (functionList.size() > 0) {
				SysFunctionDTO function = functionList.get(0);
				HashMap<String, Object> searchMap = new HashMap<>();
				searchMap.put("funId", function.getId());
				searchMap.put("orgId", sysUser.getOrgId());
				// 查询是否配置审核
				List<SysCheckConfigDTO> checkConfigList = sysCheckConfigFacade.getListByWhere(searchMap);
				logger.info("createCheckBusiness 查询是否配置审核 funId={},orgId={},checkConfigList={}",function.getId(),sysUser.getOrgId(),checkConfigList);
				if (checkConfigList.size()>0) {
					SysCheckConfigDTO config = checkConfigList.get(0);
					Long configId = null;
					if (config.getAuFlag().equals("1")) {
						configId = config.getId();
						String isProRepeat = config.getIsProRepeat();
						SysCheckBusinessDTO checkBusiness = new SysCheckBusinessDTO();
						checkBusiness.setConfigId(configId);
						checkBusiness.setOperatioName(operatioName);
						checkBusiness.setTaskStatus("0");
						checkBusiness.setArraignmentTime(new Date());
						checkBusiness.setArraignmentPeople(sysUser.getDisplayName());
						checkBusiness.setArraignmentPeopleId(sysUser.getId().toString());
						// 取最新版本流程
						String version = sysCheckProcessFacade.findVersionByConfigId(configId);
						checkBusiness.setVersion(version);
						// 创建审核业务
						Long busId = sysCheckBusinessFacade.addSysCheckBusiness(checkBusiness);
						// 添加所有审核环节信息
						List<SysCheckProcessDTO> proList = sysCheckProcessFacade.getListByConfigId(configId);
						for (SysCheckProcessDTO p : proList) {
							logger.info("createCheckBusiness 添加审核信息 configId={},proList={}",configId,proList);
							SysCheckInformationDTO checkInformation = new SysCheckInformationDTO();
							if (p.getDispatchRule().equals("2")) {// 手动指派
								
							} else if (p.getDispatchRule().equals("3")) {// 系统轮询
								List<SysUserDTO> userList =  getUserList(p.getId(),busId,isProRepeat,p.getStep());
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
									sysCheckProcessFacade.updatepo(p);
								}
								
							}else if(p.getDispatchRule().equals("4")){//系统随机
								List<SysUserDTO> userList =  getUserList(p.getId(),busId,isProRepeat,p.getStep());
								if(userList.size()>0){
									Random random = new Random();
									int i = random.nextInt(userList.size());
									SysUserDTO user = userList.get(i);
									checkInformation.setCheckPeopleId(user.getId().toString());
									checkInformation.setCheckPeopleName(user.getDisplayName());
								}
								
							}else if(p.getDispatchRule().equals("5")){//按任务数量最少，规则分配
								List<SysUserDTO> userList =  getUserList(p.getId(),busId,isProRepeat,p.getStep());
								if(userList.size()>0){
									int i = 0;
									SysUserDTO user = null;
									for(SysUserDTO u : userList){
										HashMap<String, Object> map = new HashMap<>();
										map.put("checkPeopleId", u.getId());
										map.put("checkResult", "0");
										List<SysCheckInformationDTO> infoList = sysCheckInformationFacade.getListByCheckPeople(map);
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
											SysCheckProcessDTO pro = sysCheckProcessFacade.getByPrimaryKey(info.getProId());
											//还没到终审的环节不在列表展示
											int topStep = Integer.parseInt(pro.getStep()) - 1;
											searchMap.clear();
											searchMap.put("step", topStep);
											searchMap.put("configId", pro.getConfigId());
											searchMap.put("version", pro.getVersion());
											List<SysCheckProcessDTO> pList = sysCheckProcessFacade.getListByWhere(searchMap);
											if (pList.size() > 0) {
												searchMap.clear();
												searchMap.put("proId", pList.get(0).getId());
												searchMap.put("busId", info.getBusId());
												List<SysCheckInformationDTO> iList = sysCheckInformationFacade.getListByWhere(searchMap);
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
							checkInformation.setBusId(busId);
							checkInformation.setProId(p.getId());
							checkInformation.setCheckResult("0");
							
							sysCheckInformationFacade.addSysCheckInformation(checkInformation);
						}

						//业务关联日志ID
						checkBusiness.setId(busId);
						sysCheckBusinessFacade.updatepo(checkBusiness);

						retMap.put("flag", true);
						retMap.put("busId", busId);
						
						return retMap;
					}
				}

			}
		} catch (Exception e) {
			logger.error("createCheckBusiness 创建审核业务失败 message:={}",e.getMessage());
		}
		retMap.put("flag", false);
		return retMap;
	}

	/**
	 * 获取可指派人员对象
	 * @param proId
	 * @return
	 * @throws BizRuleException
	 */
	public List<SysUserDTO> getUserList(Long proId, Long busId, String isProRepeat, String step) throws BizRuleException {
		// 从缓存获取用户信息
		Long userId = loginComonent.getLoginUserId();
		List<SysUserDTO> userList = new ArrayList<>();
		List<String> userIds = new ArrayList<>();
		
		//获取提审人
		String arraignmentPeopleId = null;
		SysCheckBusinessDTO bus = sysCheckBusinessFacade.getByPrimaryKey(busId);
		if(null != bus){
			arraignmentPeopleId = bus.getArraignmentPeopleId();
		}

		HashMap<String, Object> userMap = new HashMap<>();
		userMap.put("proId", proId);
//		userMap.put("userId", userId);
//		userMap.put("arraignmentPeopleId", arraignmentPeopleId);
//		
//		userList = sysUserFacade.getUserListByProId(userMap);
//		logger.info("getUserList 获取可指派人员对象 proId={}, busId={}, userList.size={},userId={},arraignmentPeopleId={}",proId,proId,userList.size(),userId,arraignmentPeopleId);
		
		List<SysCheckUserDTO> uList = sysCheckUserFacade.getListByWhere(userMap);
		for (SysCheckUserDTO u : uList) {
			if (!u.getUserId().equals(userId)&& !u.getUserId().equals(arraignmentPeopleId)) {
				userIds.add(u.getUserId());
			}
		}

		HashMap<String, Object> roleMap = new HashMap<>();
		roleMap.put("proId", proId);
		List<SysCheckRoleDTO> rList = sysCheckRoleFacade.getListByWhere(roleMap);
		for (SysCheckRoleDTO r : rList) {
			List<SysUserRoleDTO> urList = sysUserFacade.findUserByRoleId(r.getRoleId());
			for (SysUserRoleDTO ur : urList) {
				if (!ur.getUserId().equals(userId)&& !ur.getUserId().equals(arraignmentPeopleId)) {
					userIds.add(ur.getUserId());
				}
			}
		}
		// set去重用户
//		List<String> listIds = new ArrayList<>(new HashSet<String>(userIds));
		String ids = "'" + StringUtils.join(userIds, "','") + "'";
		userList = sysUserFacade.getListByIds(ids);
		
		if(isProRepeat.equals("1")){//不允许重复
			if(!step.equals("0")){//初审不做判断
				HashMap<String, Object> infoMap = new HashMap<>();
				infoMap.put("busId", busId);
				List<SysCheckInformationDTO> infoList= sysCheckInformationFacade.getListByWhere(infoMap);
				if(infoList.size()>0){
					for(SysCheckInformationDTO i :infoList){
//						userList.remove(i.getCheckPeopleId());//移除已指派过的人员
						userList=userList.stream().filter(j->!j.getId().equals(i.getCheckPeopleId())).collect(Collectors.toList());
					}
				}
			}
		}
		
		return userList;
	}

}
