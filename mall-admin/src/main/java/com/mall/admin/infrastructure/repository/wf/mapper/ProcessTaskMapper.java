package com.mall.admin.infrastructure.repository.wf.mapper;

import com.mall.admin.domain.entity.wf.ProcessTask;
import com.mall.admin.domain.entity.wf.TaskStep;

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

    public List<ProcessTask> getProcessTaskDetailList(Map<String,Object> parameterObject);

    public List<TaskStep> getProcessTaskStepList(Map<String,Object> parameterObject);

    public List<Map> getInstanceTaskProcessedAssigneeList(Map<String,Object> parameterObject);

    public List<Map> getTaskAssigneeList(Map<String,Object> parameterObject);

    public void addProcessTask(ProcessTask processTask);

    public void updateProcessTaskOpinion(ProcessTask processTask);

    public void updateDrawbackPostProcessTask(ProcessTask processTask);

    public void updateNoneStatePostHandleTask(ProcessTask processTask);

    public void updateProcessTaskState(ProcessTask processTask);

}
