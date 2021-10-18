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
    @GetMapping(value = "/listPage")
    @ApiOperation("用户任务")
    public ResponseData listPage(@RequestBody Map<String, Object> params){
        ResponseData res=ResponseData.ok();
        String processInstanceId=StringUtils.replaceNull(params.get("processInstanceId"));
        String userId=StringUtils.replaceNull(params.get("userId"));
        int pageNum=Integer.parseInt(StringUtils.replaceNull(params.get("pageNum")));
        int pageSize=Integer.parseInt(StringUtils.replaceNull(params.get("pageSize")));
        try{
            PageInfo pageInfo=taskMgrService.listPage(userId,pageNum,pageSize);
            res.setData(pageInfo);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res=ResponseData.error(ResponseData.RETCODE_FAILURE,e.getMessage());
        }
        return res;
    }
    @ResponseBody
    @PostMapping(value = "/assignee")
    @ApiOperation("完成任务")
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

}
