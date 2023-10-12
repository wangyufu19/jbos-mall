package com.mall.admin.application.service.im;


import com.mall.admin.application.request.im.MaterialInStoreDto;
import com.mall.admin.application.request.wf.ProcessTaskDto;
import com.mall.admin.application.service.sm.BusinessDict;
import com.mall.admin.application.service.wf.ProcessCallback;
import com.mall.admin.application.service.wf.ProcessDefConstants;
import com.mall.admin.application.service.wf.ProcessMgrService;
import com.mall.admin.application.service.wf.ProcessTaskService;
import com.mall.admin.common.exception.CamundaException;
import com.mall.admin.domain.entity.im.MaterialInStore;
import com.mall.admin.domain.entity.im.MaterialList;
import com.mall.admin.domain.entity.sm.Role;
import com.mall.admin.domain.entity.wf.ProcessInst;
import com.mall.admin.domain.entity.wf.ProcessTask;
import com.mall.admin.infrastructure.repository.im.MaterialInStoreRepo;
import com.mall.common.page.PageParam;
import com.mall.common.response.ResponsePageResult;
import com.mall.common.response.ResponseResult;
import com.mall.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MaterialInStoreService
 *
 * @author youfu.wang
 * @date 2023/7/13
 **/
@Service
public class MaterialInStoreService {
    @Autowired
    private BusinessDict businessDict;
    @Autowired
    private ProcessMgrService processMgrService;
    @Autowired
    private ProcessTaskService processTaskService;
    @Autowired
    private MaterialInStoreRepo materialInStoreRepo;
    @Autowired
    private MaterialListService materialListService;
    @Autowired
    private MaterialStoreService materialStoreService;
    /**
     * 得到物品入库列表
     *
     * @param pageParam
     * @param parameterObject
     * @return
     */
    public ResponsePageResult getMaterialInStoreList(PageParam pageParam, Map<String, Object> parameterObject) {
        List<MaterialInStore> materialBuyList = materialInStoreRepo.getMaterialInStoreList(pageParam,parameterObject);
        return ResponsePageResult.ok().isPage(true).setData(materialBuyList);
    }

    /**
     * 得到物品入库数据
     * @param id
     * @return
     */
    public MaterialInStoreDto getMaterialInStoreById(String id) {
        MaterialInStoreDto materialInStoreDto=new MaterialInStoreDto();
        MaterialInStore materialInStore = materialInStoreRepo.getMaterialInStoreById(id);
        Map<String, Object> parameterObject = new HashMap<>();
        parameterObject.put("bizId", id);
        List<MaterialList> materialListList = materialListService.getMaterialListList(parameterObject);
        materialInStoreDto.setMaterialInStore(materialInStore);
        materialInStoreDto.setMaterialList(materialListList);
        return materialInStoreDto;
    }

    /**
     * 新增物品入库数据
     * @param materialInStoreDto
     */
    @Transactional
    public void addMaterialInStore(MaterialInStoreDto materialInStoreDto) {
        //新增物品入库基本信息
        materialInStoreRepo.addMaterialInStore(materialInStoreDto.getMaterialInStore());
        //新增物品入库清单
        materialListService.addMaterialList(materialInStoreDto.getMaterialList());
    }
    /**
     * 更新物品入库数据
     *
     * @param materialInStoreDto
     */
    @Transactional
    public void updateMaterialInStore(MaterialInStoreDto materialInStoreDto) {
        //更新物品入库基本信息
        materialInStoreRepo.updateMaterialInStore(materialInStoreDto.getMaterialInStore());
        Map<String, Object> parameterObject = new HashMap<String, Object>();
        parameterObject.put("bizid", materialInStoreDto.getMaterialInStore().getId());
        parameterObject.put("biztype", ProcessDefConstants.PROC_DEF_MATERIAL_IN_STORE);
        //删除物品入库清单
        materialListService.deleteMaterial(parameterObject);
        //新增物品入库清单
        materialListService.addMaterialList(materialInStoreDto.getMaterialList());
    }

    /**
     * 删除物品入库
     *
     * @param parameterObject
     */
    @Transactional
    public void deleteMaterialInStore(Map<String, Object> parameterObject) {
        //删除物品入库基本信息
        materialInStoreRepo.deleteMaterialInStore(parameterObject);
        Map<String, Object> listParams = new HashMap<String, Object>();
        listParams.put("bizid", StringUtils.replaceNull(parameterObject.get("id")));
        listParams.put("biztype", ProcessDefConstants.PROC_DEF_MATERIAL_IN_STORE);
        //删除物品入库清单
        materialListService.deleteMaterial(listParams);
    }

    /**
     * 启动物品入库流程
     * @param materialInStoreDto
     * @throws CamundaException
     */
    @Transactional
    public void startMaterialInStore(MaterialInStoreDto materialInStoreDto) throws CamundaException {
        //启动物品入库流程
        Map<String, Object> processMap = new HashMap<String, Object>();
        processMap.put("processDefinitionKey", ProcessDefConstants.PROC_DEF_MATERIAL_IN_STORE);
        processMap.put("businessKey", "KEY_" + materialInStoreDto.getMaterialInStore().getBizNo());
        processMap.put("userId", materialInStoreDto.getMaterialInStore().getApplyUserId());
        processMap.put("startActivityId", Role.ROLE_PROCESS_STARTER);
        processMap.put("routeUrl",
                businessDict.getDictValue("JBOS_PROC_ROUTE", ProcessDefConstants.PROC_DEF_MATERIAL_IN_STORE));
        processMap.put("bizId", materialInStoreDto.getMaterialInStore().getId());
        processMap.put("bizNo", materialInStoreDto.getMaterialInStore().getBizNo());
        processMap.put("bizType", ProcessDefConstants.PROC_DEF_MATERIAL_IN_STORE);
        processMap.put("amount", materialInStoreDto.getMaterialInStore().getTotalAmt());

        processMgrService.startProcessInstance(processMap,new ProcessCallback(){
            public void call(Map<String, String> data) {
                String processDefinitionId = data.get("processDefinitionId");
                String processInstanceId = data.get("processInstanceId");
                if("create".equals(materialInStoreDto.getAction())) {
                    //新增物品入库业务数据
                    addMaterialInStore(materialInStoreDto);
                }else{
                    //更新物品入库业务数据
                    updateMaterialInStore(materialInStoreDto);
                }
                //更新物品入库业务实例ID和业务状态
                Map<String, Object> parameterObject = new HashMap<>();
                parameterObject.put("BIZNO", materialInStoreDto.getMaterialInStore().getBizNo());
                parameterObject.put("INSTID", processInstanceId);
                parameterObject.put("BIZSTATE", ProcessInst.PROCESS_STATE_ACTIVE);
                materialInStoreRepo.updateInstIdAndBizState(parameterObject);
            }
        });
    }
    @Transactional
    public ResponseResult handleMaterialInStore(Map<String, Object> params) throws CamundaException {
        ResponseResult res;
        Map<String,Object> materialBuyMap=(Map<String,Object>)params.get("formObj");
        ProcessTask processTask = ProcessTaskDto.build(materialBuyMap);
        MaterialInStoreDto materialInStoreDto = MaterialInStoreDto.build(params);
        //审批驳回
        if("101".equals(processTask.getOpinion())){
            res = processTaskService.rejectProcessTask(processTask);
        }else{
            String bizNo=materialInStoreDto.getMaterialInStore().getBizNo();
            double amount = materialInStoreDto.getMaterialInStore().getTotalAmt();
            //查询物品入库业务流程变量
            Map<String, Object> variables = this.getMaterialInStoreProcessVariables(processTask);
            if (!Role.ROLE_REPO_ADMIN.equals(processTask.getActivityId()) && StringUtils.isNUll(variables.get("nextAssignees"))) {
                res = ResponseResult.error(ResponseResult.CODE_FAILURE, "对不起，实例任务下一个候选人不能为空！");
                return res;
            }
            res = processTaskService.completeTask(processTask,variables,new ProcessCallback(){
                public void call(Map<String, String> data) throws CamundaException {
                    //更新物品入库业务完成状态
                    Map<String, Object> parameterObject = new HashMap<>();
                    parameterObject.put("BIZNO", bizNo);
                    parameterObject.put("INSTID", processTask.getProcInstId());
                    parameterObject.put("BIZSTATE", ProcessInst.PROCESS_STATE_COMPLETED);
                    materialInStoreRepo.updateBizState(parameterObject);
                    //物品入库完成后，新增仓库物品数据
                    materialStoreService.addMaterialStore(materialInStoreDto.getMaterialList());
                }
            });
        }
        return res;
    }
    private Map<String, Object> getMaterialInStoreProcessVariables(ProcessTask processTask){
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
        }
        return variables;
    }
}
