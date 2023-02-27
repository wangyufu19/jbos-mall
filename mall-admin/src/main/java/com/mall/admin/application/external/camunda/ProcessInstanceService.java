package com.mall.admin.application.external.camunda;

import com.mall.common.response.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
 * ProcessInstanceService
 * @author youfu.wang
 * @date 2021-08-24
 */
@Component
@FeignClient(name = "mall-workflow" , contextId = "process", fallback = ProcessInstanceServiceFallback.class)
public interface ProcessInstanceService {
    /**
     * 启动流程实例
     * @param params
     * @return
     */
    @PostMapping(value = "/process/startProcessInstance")
    public ResponseResult startProcessInstance(@RequestBody Map<String, Object> params);

    /**
     * 启动和完成流程实例
     * @param params
     * @return
     */
    @PostMapping(value = "/process/startAndFinishProcessInstance")
    public ResponseResult startAndFinishProcessInstance(@RequestBody Map<String, Object> params);
}
