package com.mall.workflow.application.api;

import com.github.pagehelper.PageInfo;
import com.mall.common.response.ResponseResult;
import com.mall.workflow.application.service.ProcessInstanceService;
import com.mall.workflow.application.service.TaskMgrService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * TaskMgrApi
 *
 * @author youfu.wang
 * @date 2019-01-31
 */
@Slf4j
@RestController
@RequestMapping("/task")
@Api("任务管理接口")
public class TaskMgrApi {
    @Autowired
    private ProcessInstanceService processInstanceService;
    @Autowired
    private TaskMgrService taskMgrService;


    @ResponseBody
    @GetMapping(value = "/listForPage")
    @ApiOperation("查询待办任务")
    public ResponseResult listForPage(@RequestParam Map<String, Object> params) {
        ResponseResult res = ResponseResult.ok();
        String userId = String.valueOf(params.get("userId"));
        int pageNum = Integer.parseInt(String.valueOf(params.get("pageNum")));
        int pageSize = Integer.parseInt(String.valueOf(params.get("pageSize")));
        try {
            if (log.isDebugEnabled()) {
                log.info("============查询用户[" + userId + "]待办任务");
            }
            PageInfo pageInfo = taskMgrService.listForPage(userId, pageNum, pageSize);
            res.setData(pageInfo);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponseResult.error(ResponseResult.CODE_FAILURE, e.getMessage());
        }
        return res;
    }

    @ResponseBody
    @GetMapping(value = "/completedListForPage")
    @ApiOperation("查询已办任务")
    public ResponseResult completedListForPage(@RequestBody Map<String, Object> params) {
        ResponseResult res = ResponseResult.ok();
        String userId = String.valueOf(params.get("userId"));
        int pageNum = Integer.parseInt(String.valueOf(params.get("pageNum")));
        int pageSize = Integer.parseInt(String.valueOf(params.get("pageSize")));
        try {
            if (log.isDebugEnabled()) {
                log.info("============查询用户[" + userId + "]已办任务");
            }
            PageInfo pageInfo = taskMgrService.completedListForPage(userId, pageNum, pageSize);
            res.setData(pageInfo);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponseResult.error(ResponseResult.CODE_FAILURE, e.getMessage());
        }
        return res;
    }

    @ResponseBody
    @PostMapping(value = "/assignee")
    @ApiOperation("领取任务")
    public ResponseResult assignee(@RequestBody Map<String, Object> params) {
        ResponseResult res = ResponseResult.ok();
        String userId = String.valueOf(params.get("userId"));
        String processInstanceId = String.valueOf(params.get("processInstanceId"));
        String taskId = String.valueOf(params.get("taskId"));
        String assignee = String.valueOf(params.get("assignee"));
        try {
            if (log.isDebugEnabled()) {
                log.info("============[" + userId + "]用户领取任务");
            }
            taskMgrService.assignee(userId, processInstanceId, taskId, assignee);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponseResult.error(ResponseResult.CODE_FAILURE, e.getMessage());
        }
        return res;
    }

    @ResponseBody
    @PostMapping(value = "/addAssignee")
    @ApiOperation("新增任务领取人")
    public ResponseResult addAssignee(@RequestBody Map<String, Object> params) {
        ResponseResult res = ResponseResult.ok();
        String userId = String.valueOf(params.get("userId"));
        String processInstanceId = String.valueOf(params.get("processInstanceId"));
        String activityId = String.valueOf(params.get("activityId"));
        String activityName = String.valueOf(params.get("activityName"));
        String elementVariable = String.valueOf(params.get("elementVariable"));
        String assignees = String.valueOf(params.get("assignees"));
        try {
            if (log.isDebugEnabled()) {
                log.info("============[" + userId + "]用户新增任务领取人");
            }
            taskMgrService.addAssignee(userId, processInstanceId, activityId, activityName, elementVariable, assignees);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponseResult.error(ResponseResult.CODE_FAILURE, e.getMessage());
        }
        return res;
    }

    @ResponseBody
    @PostMapping(value = "/reduceAssignee")
    @ApiOperation("减去任务领取人")
    public ResponseResult reduceAssignee(@RequestBody Map<String, Object> params) {
        ResponseResult res = ResponseResult.ok();
        String userId = String.valueOf(params.get("userId"));
        String processInstanceId = String.valueOf(params.get("processInstanceId"));
        String assignee = String.valueOf(params.get("assignee"));
        try {
            if (log.isDebugEnabled()) {
                log.info("============[" + userId + "]用户减去任务领取人");
            }
            int reduceNum = taskMgrService.reduceAssignee(userId, processInstanceId, assignee);
            if (reduceNum <= 0) {
                res = ResponseResult.error(ResponseResult.CODE_FAILURE, "当前活动实例必须包含一个子活动实例");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponseResult.error(ResponseResult.CODE_FAILURE, e.getMessage());
        }
        return res;
    }

    @ResponseBody
    @PostMapping(value = "/complete")
    @ApiOperation("完成任务")
    public ResponseResult complete(@RequestBody Map<String, Object> params) {
        ResponseResult res = ResponseResult.ok();
        String userId = String.valueOf(params.get("userId"));
        String processInstanceId = String.valueOf(params.get("processInstanceId"));
        try {
            if (log.isDebugEnabled()) {
                log.info("============[" + userId + "]用户完成任务");
            }
            String taskId = taskMgrService.complete(params);
            String processInstanceState = processInstanceService.getProcessInstanceState(processInstanceId);
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("taskId", taskId);
            data.put("processInstanceState", processInstanceState);
            res.setData(data);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponseResult.error(ResponseResult.CODE_FAILURE, e.getMessage());
        }
        return res;
    }

    @ResponseBody
    @PostMapping(value = "/isDrawback")
    @ApiOperation("是否可撤回任务")
    public ResponseResult isDrawback(@RequestBody Map<String, Object> params) {
        ResponseResult res = ResponseResult.ok();
        boolean isDrawback = false;
        String userId = String.valueOf(params.get("userId"));
        String processDefinitionId = String.valueOf(params.get("processDefinitionId"));
        String processInstanceId = String.valueOf(params.get("processInstanceId"));
        String taskId = String.valueOf(params.get("taskId"));
        try {
            if (log.isDebugEnabled()) {
                log.info("============[" + userId + "]用户是否可撤回任务[" + taskId + "]");
            }
            isDrawback = taskMgrService.isDrawback(userId, processInstanceId, taskId);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponseResult.error(ResponseResult.CODE_FAILURE, e.getMessage());
        }
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("processInstanceId", processInstanceId);
        data.put("taskId", taskId);
        data.put("isDrawback", isDrawback);
        res.setData(data);
        return res;
    }

    @ResponseBody
    @PostMapping(value = "/drawback")
    @ApiOperation("撤回任务")
    public ResponseResult drawback(@RequestBody Map<String, Object> params) {
        ResponseResult res = ResponseResult.ok();
        String userId = String.valueOf(params.get("userId"));
        String processDefinitionId = String.valueOf(params.get("processDefinitionId"));
        String processInstanceId = String.valueOf(params.get("processInstanceId"));
        String taskId = String.valueOf(params.get("taskId"));
        boolean isDrawback;
        try {
            if (log.isDebugEnabled()) {
                log.info("============[" + userId + "]用户撤回任务[" + taskId + "]");
            }
            isDrawback = taskMgrService.drawback(userId, processDefinitionId, processInstanceId, taskId);
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("processInstanceId", processInstanceId);
            data.put("taskId", taskId);
            data.put("isDrawback", isDrawback);
            res.setData(data);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponseResult.error(ResponseResult.CODE_FAILURE, e.getMessage());
        }
        return res;
    }

    @ResponseBody
    @PostMapping(value = "/reject")
    @ApiOperation("驳回任务")
    public ResponseResult reject(@RequestBody Map<String, Object> params) {
        ResponseResult res = ResponseResult.ok();
        String userId = String.valueOf(params.get("userId"));
        String processInstanceId = String.valueOf(params.get("processInstanceId"));
        String taskId = String.valueOf(params.get("taskId"));
        try {
            if (log.isDebugEnabled()) {
                log.info("============[" + userId + "]用户驳回任务[" + taskId + "]");
            }
            Map<String, Object> data = taskMgrService.reject(userId, processInstanceId, taskId);
            res.setData(data);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponseResult.error(ResponseResult.CODE_FAILURE, e.getMessage());
        }
        return res;
    }
}
