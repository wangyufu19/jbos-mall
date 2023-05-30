package com.mall.admin.infrastructure.repository.sm;

import com.mall.admin.domain.entity.sm.ProcessTask;
import com.mall.admin.domain.entity.sm.TaskStep;
import com.mall.admin.infrastructure.repository.sm.mapper.ProcessTaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * ProcessTaskRepo
 * @author youfu.wang
 * @date 2023/4/6
 **/
@Component
public class ProcessTaskRepo {
    @Autowired
    private ProcessTaskMapper processTaskMapper;

    public List<ProcessTask> getUserTaskList(Map<String,Object> parameterObject){
        return processTaskMapper.getUserTaskList(parameterObject);
    }
    public List<ProcessTask> getUserTaskProcessedList(Map<String,Object> parameterObject){
        return processTaskMapper.getUserTaskProcessedList(parameterObject);
    }
    public List<TaskStep> getUserTaskStepList(Map<String,Object> parameterObject){
        return processTaskMapper.getUserTaskStepList(parameterObject);
    }
    public List<Map> getTaskAssigneeList(Map<String,Object> parameterObject){
        return processTaskMapper.getTaskAssigneeList(parameterObject);
    }
    public void addProcessTask(ProcessTask processTask){
        processTaskMapper.addProcessTask(processTask);
    }

    public void updateProcessTaskOpinion(ProcessTask processTask){
        processTaskMapper.updateProcessTaskOpinion(processTask);
    }
}
