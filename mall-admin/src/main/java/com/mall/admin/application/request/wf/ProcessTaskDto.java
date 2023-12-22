package com.mall.admin.application.request.wf;

import com.mall.admin.domain.entity.wf.ProcessTask;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * ProcessTaskDto
 *
 * @author youfu.wang
 * @date 2023/6/5
 **/
@Data
public class ProcessTaskDto {

    public static ProcessTask build(Map<String, Object> formMap) {
        ProcessTask processTask = new ProcessTask();
        processTask.setUserId(String.valueOf(formMap.get("userId")));
        processTask.setProcDefId(String.valueOf(formMap.get("processDefinitionId")));
        processTask.setProcInstId(String.valueOf(formMap.get("processInstanceId")));
        processTask.setTaskId(String.valueOf(formMap.get("taskId")));
        processTask.setActivityId(String.valueOf(formMap.get("activityId")));
        processTask.setActivityName(String.valueOf(formMap.get("activityName")));
        if (StringUtils.isEmpty(formMap.get("assignee"))) {
            processTask.setAssignee(String.valueOf(formMap.get("userId")));
        } else {
            processTask.setAssignee(String.valueOf(formMap.get("assignee")));
        }
        processTask.setAssigneeDepId(String.valueOf(formMap.get("depId")));
        processTask.setOpinion(String.valueOf(formMap.get("opinion")));
        processTask.setOpinionDesc(String.valueOf(formMap.get("opinionDesc")));
        return processTask;
    }
}
