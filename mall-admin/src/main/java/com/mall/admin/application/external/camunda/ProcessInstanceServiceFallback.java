package com.mall.admin.application.external.camunda;

import com.mall.common.response.ResponseData;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * ProcessInstanceServiceFallback
 * @author youfu.wang
 * @date 2021-08-24
 */
@Component
public class ProcessInstanceServiceFallback implements ProcessInstanceService{

    public ResponseData startProcessInstance(Map<String, Object> params) {
        return ResponseData.error();
    }
    public ResponseData startAndFinishProcessInstance(Map<String, Object> params){
        return ResponseData.error();
    }
}
