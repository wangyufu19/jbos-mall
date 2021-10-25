package com.mall.workflow.application.api;

import com.github.pagehelper.PageInfo;
import com.mall.common.response.ResponseData;
import com.mall.common.utils.StringUtils;
import com.mall.workflow.application.service.TaskMgrService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;

/**
 * TaskMgrApi
 * @author youfu.wang
 * @date 2019-01-31
 */
@Slf4j
@RestController
@RequestMapping("/task")
@Api("任务管理接口")
public class TaskMgrApi{
    @Autowired
    private TaskMgrService taskMgrService;


    @ResponseBody
    @GetMapping(value = "/listForPage")
    @ApiOperation("查询待办任务")
    public ResponseData listForPage(@RequestParam Map<String, Object> params){
        ResponseData res=ResponseData.ok();
        String userId=StringUtils.replaceNull(params.get("userId"));
        int pageNum=Integer.parseInt(StringUtils.replaceNull(params.get("pageNum")));
        int pageSize=Integer.parseInt(StringUtils.replaceNull(params.get("pageSize")));
        try{
            if(log.isDebugEnabled()){
                log.info("============查询用户["+userId+"]待办任务");
            }
            PageInfo pageInfo=taskMgrService.listForPage(userId,pageNum,pageSize);
            res.setData(pageInfo);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res=ResponseData.error(ResponseData.RETCODE_FAILURE,e.getMessage());
        }
        return res;
    }
    @ResponseBody
    @GetMapping(value = "/completedListForPage")
    @ApiOperation("查询已办任务")
    public ResponseData completedListForPage(@RequestBody Map<String, Object> params){
        ResponseData res=ResponseData.ok();
        String userId=StringUtils.replaceNull(params.get("userId"));
        int pageNum=Integer.parseInt(StringUtils.replaceNull(params.get("pageNum")));
        int pageSize=Integer.parseInt(StringUtils.replaceNull(params.get("pageSize")));
        try{
            PageInfo pageInfo=taskMgrService.completedListForPage(userId,pageNum,pageSize);
            res.setData(pageInfo);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res=ResponseData.error(ResponseData.RETCODE_FAILURE,e.getMessage());
        }
        return res;
    }
    @ResponseBody
    @PostMapping(value = "/assignee")
    @ApiOperation("领取任务")
    public ResponseData assignee(@RequestBody Map<String, Object> params){
        ResponseData res=ResponseData.ok();
        String taskId=StringUtils.replaceNull(params.get("taskId"));
        String userId=StringUtils.replaceNull(params.get("userId"));
        try {
            taskMgrService.assignee(taskId,userId);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res=ResponseData.error(ResponseData.RETCODE_FAILURE,e.getMessage());
        }
        return res;
    }

    @ResponseBody
    @PostMapping(value = "/complete")
    @ApiOperation("完成任务")
    public ResponseData complete(@RequestBody Map<String, Object> params){
        ResponseData res=ResponseData.ok();
        try {
            taskMgrService.complete(params);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res=ResponseData.error(ResponseData.RETCODE_FAILURE,e.getMessage());
        }
        return res;
    }
    @ResponseBody
    @PostMapping(value = "/isDrawback")
    @ApiOperation("是否可撤回任务")
    public ResponseData isDrawback(@RequestBody Map<String, Object> params){
        ResponseData res=ResponseData.ok();
        boolean isDrawback=false;
        String userId=StringUtils.replaceNull(params.get("userId"));
        String processDefinitionId= StringUtils.replaceNull(params.get("processDefinitionId"));
        String processInstanceId= StringUtils.replaceNull(params.get("processInstanceId"));
        String taskId=StringUtils.replaceNull(params.get("taskId"));
        try {
            isDrawback=taskMgrService.isDrawback(userId,processInstanceId,taskId);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res=ResponseData.error(ResponseData.RETCODE_FAILURE,e.getMessage());
        }
        Map<String,Object> data=new HashMap<String,Object>();
        data.put("processInstanceId",processInstanceId);
        data.put("taskId",taskId);
        data.put("isDrawback",isDrawback);
        res.setData(data);
        return res;
    }
    @ResponseBody
    @PostMapping(value = "/drawback")
    @ApiOperation("撤回任务")
    public ResponseData drawback(@RequestBody Map<String, Object> params){
        ResponseData res=ResponseData.ok();
        String userId=StringUtils.replaceNull(params.get("userId"));
        String processDefinitionId= StringUtils.replaceNull(params.get("processDefinitionId"));
        String processInstanceId= StringUtils.replaceNull(params.get("processInstanceId"));
        String taskId=StringUtils.replaceNull(params.get("taskId"));
        try {
            taskMgrService.drawback(userId,processDefinitionId,processInstanceId,taskId);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res=ResponseData.error(ResponseData.RETCODE_FAILURE,e.getMessage());
        }
        return res;
    }
    @ResponseBody
    @PostMapping(value = "/reject")
    @ApiOperation("驳回任务")
    public ResponseData reject(@RequestBody Map<String, Object> params){
        ResponseData res=ResponseData.ok();
        String userId=StringUtils.replaceNull(params.get("userId"));
        String processInstanceId= StringUtils.replaceNull(params.get("processInstanceId"));
        String taskId=StringUtils.replaceNull(params.get("taskId"));
        try {
            taskMgrService.reject(userId,processInstanceId,taskId);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res=ResponseData.error(ResponseData.RETCODE_FAILURE,e.getMessage());
        }
        return res;
    }
}
