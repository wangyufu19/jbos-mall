package com.mall.admin.infrastructure.repository.sm;

import com.mall.common.page.PageParam;
import com.mall.common.page.Paging;
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
    @Paging
    public List<ProcessTask> getUserTaskList(PageParam pageParam,Map<String,Object> parameterObject){
        return processTaskMapper.getUserTaskList(parameterObject);
    }
    @Paging
    public List<ProcessTask>  getUserTaskProcessedList(PageParam pageParam,Map<String, Object> parameterObject){
        return processTaskMapper.getUserTaskProcessedList(parameterObject);
    }
    public List<TaskStep> getUserTaskStepList(Map<String,Object> parameterObject){
        return processTaskMapper.getUserTaskStepList(parameterObject);
    }
    public List<Map> getInstanceTaskProcessedAssigneeList(Map<String,Object> parameterObject){
        return processTaskMapper.getInstanceTaskProcessedAssigneeList(parameterObject);
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
    public void updateDrawbackPostProcessTask(ProcessTask processTask){
        processTaskMapper.updateDrawbackPostProcessTask(processTask);
    }
    public void updateHandlePostProcessTask(ProcessTask processTask){
        processTaskMapper.updateHandlePostProcessTask(processTask);
    }
    public void updateProcessTaskState(ProcessTask processTask){
        processTaskMapper.updateProcessTaskState(processTask);
    }
}
