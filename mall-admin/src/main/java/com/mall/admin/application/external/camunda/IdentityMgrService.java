package com.mall.admin.application.external.camunda;

import com.mall.common.response.ResponseData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
 * IdentityMgrService
 * @author youfu.wang
 * @date 2021-08-24
 */
@Component
@FeignClient(name = "mall-workflow" , contextId = "user", fallback = IdentityMgrServiceFallback.class)
public interface IdentityMgrService {

    @PostMapping(value = "/user/create")
    public ResponseData create(@RequestBody Map<String, Object> params);
}
