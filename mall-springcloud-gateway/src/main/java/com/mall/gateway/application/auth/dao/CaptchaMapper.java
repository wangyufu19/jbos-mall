package com.mall.gateway.application.auth.dao;

import java.util.Map;

/**
 * CaptchaMapper
 * @author youfu.wang
 * @date 2019-01-31
 */
public interface CaptchaMapper {
	/**
	 * 根据令牌查询验证码信息
	 * @param parameterObject
	 * @return
	 */
	public Map<String, Object> getCaptcha(Map<String, Object> parameterObject);

	/**
	 * 新增验证码信息
	 * @param parameterObject
	 */
	public void addCaptcha(Map<String, Object> parameterObject);

	/**
	 * 删除验证码信息
	 * @param token
	 */
	public void deleteCaptcha(String token);

}
