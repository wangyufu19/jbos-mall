package com.mall.admin.infrastructure.repository.sm.mapper;
import java.util.List;
import java.util.Map;

/**
 * UserMapper
 * @author youfu.wang
 * @date 2019-01-31
 */
public interface UserMapper{
	/**
	 * 根据ID查询用户信息
	 * @param parameterObject
	 * @return
	 */
	public Map<String, Object> getUserInfoById(Map<String, Object> parameterObject);
	/**
	 * 根据登录名称查询用户信息
	 * @param parameterObject
	 * @return
	 */
	public Map<String, Object> getUserInfo(Map<String, Object> parameterObject);
	/**
	 * 查询用户信息
	 * @param parameterObject
	 * @return
	 */
	public Map<String, Object> getUserInfoByLoginName(Map<String, Object> parameterObject);
	/**
	 * 新增用户信息
	 * @param parameterObject
	 */
	public void addUserInfo(Map<String, Object> parameterObject);
	/**
	 * 新增用户默认角色
	 * @param parameterObject
	 */
	public void addUserDefaultRole(Map<String, Object> parameterObject);
	public List<Map<String, Object>> getUserWorkList(Map<String, Object> parameterObject);
}
