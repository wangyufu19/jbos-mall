package com.mall.workflow.application.service;

import com.github.pagehelper.PageInfo;
import com.mall.common.utils.StringUtils;
import com.mall.workflow.common.exception.CamundaException;
import lombok.Data;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.ProcessEngineException;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.history.HistoricActivityInstance;
import org.camunda.bpm.engine.history.HistoricTaskInstance;
import org.camunda.bpm.engine.runtime.ActivityInstance;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.runtime.ProcessInstanceModificationBuilder;
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
    private RuntimeService runtimeService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private ActivityInstanceService activityInstanceService;

    /**
     * 查询待办任务
     * @param userId
     * @param pageNum
     * @param pageSize
     * @return
     */
    public PageInfo listForPage(String userId, int pageNum, int pageSize) throws CamundaException {
        //用户认证
        if (!this.identityMgrService.auth(userId)) {
            return null;
        }
        List<Task> list = taskService
                .createTaskQuery()
                .taskAssignee(userId)
                .orderByTaskCreateTime()
                .asc()
                .listPage((pageNum - 1) * pageSize, pageSize);
        long count = taskService.createTaskQuery().taskAssignee(userId).count();
        PageInfo pageInfo = new PageInfo();
        List tasks = new ArrayList();
        for (Task task : list) {
            TaskEntity taskEntity = new TaskEntity();
            taskEntity.setProcessDefinitionId(task.getProcessDefinitionId());
            taskEntity.setProcessInstanceId(task.getProcessInstanceId());
            taskEntity.setTaskDefinitionKey(task.getTaskDefinitionKey());
            taskEntity.setTaskId(task.getId());
            taskEntity.setTaskName(task.getName());
            taskEntity.setExecutionId(task.getExecutionId());
            taskEntity.setAssignee(task.getAssignee());
            taskEntity.setCreateTime(task.getCreateTime());
            tasks.add(taskEntity);
        }
        pageInfo.setPages(((int) count + pageSize - 1) / pageSize);
        pageInfo.setPageNum(pageNum);
        pageInfo.setPageSize(pageSize);
        pageInfo.setTotal(count);
        pageInfo.setList(tasks);
        return pageInfo;
    }
    /**
     * 查询已办任务
     * @param userId
     * @param pageNum
     * @param pageSize
     * @return
     */
    public PageInfo completedListForPage(String userId, int pageNum, int pageSize) throws CamundaException {
        PageInfo pageInfo = new PageInfo();
        //用户认证
        if (!this.identityMgrService.auth(userId)) {
            return null;
        }
        List<HistoricTaskInstance> historicActivityInstances = historyService
                .createHistoricTaskInstanceQuery()
                .taskAssignee(userId)
                .finished()
                .orderByHistoricTaskInstanceEndTime()
                .desc()
                .listPage((pageNum - 1) * pageSize, pageSize);
        long count = historyService
                .createHistoricTaskInstanceQuery()
                .taskAssignee(userId)
                .finished()
                .count();
        List tasks = new ArrayList();
        for (HistoricTaskInstance historicTaskInstance : historicActivityInstances) {
            TaskEntity taskEntity = new TaskEntity();
            taskEntity.setProcessDefinitionId(historicTaskInstance.getProcessDefinitionId());
            taskEntity.setProcessInstanceId(historicTaskInstance.getProcessInstanceId());
            taskEntity.setTaskDefinitionKey(historicTaskInstance.getTaskDefinitionKey());
            taskEntity.setTaskId(historicTaskInstance.getId());
            taskEntity.setTaskName(historicTaskInstance.getName());
            taskEntity.setExecutionId(historicTaskInstance.getExecutionId());
            taskEntity.setAssignee(historicTaskInstance.getAssignee());
            taskEntity.setCreateTime(historicTaskInstance.getStartTime());
            taskEntity.setEndTime(historicTaskInstance.getEndTime());
            tasks.add(taskEntity);
        }
        pageInfo.setPages(((int) count + pageSize - 1) / pageSize);
        pageInfo.setPageNum(pageNum);
        pageInfo.setPageSize(pageSize);
        pageInfo.setTotal(count);
        pageInfo.setList(tasks);
        return pageInfo;
    }

    /**
     * 得到任务
     * @param userId
     * @param processInstanceId
     * @param taskId
     * @return
     * @throws CamundaException
     */
    private Task get(String userId, String processInstanceId, String taskId) throws CamundaException {
        Task task = taskService.createTaskQuery()
                .taskAssignee(userId)
                .taskId(taskId)
                .processInstanceId(processInstanceId)
                .singleResult();
        if (task == null) {
            throw new CamundaException("Camunda[" + taskId + "]任务不存在！");
        }
        return task;
    }

    /**
     * 得到任务
     * @param userId
     * @param processInstanceId
     * @return
     * @throws CamundaException
     */
    private Task get(String userId, String processInstanceId) throws CamundaException {
        Task task = taskService.createTaskQuery()
                .active()
                .taskAssignee(userId)
                .processInstanceId(processInstanceId)
                .singleResult();
        if (task == null) {
            throw new CamundaException("Camunda["+userId+"]用户任务不存在！");
        }
        return task;
    }
    /**
     * 领取任务
     * @param taskId
     * @param userId
     */
    public void assignee(String taskId, String userId) throws CamundaException {
        //用户认证
        if (!this.identityMgrService.auth(userId)) {
            return;
        }
        taskService.setAssignee(taskId, userId);
    }

    /**
     * 完成任务
     * @param params
     */
    public String complete(Map<String, Object> params) throws CamundaException {
        String userId = StringUtils.replaceNull(params.get("userId"));
        String processInstanceId = StringUtils.replaceNull(params.get("processInstanceId"));
        String taskId = StringUtils.replaceNull(params.get("taskId"));
        String taskDefKey = StringUtils.replaceNull(params.get("taskDefKey"));
        String assignees = StringUtils.replaceNull(params.get("assignees"));
        String multiInstance = StringUtils.replaceNull(params.get("multiInstance"));

        //用户认证
        if (!this.identityMgrService.auth(userId)) {
            return taskId;
        }
        //判断下一个任务是否多实例任务
        if ("true".equals(multiInstance)) {
            String[] assigneeList = assignees.split(",");
            if (assigneeList != null && assigneeList.length > 0) {
                params.put(taskDefKey, Arrays.asList(assigneeList));
            }
        } else {
            params.put(taskDefKey, assignees);
        }
        Task task = this.get(userId,processInstanceId,taskId);
        if(task!=null){
            try {
                taskService.complete(taskId, params);
            } catch (ProcessEngineException e){
                throw e;
            }
        }
        return task.getId();
    }
    /**
     * 是否可撤回任务
     * @param userId
     * @param processInstanceId
     * @param taskId
     */
    public boolean isDrawback(String userId, String processInstanceId, String taskId){
        //判断用户任务是否最后完成的任务，如果不是则不能撤回
        HistoricActivityInstance historicActivityInstance = this.activityInstanceService.getActivityInstance(userId,processInstanceId,taskId);
        if(historicActivityInstance==null||!taskId.equals(historicActivityInstance.getTaskId())){
            return false;
        }
        //得到用户任务活动实例的父活动实例,如果父活动实例是多实例任务则不能撤回
        HistoricActivityInstance parentHistoricActivityInstance=this.activityInstanceService.getActivityInstance(processInstanceId,historicActivityInstance.getParentActivityInstanceId());
        if(parentHistoricActivityInstance!=null&&"multiInstanceBody".equals(parentHistoricActivityInstance.getActivityType())){
            return false;
        }
        return true;
    }
    /**
     * 撤回任务
     * @param userId
     * @param processInstanceId
     * @param taskId
     */
    public void drawback(String userId, String processDefinitionId,String processInstanceId, String taskId) {
       //是否可撤回任务
       if(this.isDrawback(userId,processInstanceId,taskId)){
           //得到用户任务活动实例
           HistoricActivityInstance historicActivityInstance = this.activityInstanceService.getActivityInstance(userId,processInstanceId,taskId);
           //得到实例当前活动实例
           ActivityInstance activityInstance=runtimeService.getActivityInstance(processInstanceId);
           this.reject(userId,processInstanceId,(activityInstance.getChildActivityInstances()[0]).getActivityId(),historicActivityInstance.getActivityId());
           //用户任务撤回后，重新领取该任务
           Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).active().singleResult();
           taskService.setAssignee(task.getId(), userId);
       }
    }
    /**
     * 驳回任务
     * @param userId
     * @param processInstanceId
     * @param taskId
     */
    public void reject(String userId, String processInstanceId, String taskId) throws CamundaException {
        Task task = this.get(userId,processInstanceId,taskId);
        List<HistoricActivityInstance> historicActivityInstances = historyService
                .createHistoricActivityInstanceQuery()
                .processInstanceId(processInstanceId)
                .activityType("userTask")
                .orderByHistoricActivityInstanceStartTime()
                .asc()
                .list();
        HistoricActivityInstance historicActivityInstance = historicActivityInstances.get(0);
        String toActId = historicActivityInstance.getActivityId();
        reject(userId,processInstanceId,task.getTaskDefinitionKey(),toActId);
    }
    /**
     * 驳回任务
     * @param userId
     * @param processInstanceId
     * @param srcActId
     * @param toActId
     */
    public void reject(String userId, String processInstanceId,String srcActId,String toActId){
        ProcessInstanceModificationBuilder builder = runtimeService.createProcessInstanceModification(processInstanceId);
        builder.cancelAllForActivity(srcActId);
        builder.startBeforeActivity(toActId);//启动目标活动节点
        builder.execute();
    }


    @Data
    public class TaskEntity{
        private String processDefinitionId;
        private String processInstanceId;
        private String taskDefinitionKey;
        private String taskId;
        private String taskName;
        private String assignee;
        private String executionId;
        private Date createTime;
        private Date endTime;
    }
}
