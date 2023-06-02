package com.mall.admin.application.api.im;

import com.mall.admin.application.request.im.MaterialBuyDto;
import com.mall.admin.application.service.ProcessDefConstants;
import com.mall.admin.application.service.external.camunda.ProcessInstanceService;
import com.mall.admin.application.service.external.camunda.TaskService;
import com.mall.admin.application.service.im.MaterialBuyService;
import com.mall.admin.domain.entity.im.MaterialBuy;
import com.mall.admin.domain.entity.sm.ProcessTask;
import com.mall.admin.domain.entity.sm.Role;
import com.mall.common.response.BaseApi;
import com.mall.common.response.ResponseResult;
import com.mall.common.utils.DateUtils;
import com.mall.common.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MaterialBuyApi
 * @author youfu.wang
 * @date 2019-01-31
 */
@RestController
@RequestMapping("/material/buy")
@Slf4j
@Api("物品采购业务接口")
public class MaterialBuyApi extends BaseApi {
    @Autowired
    private MaterialBuyService materialBuyService;
    @Autowired
    private ProcessInstanceService processInstanceService;
    @Autowired
    private TaskService taskService;
    /**
     * 查询物采购业务列表
     * @return
     */
    @ResponseBody
    @GetMapping("/bizno")
    @ApiOperation("查询物采购业务列表")
    public ResponseResult getBizNo(@RequestParam Map<String, Object> params){
        ResponseResult res= ResponseResult.ok();
        try{
           res.setData("BIZ_BUY_"+DateUtils.format(DateUtils.getCurrentDate(),"yyyyMMddHHmmss"));
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res= ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }
    /**
     * 查询物采购业务列表
     * @return
     */
    @ResponseBody
    @GetMapping("/list")
    @ApiOperation("查询物采购业务列表")
    public ResponseResult getMaterialBuyList(@RequestParam Map<String, Object> params){
        ResponseResult res= ResponseResult.ok();
        try{
//            this.doStartPage(params);
            List<MaterialBuy> materialBuys=materialBuyService.getMaterialBuyList(params);
//            this.doFinishPage(res,materialBuys);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res= ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }
    @ResponseBody
    @PostMapping("/add")
    @ApiOperation("新增物品采购业务")
    public ResponseResult add(@RequestBody Map<String, Object> params) {
        ResponseResult res = ResponseResult.ok();
        try{
            MaterialBuyDto materialBuyDto=MaterialBuyDto.build(params);
            materialBuyService.addMaterialBuy(materialBuyDto);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res= ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }
    /**
     * 查询物采购业务列表
     * @return
     */
    @ResponseBody
    @GetMapping("/infoById")
    @ApiOperation("查询物采购业务列表")
    public ResponseResult infoById(@RequestParam Map<String, Object> params){
        ResponseResult res= ResponseResult.ok();
        try{
            MaterialBuy materialBuy=materialBuyService.getMaterialBuyById(StringUtils.replaceNull(params.get("id")));
            res.setData(materialBuy);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res= ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }
    @ResponseBody
    @PostMapping("/update")
    @ApiOperation("修改物品采购业务")
    public ResponseResult update(@RequestBody Map<String, Object> params) {
        ResponseResult res = ResponseResult.ok();
        try{
            MaterialBuyDto materialBuyDto=MaterialBuyDto.build(params);
            materialBuyService.updateMaterialBuy(materialBuyDto);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res= ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }
    @ResponseBody
    @PostMapping("/deleteOne")
    @ApiOperation("删除物品采购业务")
    public ResponseResult deleteOne(@RequestBody Map<String, Object> params) {
        ResponseResult res = ResponseResult.ok();
        try{
            materialBuyService.deleteMaterialBuy(params);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res= ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }
    @ResponseBody
    @PostMapping("/startTrans")
    @ApiOperation("流转物品采购业务")
    public ResponseResult startTrans(@RequestBody Map<String, Object> params) {
        ResponseResult res;
        String action=StringUtils.replaceNull(params.get("action"));
        MaterialBuyDto materialBuyDto=MaterialBuyDto.build(params);
        try {
            //启动物品采购流程
            Map<String, Object> processMap=new HashMap<String, Object>();
            processMap.put("processDefinitionKey", ProcessDefConstants.PROC_BIZTYPE_MATERIAL_BUY);
            processMap.put("businessKey",materialBuyDto.getMaterialBuy().getBizNo());
            processMap.put("userId",materialBuyDto.getMaterialBuy().getApplyUserId());
            processMap.put(Role.ROLE_PROCESS_STARTER,materialBuyDto.getMaterialBuy().getApplyUserId());
            processMap.put("amount",materialBuyDto.getMaterialBuy().getTotalAmt());
            res=processInstanceService.startProcessInstance(processMap);

            //处理物品采购业务数据
            if(ResponseResult.CODE_SUCCESS.equals(res.getRetCode())){
                Map<String,String> data=(Map<String,String>)res.getData();
                if(data!=null){
                    materialBuyService.handleMaterialStartProcess(
                            action,data.get("processInstanceId"),data.get("processDefinitionId"),materialBuyDto);
                }
            }
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
        Map<String, Object> materialBuyMap = (Map<String, Object>) params.get("formObj");
        String processDefinitionId=StringUtils.replaceNull(materialBuyMap.get("processDefinitionId"));
        String processInstanceId = StringUtils.replaceNull(materialBuyMap.get("processInstanceId"));
        String processInstanceState="active";
        String taskId = StringUtils.replaceNull(materialBuyMap.get("taskId"));
        String taskDefKey=StringUtils.replaceNull(materialBuyMap.get("taskDefKey"));
        String userId = StringUtils.replaceNull(materialBuyMap.get("userId"));
        String depId = StringUtils.replaceNull(materialBuyMap.get("depId"));
        String opinion = StringUtils.replaceNull(materialBuyMap.get("opinion"));
        MaterialBuyDto materialBuyDto=MaterialBuyDto.build(params);
        double amount=materialBuyDto.getMaterialBuy().getTotalAmt();
        try{
            //查询物品采购业务流程变量
            Map<String,Object> variables=materialBuyService.getMaterialBuyProcessVariables(
                    processDefinitionId,processInstanceId,taskId,taskDefKey,userId,depId,amount);
            if(!Role.ROLE_REPO_ADMIN.equals(taskDefKey)&&StringUtils.isNUll(variables.get("assignees"))){
                res=ResponseResult.error(ResponseResult.CODE_FAILURE,"对不起，实例任务下一个候选人不能为空！");
                return res;
            }
            //审批物品采购业务任务
            res=taskService.complete(variables);
            if(ResponseResult.CODE_SUCCESS.equals(res.getRetCode())) {
                //得到流程当前执行任务和实例状态(active;isEnd)
                Map<String, Object> taskMap=(Map<String, Object>) res.getData();
                taskId= StringUtils.replaceNull(taskMap.get("taskId"));
                processInstanceState = StringUtils.replaceNull(taskMap.get("processInstanceState"));

            }
            //处理物品采购业务任务数据
            ProcessTask processCurrentTask = ProcessTask.build(
                    null,processInstanceId,taskId,taskDefKey,null,userId,ProcessTask.PROCESS_STATE_COMPLETED);
            processCurrentTask.setOpinion(opinion);
            materialBuyService.handleMaterialBuyProcessTask(processInstanceState,variables,processCurrentTask,
                    materialBuyDto.getMaterialBuy().getBizNo());
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
        Map<String, Object> materialBuyMap = (Map<String, Object>) params.get("formObj");
        try{
            res=taskService.drawback(materialBuyMap);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }

}
