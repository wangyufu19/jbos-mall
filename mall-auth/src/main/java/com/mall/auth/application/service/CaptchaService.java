package com.mall.auth.application.service;


import com.mall.auth.common.redis.RedisKey;
import com.mall.auth.common.redis.RedisService;
import com.mall.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * CaptchaService
 * @author youfu.wang
 * @date 2019-01-31
 */
@Service
public class CaptchaService {
	@Autowired
	private RedisService redisService;
	/**
	 * 根据令牌查询验证码信息
	 * @param token
	 * @return
	 */
	public boolean validate(String token,String text){
		Object data=redisService.get(RedisKey.getDataKey(token));
		//每次验证则删除验证码数据
		this.deleteCaptcha(token);
		if(data==null){
			return false;
		}else {
			if(text.equals(StringUtils.replaceNull(data))){
				return true;
			}else{
				return false;
			}
		}
	}

	/**
	 * 新增验证码信息
	 * @param text
	 * @param token
	 */
	public void addCaptcha(String text,String token){
		redisService.set(RedisKey.getDataKey(token),text,60);
	}

	/**
	 * 删除验证码信息
	 * @param token
	 */
	public void deleteCaptcha(String token){
		redisService.delete(RedisKey.getDataKey(token));
	}
}
