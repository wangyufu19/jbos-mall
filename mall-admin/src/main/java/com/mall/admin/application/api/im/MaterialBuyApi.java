package com.mall.admin.application.api.im;

import com.mall.admin.application.api.BaseApi;
import com.mall.admin.application.external.camunda.ProcessInstanceService;
import com.mall.admin.application.service.im.MaterialBuyService;
import com.mall.admin.domain.entity.im.MaterialBuy;
import com.mall.common.response.ResponseData;
import com.mall.common.utils.DateUtils;
import com.mall.common.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
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
    /**
     * 查询物采购业务列表
     * @return
     */
    @ResponseBody
    @GetMapping("/bizno")
    @ApiOperation("查询物采购业务列表")
    public ResponseData getBizNo(@RequestParam Map<String, Object> params){
        ResponseData res=ResponseData.ok();
        try{
           res.setData("BIZ_BUY_"+DateUtils.format(DateUtils.getCurrentDate(),"yyyyMMddHHmmss"));
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res=ResponseData.error(ResponseData.RETCODE_FAILURE,ResponseData.RETMSG_FAILURE);
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
    public ResponseData getMaterialBuyList(@RequestParam Map<String, Object> params){
        ResponseData res=ResponseData.ok();
        try{
            this.doStartPage(params);
            List<MaterialBuy> materialBuys=materialBuyService.getMaterialBuyList(params);
            this.doFinishPage(res,materialBuys);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res=ResponseData.error(ResponseData.RETCODE_FAILURE,ResponseData.RETMSG_FAILURE);
        }
        return res;
    }
    @ResponseBody
    @PostMapping("/add")
    @ApiOperation("新增物品采购业务")
    public ResponseData add(@RequestBody Map<String, Object> params) {
        ResponseData res = ResponseData.ok();
        try{
            Map<String,Object> materialBuyMap=(Map<String,Object>)params.get("formObj");
            String applyUserId=StringUtils.replaceNull(materialBuyMap.get("applyUserId"));
            String bizNo=StringUtils.replaceNull(materialBuyMap.get("bizNo"));
            double totalAmt=Double.parseDouble(StringUtils.replaceNull(materialBuyMap.get("totalAmt")));
            List<Map<String,Object>> materials=(ArrayList<Map<String,Object>>)params.get("materials");
            MaterialBuy materialBuy=new MaterialBuy();
            materialBuy.setId(StringUtils.getUUID());
            materialBuy.setBizNo(bizNo);
            materialBuy.setApplyUserId(applyUserId);
            materialBuy.setApplyDepId(StringUtils.replaceNull(materialBuyMap.get("applyDepId")));
            materialBuy.setApplyTime(DateUtils.parse(StringUtils.replaceNull(materialBuyMap.get("applyTime"))));
            materialBuy.setGmoTime(DateUtils.parse(StringUtils.replaceNull(materialBuyMap.get("gmoTime"))));
            materialBuy.setTotalAmt(totalAmt);
            materialBuy.setPurpose(StringUtils.replaceNull(materialBuyMap.get("purpose")));
            materialBuy.setBizState("10");
            materialBuyService.addMaterialBuy(materialBuy,materials);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res=ResponseData.error(ResponseData.RETCODE_FAILURE,ResponseData.RETMSG_FAILURE);
        }
        return res;
    }
    @ResponseBody
    @PostMapping("/startTrans")
    @ApiOperation("流转物品采购业务")
    public ResponseData startTrans(@RequestBody Map<String, Object> params) {
        ResponseData res = ResponseData.ok();
        String action=StringUtils.replaceNull(params.get("action"));
        Map<String,Object> materialBuyMap=(Map<String,Object>)params.get("formObj");
        String applyUserId=StringUtils.replaceNull(materialBuyMap.get("applyUserId"));
        String bizNo=StringUtils.replaceNull(materialBuyMap.get("bizNo"));
        double totalAmt=Double.parseDouble(StringUtils.replaceNull(materialBuyMap.get("totalAmt")));
        try {
            //启动物品采购流程
            Map<String, Object> processParams=new HashMap<String, Object>();
            processParams.put("userId",applyUserId);
            processParams.put("applyUserId",applyUserId);
            processParams.put("processDefinitionKey","materialBuy");
            processParams.put("businessKey",bizNo);
            processParams.put("amount",totalAmt);

            if("create".equals(action)){
                res=this.add(params);
            }else if("update".equals(action)){
                res=this.update(params);
            }
            if(ResponseData.RETCODE_SUCCESS.equals(res.getRetCode())){
                res=processInstanceService.startProcessInstance(processParams);
            }
            if(ResponseData.RETCODE_SUCCESS.equals(res.getRetCode())){
                Map<String,Object> data=(Map<String,Object>)res.getData();
                if(data!=null){
                    materialBuyMap.put("INSTID",data.get("processInstanceId"));
                }
                materialBuyMap.put("BIZSTATE","20");
                //更新物品采购业务实例ID和业务状态
                materialBuyService.updateMaterialInstIdAndBizState(materialBuyMap);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponseData.error(ResponseData.RETCODE_FAILURE, ResponseData.RETMSG_FAILURE);
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
    public ResponseData infoById(@RequestParam Map<String, Object> params){
        ResponseData res=ResponseData.ok();
        try{
            MaterialBuy materialBuy=materialBuyService.getMaterialBuyById(StringUtils.replaceNull(params.get("id")));
            res.setData(materialBuy);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res=ResponseData.error(ResponseData.RETCODE_FAILURE,ResponseData.RETMSG_FAILURE);
        }
        return res;
    }
    @ResponseBody
    @PostMapping("/update")
    @ApiOperation("修改物品采购业务")
    public ResponseData update(@RequestBody Map<String, Object> params) {
        ResponseData res = ResponseData.ok();
        try{
            Map<String,Object> materialBuyMap=(Map<String,Object>)params.get("formObj");
            List<Map<String,Object>> materials=(ArrayList<Map<String,Object>>)params.get("materials");
            MaterialBuy materialBuy=new MaterialBuy();
            materialBuy.setId(StringUtils.replaceNull(materialBuyMap.get("id")));
            materialBuy.setBizNo(StringUtils.replaceNull(materialBuyMap.get("bizNo")));
            materialBuy.setApplyUserId(StringUtils.replaceNull(materialBuyMap.get("applyUserId")));
            materialBuy.setApplyDepId(StringUtils.replaceNull(materialBuyMap.get("applyDepId")));
            materialBuy.setApplyTime(DateUtils.parse(StringUtils.replaceNull(materialBuyMap.get("applyTime"))));
            materialBuy.setGmoTime(DateUtils.parse(StringUtils.replaceNull(materialBuyMap.get("gmoTime"))));
            materialBuy.setTotalAmt(Double.parseDouble(StringUtils.replaceNull(materialBuyMap.get("totalAmt"))));
            materialBuy.setPurpose(StringUtils.replaceNull(materialBuyMap.get("purpose")));
            materialBuyService.updateMaterialBuy(materialBuy,materials);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res=ResponseData.error(ResponseData.RETCODE_FAILURE,ResponseData.RETMSG_FAILURE);
        }
        return res;
    }
    @ResponseBody
    @PostMapping("/deleteOne")
    @ApiOperation("删除物品采购业务")
    public ResponseData deleteOne(@RequestBody Map<String, Object> params) {
        ResponseData res = ResponseData.ok();
        try{
            materialBuyService.deleteMaterialBuy(params);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res=ResponseData.error(ResponseData.RETCODE_FAILURE,ResponseData.RETMSG_FAILURE);
        }
        return res;
    }
}
