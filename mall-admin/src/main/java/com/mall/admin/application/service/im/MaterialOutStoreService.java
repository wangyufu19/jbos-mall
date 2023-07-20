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
import com.mall.admin.domain.entity.im.MaterialList;
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
    private MaterialListService materialListService;
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
        return ResponseResult.ok().isPage(true).data(materialOutStoreList);
    }

    /**
     * 得到物品领取数据
     * @param id
     * @return
     */
    public MaterialOutStoreDto getMaterialOutStoreById(String id) {
        MaterialOutStoreDto materialOutStoreDto=new MaterialOutStoreDto();
        MaterialOutStore materialOutStore = materialOutStoreRepo.getMaterialOutStoreById(id);
        Map<String, Object> parameterObject = new HashMap<>();
        parameterObject.put("bizId", id);
        List<MaterialList> materialListList = materialListService.getMaterialListList(parameterObject);
        materialOutStoreDto.setMaterialOutStore(materialOutStore);
        materialOutStoreDto.setMaterialList(materialListList);
        return materialOutStoreDto;
    }

    /**
     * 新增物品领取数据
     * @param materialOutStoreDto
     */
    @Transactional
    public void addMaterialOutStore(MaterialOutStoreDto materialOutStoreDto) {
        //新增物品领取基本信息
        materialOutStoreRepo.addMaterialOutStore(materialOutStoreDto.getMaterialOutStore());
        //新增物品领取清单
        materialListService.addMaterialList(materialOutStoreDto.getMaterialList());
    }
    /**
     * 更新物品领取数据
     *
     * @param materialOutStoreDto
     */
    @Transactional
    public void updateMaterialOutStore(MaterialOutStoreDto materialOutStoreDto) {
        //更新物品领取基本信息
        materialOutStoreRepo.updateMaterialOutStore(materialOutStoreDto.getMaterialOutStore());
        Map<String, Object> parameterObject = new HashMap<String, Object>();
        parameterObject.put("bizid", materialOutStoreDto.getMaterialOutStore().getId());
        parameterObject.put("biztype", ProcessDefConstants.PROC_DEF_MATERIAL_OUT_STORE);
        //删除物品领取清单
        materialListService.deleteMaterial(parameterObject);
        //新增物品领取清单
        materialListService.addMaterialList(materialOutStoreDto.getMaterialList());
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
        Map<String, Object> listParams = new HashMap<String, Object>();
        listParams.put("bizid", StringUtils.replaceNull(parameterObject.get("id")));
        listParams.put("biztype", ProcessDefConstants.PROC_DEF_MATERIAL_OUT_STORE);
        //删除物品领取清单
        materialListService.deleteMaterial(listParams);
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
                if("create".equals(materialOutStoreDto.getAction())) {
                    //新增物品领取业务数据
                    addMaterialOutStore(materialOutStoreDto);
                }else{
                    //更新物品领取业务数据
                    updateMaterialOutStore(materialOutStoreDto);
                }
                //更新物品领取业务实例ID和业务状态
                Map<String, Object> parameterObject = new HashMap<>();
                parameterObject.put("BIZNO", materialOutStoreDto.getMaterialOutStore().getBizNo());
                parameterObject.put("INSTID", processInstanceId);
                parameterObject.put("BIZSTATE", ProcessInst.PROCESS_STATE_ACTIVE);
                materialOutStoreRepo.updateInstIdAndBizState(parameterObject);
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
