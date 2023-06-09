package com.mall.admin.application.service.wf;

import com.mall.admin.domain.entity.wf.ProcessDeployment;
import com.mall.admin.infrastructure.repository.wf.ProcessDeploymentRepo;
import com.mall.common.page.PageParam;
import com.mall.common.response.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * ProcessDefService
 *
 * @author youfu.wang
 * @date 2023/6/9
 **/
@Service
public class ProcessDeploymentService {
    @Autowired
    private ProcessDeploymentRepo processDeploymentRepo;

    public ResponseResult getProcessDeploymentList(PageParam pageParam, Map<String, Object> parameterObject){
        List<ProcessDeployment> processDefList=processDeploymentRepo.getProcessDeploymentList(pageParam,parameterObject);
        return ResponseResult.ok().isPage(true).data(processDefList);
    }

    public void deployProcess(ProcessDeployment processDeployment){
        processDeploymentRepo.addProcessDeployment(processDeployment);
    }

    public void unDeployProcess(ProcessDeployment processDeployment){
        processDeploymentRepo.deleteProcessDeployment(processDeployment);
    }
}
