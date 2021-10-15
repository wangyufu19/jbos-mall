package com.mall.workflow.application.api;

import com.github.pagehelper.PageInfo;
import com.mall.common.response.ResponseData;
import com.mall.common.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * TaskMgrApi
 * @author youfu.wang
 * @date 2019-01-31
 */
@RestController
@RequestMapping("/task")
@Api("任务管理接口")
public class TaskMgrApi {
    public final static String MULTI_INSTANCE_ASSIGNEE_VARIABLE="assigneeList";
    @Autowired
    private TaskService taskService;
    @ResponseBody
    @GetMapping(value = "/listPage")
    @ApiOperation("用户任务")
    public ResponseData listPage(@RequestBody Map<String, Object> params){
        ResponseData res=ResponseData.ok();
        String processInstanceId=StringUtils.replaceNull(params.get("processInstanceId"));
        String taskId=StringUtils.replaceNull(params.get("taskId"));
        String assignee=StringUtils.replaceNull(params.get("assignee"));
        int pageNum=Integer.parseInt(StringUtils.replaceNull(params.get("pageNum")));
        int pageSize=Integer.parseInt(StringUtils.replaceNull(params.get("pageSize")));
        List<Task> list=taskService.createTaskQuery().taskAssignee(assignee).listPage((pageNum-1)*pageSize,pageSize);
        long count=taskService.createTaskQuery().taskAssignee(assignee).count();
        PageInfo pageInfo=new PageInfo();
        List tasks=new ArrayList();
        for(Task task:list){
            UserTask userTask=new UserTask();
            userTask.setProcessDefinitionId(task.getProcessDefinitionId());
            userTask.setProcessInstanceId(task.getProcessInstanceId());
            userTask.setTaskDefinitionKey(task.getTaskDefinitionKey());
            userTask.setTaskId(task.getId());
            userTask.setTaskName(task.getName());
            userTask.setExecutionId(task.getExecutionId());
            userTask.setAssignee(task.getAssignee());
            userTask.setCreateTime(task.getCreateTime());
            tasks.add(userTask);
        }
        pageInfo.setPages(((int)count+pageSize-1)/pageSize);
        pageInfo.setPageNum(pageNum);
        pageInfo.setPageSize(pageSize);
        pageInfo.setTotal(count);
        pageInfo.setList(tasks);
        res.setData(pageInfo);
        return res;
    }
    @ResponseBody
    @PostMapping(value = "/complete")
    @ApiOperation("完成任务")
    public ResponseData complete(@RequestBody Map<String, Object> params){
        ResponseData res=ResponseData.ok();
        String processInstanceId=StringUtils.replaceNull(params.get("processInstanceId"));
        String taskId=StringUtils.replaceNull(params.get("taskId"));
        String assignees=StringUtils.replaceNull(params.get("assignees"));
        String[] assigneeList=assignees.split(",");
        if(assigneeList!=null&&assigneeList.length>0){
            params.put(TaskMgrApi.MULTI_INSTANCE_ASSIGNEE_VARIABLE, Arrays.asList(assigneeList));
        }
        taskService.complete(taskId,params);
        return res;
    }
    @Data
    public class UserTask{
        private String processDefinitionId;
        private String processInstanceId;
        private String taskDefinitionKey;
        private String taskId;
        private String taskName;
        private String assignee;
        private String executionId;
        private Date createTime;
    }
}
