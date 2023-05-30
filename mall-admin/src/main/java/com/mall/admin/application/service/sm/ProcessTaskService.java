package com.mall.admin.application.service.sm;

import com.mall.admin.domain.entity.sm.ProcessTask;
import com.mall.admin.domain.entity.sm.TaskStep;
import com.mall.admin.infrastructure.repository.sm.ProcessTaskRepo;
import com.mall.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ProcessTaskService
 * @author youfu.wang
 * @date 2023/4/6
 **/
@Service
public class ProcessTaskService {
    @Autowired
    private ProcessTaskRepo processTaskRepo;

    public List<ProcessTask> getUserTaskList(Map<String,Object> parameterObject){
        return processTaskRepo.getUserTaskList(parameterObject);
    }
    public List<ProcessTask> getUserTaskProcessedList(Map<String,Object> parameterObject){
        return processTaskRepo.getUserTaskProcessedList(parameterObject);
    }
    public List<TaskStep> getUserTaskStepList(Map<String,Object> parameterObject){
        List<TaskStep> taskSteps=processTaskRepo.getUserTaskStepList(parameterObject);
        return taskSteps;
    }
    public String getTaskAssigneeList(Map<String,Object> parameterObject){
        String assignees="";
        List<Map> assigneeList=processTaskRepo.getTaskAssigneeList(parameterObject);
        if(assigneeList!=null){
            for(int i=0;i<assigneeList.size();i++){
                if(i==assigneeList.size()-1){
                    assignees=assignees+StringUtils.replaceNull(assigneeList.get(i).get("BADGE"));
                }else{
                    assignees=assignees+StringUtils.replaceNull(assigneeList.get(i).get("BADGE"))+",";
                }
            }
        }
        return assignees;
    }
    public void addProcessTask(ProcessTask processTask){
        processTaskRepo.addProcessTask(processTask);
    }

    public void updateProcessTaskOpinion(ProcessTask processTask) {
        processTaskRepo.updateProcessTaskOpinion(processTask);
    }
}
