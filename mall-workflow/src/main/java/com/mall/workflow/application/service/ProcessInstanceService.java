package com.mall.workflow.application.service;

import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * ProcessInstanceService
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

    /**
     * 启动流程实例
     * @param processDefinitionKey
     * @param businessKey
     * @return
     */
    public ProcessInstance startProcessInstance(String processDefinitionKey, String businessKey, Map<String,Object> variables){
        ProcessInstance processInstance=runtimeService.startProcessInstanceByKey(processDefinitionKey,businessKey,variables);
        return processInstance;
    }
    /**
     * 启动流程实例并完成第一个节点
     * @param processDefinitionKey
     * @param businessKey
     * @return
     */
    public ProcessInstance startAndFinishProcessInstance(String processDefinitionKey,String businessKey,Map<String,Object> variables){
        ProcessInstance processInstance=this.startProcessInstance(processDefinitionKey,businessKey,variables);
        String processInstanceId=processInstance.getProcessInstanceId();
        Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).active().singleResult();
        taskService.complete(task.getId());
        return processInstance;
    }
}
