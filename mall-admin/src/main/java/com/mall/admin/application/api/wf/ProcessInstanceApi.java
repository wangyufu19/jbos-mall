package com.mall.admin.application.api.wf;

import com.mall.admin.application.service.external.camunda.ProcessInstanceService;
import com.mall.admin.application.service.sm.BusinessDict;
import com.mall.admin.application.service.wf.ProcessMgrService;
import com.mall.admin.domain.entity.wf.ProcessInst;
import com.mall.common.page.PageParam;
import com.mall.common.response.ResponseResult;
import com.mall.common.utils.StringUtils;
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
@Api("流程实例接口")
public class ProcessInstanceApi {
    @Autowired
    private ProcessInstanceService processInstanceService;
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
    public ResponseResult getProcessInstanceList(@RequestParam Map<String, Object> params) {
        ResponseResult res = ResponseResult.ok();
        try {
            PageParam pageParam = PageParam.getPageParam(params);
            res = processMgrService.getProcessInstList(pageParam, params);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
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
            String typeId=StringUtils.replaceNull(params.get("typeId"));
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
        try {
            res = processInstanceService.getProcessInstanceCurrentActivityId(params);
            if (ResponseResult.CODE_SUCCESS.equals(res.getRetCode())) {
                Map<String, Object> data = (Map<String, Object>) res.getData();
                res.setData(data);
            }

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
        try {
            res = processInstanceService.suspendProcessInstanceById(params);
            if (ResponseResult.CODE_SUCCESS.equals(res.getRetCode())) {
                String processInstanceId = StringUtils.replaceNull(params.get("processInstanceId"));
                processMgrService.updateProcState(processInstanceId, ProcessInst.PROCESS_STATE_SUSPENDED);
            }
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
        try {
            res = processInstanceService.activateProcessInstanceById(params);
            if (ResponseResult.CODE_SUCCESS.equals(res.getRetCode())) {
                String processInstanceId = StringUtils.replaceNull(params.get("processInstanceId"));
                processMgrService.updateProcState(processInstanceId, ProcessInst.PROCESS_STATE_ACTIVE);
            }
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
        try {
            res = processInstanceService.deleteProcessInstance(params);
            if (ResponseResult.CODE_SUCCESS.equals(res.getRetCode())) {
                String processInstanceId = StringUtils.replaceNull(params.get("processInstanceId"));
                processMgrService.updateProcState(processInstanceId, ProcessInst.PROCESS_STATE_CANCELED);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }
}
