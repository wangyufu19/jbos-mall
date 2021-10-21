package com.mall.admin.application.external;

import com.mall.common.response.ResponseData;
import org.springframework.stereotype.Component;
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

    public ResponseData startProcessInstance(Map<String, Object> params) {
        return ResponseData.error();
    }
    public ResponseData startAndFinishProcessInstance(@RequestBody Map<String, Object> params){
        return ResponseData.error();
    }
}
