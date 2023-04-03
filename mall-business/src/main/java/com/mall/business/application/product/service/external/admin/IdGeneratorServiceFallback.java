package com.mall.business.application.product.service.external.admin;

import com.mall.common.response.ResponseResult;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * IdGeneratorServiceFallback
 * @author youfu.wang
 * @date 2021-08-24
 */
@Component
public class IdGeneratorServiceFallback implements IdGeneratorService {

    public ResponseResult get(@RequestParam Map<String, Object> params){
        return ResponseResult.error();
    }
}
