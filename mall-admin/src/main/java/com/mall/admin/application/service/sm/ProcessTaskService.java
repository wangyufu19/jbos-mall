package com.mall.admin.application.service.sm;

import com.mall.admin.domain.entity.sm.ProcessTask;
import com.mall.admin.infrastructure.repository.sm.ProcessTaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void addProcessTask(ProcessTask processTask){
        processTaskRepo.addProcessTask(processTask);
    }

    public void updateProcessTaskOpinion(Map<String,String> parameterObject) {
        processTaskRepo.updateProcessTaskOpinion(parameterObject);
    }
}
