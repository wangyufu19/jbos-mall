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
     *
     * @param params
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/task/listForPage")
    public ResponseResult listForPage(@RequestParam Map<String, Object> params);

    /**
     * 领取任务
     * @param params
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/task/assignee")
    public ResponseResult assignee(@RequestBody Map<String, Object> params);

    /**
     * 新增任务领取人
     * @param params
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/task/addAssignee")
    public ResponseResult addAssignee(@RequestBody Map<String, Object> params);

    /**
     * 减去任务领取人
     * @param params
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/task/reduceAssignee")
    public ResponseResult reduceAssignee(@RequestBody Map<String, Object> params);
    /**
     * 完成任务
     *
     * @param params
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/task/complete")
    public ResponseResult complete(@RequestBody Map<String, Object> params);

    /**
     * 是否可撤回任务
     *
     * @param params
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/task/isDrawback")
    public ResponseResult isDrawback(@RequestBody Map<String, Object> params);

    /**
     * 撤回任务
     *
     * @param params
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/task/drawback")
    public ResponseResult drawback(@RequestBody Map<String, Object> params);

    /**
     * 驳回任务
     * @param params
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/task/reject")
    public ResponseResult reject(@RequestBody Map<String, Object> params);
}