package com.mall.admin.application.service.im;

import com.mall.admin.application.request.im.MaterialBuyDto;
import com.mall.admin.application.request.wf.ProcessTaskDto;
import com.mall.admin.application.service.wf.ProcessCallback;
import com.mall.admin.application.service.wf.ProcessDefConstants;
import com.mall.admin.application.service.external.camunda.TaskService;
import com.mall.admin.application.service.wf.ProcessMgrService;
import com.mall.admin.application.service.wf.ProcessTaskService;
import com.mall.common.base.BaseService;
import com.mall.common.page.PageParam;
import com.mall.admin.domain.entity.im.MaterialBuy;
import com.mall.admin.domain.entity.im.MaterialList;
import com.mall.admin.domain.entity.wf.ProcessInst;
import com.mall.admin.domain.entity.wf.ProcessTask;
import com.mall.admin.domain.entity.sm.Role;
import com.mall.admin.infrastructure.repository.im.MaterialBuyRepo;
import com.mall.common.response.ResponseResult;
import com.mall.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mall.admin.application.service.sm.BusinessDict;

/**
 * MaterialBuyService
 *
 * @author youfu.wang
 * @date 2020-06-24
 */
@Service
public class MaterialBuyService extends BaseService {
    @Autowired
    private ProcessMgrService processMgrService;
    @Autowired
    private ProcessTaskService processTaskService;
    @Autowired
    private MaterialBuyRepo materialBuyRepo;
    @Autowired
    private MaterialListService materialListService;
    @Autowired
    private BusinessDict businessDict;


    /**
     * 得到物品采购列表
     *
     * @param parameterObject
     * @return
     */
    public ResponseResult getMaterialBuyList(PageParam pageParam, Map<String, Object> parameterObject) {
        List<MaterialBuy> materialBuyList = materialBuyRepo.getMaterialBuyList(pageParam, parameterObject);
        return ResponseResult.ok().isPage(true).data(materialBuyList);
    }

    /**
     * 得到物品采购信息
     *
     * @param id
     * @return
     */
    public MaterialBuyDto getMaterialBuyById(String id) {
        MaterialBuyDto materialBuyDto=new MaterialBuyDto();
        MaterialBuy materialBuy = materialBuyRepo.getMaterialBuyById(id);
        Map<String, Object> parameterObject = new HashMap<>();
        parameterObject.put("bizid", id);
        List<MaterialList> materialListList = materialListService.getMaterialListList(parameterObject);
        materialBuyDto.setMaterialBuy(materialBuy);
        materialBuyDto.setMaterialList(materialListList);
        return materialBuyDto;
    }

    /**
     * 新增物品采购
     *
     * @param materialBuyDto
     */
    @Transactional
    public void addMaterialBuy(MaterialBuyDto materialBuyDto) {
        //新增物品采购基本信息
        materialBuyRepo.addMaterialBuy(materialBuyDto.getMaterialBuy());
        //新增物品采购清单
        this.addMaterialList(materialBuyDto.getMaterialList());
    }

    /**
     * 更新物品采购清单
     *
     * @param materialBuyDto
     */
    @Transactional
    public void updateMaterialBuy(MaterialBuyDto materialBuyDto) {
        //更新物品采购基本信息
        materialBuyRepo.updateMaterialBuy(materialBuyDto.getMaterialBuy());
        Map<String, Object> parameterObject = new HashMap<String, Object>();
        parameterObject.put("bizid", materialBuyDto.getMaterialBuy().getId());
        parameterObject.put("biztype", ProcessDefConstants.PROC_DEF_MATERIAL_BUY);
        //删除物品采购清单
        materialListService.deleteMaterial(parameterObject);
        //新增物品采购清单
        this.addMaterialList(materialBuyDto.getMaterialList());
    }

    /**
     * 新增物品采购清单
     *
     * @param materials
     */
    private void addMaterialList(List<MaterialList> materials) {
        if (materials != null) {
            for (MaterialList materialList : materials) {
                materialListService.addMaterialList(materialList);
            }
        }
    }

    /**
     * 删除物品采购
     *
     * @param parameterObject
     */
    @Transactional
    public void deleteMaterialBuy(Map<String, Object> parameterObject) {
        //删除物品采购基本信息
        materialBuyRepo.deleteMaterialBuy(parameterObject);
        Map<String, Object> listParams = new HashMap<String, Object>();
        listParams.put("bizid", StringUtils.replaceNull(parameterObject.get("id")));
        listParams.put("biztype", ProcessDefConstants.PROC_DEF_MATERIAL_BUY);
        //删除物品采购清单
        materialListService.deleteMaterial(listParams);
    }

    /**
     * 处理物品采购业务流程数据
     */
    public ResponseResult handleMaterialStartProcess(Map<String, Object> params) {
        ResponseResult res;
        String action = StringUtils.replaceNull(params.get("action"));
        MaterialBuyDto materialBuyDto = MaterialBuyDto.build(params);

        //启动物品采购流程
        Map<String, Object> processMap = new HashMap<String, Object>();
        processMap.put("processDefinitionKey", ProcessDefConstants.PROC_DEF_MATERIAL_BUY);
        processMap.put("businessKey", "KEY_" + materialBuyDto.getMaterialBuy().getBizNo());
        processMap.put("userId", materialBuyDto.getMaterialBuy().getApplyUserId());
        processMap.put("startActivityId",Role.ROLE_PROCESS_STARTER);
        processMap.put("routeUrl",
                businessDict.getDictValue("JBOS_PROC_ROUTE", ProcessDefConstants.PROC_DEF_MATERIAL_BUY));
        processMap.put("bizId", materialBuyDto.getMaterialBuy().getId());
        processMap.put("bizNo", materialBuyDto.getMaterialBuy().getBizNo());
        processMap.put("bizType", ProcessDefConstants.PROC_DEF_MATERIAL_BUY);
        processMap.put("amount", materialBuyDto.getMaterialBuy().getTotalAmt());

        res = processMgrService.startProcessInstance(processMap,new ProcessCallback(){
            public void call(Map<String, String> data) {
                String processDefinitionId = data.get("processDefinitionId");
                String processInstanceId = data.get("processInstanceId");
                if ("create".equals(action)) {
                    //新增物品采购业务数据
                    addMaterialBuy(materialBuyDto);
                } else {
                    //更新物品采购业务数据
                    updateMaterialBuy(materialBuyDto);
                }
                //更新物品采购业务实例ID和业务状态
                Map<String, Object> parameterObject = new HashMap<>();
                parameterObject.put("BIZNO", materialBuyDto.getMaterialBuy().getBizNo());
                parameterObject.put("INSTID", processInstanceId);
                parameterObject.put("BIZSTATE", ProcessInst.PROCESS_STATE_ACTIVE);
                updateMaterialInstIdAndBizState(parameterObject);
            }
        });
        return res;
    }

    /**
     * 处理物品采购业务流程任务数据
     */
    public ResponseResult handleMaterialBuyProcessTask(Map<String, Object> params) {
        ResponseResult res;
        Map<String,Object> materialBuyMap=(Map<String,Object>)params.get("formObj");
        ProcessTask processTask = ProcessTaskDto.build(materialBuyMap);
        MaterialBuyDto materialBuyDto = MaterialBuyDto.build(params);

        //审批驳回
        if("101".equals(processTask.getOpinion())){
            res = processTaskService.handleRejectProcessTask(processTask);
        }else{
            String bizNo=materialBuyDto.getMaterialBuy().getBizNo();
            double amount = materialBuyDto.getMaterialBuy().getTotalAmt();
            //查询物品采购业务流程变量
            Map<String, Object> variables = this.getMaterialBuyProcessVariables(processTask, amount);
            if (!Role.ROLE_REPO_ADMIN.equals(processTask.getTaskDefKey()) && StringUtils.isNUll(variables.get("nextAssignees"))) {
                res = ResponseResult.error(ResponseResult.CODE_FAILURE, "对不起，实例任务下一个候选人不能为空！");
                return res;
            }
            res = processTaskService.handleCompleteTask(processTask,variables,new ProcessCallback(){
                public void call(Map<String, String> data) {
                    Map<String, Object> parameterObject = new HashMap<>();
                    parameterObject.put("BIZNO", bizNo);
                    parameterObject.put("INSTID", processTask.getProcInstId());
                    parameterObject.put("BIZSTATE", ProcessInst.PROCESS_STATE_COMPLETED);
                    //更新物品采购业务状态
                    updateMaterialBizState(parameterObject);
                }
            });
        }
        return res;
    }

    /**
     * 更新物品采购业务实例ID和业务状态
     *
     * @param parameterObject
     */
    public void updateMaterialInstIdAndBizState(Map<String, Object> parameterObject) {
        materialBuyRepo.updateMaterialInstIdAndBizState(parameterObject);
    }

    /**
     * 更新物品采购业务状态
     *
     * @param parameterObject
     */
    public void updateMaterialBizState(Map<String, Object> parameterObject) {
        materialBuyRepo.updateMaterialBizState(parameterObject);
    }

    /**
     * 得到物品采购流程变量
     */
    public Map<String, Object> getMaterialBuyProcessVariables(ProcessTask processTask, double amount) {
        Map<String, Object> variables = new HashMap<>();

        variables.put("userId", processTask.getAssignee());
        variables.put("processDefinitionId", processTask.getProcDefId());
        variables.put("processInstanceId", processTask.getProcInstId());

        String nextAssignees;
        variables.put("depId", processTask.getAssigneeDepId());
        if (Role.ROLE_PROCESS_STARTER.equals(processTask.getTaskDefKey())) {
            variables.put("currentTaskDefKey", Role.ROLE_PROCESS_STARTER);
            variables.put("nextTaskDefKey", Role.ROLE_DEP_LEADER);
            nextAssignees = processTaskService.getTaskAssigneeList(variables,false);
            variables.put("multiInstance", "true");
            variables.put("nextTaskName", Role.ROLE_DEP_LEADER_DESC);
            variables.put("nextAssignees", nextAssignees);
        } else if (Role.ROLE_DEP_LEADER.equals(processTask.getTaskDefKey())) {
            variables.put("currentTaskDefKey", Role.ROLE_DEP_LEADER);
            if (amount <= 5000) {
                variables.put("nextTaskDefKey", Role.ROLE_REPO_ADMIN);
                nextAssignees = processTaskService.getTaskAssigneeList(variables,false);
                variables.put("nextTaskName", Role.ROLE_REPO_ADMIN_DESC);
            } else {
                variables.put("nextTaskDefKey", Role.ROLE_CHARGE_LEADER);
                nextAssignees = processTaskService.getTaskAssigneeList(variables,false);
                variables.put("multiInstance", "true");
                variables.put("nextTaskName", Role.ROLE_CHARGE_LEADER_DESC);
            }
            variables.put("nextAssignees", nextAssignees);
        } else if (Role.ROLE_CHARGE_LEADER.equals(processTask.getTaskDefKey())) {
            variables.put("currentTaskDefKey", Role.ROLE_CHARGE_LEADER);
            variables.put("nextTaskDefKey", Role.ROLE_REPO_ADMIN);
            nextAssignees = processTaskService.getTaskAssigneeList(variables,true);
            variables.put("nextTaskName", Role.ROLE_REPO_ADMIN_DESC);
            variables.put("nextAssignees", nextAssignees);
        }
        return variables;
    }
}
