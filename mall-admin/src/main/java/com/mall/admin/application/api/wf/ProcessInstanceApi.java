package com.mall.admin.application.api.wf;

import com.mall.admin.application.service.sm.BusinessDict;
import com.mall.admin.application.service.wf.ProcessMgrService;
import com.mall.common.page.PageParam;
import com.mall.common.response.ResponsePageResult;
import com.mall.common.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * ProcessInstanceApi
 *
 * @author youfu.wang
 * @date 2023/6/8
 **/
@RestController
@RequestMapping("/workflow/instance")
@Slf4j
@Api(tags = "流程实例接口")
public class ProcessInstanceApi {
    @Autowired
    private ProcessMgrService processMgrService;
    @Autowired
    private BusinessDict businessDict;

    @ResponseBody
    @PostMapping(value = "/startProcessInstance")
    @ApiOperation("启动流程实例")
    public ResponseResult startProcessInstance(@RequestBody Map<String, Object> params) {
        ResponseResult res = ResponseResult.ok();
        Map<String, Object> formMap = (Map<String, Object>) params.get("formObj");
        try {
            res = processMgrService.startProcessInstance(formMap, null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }

    /**
     * 查询流程实例列表
     *
     * @param params
     * @return
     */
    @ResponseBody
    @GetMapping("/getProcessInstanceList")
    @ApiOperation("查询流程实例列表")
    public ResponsePageResult getProcessInstanceList(@RequestParam Map<String, Object> params) {
        ResponsePageResult res = ResponsePageResult.ok();
        try {
            PageParam pageParam = PageParam.getPageParam(params);
            res = processMgrService.getProcessInstList(pageParam, params);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponsePageResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }
    /**
     * 查询流程实例默认变量列表
     *
     * @param params
     * @return
     */
    @ResponseBody
    @GetMapping("/getProcessInstanceVariableList")
    @ApiOperation("查询流程实例默认变量列表")
    public ResponseResult getProcessInstanceVariableList(@RequestParam Map<String, Object> params) {
        ResponseResult res = ResponseResult.ok();
        try {
            String typeId=String.valueOf(params.get("typeId"));
            List<Map<String, Object>> dictList= businessDict.getDictCodeList(typeId);
            res.setData(dictList);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }
    @ResponseBody
    @GetMapping(value = "/getProcessInstanceCurrentActivityId")
    @ApiOperation("查询流程实例当前活动")
    public ResponseResult getProcessInstanceCurrentActivityId(@RequestParam Map<String, Object> params) {
        ResponseResult res = ResponseResult.ok();
        String processDefinitionId=String.valueOf(params.get("processDefinitionId"));
        String processInstanceId=String.valueOf(params.get("processInstanceId"));
        try {
            Map<String,Object> data=processMgrService.getProcessInstanceCurrentActivityId(
                    processDefinitionId,processInstanceId);
            res.setData(data);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponseResult.error(ResponseResult.CODE_FAILURE, e.getMessage());
        }
        return res;
    }

    @ResponseBody
    @PostMapping("/suspendProcessInstance")
    @ApiOperation("查询流程实例列表")
    public ResponseResult suspendProcessInstance(@RequestBody Map<String, Object> params) {
        ResponseResult res = ResponseResult.ok();
        String processInstanceId=String.valueOf(params.get("processInstanceId"));
        try {
            processMgrService.suspendProcessInstanceById(processInstanceId);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }

    @ResponseBody
    @PostMapping("/activateProcessInstance")
    @ApiOperation("查询流程实例列表")
    public ResponseResult activateProcessInstance(@RequestBody Map<String, Object> params) {
        ResponseResult res = ResponseResult.ok();
        String processInstanceId=String.valueOf(params.get("processInstanceId"));
        try {
            processMgrService.activateProcessInstanceById(processInstanceId);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }

    @ResponseBody
    @PostMapping("/deleteProcessInstance")
    @ApiOperation("删除流程实例")
    public ResponseResult deleteProcessInstance(@RequestBody Map<String, Object> params) {
        ResponseResult res = ResponseResult.ok();
        String processInstanceId=String.valueOf(params.get("processInstanceId"));
        try {
            processMgrService.deleteProcessInstance(processInstanceId);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }
}
