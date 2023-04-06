package com.mall.admin.application.service.im;

import com.mall.admin.application.service.sm.ProcessMgrService;
import com.mall.admin.application.service.sm.ProcessTaskService;
import com.mall.admin.domain.entity.im.MaterialBuy;
import com.mall.admin.domain.entity.im.MaterialList;
import com.mall.admin.domain.entity.sm.ProcessInst;
import com.mall.admin.domain.entity.sm.ProcessTask;
import com.mall.admin.infrastructure.repository.im.MaterialBuyRepo;
import com.mall.common.utils.DateUtils;
import com.mall.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MaterialBuyService
 * @author youfu.wang
 * @date 2020-06-24
 */
@Service
public class MaterialBuyService {
    @Autowired
    private ProcessMgrService processMgrService;
    @Autowired
    private ProcessTaskService processTaskService;
    @Autowired
    private MaterialBuyRepo materialBuyRepo;
    @Autowired
    private MaterialListService materialListService;

    /**
     * 得到物品采购列表
     * @param parameterObject
     * @return
     */
    public List<MaterialBuy> getMaterialBuyList(Map<String, Object> parameterObject){
        return materialBuyRepo.getMaterialBuyList(parameterObject);
    }

    /**
     * 得到物品采购信息
     * @param id
     * @return
     */
    public MaterialBuy getMaterialBuyById(String id){
        return materialBuyRepo.getMaterialBuyById(id);
    }
    /**
     * 新增物品采购
     * @param materialBuy
     * @param materials
     */
    @Transactional
    public void addMaterialBuy(MaterialBuy materialBuy,List<Map<String,Object>> materials){
        //新增物品采购基本信息
        materialBuyRepo.addMaterialBuy(materialBuy);
        //新增物品采购清单
        this.addMaterialList(materialBuy.getId(),materials);
    }

    /**
     * 新增物品采购清单
     * @param bizId
     * @param materials
     */
    private void addMaterialList(String bizId,List<Map<String,Object>> materials){
        if(materials!=null){
            for(Map<String,Object> materialMap:materials){
                MaterialList materialList=new MaterialList();
                materialList.setId(StringUtils.getUUID());
                materialList.setBizId(bizId);
                materialList.setBizType("BUY");
                materialList.setMaterialName(StringUtils.replaceNull(materialMap.get("materialName")));
                materialList.setAmount(Double.parseDouble(StringUtils.replaceNull(materialMap.get("amount"))));
                materialList.setPrice(Double.parseDouble(StringUtils.replaceNull(materialMap.get("price"))));
                materialListService.addMaterialList(materialList);
            }
        }
    }
    /**
     * 更新物品采购清单
     * @param materialBuy
     * @param materials
     */
    @Transactional
    public void updateMaterialBuy(MaterialBuy materialBuy,List<Map<String,Object>> materials){
        //更新物品采购基本信息
        materialBuyRepo.updateMaterialBuy(materialBuy);
        Map<String, Object> parameterObject=new HashMap<String, Object>();
        parameterObject.put("bizid",materialBuy.getId());
        parameterObject.put("biztype","BUY");
        //删除物品采购清单
        materialListService.deleteMaterial(parameterObject);
        //新增物品采购清单
        this.addMaterialList(materialBuy.getId(),materials);
    }

    /**
     * 删除物品采购
     * @param parameterObject
     */
    @Transactional
    public void deleteMaterialBuy(Map<String, Object> parameterObject){
        //删除物品采购基本信息
        materialBuyRepo.deleteMaterialBuy(parameterObject);
        Map<String, Object> listParams=new HashMap<String, Object>();
        listParams.put("bizid",StringUtils.replaceNull(parameterObject.get("id")));
        listParams.put("biztype","BUY");
        //删除物品采购清单
        materialListService.deleteMaterial(listParams);
    }

    /**
     * 处理物品采购业务流程数据
     */
    @Transactional
    public void handleMaterialStartProcess(String bizId,String bizNo,String userId,String processInstanceId,String processDefinitionId){
        String currentTime=DateUtils.format(DateUtils.getCurrentDate(),DateUtils.YYYYMMDDHIMMSS);
        ProcessInst processInst=new ProcessInst();
        //新增物品采购流程实例数据
        processInst.setId(StringUtils.getUUID());
        processInst.setProcInstId(processInstanceId);
        processInst.setProcDefId(processDefinitionId);
        processInst.setUserId(userId);
        processInst.setBizId(bizId);
        processInst.setBizNo(bizNo);
        processInst.setBusinessKey(bizNo);
        processInst.setStartTime(currentTime);
        processInst.setCreateUserId(userId);
        processInst.setCreateTime(currentTime);
        processMgrService.addProcessInst(processInst);
        //新增物品采购流程任务数据
        ProcessTask processTask=new ProcessTask();
        processTask.setId(StringUtils.getUUID());
        processTask.setProcInstId(processInstanceId);
        processTask.setAssignee(userId);
        processTask.setStartTime(currentTime);
        processTask.setCreateUserId(userId);
        processTask.setCreateTime(currentTime);
        processTaskService.addProcessTask(processTask);
        //更新物品采购业务实例ID和业务状态
        Map<String, Object> parameterObject=new HashMap<>();
        parameterObject.put("BIZNO",bizNo);
        parameterObject.put("INSTID",processInst.getProcInstId());
        parameterObject.put("BIZSTATE","20");
        this.updateMaterialInstIdAndBizState(parameterObject);
    }
    /**
     * 更新物品采购业务实例ID和业务状态
     * @param parameterObject
     */
    public void updateMaterialInstIdAndBizState(Map<String, Object> parameterObject){
        materialBuyRepo.updateMaterialInstIdAndBizState(parameterObject);
    }

    /**
     * 更新物品采购业务状态
     * @param parameterObject
     */
    public void updateMaterialBizState(Map<String, Object> parameterObject){
        materialBuyRepo.updateMaterialBizState(parameterObject);
    }
}
