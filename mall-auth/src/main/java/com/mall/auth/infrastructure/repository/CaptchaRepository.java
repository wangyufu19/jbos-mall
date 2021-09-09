package com.mall.auth.infrastructure.repository;

import com.mall.auth.infrastructure.repository.mapper.CaptchaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * CaptchaRepository
 * @author youfu.wang
 * @date 2020-06-24
 */
@Component
public class CaptchaRepository {
    @Autowired
    private CaptchaMapper captchaMapper;

    /**
     * 根据令牌查询验证码信息
     * @param token
     * @return
     */
    public Map<String, Object> getCaptcha(String token){
        Map<String, Object> parameterObject=new HashMap<String, Object>();
        parameterObject.put("token",token);
        return captchaMapper.getCaptcha(parameterObject);
    }

    /**
     * 新增验证码信息
     * @param text
     * @param token
     */
    public void addCaptcha(String text,String token){
        Map<String, Object> parameterObject=new HashMap<String, Object>();
        parameterObject.put("text",text);
        parameterObject.put("token",token);
        captchaMapper.addCaptcha(parameterObject);
    }

    /**
     * 删除验证码信息
     * @param token
     */
    public void deleteCaptcha(String token){
        captchaMapper.deleteCaptcha(token);
    }
}
