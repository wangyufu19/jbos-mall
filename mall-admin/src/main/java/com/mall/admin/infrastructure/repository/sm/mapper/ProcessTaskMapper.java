package com.mall.admin.infrastructure.repository.sm.mapper;

import com.mall.admin.domain.entity.sm.ProcessTask;

import java.util.List;
import java.util.Map;

/**
 * ProcessTaskMapper
 * @author youfu.wang
 * @date 2023/4/6
 **/
public interface ProcessTaskMapper {

    public List<ProcessTask> getUserTaskList(Map<String,Object> parameterObject);

    public void addProcessTask(ProcessTask processTask);

    public void updateProcessTaskOpinion(Map<String,String> parameterObject);
}
