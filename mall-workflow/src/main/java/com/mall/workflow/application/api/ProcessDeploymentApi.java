package com.mall.workflow.application.api;

import com.mall.common.response.ResponseResult;
import com.mall.common.utils.StringUtils;
import com.mall.workflow.application.service.ProcessDeploymentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.impl.task.TaskDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
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

    @ResponseBody
    @PostMapping(value = "/deploy", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation("启动流程部署")
    public ResponseResult deploy(MultipartFile file, @RequestParam Map<String, Object> params){
        ResponseResult res= ResponseResult.ok();
        String resource= file.getOriginalFilename();
        try{
            //判断合法的文件类型
            if(processDeploymentService.includeExtensions(resource)){
                Map<String,Object> data=processDeploymentService.deploy(file);
                res.data(data);
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
    @ResponseBody
    @PostMapping(value = "/unDeploy")
    @ApiOperation("删除流程部署")
    public ResponseResult unDeploy(@RequestBody Map<String, Object> params) {
        ResponseResult res = ResponseResult.ok();
        String id= StringUtils.replaceNull(params.get("id"));
        String cascade=StringUtils.replaceNull(params.get("cascade"));
        try{
            if("true".equals(cascade)){
                processDeploymentService.deleteDeployment(id,true);
            }else{
                processDeploymentService.deleteDeployment(id,false);
            }

        }catch (Exception e){
            log.error(e.getMessage(),e);
            res= ResponseResult.error(ResponseResult.CODE_FAILURE,e.getMessage());
        }
        if(log.isDebugEnabled()){
            log.info("============删除流程部署[" +
                    "id="+id+";" +
                    "cascade="+cascade+
                    "]");
        }
        return res;
    }
    @ResponseBody
    @GetMapping(value = "/getProcessDefinitionList")
    @ApiOperation("得到流程定义任务")
    public ResponseResult getProcessDefinitionList(@RequestParam Map<String, Object> params) {
        ResponseResult res = ResponseResult.ok();
        String processDefinitionId= StringUtils.replaceNull(params.get("processDefinitionId"));
        try{
            List<Map<String, String>> data=processDeploymentService.getProcessDefinitionList(processDefinitionId);
            res.setData(data);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res= ResponseResult.error(ResponseResult.CODE_FAILURE,e.getMessage());
        }
        if(log.isDebugEnabled()){
            log.info("============得到流程定义任务[" +
                    "processDefinitionId="+processDefinitionId+";" +
                    "]");
        }
        return res;
    }
}
