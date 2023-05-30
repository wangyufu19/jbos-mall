package com.mall.admin.application.service.external.camunda;

import com.mall.common.response.ResponseResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@FeignClient(name = "mall-workflow" , contextId = "task", fallback = TaskServiceFallback.class)
public interface TaskService {
    /**
     * 查询待办任务
     * @param params
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/task/listForPage")
    public ResponseResult listForPage(@RequestParam Map<String, Object> params);

    /**
     * 完成任务
     * @param params
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/task/complete")
    public ResponseResult complete(@RequestBody Map<String, Object> params);
}
