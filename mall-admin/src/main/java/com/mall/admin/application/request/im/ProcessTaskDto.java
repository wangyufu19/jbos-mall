package com.mall.admin.application.request.im;

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
    private String procDefId;
    private String procInstId;
    private String bizId;
    private String bizNo;
    private String bizType;
    private String taskId;
    private String taskDefKey;
    private String taskName;
    private String assignee;
    private String assigneeDepId;
    private String routeUrl;
    private String taskState;
    private String startTime;
    private String endTime;
    private String opinion;

    public static ProcessTaskDto build(Map<String, Object> params){
        Map<String, Object> formMap = (Map<String, Object>) params.get("formObj");
        ProcessTaskDto dto=new ProcessTaskDto();
        dto.setProcDefId(StringUtils.replaceNull(formMap.get("processDefinitionId")));
        dto.setProcInstId(StringUtils.replaceNull(formMap.get("processInstanceId")));
        dto.setTaskId(StringUtils.replaceNull(formMap.get("taskId")));
        dto.setTaskDefKey(StringUtils.replaceNull(formMap.get("taskDefKey")));
        dto.setTaskName(StringUtils.replaceNull(formMap.get("taskName")));
        dto.setAssignee(StringUtils.replaceNull(formMap.get("userId")));
        dto.setAssigneeDepId(StringUtils.replaceNull(formMap.get("depId")));
        dto.setOpinion(StringUtils.replaceNull(formMap.get("opinion")));
        return dto;
    }
}
