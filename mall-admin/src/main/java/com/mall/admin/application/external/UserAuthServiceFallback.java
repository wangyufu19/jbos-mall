package com.mall.admin.application.external;

import com.mall.common.response.ResponseData;
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
    public ResponseData getPrincipalInfo(@RequestParam Map<String, Object> params) {
        return ResponseData.error();
    }
}
