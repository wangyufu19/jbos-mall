package com.mall.admin.application.api.dashboard;

import com.mall.admin.application.service.external.camunda.TaskService;
import com.mall.admin.application.service.sm.ProcessTaskService;
import com.mall.common.page.PageParam;
import com.mall.admin.domain.entity.sm.TaskStep;
import com.mall.common.response.ResponseResult;
import com.mall.common.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dashboard")
@Slf4j
@Api("我的工作台接口")
public class MyWorkApi {

    @Autowired
    private ProcessTaskService processTaskService;
    @Autowired
    private TaskService taskService;

    /**
     * 查询我的待办列表
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/getMyWorkList")
    @ApiOperation("查询我的待办列表")
    public ResponseResult getMyWorkList(@RequestParam Map<String, Object> params) {
        ResponseResult res;
        String workType = StringUtils.replaceNull(params.get("workType"));
        try {
            PageParam pageParam = PageParam.getPageParam(params);
            if ("waiting".equals(workType)) {
                res = processTaskService.getUserTaskList(pageParam,params);
            } else {
                res = processTaskService.getUserTaskProcessedList(pageParam,params);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }

    /**
     * 查询流程任务处理步骤
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/getUserTaskStepList")
    @ApiOperation("查询流程任务处理步骤")
    public ResponseResult getUserTaskStepList(@RequestParam Map<String, Object> params) {
        ResponseResult res = ResponseResult.ok();
        try {
            List<TaskStep> taskSteps = processTaskService.getUserTaskStepList(params);
            res.setData(taskSteps);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }

    /**
     * 撤回用户任务
     *
     * @param params
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/drawbackUserTask")
    @ApiOperation("撤回用户任务")
    public ResponseResult drawbackUserTask(@RequestBody Map<String, Object> params) {
        ResponseResult res = ResponseResult.ok();
        try {
            res = taskService.drawback(params);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }
}
