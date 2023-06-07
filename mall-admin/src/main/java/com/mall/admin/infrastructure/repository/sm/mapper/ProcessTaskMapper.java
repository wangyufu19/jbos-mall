package com.mall.admin.infrastructure.repository.sm.mapper;

import com.mall.admin.domain.entity.sm.ProcessTask;
import com.mall.admin.domain.entity.sm.TaskStep;

import java.util.List;
import java.util.Map;

/**
 * ProcessTaskMapper
 * @author youfu.wang
 * @date 2023/4/6
 **/
public interface ProcessTaskMapper {

    public List<ProcessTask> getUserTaskList(Map<String,Object> parameterObject);

    public List<ProcessTask> getUserTaskProcessedList(Map<String,Object> parameterObject);

    public List<TaskStep> getUserTaskStepList(Map<String,Object> parameterObject);

    public List<Map> getTaskAssigneeList(Map<String,Object> parameterObject);

    public void addProcessTask(ProcessTask processTask);

    public void updateProcessTaskOpinion(ProcessTask processTask);

    public void updateDrawbackPostProcessTask(ProcessTask processTask);

    public void updateRejectPostProcessTask(ProcessTask processTask);

    public void updateProcessTaskState(ProcessTask processTask);

}
