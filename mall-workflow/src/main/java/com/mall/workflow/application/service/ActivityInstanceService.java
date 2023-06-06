package com.mall.workflow.application.service;

import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.history.HistoricActivityInstance;
import org.camunda.bpm.engine.runtime.ActivityInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ActivityInstanceService
 *
 * @author youfu.wang
 * @date 2019-01-31
 */
@Service
public class ActivityInstanceService {
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private HistoryService historyService;

    /**
     * 得到活动实例
     *
     * @param processInstanceId
     * @param activityInstanceId
     * @return
     */
    public HistoricActivityInstance getActivityInstance(String processInstanceId, String activityInstanceId) {
        HistoricActivityInstance historicActivityInstance = historyService
                .createHistoricActivityInstanceQuery()
                .processInstanceId(processInstanceId)
                .activityInstanceId(activityInstanceId)
                .singleResult();
        return historicActivityInstance;
    }

    /**
     * 得到活动实例
     *
     * @param userId
     * @param processInstanceId
     * @param taskId
     * @return
     */
    public HistoricActivityInstance getActivityInstance(String userId, String processInstanceId, String taskId) {
        HistoricActivityInstance activityInstance = null;
        List<HistoricActivityInstance> historicActivityInstances = historyService
                .createHistoricActivityInstanceQuery()
                .activityType("userTask")
                .processInstanceId(processInstanceId)
                .finished()
                .orderByHistoricActivityInstanceEndTime()
                .desc()
                .list();
        if (historicActivityInstances == null || historicActivityInstances.size() <= 0) {
            return null;
        }
        activityInstance = historicActivityInstances.get(0);
        if (userId.equals(activityInstance.getAssignee())
                && taskId.equals(activityInstance.getTaskId())) {
           return activityInstance;
        }
        return null;
    }

    public ActivityInstance getActivityInstance(String processInstanceId) {
        ActivityInstance activityInstance = runtimeService.getActivityInstance(processInstanceId);
        return activityInstance;
    }

}
