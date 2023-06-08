package com.mall.admin.application.service.wf;

import com.mall.admin.domain.entity.sm.ProcessInst;
import com.mall.admin.infrastructure.repository.sm.ProcessInstRepo;
import com.mall.common.page.PageParam;
import com.mall.common.response.ResponseResult;
import com.mall.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author youfu.wang
 * @date 2023/4/6
 **/
@Service
public class ProcessMgrService {
    @Autowired
    private ProcessInstRepo processInstRepo;

    public ResponseResult getProcessInstList(PageParam pageParam,Map<String, Object> parameterObject){
        List<ProcessInst> processInstList=processInstRepo.getProcessInstList(pageParam,parameterObject);
        return ResponseResult.ok().isPage(true).data(processInstList);
    }
    public void addProcessInst(ProcessInst processInst){
        processInstRepo.addProcessInst(processInst);
    }

    public void updateProcState(String processInstanceId,String procState){
        ProcessInst processInst=new ProcessInst();
        processInst.setProcInstId(processInstanceId);
        processInst.setProcState(procState);
        processInst.setEndTime(DateUtils.format(DateUtils.getCurrentDate(), DateUtils.YYYYMMDDHIMMSS));
        processInstRepo.updateProcState(processInst);
    }
}
