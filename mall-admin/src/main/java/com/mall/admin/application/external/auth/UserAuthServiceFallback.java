package com.mall.admin.application.external.auth;

import com.mall.common.response.ResponseResult;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * UserAuthServiceFallback
 * @author youfu.wang
 * @date 2021-08-24
 */
@Component
public class UserAuthServiceFallback implements UserAuthService{
    public ResponseResult getPrincipalInfo(@RequestParam Map<String, Object> params) {
        return ResponseResult.error();
    }
}
