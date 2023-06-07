package com.mall.admin.application.api.im;

import com.mall.admin.application.request.im.MaterialBuyDto;
import com.mall.admin.application.request.im.ProcessTaskDto;
import com.mall.admin.application.service.external.camunda.TaskService;
import com.mall.admin.application.service.im.MaterialBuyService;
import com.mall.admin.application.service.sm.ProcessTaskService;
import com.mall.common.page.PageParam;
import com.mall.common.response.ResponseResult;
import com.mall.common.utils.DateUtils;
import com.mall.common.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * MaterialBuyApi
 *
 * @author youfu.wang
 * @date 2019-01-31
 */
@RestController
@RequestMapping("/material/buy")
@Slf4j
@Api("物品采购业务接口")
public class MaterialBuyApi {
    @Autowired
    private MaterialBuyService materialBuyService;
    @Autowired
    private ProcessTaskService processTaskService;
    @Autowired
    private TaskService taskService;

    /**
     * 查询物采购业务列表
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/bizno")
    @ApiOperation("查询物采购业务列表")
    public ResponseResult getBizNo(@RequestParam Map<String, Object> params) {
        ResponseResult res = ResponseResult.ok();
        try {
            res.setData("BIZ_BUY_" + DateUtils.format(DateUtils.getCurrentDate(), "yyyyMMddHHmmss"));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }

    /**
     * 查询物采购业务列表
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/list")
    @ApiOperation("查询物采购业务列表")
    public ResponseResult getMaterialBuyList(@RequestParam Map<String, Object> params) {
        ResponseResult res;
        try {
            PageParam pageParam = PageParam.getPageParam(params);
            res = materialBuyService.getMaterialBuyList(pageParam, params);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }

    @ResponseBody
    @PostMapping("/add")
    @ApiOperation("新增物品采购业务")
    public ResponseResult add(@RequestBody Map<String, Object> params) {
        ResponseResult res = ResponseResult.ok();
        try {
            MaterialBuyDto materialBuyDto = MaterialBuyDto.build(params);
            materialBuyService.addMaterialBuy(materialBuyDto);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }

    /**
     * 查询物采购业务列表
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/infoById")
    @ApiOperation("查询物采购业务列表")
    public ResponseResult infoById(@RequestParam Map<String, Object> params) {
        ResponseResult res = ResponseResult.ok();
        try {
            MaterialBuyDto materialBuyDto = materialBuyService.getMaterialBuyById(StringUtils.replaceNull(params.get("id")));
            Map<String, Object> formObj = new HashMap<>();
            formObj.put("materialBuyDto", materialBuyDto);
            //查询实例任务是否可撤回
            String isDrawback = "false";
            ResponseResult taskRes = taskService.isDrawback(params);
            if (ResponseResult.CODE_SUCCESS.equals(taskRes.getRetCode())
                    && taskRes.getData() != null
                    && "true".equals(StringUtils.replaceNull(((Map<String, Object>) taskRes.getData()).get("isDrawback")))
            ) {
                isDrawback = StringUtils.replaceNull(((Map<String, Object>) taskRes.getData()).get("isDrawback"));
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
    @PostMapping("/update")
    @ApiOperation("修改物品采购业务")
    public ResponseResult update(@RequestBody Map<String, Object> params) {
        ResponseResult res = ResponseResult.ok();
        try {
            MaterialBuyDto materialBuyDto = MaterialBuyDto.build(params);
            materialBuyService.updateMaterialBuy(materialBuyDto);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }

    @ResponseBody
    @PostMapping("/deleteOne")
    @ApiOperation("删除物品采购业务")
    public ResponseResult deleteOne(@RequestBody Map<String, Object> params) {
        ResponseResult res = ResponseResult.ok();
        try {
            materialBuyService.deleteMaterialBuy(params);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }

    @ResponseBody
    @PostMapping("/startTrans")
    @ApiOperation("流转物品采购业务")
    public ResponseResult startTrans(@RequestBody Map<String, Object> params) {
        ResponseResult res;
        String action = StringUtils.replaceNull(params.get("action"));
        MaterialBuyDto materialBuyDto = MaterialBuyDto.build(params);
        try {
            res = materialBuyService.handleMaterialStartProcess(action, materialBuyDto);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }

    @ResponseBody
    @PostMapping("/doTrans")
    @ApiOperation("处理流转物品采购业务")
    public ResponseResult doTrans(@RequestBody Map<String, Object> params) {
        ResponseResult res;
        try {
            ProcessTaskDto processTaskDto = ProcessTaskDto.build(params);
            MaterialBuyDto materialBuyDto = MaterialBuyDto.build(params);
            //审批驳回
            if("101".equals(processTaskDto.getOpinion())){
                res = processTaskService.handleRejectProcessTask(processTaskDto);
            }else{
                res = materialBuyService.handleMaterialBuyProcessTask(processTaskDto, materialBuyDto);
            }

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }

    @ResponseBody
    @PostMapping("/doDrawback")
    @ApiOperation("撤回流转物品采购业务")
    public ResponseResult doDrawback(@RequestBody Map<String, Object> params) {
        ResponseResult res;
        ProcessTaskDto processTaskDto = ProcessTaskDto.build(params);
        try {
            res = processTaskService.handleDrawbackProcessTask(processTaskDto);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }
    @ResponseBody
    @PostMapping("/doReject")
    @ApiOperation("驳回流转物品采购业务")
    public ResponseResult doReject(@RequestBody Map<String, Object> params) {
        ResponseResult res;
        ProcessTaskDto processTaskDto = ProcessTaskDto.build(params);
        try {
            res = processTaskService.handleRejectProcessTask(processTaskDto);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }

}
