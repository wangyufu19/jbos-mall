package com.mall.workflow.application.service;

import com.github.pagehelper.PageInfo;
import com.mall.common.utils.StringUtils;
import com.mall.workflow.common.exception.IdentityException;
import lombok.Data;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * TaskMgrService
 * @author youfu.wang
 * @date 2019-01-31
 */
@Service
public class TaskMgrService {
    @Autowired
    private IdentityMgrService identityMgrService;
    @Autowired
    private TaskService taskService;

    /**
     * 查询用户待办任务
     * @param userId
     * @param pageNum
     * @param pageSize
     * @return
     */
    public PageInfo listPage(String userId,int pageNum,int pageSize) throws IdentityException {
        //用户认证
        if(!this.identityMgrService.auth(userId)){
            return null;
        }
        List<Task> list=taskService.createTaskQuery().taskAssignee(userId).listPage((pageNum-1)*pageSize,pageSize);
        long count=taskService.createTaskQuery().taskAssignee(userId).count();
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
        return pageInfo;
    }

    /**
     * 领取任务
     * @param taskId
     * @param userId
     */
    public void assignee(String taskId,String userId) throws IdentityException{
        //用户认证
        if(!this.identityMgrService.auth(userId)){
            return;
        }
        taskService.setAssignee(taskId,userId);
    }
    /**
     * 完成用户任务
     * @param params
     */
    public void complete(Map<String, Object> params) throws IdentityException{
        String userId=StringUtils.replaceNull(params.get("userId"));
        String processInstanceId= StringUtils.replaceNull(params.get("processInstanceId"));
        String taskId=StringUtils.replaceNull(params.get("taskId"));
        String assignees=StringUtils.replaceNull(params.get("assignees"));
        String assigneeName=StringUtils.replaceNull(params.get("assigneeName"));
        String multiInstance=StringUtils.replaceNull(params.get("multiInstance"));
        String assigneeCollectionName=StringUtils.replaceNull(params.get("assigneeCollectionName"));
        //用户认证
        if(!this.identityMgrService.auth(userId)){
            return;
        }
        //判断下一个任务是否多实例任务
        if("true".equals(multiInstance)){
            String[] assigneeList=assignees.split(",");
            if(assigneeList!=null&&assigneeList.length>0){
                params.put(assigneeCollectionName, Arrays.asList(assigneeList));
            }
        }else{
            params.put(assigneeName,assignees);
        }

        Task task=taskService.createTaskQuery().taskAssignee(userId).singleResult();
        if(task!=null){
            taskService.complete(taskId,params);
        }
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
