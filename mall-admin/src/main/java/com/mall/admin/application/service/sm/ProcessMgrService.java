package com.mall.admin.application.service.sm;

import com.mall.admin.domain.entity.sm.ProcessInst;
import com.mall.admin.infrastructure.repository.sm.ProcessInstRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author youfu.wang
 * @date 2023/4/6
 **/
@Service
public class ProcessMgrService {
    @Autowired
    private ProcessInstRepo processInstRepo;

    public void addProcessInst(ProcessInst processInst){
        processInstRepo.addProcessInst(processInst);
    }
    public void updateProcState(String procInstId,String procState){
        Map<String,String> parameterObject = new HashMap<>();
        parameterObject.put("procInstId",procInstId);
        parameterObject.put("procState",procState);
        processInstRepo.updateProcState(parameterObject);
    }
}
