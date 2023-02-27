package com.mall.product.application.external.admin;

import com.mall.common.response.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * IdGeneratorService
 * @author youfu.wang
 * @date 2021-08-24
 */
@FeignClient(name = "mall-admin" , fallback = IdGeneratorServiceFallback.class)
public interface IdGeneratorService {
    public final int BIZ_TYPE_PRODUCT=100;

    @RequestMapping(value = "/id/get", method = RequestMethod.GET)
    public ResponseResult get(@RequestParam Map<String, Object> params);
}
