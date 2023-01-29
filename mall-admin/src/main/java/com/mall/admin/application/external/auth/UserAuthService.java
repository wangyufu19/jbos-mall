package com.mall.admin.application.external.auth;

import com.mall.admin.common.config.FeignConfig;
import com.mall.common.response.ResponseData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * UserAuthService
 * @author youfu.wang
 * @date 2021-08-24
 */
@FeignClient(name = "mall-auth" , configuration = FeignConfig.class,fallback = UserAuthServiceFallback.class)
public interface UserAuthService {
    @RequestMapping(value = "/principal/getPrincipalInfo", method = RequestMethod.GET)
    public ResponseData getPrincipalInfo(@RequestParam Map<String, Object> params);
}
