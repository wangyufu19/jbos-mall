package com.mall.admin.application.service.external.camunda;

import com.mall.common.response.ResponseResult;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

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

    public ResponseResult getProcessInstanceState(@RequestParam Map<String, Object> params){
        return ResponseResult.error();
    }

    public ResponseResult getProcessInstanceCurrentActivityId(@RequestParam Map<String, Object> params){
        return ResponseResult.error();
    }


    public ResponseResult suspendProcessInstanceById(@RequestBody Map<String, Object> params){
        return ResponseResult.error();
    }

    public ResponseResult activateProcessInstanceById(@RequestBody Map<String, Object> params){
        return ResponseResult.error();
    }
}
