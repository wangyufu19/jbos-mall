package com.mall.admin.application.service.wf;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.admin.domain.entity.wf.ProcessDeployment;
import com.mall.admin.infrastructure.camunda.DeploymentService;
import com.mall.admin.infrastructure.repository.wf.ProcessDeploymentRepo;
import com.mall.admin.infrastructure.repository.wf.mapper.ProcessDeploymentMapper;
import com.mall.common.page.PageParam;
import com.mall.common.response.ResponseResult;
import com.mall.common.utils.DateUtils;
import com.mall.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    private DeploymentService deploymentService;
    @Autowired
    private ProcessDeploymentRepo processDeploymentRepo;

    /**
     * 查询流程部署列表
     * @param pageParam
     * @param parameterObject
     * @return
     */
    public ResponseResult getProcessDeploymentList(PageParam pageParam, Map<String, Object> parameterObject){
        List<ProcessDeployment> processDefList=processDeploymentRepo.getProcessDeploymentList(pageParam,parameterObject);
        return ResponseResult.ok().isPage(true).data(processDefList);
    }

    /**
     * 部署流程
     * @param file
     * @param params
     * @return
     * @throws IOException
     */
    @Transactional
    public ResponseResult deployProcessDeployment(MultipartFile file, Map<String, Object> params) throws IOException {
        ResponseResult res= ResponseResult.ok();
        String resource= file.getOriginalFilename();
        //判断合法的文件类型
        if(deploymentService.includeExtensions(resource)){
            Map<String,Object> deploymentMap=deploymentService.deploy(file);
            if(deploymentMap!=null){
                ProcessDeployment processDeployment=new ProcessDeployment();
                processDeployment.setId(StringUtils.replaceNull(deploymentMap.get("id")));
                processDeployment.setDeploymentId(StringUtils.replaceNull(deploymentMap.get("deploymentId")));
                processDeployment.setProcKey(StringUtils.replaceNull(deploymentMap.get("key")));
                processDeployment.setProcName(StringUtils.replaceNull(deploymentMap.get("name")));
                processDeployment.setResource(StringUtils.replaceNull(deploymentMap.get("source")));
                processDeployment.setVersion(StringUtils.replaceNull(deploymentMap.get("version")));
                processDeployment.setDeployTime(DateUtils.format(DateUtils.getCurrentDate(), DateUtils.YYYYMMDDHIMMSS));
                processDeploymentRepo.save(processDeployment);
            }
        }else{
            res= ResponseResult.error(ResponseResult.CODE_FAILURE,"对不起，请上传合法的Camunda文件类型[bmmn]");
        }
        return res;
    }

    /**
     * 删除流程
     * @param id
     */
    @Transactional
    public void unDeployProcessDeployment(String id){
        deploymentService.deleteDeployment(id,true);
        UpdateWrapper<ProcessDeployment> updateWrapper=new UpdateWrapper();
        updateWrapper.eq("ID",id);
        processDeploymentRepo.remove(updateWrapper);
    }

    /**
     * 得到流程定义任务
     * @param processDefinitionId
     * @return
     */
    public List<Map<String, String>> getProcessDefinitionList(String processDefinitionId){
        return deploymentService.getProcessDefinitionList(processDefinitionId);
    }
}
