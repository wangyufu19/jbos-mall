package com.mall.workflow.application.api;

import com.mall.common.response.ResponseData;
import com.mall.common.utils.StringUtils;
import io.swagger.annotations.ApiOperation;
import org.camunda.bpm.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * TaskManagerApi
 * @author youfu.wang
 * @date 2019-01-31
 */
@RestController
@RequestMapping("/task")
public class TaskManagerApi {
    @Autowired
    private TaskService taskService;

    @ResponseBody
    @PostMapping(value = "/complete")
    @ApiOperation("完成任务")
    public ResponseData complete(@RequestBody Map<String, Object> params){
        ResponseData responseData=ResponseData.ok();
        String processInstanceId=StringUtils.replaceNull(params.get("processInstanceId"));
        String taskId= StringUtils.replaceNull(params.get("taskId"));
        String[] assigneeList={"k0091","k0092"};
        Map<String,Object> variables=new HashMap<String,Object>();
        variables.put("assigneeList", Arrays.asList(assigneeList));
        taskService.complete(taskId,variables);
        return responseData;
    }
}
