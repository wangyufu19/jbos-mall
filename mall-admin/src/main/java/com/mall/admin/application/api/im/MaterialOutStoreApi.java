package com.mall.admin.application.api.im;

import com.mall.admin.application.request.im.MaterialOutStoreDto;
import com.mall.admin.application.request.wf.ProcessTaskDto;
import com.mall.admin.application.service.comm.BizGeneratorService;
import com.mall.admin.application.service.im.MaterialOutStoreService;
import com.mall.admin.application.service.wf.ProcessTaskService;
import com.mall.admin.domain.entity.wf.ProcessTask;
import com.mall.admin.infrastructure.camunda.InstanceTaskService;
import com.mall.common.page.PageParam;
import com.mall.common.response.ResponseResult;
import com.mall.common.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * MaterialOutStoreApi
 *
 * @author youfu.wang
 * @date 2023/7/14
 **/
@RestController
@RequestMapping("/material/outStore")
@Slf4j
@Api(tags = "物品领取接口")
public class MaterialOutStoreApi {
    @Autowired
    private BizGeneratorService bizGeneratorService;
    @Autowired
    private MaterialOutStoreService materialOutStoreService;
    @Autowired
    private InstanceTaskService instanceTaskService;
    @Autowired
    private ProcessTaskService processTaskService;

    @ResponseBody
    @GetMapping("/getBizNo")
    @ApiOperation("查询物品领取业务编号")
    public ResponseResult getBizNo(@RequestParam Map<String, Object> params) {
        ResponseResult res = ResponseResult.ok();
        try {
            res.setData(bizGeneratorService.getBizNo());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }
    /**
     * 查询物领取业务列表
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/list")
    @ApiOperation("查询物领取业务列表")
    public ResponseResult getMaterialOutStoreList(@RequestParam Map<String, Object> params) {
        ResponseResult res;
        try {
            PageParam pageParam = PageParam.getPageParam(params);
            res = materialOutStoreService.getMaterialOutStoreList(pageParam, params);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }
    /**
     * 查询物领取业务
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/infoById")
    @ApiOperation("查询物领取业务")
    public ResponseResult infoById(@RequestParam Map<String, Object> params) {
        ResponseResult res = ResponseResult.ok();
        try {
            MaterialOutStoreDto materialOutStoreDto = materialOutStoreService.getMaterialOutStoreById(
                    StringUtils.replaceNull(params.get("id")));
            Map<String, Object> formObj = new HashMap<>();
            formObj.put("materialOutStoreDto", materialOutStoreDto);
            //如果是待办处理路由则查询当前任务是否可撤回
            boolean isDrawback=false;
            //查询实例任务是否可撤回
            if ("trans".equals(params.get("action"))) {
                String userId=StringUtils.replaceNull(params.get("userId"));
                String processInstanceId= StringUtils.replaceNull(params.get("processInstanceId"));
                String taskId=StringUtils.replaceNull(params.get("taskId"));
                isDrawback = instanceTaskService.isDrawback(userId,processInstanceId,taskId);
            }
            formObj.put("isDrawback", isDrawback);
            res.setData(formObj);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }

    @ResponseBody
    @PostMapping("/add")
    @ApiOperation("新增物品领取业务")
    public ResponseResult add(@RequestBody Map<String, Object> params) {
        ResponseResult res = ResponseResult.ok();
        try {
            MaterialOutStoreDto materialOutStoreDto = MaterialOutStoreDto.build(params);
            materialOutStoreService.addMaterialOutStore(materialOutStoreDto);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }

    @ResponseBody
    @PostMapping("/update")
    @ApiOperation("修改物品领取业务")
    public ResponseResult update(@RequestBody Map<String, Object> params) {
        ResponseResult res = ResponseResult.ok();
        try {
            MaterialOutStoreDto materialOutStoreDto = MaterialOutStoreDto.build(params);
            materialOutStoreService.updateMaterialOutStore(materialOutStoreDto);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }

    @ResponseBody
    @PostMapping("/deleteOne")
    @ApiOperation("删除物品领取业务")
    public ResponseResult deleteOne(@RequestBody Map<String, Object> params) {
        ResponseResult res = ResponseResult.ok();
        try {
            materialOutStoreService.deleteMaterialOutStore(params);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }

    @ResponseBody
    @PostMapping("/startTrans")
    @ApiOperation("流转物品领取业务")
    public ResponseResult startTrans(@RequestBody Map<String, Object> params) {
        ResponseResult res=ResponseResult.ok();
        try {
            MaterialOutStoreDto materialOutStoreDto = MaterialOutStoreDto.build(params);
            materialOutStoreService.startMaterialOutStore(materialOutStoreDto);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }
    @ResponseBody
    @PostMapping("/doTrans")
    @ApiOperation("处理物品领取流程")
    public ResponseResult doTrans(@RequestBody Map<String, Object> params) {
        ResponseResult res;
        try {
            res=materialOutStoreService.handleMaterialOutStore(params);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponseResult.error(ResponseResult.CODE_FAILURE, e.getMessage());
        }
        return res;
    }
    @ResponseBody
    @PostMapping("/doDrawback")
    @ApiOperation("撤回物品领取流程")
    public ResponseResult doDrawback(@RequestBody Map<String, Object> params) {
        ResponseResult res;
        try {
            Map<String,Object> formObjMap=(Map<String,Object>)params.get("formObj");
            ProcessTask processTask = ProcessTaskDto.build(formObjMap);
            res = processTaskService.drawbackProcessTask(processTask);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }
    @ResponseBody
    @PostMapping("/doReject")
    @ApiOperation("驳回物品领取流程")
    public ResponseResult doReject(@RequestBody Map<String, Object> params) {
        ResponseResult res;
        try {
            Map<String,Object> formObjMap=(Map<String,Object>)params.get("formObj");
            ProcessTask processTask = ProcessTaskDto.build(formObjMap);
            res = processTaskService.rejectProcessTask(processTask);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }
}
