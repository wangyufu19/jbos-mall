package com.mall.admin.application.service;

import com.mall.admin.domain.entity.UserInfo;
import com.mall.admin.infrastructure.repository.UserMgrRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.UUID;

/**
 * UserMgrService
 * @author youfu.wang
 * @date 2019-01-31
 */
@Service
public class UserMgrService {

	@Autowired
	private UserMgrRepository userMgrRepository;
	/**
	 * 查询用户数据列表
	 * @param params
	 * @return
	 */
	public void getUserList(Map<String, Object> params) {
		return;
	}
	/**
	 * 根据ID查询用户信息
	 * @param userid
	 * @return
	 */
	public UserInfo getUserInfoById(String userid){
		return userMgrRepository.getUserInfoById(userid);
	}
	/**
	 * 根据登录名称查询用户信息
	 * @param username
	 * @return
	 */
	public Map<String, Object> getUserInfoByLoginName(String username){
		return userMgrRepository.getUserInfoByLoginName(username);
	}
	/**
	 * 得到用户信息
	 * @param loginName
	 */
	public Map<String, Object> getUserInfo(String loginName){
		return userMgrRepository.getUserInfo(loginName);
	}
	/**
	 * 新增用户信息
	 * @param parameterObject
	 */
	@Transactional
	public void addUserInfo(Map<String, Object> parameterObject){
		String id=UUID.randomUUID().toString();
		parameterObject.put("id", id);
		parameterObject.put("userId", id);
		userMgrRepository.addUserInfo(parameterObject);
		userMgrRepository.addUserDefaultRole(parameterObject);
	}
	/**
	 * 更新用户信息
	 * @param user
	 */
	public void updateUserInfo(UserInfo user){

	}
}
