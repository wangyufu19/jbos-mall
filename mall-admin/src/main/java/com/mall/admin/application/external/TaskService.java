package com.mall.admin.application.external;

import com.mall.common.response.ResponseData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public ResponseData listForPage(@RequestBody Map<String, Object> params);
}
