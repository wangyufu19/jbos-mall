package com.mall.admin.application.service.sm;

import com.mall.admin.application.service.ProcessDefConstants;
import com.mall.admin.domain.entity.sm.ProcessInst;
import com.mall.admin.infrastructure.repository.sm.ProcessInstRepo;
import com.mall.common.utils.DateUtils;
import com.mall.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    public void updateProcState(ProcessInst processInst){
        processInstRepo.updateProcState(processInst);
    }

}
