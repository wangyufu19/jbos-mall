package com.mall.admin.application.request.wf;

import com.mall.admin.domain.entity.wf.ProcessTask;
import com.mall.common.utils.StringUtils;
import lombok.Data;

import java.util.Map;

/**
 * ProcessTaskDto
 *
 * @author youfu.wang
 * @date 2023/6/5
 **/
@Data
public class ProcessTaskDto {

    public static ProcessTask build(Map<String, Object> formMap){
        ProcessTask processTask=new ProcessTask();
        processTask.setUserId(StringUtils.replaceNull(formMap.get("userId")));
        processTask.setProcDefId(StringUtils.replaceNull(formMap.get("processDefinitionId")));
        processTask.setProcInstId(StringUtils.replaceNull(formMap.get("processInstanceId")));
        processTask.setTaskId(StringUtils.replaceNull(formMap.get("taskId")));
        processTask.setActivityId(StringUtils.replaceNull(formMap.get("activityId")));
        processTask.setActivityName(StringUtils.replaceNull(formMap.get("activityName")));
        if(StringUtils.isNUll(formMap.get("assignee"))){
            processTask.setAssignee(StringUtils.replaceNull(formMap.get("userId")));
        }else{
            processTask.setAssignee(StringUtils.replaceNull(formMap.get("assignee")));
        }
        processTask.setAssigneeDepId(StringUtils.replaceNull(formMap.get("depId")));
        processTask.setOpinion(StringUtils.replaceNull(formMap.get("opinion")));
        processTask.setOpinionDesc(StringUtils.replaceNull(formMap.get("opinionDesc")));
        return processTask;
    }
}
