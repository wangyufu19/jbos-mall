package com.mall.admin.infrastructure.repository.sm;

import com.mall.admin.domain.entity.sm.ProcessTask;
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

    public void addProcessTask(ProcessTask processTask){
        processTaskMapper.addProcessTask(processTask);
    }

    public void updateProcessTaskOpinion(Map<String,String> parameterObject){
        processTaskMapper.updateProcessTaskOpinion(parameterObject);
    }
}
