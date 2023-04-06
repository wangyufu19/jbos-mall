package com.mall.admin.infrastructure.repository.sm;

import com.mall.admin.domain.entity.sm.ProcessInst;
import com.mall.admin.infrastructure.repository.sm.mapper.ProcessInstMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * ProcessInstRepo
 * @author youfu.wang
 * @date 2023/4/6
 **/
@Component
public class ProcessInstRepo {
    @Autowired
    private ProcessInstMapper processInstMapper;

    public List<ProcessInst> getProcessInstList(Map<String,String> parameterObject){
        return processInstMapper.getProcessInstList(parameterObject);
    }

    public void addProcessInst(ProcessInst processInst){
        processInstMapper.addProcessInst(processInst);
    }

    public void updateProcState(Map<String,String> parameterObject){
        processInstMapper.updateProcState(parameterObject);
    }
}
