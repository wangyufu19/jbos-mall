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
        processTask.setProcDefId(StringUtils.replaceNull(formMap.get("processDefinitionId")));
        processTask.setProcInstId(StringUtils.replaceNull(formMap.get("processInstanceId")));
        processTask.setTaskId(StringUtils.replaceNull(formMap.get("taskId")));
        processTask.setTaskDefKey(StringUtils.replaceNull(formMap.get("taskDefKey")));
        processTask.setTaskName(StringUtils.replaceNull(formMap.get("taskName")));
        processTask.setAssignee(StringUtils.replaceNull(formMap.get("userId")));
        processTask.setAssigneeDepId(StringUtils.replaceNull(formMap.get("depId")));
        processTask.setOpinion(StringUtils.replaceNull(formMap.get("opinion")));
        processTask.setOpinionDesc(StringUtils.replaceNull(formMap.get("opinionDesc")));
        return processTask;
    }
}
