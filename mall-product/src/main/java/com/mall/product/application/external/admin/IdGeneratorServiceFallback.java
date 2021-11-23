package com.mall.product.application.external.admin;

import com.mall.common.response.ResponseData;
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

    public ResponseData get(@RequestParam Map<String, Object> params){
        return ResponseData.error();
    }
}
