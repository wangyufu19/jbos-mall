package com.mall.gateway.application.service.auth;
import com.mall.gateway.application.external.redis.RedisService;
import com.mall.gateway.infrastructure.repository.auth.CaptchaRepository;
import com.mall.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * CaptchaService
 * @author youfu.wang
 * @date 2019-01-31
 */
@Service
public class CaptchaService {
	@Value("${spring.redis.enable}")
	private String redisEnable;
	@Autowired
	private RedisService redisService;
	@Autowired
	private CaptchaRepository captchaRepository;
	/**
	 * 根据令牌查询验证码信息
	 * @param token
	 * @return
	 */
	public boolean validate(String token,String text){
		Object data=null;
		//是否启用Redis
		if("true".equals(redisEnable)){
			data=redisService.get(RedisKey.getDataKey(token));
		}else{
			data=captchaRepository.getCaptcha(token);
		}
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
		//是否启用Redis
		if("true".equals(redisEnable)) {
			redisService.set(RedisKey.getDataKey(token), text, 60);
		}else{
			captchaRepository.addCaptcha(text,token);
		}
	}

	/**
	 * 删除验证码信息
	 * @param token
	 */
	public void deleteCaptcha(String token){
		//是否启用Redis
		if("true".equals(redisEnable)) {
			redisService.delete(RedisKey.getDataKey(token));
		}else{
			captchaRepository.deleteCaptcha(token);
		}
	}
}
