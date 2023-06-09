package com.mall.workflow.application.api;

import com.mall.common.response.ResponseResult;
import com.mall.workflow.application.service.ProcessDeploymentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.engine.repository.DeploymentQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * ProcessDeploymentApi
 *
 * @author youfu.wang
 * @date 2023/6/9
 **/
@Slf4j
@RestController
@RequestMapping("/deployment")
@Api("流程部署接口")
public class ProcessDeploymentApi {
    @Autowired
    private ProcessDeploymentService processDeploymentService;
    @Autowired
    private RepositoryService repositoryService;

    @ResponseBody
    @PostMapping(value = "/deploy")
    @ApiOperation("启动流程部署")
    public ResponseResult deploy(MultipartFile file, @RequestParam Map<String, Object> params){
        ResponseResult res= ResponseResult.ok();
        String resource= file.getOriginalFilename();
        try{
            //判断合法的文件类型
            if(processDeploymentService.includeExtensions(resource)){
                Deployment deployment=processDeploymentService.deploy(file);
                DeploymentQuery query = repositoryService
                        .createDeploymentQuery().deploymentId(deployment.getId());
                Map<String,Object> data=new HashMap<String,Object>();
                data.put("id",deployment.getId());
                data.put("name",deployment.getName());
                data.put("Source",deployment.getSource());
                res.setData(data);
            }else{
                res= ResponseResult.error(ResponseResult.CODE_FAILURE,"对不起，请上传合法的Camunda文件类型[bmmn]");
            }

        }catch (Exception e){
            log.error(e.getMessage(),e);
            res= ResponseResult.error(ResponseResult.CODE_FAILURE,e.getMessage());
        }
        if(log.isDebugEnabled()){
            log.info("============启动流程部署[" +
                    "resource="+resource+
                    "]");
        }
        return res;
    }
}
