package com.mall.gateway.application.auth.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * UserMapper
 * @author youfu.wang
 * @date 2019-01-31
 */
public interface UserAuthMapper {
	/**
     * 用户认证
	 * @param parameterObject
     * @return
     */
	public Map<String,Object> login(Map<String, Object> parameterObject);
	/**
	 * 得到用户认证信息
	 * @param parameterObject
	 * @return
	 */
	public Map<String,Object> getUserAuthInfo(Map<String, Object> parameterObject);
	/**
	 * 得到认证用户角色
	 * @param parameterObject
	 * @return
	 */
	public List<HashMap> getAuthUserRole(Map<String, Object> parameterObject);
}
