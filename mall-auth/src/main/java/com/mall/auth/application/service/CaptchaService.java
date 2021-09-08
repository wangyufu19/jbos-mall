package com.mall.auth.application.service;


import com.mall.auth.infrastructure.repository.CaptchaRepository;
import com.mall.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;

/**
 * CaptchaService
 * @author youfu.wang
 * @date 2019-01-31
 */
@Service
public class CaptchaService {
	@Autowired
	private CaptchaRepository captchaRepository;
	/**
	 * 根据令牌查询验证码信息
	 * @param token
	 * @return
	 */
	public boolean validate(String token,String text){
		Map<String, Object> data=captchaRepository.getCaptcha(token,text);
		//每次验证则删除验证码数据
		this.deleteCaptcha(token);
		if(data==null){
			return false;
		}else {
			if(token.equals(StringUtils.replaceNull(data.get("TEXT")))){
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
		captchaRepository.addCaptcha(text,token);
	}

	/**
	 * 删除验证码信息
	 * @param token
	 */
	public void deleteCaptcha(String token){
		captchaRepository.deleteCaptcha(token);
	}
}
