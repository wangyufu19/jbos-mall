package com.mall.admin.application.service.external.camunda;

import com.mall.common.response.ResponseResult;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Component
public class TaskServiceFallback implements TaskService{

    public ResponseResult listForPage(Map<String, Object> params) {
        return ResponseResult.error();
    }

    public ResponseResult assignee(@RequestBody Map<String, Object> params){
        return ResponseResult.error();
    }

    public ResponseResult addAssignee(@RequestBody Map<String, Object> params){
        return ResponseResult.error();
    }

    public ResponseResult reduceAssignee(@RequestBody Map<String, Object> params){
        return ResponseResult.error();
    }

    public ResponseResult complete(@RequestBody Map<String, Object> params){
        return ResponseResult.error();
    }

    public ResponseResult isDrawback(@RequestBody Map<String, Object> params){
        return ResponseResult.error();
    }

    public ResponseResult drawback(@RequestBody Map<String, Object> params){
        return ResponseResult.error();
    }

    public ResponseResult reject(@RequestBody Map<String, Object> params){
        return ResponseResult.error();
    }
}
