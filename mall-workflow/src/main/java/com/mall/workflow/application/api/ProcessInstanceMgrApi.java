package com.mall.workflow.application.api;

import com.mall.common.response.ResponseData;
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
    public ResponseData startProcessInstance(@RequestBody Map<String, Object> params){
        ResponseData res=ResponseData.ok();
        log.info("============启动流程实例");
        String processDefinitionKey= StringUtils.replaceNull(params.get("processDefinitionKey"));
        String businessKey= StringUtils.replaceNull(params.get("businessKey"));
        ProcessInstance processInstance=processInstanceService.startProcessInstance(processDefinitionKey,businessKey,params);
        String processDefinitionId=processInstance.getProcessDefinitionId();
        String processInstanceId=processInstance.getProcessInstanceId();
        Map<String,Object> data=new HashMap<String,Object>();
        data.put("processDefinitionId",processDefinitionId);
        data.put("processInstanceId",processInstanceId);
        res.setData(data);
        return res;
    }
    @ResponseBody
    @PostMapping(value = "/startAndFinishProcessInstance")
    @ApiOperation("启动和完成流程实例")
    public ResponseData startAndFinishProcessInstance(@RequestBody Map<String, Object> params){
        ResponseData res=ResponseData.ok();
        String processDefinitionKey= StringUtils.replaceNull(params.get("processDefinitionKey"));
        String businessKey= StringUtils.replaceNull(params.get("businessKey"));
        String assignee=StringUtils.replaceNull(params.get("assignee"));
        ProcessInstance processInstance=processInstanceService.startAndFinishProcessInstance(processDefinitionKey,businessKey,params);
        String processDefinitionId=processInstance.getProcessDefinitionId();
        String processInstanceId=processInstance.getProcessInstanceId();
        Map<String,Object> data=new HashMap<String,Object>();
        data.put("processDefinitionId",processDefinitionId);
        data.put("processInstanceId",processInstanceId);
        res.setData(data);
        return res;
    }
}
