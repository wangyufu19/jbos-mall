package com.mall.admin.application.service.wf;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.mall.admin.domain.entity.wf.ProcessDeployment;
import com.mall.admin.infrastructure.camunda.DeploymentService;
import com.mall.admin.infrastructure.repository.wf.ProcessDeploymentRepo;
import com.mall.common.page.PageParam;
import com.mall.common.response.ResponsePageResult;
import com.mall.common.response.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
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
    public ResponsePageResult getProcessDeploymentList(PageParam pageParam, Map<String, Object> parameterObject){
        List<ProcessDeployment> processDefList=processDeploymentRepo.getProcessDeploymentList(pageParam,parameterObject);
        return ResponsePageResult.ok().isPage(true).setData(processDefList);
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
            if(deploymentMap!=null&&!StringUtils.isEmpty(deploymentMap.get("deploymentId"))){
                ProcessDeployment processDeployment=new ProcessDeployment();
                processDeployment.setId(String.valueOf(deploymentMap.get("id")));
                processDeployment.setDeploymentId(String.valueOf(deploymentMap.get("deploymentId")));
                processDeployment.setProcKey(String.valueOf(deploymentMap.get("key")));
                processDeployment.setProcName(String.valueOf(deploymentMap.get("name")));
                processDeployment.setResource(String.valueOf(deploymentMap.get("source")));
                processDeployment.setVersion(String.valueOf(deploymentMap.get("version")));
                processDeployment.setDeployTime(DateUtil.format(DateUtil.date(), DatePattern.PURE_DATE_FORMAT));
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
