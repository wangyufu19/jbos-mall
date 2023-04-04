package com.mall.admin.application.service.external.camunda;

import com.mall.common.response.ResponseResult;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TaskServiceFallback implements TaskService{

    public ResponseResult listForPage(Map<String, Object> params) {
        return ResponseResult.error();
    }
}
