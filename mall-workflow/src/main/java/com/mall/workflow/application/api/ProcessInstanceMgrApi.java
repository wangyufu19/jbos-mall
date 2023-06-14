package com.mall.workflow.application.api;

import com.mall.common.response.ResponseResult;
import com.mall.common.utils.StringUtils;
import com.mall.workflow.application.service.ProcessInstanceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

/**
 * ProcessInstanceMgrApi
 * @author youfu.wang
 * @date 2019-01-31
 */
@Slf4j
@RestController
@RequestMapping("/process")
@Api("实例管理接口")
public class ProcessInstanceMgrApi {
    @Autowired
    private ProcessInstanceService processInstanceService;

    @ResponseBody
    @PostMapping(value = "/startProcessInstance")
    @ApiOperation("启动流程实例")
    public ResponseResult startProcessInstance(@RequestBody Map<String, Object> params){
        ResponseResult res= ResponseResult.ok();
        String userId=StringUtils.replaceNull(params.get("userId"));
        String processDefinitionKey= StringUtils.replaceNull(params.get("processDefinitionKey"));
        String businessKey= StringUtils.replaceNull(params.get("businessKey"));
        String processDefinitionId="";
        String processInstanceId="";
        try{
            ProcessInstance processInstance=processInstanceService.startProcessInstance(userId,processDefinitionKey,businessKey,params);
            processDefinitionId=processInstance.getProcessDefinitionId();
            processInstanceId=processInstance.getProcessInstanceId();
            Map<String,Object> data=new HashMap<String,Object>();
            data.put("processDefinitionId",processDefinitionId);
            data.put("processInstanceId",processInstanceId);
            res.setData(data);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res= ResponseResult.error(ResponseResult.CODE_FAILURE,e.getMessage());
        }
        if(log.isDebugEnabled()){
            log.info("============启动流程实例[" +
                    "userId="+userId+";" +
                    "processDefinitionKey="+processDefinitionKey+";" +
                    "businessKey="+businessKey+";" +
                    "processDefinitionId="+processDefinitionId+";" +
                    "processInstanceId="+processInstanceId+
                    "]");
        }
        return res;
    }
    @ResponseBody
    @PostMapping(value = "/startAndFinishProcessInstance")
    @ApiOperation("启动和完成流程实例")
    public ResponseResult startAndFinishProcessInstance(@RequestBody Map<String, Object> params){
        ResponseResult res= ResponseResult.ok();
        String userId=StringUtils.replaceNull(params.get("userId"));
        String processDefinitionKey= StringUtils.replaceNull(params.get("processDefinitionKey"));
        String businessKey= StringUtils.replaceNull(params.get("businessKey"));
        String processDefinitionId="";
        String processInstanceId="";
        try{
            ProcessInstance processInstance=processInstanceService.startAndFinishProcessInstance(userId,processDefinitionKey,businessKey,params);
            processDefinitionId=processInstance.getProcessDefinitionId();
            processInstanceId=processInstance.getProcessInstanceId();
            Map<String,Object> data=new HashMap<String,Object>();
            data.put("processDefinitionId",processDefinitionId);
            data.put("processInstanceId",processInstanceId);
            res.setData(data);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res= ResponseResult.error(ResponseResult.CODE_FAILURE,e.getMessage());
        }
        if(log.isDebugEnabled()){
            log.info("============启动和完成流程实例[" +
                    "userId="+userId+";" +
                    "processDefinitionKey="+processDefinitionKey+";" +
                    "businessKey="+businessKey+";" +
                    "processDefinitionId="+processDefinitionId+";" +
                    "processInstanceId="+processInstanceId+
                    "]");
        }
        return res;
    }
    @ResponseBody
    @GetMapping(value = "/getProcessInstanceState")
    @ApiOperation("查询流程实例状态")
    public ResponseResult getProcessInstanceState(@RequestParam Map<String, Object> params){
        ResponseResult res= ResponseResult.ok();
        String processInstanceId=StringUtils.replaceNull(params.get("processInstanceId"));
        try{
            String processInstanceState=processInstanceService.getProcessInstanceState(processInstanceId);
            Map<String,Object> data=new HashMap<String,Object>();
            data.put("processInstanceState",processInstanceState);
            res.setData(data);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res= ResponseResult.error(ResponseResult.CODE_FAILURE,e.getMessage());
        }
        return res;
    }
    @ResponseBody
    @GetMapping(value = "/getProcessInstanceCurrentActivityId")
    @ApiOperation("查询流程实例当前活动")
    public ResponseResult getProcessInstanceCurrentActivityId(@RequestParam Map<String, Object> params){
        ResponseResult res= ResponseResult.ok();
        String processInstanceId=StringUtils.replaceNull(params.get("processInstanceId"));
        try{
            String currentActivityId=processInstanceService.getProcessInstanceCurrentActivityId(processInstanceId);
            Map<String,Object> data=new HashMap<String,Object>();
            data.put("currentActivityId",currentActivityId);
            res.setData(data);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res= ResponseResult.error(ResponseResult.CODE_FAILURE,e.getMessage());
        }
        return res;
    }
    @ResponseBody
    @PostMapping("/suspendProcessInstanceById")
    @ApiOperation("暂停流程实例")
    public ResponseResult suspendProcessInstanceById(@RequestBody Map<String, Object> params){
        ResponseResult res = ResponseResult.ok();
        try {
            String processInstanceId=StringUtils.replaceNull(params.get("processInstanceId"));
            processInstanceService.suspendProcessInstanceById(processInstanceId);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }

    @ResponseBody
    @PostMapping("/activateProcessInstanceById")
    @ApiOperation("激活流程实例")
    public ResponseResult activateProcessInstanceById(@RequestBody Map<String, Object> params){
        ResponseResult res = ResponseResult.ok();
        try {
            String processInstanceId=StringUtils.replaceNull(params.get("processInstanceId"));
            processInstanceService.activateProcessInstanceById(processInstanceId);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }
    @ResponseBody
    @PostMapping("/deleteProcessInstance")
    @ApiOperation("删除流程实例")
    public ResponseResult deleteProcessInstance(@RequestBody Map<String, Object> params){
        ResponseResult res = ResponseResult.ok();
        try {
            String processInstanceId=StringUtils.replaceNull(params.get("processInstanceId"));
            processInstanceService.deleteProcessInstance(processInstanceId,"作废流程实例");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }
}
