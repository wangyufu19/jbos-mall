package com.mall.workflow.application.service;

import com.mall.workflow.common.exception.CamundaException;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.impl.util.StringUtil;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
    @Autowired
    private IdentityMgrService identityMgrService;
    /**
     * 启动流程实例
     * @param processDefinitionKey
     * @param businessKey
     * @return
     */
    public ProcessInstance startProcessInstance(String userId,String processDefinitionKey, String businessKey, Map<String,Object> variables) throws CamundaException {
        //用户认证
        if (!this.identityMgrService.auth(userId)) {
            return null;
        }
        ProcessInstance processInstance=runtimeService.startProcessInstanceByKey(processDefinitionKey,businessKey,variables);
        if(processInstance==null|| StringUtils.isEmpty(processInstance.getProcessInstanceId())){
            throw new CamundaException("Camunda["+userId+"]用户启动实例失败！");
        }
        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getProcessInstanceId()).active().singleResult();
        taskService.setAssignee(task.getId(), userId);
        return processInstance;
    }
    /**
     * 启动流程实例并完成第一个节点
     * @param processDefinitionKey
     * @param businessKey
     * @return
     */
    public ProcessInstance startAndFinishProcessInstance(String userId,String processDefinitionKey,String businessKey,Map<String,Object> variables) throws CamundaException {
        //用户认证
        if (!this.identityMgrService.auth(userId)) {
            return null;
        }
        ProcessInstance processInstance=runtimeService.startProcessInstanceByKey(processDefinitionKey,businessKey,variables);
        if(processInstance==null|| StringUtils.isEmpty(processInstance.getProcessInstanceId())){
            throw new CamundaException("Camunda["+userId+"]用户启动实例失败！");
        }
        String processInstanceId=processInstance.getProcessInstanceId();
        Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).active().singleResult();
        taskService.complete(task.getId());
        return processInstance;
    }

    /**
     * 查询流程实例状态
     * @param processInstanceId
     * @return
     */
    public String getProcessInstanceState(String processInstanceId){
        ProcessInstance processInstance= runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        if(processInstance==null||processInstance.isEnded()){
           return "isEnd";
        }
        return "active";
    }
}
