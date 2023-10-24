package com.mall.gateway.application.auth.service;

import com.mall.common.utils.StringUtils;
import com.mall.gateway.application.external.redis.RedisService;
import com.mall.gateway.application.auth.dao.CaptchaMapper;
import com.mall.gateway.common.util.RedisKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * CaptchaService
 *
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
    private CaptchaMapper captchaMapper;

    /**
     * 根据令牌查询验证码信息
     *
     * @param token
     * @param text
     * @return
     */
    public boolean validate(String token, String text) {
        Object data = null;
        if (StringUtils.isNUll(token) || StringUtils.isNUll(text)) {
            return false;
        }
        //是否启用Redis
        if ("true".equals(redisEnable)) {
            data = redisService.get(RedisKey.getDataKey(token));
        } else {
            data = this.getCaptcha(token);
        }
        //每次验证则删除验证码数据
        this.deleteCaptcha(token);
        if (data == null) {
            return false;
        } else {
            if (text.equals(StringUtils.replaceNull(data))) {
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * 根据令牌查询验证码信息
     *
     * @param token
     * @return
     */
    public String getCaptcha(String token) {
        String text = "";
        Map<String, Object> parameterObject = new HashMap<String, Object>();
        parameterObject.put("token", token);
        Map<String, Object> retMap = captchaMapper.getCaptcha(parameterObject);
        if (retMap != null) {
            text = StringUtils.replaceNull(retMap.get("TEXT"));
        }
        return text;
    }

    /**
     * 新增验证码信息
     *
     * @param text
     * @param token
     */
    public void addCaptcha(String text, String token) {
        //是否启用Redis
        if ("true".equals(redisEnable)) {
            redisService.set(RedisKey.getDataKey(token), text, 60);
        } else {
            Map<String, Object> parameterObject = new HashMap<String, Object>();
            parameterObject.put("text", text);
            parameterObject.put("token", token);
            captchaMapper.addCaptcha(parameterObject);
        }
    }

    /**
     * 删除验证码信息
     *
     * @param token
     */
    public void deleteCaptcha(String token) {
        //是否启用Redis
        if ("true".equals(redisEnable)) {
            redisService.delete(RedisKey.getDataKey(token));
        } else {
            captchaMapper.deleteCaptcha(token);
        }
    }
}
