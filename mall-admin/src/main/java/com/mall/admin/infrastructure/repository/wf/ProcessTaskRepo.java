package com.mall.admin.infrastructure.repository.wf;

import com.mall.common.page.PageParam;
import com.mall.common.page.Paging;
import com.mall.admin.domain.entity.wf.ProcessTask;
import com.mall.admin.domain.entity.wf.TaskStep;
import com.mall.admin.infrastructure.repository.wf.mapper.ProcessTaskMapper;
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
    @Paging
    public List<ProcessTask> getProcessTaskDetailList(PageParam pageParam,Map<String,Object> parameterObject){
        return processTaskMapper.getProcessTaskDetailList(parameterObject);
    }

    public List<TaskStep> getProcessTaskStepList(Map<String,Object> parameterObject){
        return processTaskMapper.getProcessTaskStepList(parameterObject);
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
    public void updateNoneStatePostHandleTask(ProcessTask processTask){
        processTaskMapper.updateNoneStatePostHandleTask(processTask);
    }
    public void updateProcessTaskState(ProcessTask processTask){
        processTaskMapper.updateProcessTaskState(processTask);
    }
}
