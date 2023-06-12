package com.mall.admin.application.service.external.camunda;

import com.mall.common.response.ResponseResult;
import com.mall.common.utils.StringUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 查询流程实例状态
     * @param params
     * @return
     */
    @GetMapping(value = "/process/getProcessInstanceState")
    public ResponseResult getProcessInstanceState(@RequestParam Map<String, Object> params);

    /**
     * 查询流程实例当前活动
     * @param params
     * @return
     */
    @GetMapping(value = "/process/getProcessInstanceCurrentActivityId")
    public ResponseResult getProcessInstanceCurrentActivityId(@RequestParam Map<String, Object> params);

    @PostMapping("/process/suspendProcessInstanceById")
    public ResponseResult suspendProcessInstanceById(@RequestBody Map<String, Object> params);


    @PostMapping("/process/activateProcessInstanceById")
    public ResponseResult activateProcessInstanceById(@RequestBody Map<String, Object> params);
}
