package com.mall.admin.application.external;

import com.mall.common.response.ResponseData;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TaskServiceFallback implements TaskService{

    public ResponseData listForPage(Map<String, Object> params) {
        return ResponseData.error();
    }
}
