package com.mall.admin.application.service;

import com.mall.admin.domain.entity.UserInfo;
import com.mall.admin.infrastructure.repository.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * UserMgrService
 * @author youfu.wang
 * @date 2019-01-31
 */
@Service
public class UserMgrService {

	@Autowired
	private UserMapper userMapper;
	/**
	 * 查询用户数据列表
	 * @param params
	 * @return
	 */
	public void getUserList(Map<String, Object> params) {
		return;
	}
	/**
	 * 根据登录名称查询用户信息
	 * @param username
	 * @return
	 */
	public Map<String, Object> getUserInfoByLoginName(String username){
		Map<String, Object> parameterObject=new HashMap<String, Object>();
		parameterObject.put("username",username);
		Map<String, Object> dataMap=userMapper.getUserInfoByLoginName(parameterObject);
		return dataMap;
	}
	/**
	 * 根据ID查询用户信息
	 * @param userid
	 * @return
	 */
	public UserInfo getUserInfoById(String userid){
		Map<String, Object> parameterObject=new HashMap<String, Object>();
		parameterObject.put("userid",userid);
		Map<String, Object> dataMap=userMapper.getUserInfoById(parameterObject);
		if(dataMap!=null){
			UserInfo userInfo=new UserInfo();
//			userInfo.setUserId(String.valueOf(dataMap.get("ID")));
//			userInfo.setLoginName(StringUtils.replaceNull(dataMap.get("LOGIN_NAME")));
//			userInfo.setUsername(StringUtils.replaceNull(dataMap.get("USER_NAME")));
//			userInfo.setUserStatus(StringUtils.replaceNull(dataMap.get("USER_STATUS")));
//			userInfo.setOrgId(StringUtils.replaceNull(dataMap.get("ORG_ID")));
//			userInfo.setDepId(StringUtils.replaceNull(dataMap.get("DEP_ID")));
			return userInfo;
		}else{
			return null;
		}
	}
	/**
	 * 更新用户信息
	 * @param user
	 */
	//@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
	public void updateUserInfo(UserInfo user){

	}
}
