package com.mall.admin.application.service.external.camunda;

import com.mall.common.response.ResponseResult;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * IdentityMgrServiceFallback
 * @author youfu.wang
 * @date 2021-08-24
 */
@Component
public class IdentityMgrServiceFallback implements IdentityMgrService{


    public ResponseResult create(Map<String, Object> params){
        return ResponseResult.error();
    }
}
