package com.mall.admin.infrastructure.repository.mapper;

import com.mall.admin.domain.entity.UserInfo;

import java.util.List;
import java.util.Map;

/**
 * UserMapper
 * @author youfu.wang
 * @date 2019-01-31
 */
public interface UserMapper{
	/**
	 * 根据登录名称查询用户信息
	 * @param parameterObject
	 * @return
	 */
	public Map<String, Object> getUserInfoByLoginName(Map<String, Object> parameterObject);
	/**
	 * 根据ID查询用户信息
	 * @param parameterObject
	 * @return
	 */
	public Map<String, Object> getUserInfoById(Map<String, Object> parameterObject);
	/**
	 * 得到用户信息列表
	 * @return
	 */
	public List<UserInfo> getUserList();


}
