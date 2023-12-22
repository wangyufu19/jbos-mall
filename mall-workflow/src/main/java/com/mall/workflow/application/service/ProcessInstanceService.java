package com.mall.workflow.application.service;

import com.mall.common.utils.PlaceholderUtils;
import com.mall.workflow.common.exception.CamundaException;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.history.HistoricActivityInstance;
import org.camunda.bpm.engine.impl.task.TaskDefinition;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ProcessInstanceService
 *
 * @author youfu.wang
 * @date 2019-01-31
 */
@Service
public class ProcessInstanceService {
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private IdentityMgrService identityMgrService;
    @Autowired
    private ProcessDeploymentService processDeploymentService;
    @Autowired
    private ActivityInstanceService activityInstanceService;

    /**
     * 启动流程实例
     *
     * @param processDefinitionKey
     * @param businessKey
     * @return
     */
    public ProcessInstance startProcessInstance(
            String userId,
            String processDefinitionKey,
            String businessKey,
            Map<String, Object> variables) throws CamundaException {
        //用户认证
        if (!this.identityMgrService.auth(userId)) {
            return null;
        }
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey, businessKey, variables);
        if (processInstance == null || StringUtils.isEmpty(processInstance.getProcessInstanceId())) {
            throw new CamundaException("Camunda[" + userId + "]用户启动实例失败！");
        }
        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getProcessInstanceId()).active().singleResult();
        taskService.setAssignee(task.getId(), userId);
        return processInstance;
    }

    /**
     * 启动流程实例并完成第一个节点
     *
     * @param processDefinitionKey
     * @param businessKey
     * @return
     */
    public ProcessInstance startAndFinishProcessInstance(
            String userId,
            String processDefinitionKey,
            String businessKey,
            Map<String, Object> variables) throws CamundaException {
        //用户认证
        if (!this.identityMgrService.auth(userId)) {
            return null;
        }
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey, businessKey, variables);
        if (processInstance == null || StringUtils.isEmpty(processInstance.getProcessInstanceId())) {
            throw new CamundaException("Camunda[" + userId + "]用户启动实例失败！");
        }
        String processInstanceId = processInstance.getProcessInstanceId();
        Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).active().singleResult();
        taskService.complete(task.getId());
        return processInstance;
    }

    /**
     * 查询流程实例状态
     *
     * @param processInstanceId
     * @return
     */
    public String getProcessInstanceState(String processInstanceId) {
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        if (processInstance == null) {
            return "isEnded";
        }
        if (processInstance.isEnded()) {
            return "isEnded";
        } else if (processInstance.isSuspended()) {
            return "isSuspended";
        }
        return "active";
    }

    /**
     * 查询流程实例当前活动
     *
     * @param processInstanceId
     * @return
     */
    public Map<String, Object> getProcessInstanceCurrentActivityId(String processDefinitionId, String processInstanceId) {
        String currentActivityId = "";
        String currentActivityName = "";
        String multiInstance = "false";
        String elementVariable = "";

        Map<String, Object> data = new HashMap<String, Object>();

        List<HistoricActivityInstance> historicActivityInstances = historyService
                .createHistoricActivityInstanceQuery()
                .processInstanceId(processInstanceId)
                .orderByHistoricActivityInstanceStartTime()
                .desc()
                .list();

        for (HistoricActivityInstance historicActivityInstance : historicActivityInstances) {
            if ("userTask".equals(historicActivityInstance.getActivityType())
                    || "noneEndEvent".equals(historicActivityInstance.getActivityType())) {
                currentActivityId = historicActivityInstance.getActivityId();
                currentActivityName = historicActivityInstance.getActivityName();
                //得到用户任务活动实例的父活动实例,如果父活动实例是多实例任务multiInstance值为true，否则为false
                HistoricActivityInstance parentHistoricActivityInstance =
                        this.activityInstanceService.getActivityInstance(
                                processInstanceId, historicActivityInstance.getParentActivityInstanceId());
                if (parentHistoricActivityInstance != null
                        && "multiInstanceBody".equals(parentHistoricActivityInstance.getActivityType())) {
                    multiInstance = "true";
                }
                break;
            }
        }
        data.put("currentActivityId", currentActivityId);
        data.put("currentActivityName", currentActivityName);
        data.put("multiInstance", multiInstance);
        if ("true".equals(multiInstance)) {
            TaskDefinition taskDefinition = processDeploymentService.getProcessDefinition(
                    processDefinitionId, currentActivityId);
            if (taskDefinition != null) {
                elementVariable = taskDefinition.getAssigneeExpression().getExpressionText();
                elementVariable = PlaceholderUtils.getPlaceholder(elementVariable, "${", "}");
                data.put("elementVariable", elementVariable);
            }

        }
        return data;
    }

    /**
     * 暂停流程实例
     *
     * @param processInstanceId
     */
    public void suspendProcessInstanceById(String processInstanceId) {
        runtimeService.suspendProcessInstanceById(processInstanceId);
    }

    /**
     * 激活流程实例
     *
     * @param processInstanceId
     */
    public void activateProcessInstanceById(String processInstanceId) {
        runtimeService.activateProcessInstanceById(processInstanceId);
    }

    /**
     * 删除流程实例
     *
     * @param processInstanceId
     * @param deleteReason
     */
    public void deleteProcessInstance(String processInstanceId, String deleteReason) {
        runtimeService.deleteProcessInstance(processInstanceId, deleteReason);
    }

}
