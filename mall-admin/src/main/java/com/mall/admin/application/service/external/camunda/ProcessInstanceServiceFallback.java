package com.mall.admin.application.service.external.camunda;

import com.mall.common.response.ResponseResult;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
 * ProcessInstanceServiceFallback
 * @author youfu.wang
 * @date 2021-08-24
 */
@Component
public class ProcessInstanceServiceFallback implements ProcessInstanceService{

    public ResponseResult startProcessInstance(Map<String, Object> params) {
        return ResponseResult.error();
    }
    public ResponseResult startAndFinishProcessInstance(Map<String, Object> params){
        return ResponseResult.error();
    }

    public ResponseResult getProcessInstanceState(@RequestBody Map<String, Object> params){
        return ResponseResult.error();
    }
}
