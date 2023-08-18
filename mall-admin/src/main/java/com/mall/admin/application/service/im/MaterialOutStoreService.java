package com.mall.admin.application.service.im;


import com.mall.admin.application.request.im.MaterialOutStoreDto;
import com.mall.admin.application.request.wf.ProcessTaskDto;
import com.mall.admin.application.service.sm.BusinessDict;
import com.mall.admin.application.service.wf.ProcessCallback;
import com.mall.admin.application.service.wf.ProcessDefConstants;
import com.mall.admin.application.service.wf.ProcessMgrService;
import com.mall.admin.application.service.wf.ProcessTaskService;
import com.mall.admin.common.exception.CamundaException;
import com.mall.admin.domain.entity.im.MaterialOutStore;
import com.mall.admin.domain.entity.im.MaterialOutStoreList;
import com.mall.admin.domain.entity.im.MaterialStore;
import com.mall.admin.domain.entity.sm.Role;
import com.mall.admin.domain.entity.wf.ProcessInst;
import com.mall.admin.domain.entity.wf.ProcessTask;
import com.mall.admin.infrastructure.repository.im.MaterialOutStoreRepo;
import com.mall.common.page.PageParam;
import com.mall.common.response.ResponseResult;
import com.mall.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MaterialOutStoreService
 *
 * @author youfu.wang
 * @date 2023/7/13
 **/
@Service
public class MaterialOutStoreService {
    @Autowired
    private BusinessDict businessDict;
    @Autowired
    private ProcessMgrService processMgrService;
    @Autowired
    private ProcessTaskService processTaskService;
    @Autowired
    private MaterialOutStoreRepo materialOutStoreRepo;
    @Autowired
    private MaterialStoreService materialStoreService;
    /**
     * 得到物品领取列表
     *
     * @param parameterObject
     * @return
     */
    public ResponseResult getMaterialOutStoreList(PageParam pageParam, Map<String, Object> parameterObject) {
        List<MaterialOutStore> materialOutStoreList = materialOutStoreRepo.getMaterialOutStoreList(pageParam,parameterObject);
        return ResponseResult.ok().isPage(true).setData(materialOutStoreList);
    }

    /**
     * 得到物品领取清单
     * @param bizId
     * @return
     */
    private List<MaterialOutStoreList> getMaterialOutStoreItem(String bizId){
        Map<String, Object> parameterObject = new HashMap<>();
        parameterObject.put("bizId", bizId);
        List<MaterialOutStoreList> materialListList = materialOutStoreRepo.getMaterialOutStoreItem(parameterObject);
        return materialListList;
    }
    /**
     * 得到物品领取数据
     * @param id
     * @return
     */
    public MaterialOutStoreDto getMaterialOutStoreById(String id) {
        MaterialOutStoreDto materialOutStoreDto=new MaterialOutStoreDto();
        MaterialOutStore materialOutStore = materialOutStoreRepo.getMaterialOutStoreById(id);
        materialOutStoreDto.setMaterialOutStore(materialOutStore);
        materialOutStoreDto.setMaterialList(this.getMaterialOutStoreItem(id));
        return materialOutStoreDto;
    }

    /**
     * 新增物品领取数据
     * @param materialOutStoreDto
     */
    @Transactional
    public void addMaterialOutStore(MaterialOutStoreDto materialOutStoreDto) {
        List<MaterialOutStoreList> materialListList=materialOutStoreDto.getMaterialList();
        //根据进先出规则得到物品领取的库存数据
        List<MaterialOutStoreList> materialOutStoreListList=
                this.getMaterialOutStoreByFIFO(materialOutStoreDto.getMaterialOutStore().getId(),materialListList);
        //新增物品领取清单
        materialOutStoreRepo.addMaterialOutStoreItem(materialOutStoreListList);
        //新增物品领取信息
        materialOutStoreRepo.addMaterialOutStore(materialOutStoreDto.getMaterialOutStore());
    }

    /**
     * 根据进先出规则得到物品领取的库存数据
     * @param bizId
     * @param materialListList
     */
    private List<MaterialOutStoreList> getMaterialOutStoreByFIFO(String bizId,List<MaterialOutStoreList> materialListList){
        List<MaterialOutStoreList> materialOutStoreListList=new ArrayList<>();
        //处理物品库存数据
        if (materialListList != null) {
            for (MaterialOutStoreList materialList : materialListList) {
                //根据物品库存先进先出规则处理领取物品
                this.processMaterialOutStoreByFIFO(
                        materialOutStoreListList,
                        bizId,
                        materialList.getMaterialId(),
                        materialList.getAmount()
                );
            }
        }
        return materialOutStoreListList;
    }
    /**
     * 根据物品库存先进先出规则处理领取物品
     * @param materialId
     * @param amount
     */
    private void processMaterialOutStoreByFIFO(
            List<MaterialOutStoreList> materialOutStoreListList,
            String bizId,
            String materialId,
            double amount){
        double surplusOutStoreAmt=amount;//剩余领取数量
        double processOutStoreAmt=0.00;//待处理领取数量
        //根据物品库存先进先出规则查询物品库存数据
        List<MaterialStore> materialStoreList=materialStoreService.getFIFOInfoByMaterialId(materialId);
        if (materialStoreList != null) {
            for(MaterialStore materialStore:materialStoreList){

                if(surplusOutStoreAmt<=materialStore.getSurplusAmt()){
                    //剩余领取数量小于等于库存数量
                    processOutStoreAmt=surplusOutStoreAmt;
                }else{
                    //剩余领取数量大于库存数量
                    processOutStoreAmt=materialStore.getSurplusAmt();
                }
                MaterialOutStoreList materialOutStoreList=new MaterialOutStoreList();
                materialOutStoreList.setId(StringUtils.getUUID());
                materialOutStoreList.setBizId(bizId);
                materialOutStoreList.setBizType(ProcessDefConstants.PROC_DEF_MATERIAL_OUT_STORE);
                materialOutStoreList.setBatchNo(materialStore.getBatchNo());
                materialOutStoreList.setMaterialId(materialStore.getMaterialId());
                materialOutStoreList.setMaterialName(materialStore.getMaterialName());
                materialOutStoreList.setAmount(processOutStoreAmt);
                materialOutStoreList.setPrice(materialStore.getPrice());
                materialOutStoreListList.add(materialOutStoreList);
                //减少物品库存数据
                materialStoreService.updateMaterialStore(materialStore.getBatchNo(),materialId,-processOutStoreAmt);
                surplusOutStoreAmt=surplusOutStoreAmt-processOutStoreAmt;
                if(surplusOutStoreAmt<=0){
                    break;
                }
            }
        }
    }
    /**
     * 更新物品领取数据
     *
     * @param materialOutStoreDto
     */
    @Transactional
    public void updateMaterialOutStore(MaterialOutStoreDto materialOutStoreDto) {
        List<MaterialOutStoreList> materialListList=materialOutStoreDto.getMaterialList();
        Map<String, Object> parameterObject = new HashMap();
        parameterObject.put("bizId", materialOutStoreDto.getMaterialOutStore().getId());
        parameterObject.put("bizType", ProcessDefConstants.PROC_DEF_MATERIAL_OUT_STORE);
        //释放物品领取库存
        this.releaseMaterialStore(materialOutStoreDto.getMaterialOutStore().getId());
        //删除物品领取清单
        materialOutStoreRepo.deleteMaterialOutStoreItem(parameterObject);
        //根据进先出规则得到物品领取的库存数据
        List<MaterialOutStoreList> materialOutStoreListList=
                this.getMaterialOutStoreByFIFO(materialOutStoreDto.getMaterialOutStore().getId(),materialListList);
        //新增物品领取清单
        materialOutStoreRepo.addMaterialOutStoreItem(materialOutStoreListList);
        //更新物品领取信息
        materialOutStoreRepo.updateMaterialOutStore(materialOutStoreDto.getMaterialOutStore());
    }

    /**
     * 释放物品领取库存
     * @param bizId
     */
    private void releaseMaterialStore(String bizId){
        //得到业务物品清单
        List<MaterialOutStoreList> srcMaterialOutStoreListList=this.getMaterialOutStoreItem(bizId);
        //释放物品领取的库存数据
        if(srcMaterialOutStoreListList!=null){
            for(MaterialOutStoreList materialOutStoreList:srcMaterialOutStoreListList){
                //增加物品库存数据
                materialStoreService.updateMaterialStore(
                        materialOutStoreList.getBatchNo(),
                        materialOutStoreList.getMaterialId(),
                        materialOutStoreList.getAmount()
                );
            }
        }
    }
    /**
     * 删除物品领取
     *
     * @param parameterObject
     */
    @Transactional
    public void deleteMaterialOutStore(Map<String, Object> parameterObject) {
        //删除物品领取基本信息
        materialOutStoreRepo.deleteMaterialOutStore(parameterObject);
        //释放物品领取库存
        this.releaseMaterialStore(StringUtils.replaceNull(parameterObject.get("id")));
        //删除物品领取清单
        parameterObject.put("bizId", StringUtils.replaceNull(parameterObject.get("id")));
        parameterObject.put("bizType", ProcessDefConstants.PROC_DEF_MATERIAL_OUT_STORE);
        materialOutStoreRepo.deleteMaterialOutStoreItem(parameterObject);

    }

    /**
     * 启动物品领取流程
     * @param materialOutStoreDto
     * @throws CamundaException
     */
    @Transactional
    public void startMaterialOutStore(MaterialOutStoreDto materialOutStoreDto) throws CamundaException {
        //启动物品领取流程
        Map<String, Object> processMap = new HashMap<String, Object>();
        processMap.put("processDefinitionKey", ProcessDefConstants.PROC_DEF_MATERIAL_OUT_STORE);
        processMap.put("businessKey", "KEY_" + materialOutStoreDto.getMaterialOutStore().getBizNo());
        processMap.put("userId", materialOutStoreDto.getMaterialOutStore().getApplyUserId());
        processMap.put("startActivityId", Role.ROLE_PROCESS_STARTER);
        processMap.put("routeUrl",
                businessDict.getDictValue("JBOS_PROC_ROUTE", ProcessDefConstants.PROC_DEF_MATERIAL_OUT_STORE));
        processMap.put("bizId", materialOutStoreDto.getMaterialOutStore().getId());
        processMap.put("bizNo", materialOutStoreDto.getMaterialOutStore().getBizNo());
        processMap.put("bizType", ProcessDefConstants.PROC_DEF_MATERIAL_OUT_STORE);
        processMap.put("amount", materialOutStoreDto.getMaterialOutStore().getTotalAmt());

        processMgrService.startProcessInstance(processMap,new ProcessCallback(){
            public void call(Map<String, String> data) {
                String processDefinitionId = data.get("processDefinitionId");
                String processInstanceId = data.get("processInstanceId");
                materialOutStoreDto.getMaterialOutStore().setInstId(processInstanceId);
                materialOutStoreDto.getMaterialOutStore().setBizState(ProcessInst.PROCESS_STATE_ACTIVE);
                if("create".equals(materialOutStoreDto.getAction())) {
                    //新增物品领取业务数据
                    addMaterialOutStore(materialOutStoreDto);
                }else{
                    //更新物品领取业务数据
                    updateMaterialOutStore(materialOutStoreDto);
                }
            }
        });
    }
    @Transactional
    public ResponseResult handleMaterialOutStore(Map<String, Object> params) throws CamundaException {
        ResponseResult res;
        Map<String,Object> materialBuyMap=(Map<String,Object>)params.get("formObj");
        ProcessTask processTask = ProcessTaskDto.build(materialBuyMap);
        MaterialOutStoreDto materialOutStoreDto = MaterialOutStoreDto.build(params);
        //审批驳回
        if("101".equals(processTask.getOpinion())){
            res = processTaskService.rejectProcessTask(processTask);
        }else{
            String bizNo=materialOutStoreDto.getMaterialOutStore().getBizNo();
            double amount = materialOutStoreDto.getMaterialOutStore().getTotalAmt();
            //查询物品领取业务流程变量
            Map<String, Object> variables = this.getMaterialOutStoreProcessVariables(processTask);
            //流程实例非最后节点下一节点的候选人不能为空
            if (!Role.ROLE_IM_ADMIN.equals(processTask.getActivityId())
                    && StringUtils.isNUll(StringUtils.replaceNull(variables.get("nextAssignees")))) {
                res = ResponseResult.error(
                        ResponseResult.CODE_FAILURE,
                        "对不起，实例任务【"+variables.get("nextActivityName")+"】候选人不能为空！");
                return res;
            }
            res = processTaskService.completeTask(processTask,variables,new ProcessCallback(){
                public void call(Map<String, String> data) throws CamundaException {
                    //更新物品领取业务完成状态
                    Map<String, Object> parameterObject = new HashMap<>();
                    parameterObject.put("BIZNO", bizNo);
                    parameterObject.put("INSTID", processTask.getProcInstId());
                    parameterObject.put("BIZSTATE", ProcessInst.PROCESS_STATE_COMPLETED);
                    materialOutStoreRepo.updateBizState(parameterObject);
                }
            });
        }
        return res;
    }
    private Map<String, Object> getMaterialOutStoreProcessVariables(ProcessTask processTask){
        Map<String, Object> variables = new HashMap<>();

        variables.put("userId", processTask.getAssignee());
        variables.put("processDefinitionId", processTask.getProcDefId());
        variables.put("processInstanceId", processTask.getProcInstId());

        String nextAssignees;
        variables.put("depId", processTask.getAssigneeDepId());
        if (Role.ROLE_PROCESS_STARTER.equals(processTask.getActivityId())) {
            variables.put("currentActivityId", Role.ROLE_PROCESS_STARTER);
            variables.put("nextActivityId", Role.ROLE_REPO_ADMIN);
            nextAssignees = processTaskService.getTaskAssigneeList(variables,false);
            variables.put("nextActivityName", Role.ROLE_REPO_ADMIN_DESC);
            variables.put("nextAssignees", nextAssignees);
        } else if(Role.ROLE_REPO_ADMIN.equals(processTask.getActivityId())){
            variables.put("currentActivityId", Role.ROLE_REPO_ADMIN);
            variables.put("nextActivityId", Role.ROLE_IM_ADMIN);
            nextAssignees = processTaskService.getTaskAssigneeList(variables,false);
            variables.put("nextActivityName", Role.ROLE_IM_ADMIN_DESC);
            variables.put("nextAssignees", nextAssignees);
        }
        return variables;
    }
}
