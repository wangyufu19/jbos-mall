package com.mall.admin.application.api.wf;

import com.mall.admin.application.service.external.camunda.DeploymentService;
import com.mall.admin.application.service.wf.ProcessDeploymentService;
import com.mall.admin.domain.entity.wf.ProcessDeployment;
import com.mall.common.page.PageParam;
import com.mall.common.response.ResponseResult;
import com.mall.common.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * ProcessDefApi
 *
 * @author youfu.wang
 * @date 2023/6/9
 **/
@RestController
@RequestMapping("/workflow/deployment")
@Slf4j
@Api("流程部署接口")
public class ProcessDeploymentApi {
    @Autowired
    private ProcessDeploymentService processDeploymentService;
    @Autowired
    private DeploymentService deploymentService;
    /**
     * 查询流程定义列表
     * @param params
     * @return
     */
    @ResponseBody
    @GetMapping("/getProcessDeploymentList")
    @ApiOperation("查询流程部署列表")
    public ResponseResult getProcessDeploymentList(@RequestParam Map<String, Object> params) {
        ResponseResult res = ResponseResult.ok();
        try {
            PageParam pageParam = PageParam.getPageParam(params);
            res=processDeploymentService.getProcessDeploymentList(pageParam,params);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }
    @ResponseBody
    @PostMapping("/deploy")
    @ApiOperation("部署流程")
    public ResponseResult deploy(MultipartFile file, @RequestParam Map<String, Object> params){
        ResponseResult res = ResponseResult.ok();
        try {
            res = deploymentService.deploy(file,params);
            if(ResponseResult.CODE_SUCCESS.equals(res.getRetCode())){
                Map<String,Object> deploymentMap=(Map<String,Object>)res.getData();
                if(deploymentMap!=null){
                    ProcessDeployment processDeployment=new ProcessDeployment();
                    processDeployment.setId(StringUtils.replaceNull(deploymentMap.get("id")));
                    processDeployment.setDeploymentId(StringUtils.replaceNull(deploymentMap.get("deploymentId")));
                    processDeployment.setProcKey(StringUtils.replaceNull(deploymentMap.get("key")));
                    processDeployment.setProcName(StringUtils.replaceNull(deploymentMap.get("name")));
                    processDeployment.setResource(StringUtils.replaceNull(deploymentMap.get("source")));
                    processDeployment.setVersion(StringUtils.replaceNull(deploymentMap.get("version")));
                    processDeploymentService.deployProcessDeployment(processDeployment);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }

    @ResponseBody
    @PostMapping("/unDeploy")
    @ApiOperation("下架流程")
    public ResponseResult unDeploy(@RequestBody Map<String, Object> params){
        ResponseResult res = ResponseResult.ok();
        String id=StringUtils.replaceNull(params.get("id"));
        try {
            params.put("cascade","true");
            res=deploymentService.unDeploy(params);
            if(ResponseResult.CODE_SUCCESS.equals(res.getRetCode())){
                processDeploymentService.deployProcessDeployment(id);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }
    @ResponseBody
    @GetMapping(value = "/getProcessDefinitionList")
    @ApiOperation("得到流程定义任务")
    public ResponseResult getProcessDefinitionList(@RequestParam Map<String, Object> params) {
        ResponseResult res = ResponseResult.ok();
        try{
            res=deploymentService.getProcessDefinitionList(params);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res= ResponseResult.error(ResponseResult.CODE_FAILURE,e.getMessage());
        }
        return res;
    }
}
