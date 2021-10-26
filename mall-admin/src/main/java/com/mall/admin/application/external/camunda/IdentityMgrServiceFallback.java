package com.mall.admin.application.external.camunda;

import com.mall.common.response.ResponseData;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * IdentityMgrServiceFallback
 * @author youfu.wang
 * @date 2021-08-24
 */
@Component
public class IdentityMgrServiceFallback implements IdentityMgrService{


    public ResponseData create(Map<String, Object> params){
        return ResponseData.error();
    }
}
