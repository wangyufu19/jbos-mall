package com.mall.admin.application.service.im;

import com.mall.admin.application.service.ProcessDefConstants;
import com.mall.admin.application.service.external.camunda.ProcessInstanceService;
import com.mall.admin.application.service.external.camunda.TaskService;
import com.mall.admin.application.service.sm.ProcessMgrService;
import com.mall.admin.application.service.sm.ProcessTaskService;
import com.mall.admin.domain.entity.im.MaterialBuy;
import com.mall.admin.domain.entity.im.MaterialList;
import com.mall.admin.domain.entity.sm.ProcessInst;
import com.mall.admin.domain.entity.sm.ProcessTask;
import com.mall.admin.domain.entity.sm.Role;
import com.mall.admin.infrastructure.repository.im.MaterialBuyRepo;
import com.mall.common.response.ResponseResult;
import com.mall.common.utils.DateUtils;
import com.mall.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.mall.admin.application.service.sm.BusinessDict;
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
    @Autowired
    private BusinessDict businessDict;
    @Autowired
    private ProcessInstanceService processInstanceService;
    @Autowired
    private TaskService taskService;
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
                materialList.setBizType(ProcessDefConstants.PROC_BIZTYPE_MATERIAL_BUY);
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
        parameterObject.put("biztype",ProcessDefConstants.PROC_BIZTYPE_MATERIAL_BUY);
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
        listParams.put("biztype",ProcessDefConstants.PROC_BIZTYPE_MATERIAL_BUY);
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
        processInst.setBizType(ProcessDefConstants.PROC_BIZTYPE_MATERIAL_BUY);
        processInst.setBusinessKey(bizNo);
        processInst.setRouteUrl(businessDict.getDictValue("JBOS_PROC_ROUTE",ProcessDefConstants.PROC_BIZTYPE_MATERIAL_BUY));
        processInst.setStartTime(currentTime);
        processInst.setCreateUserId(userId);
        processInst.setCreateTime(currentTime);
        processMgrService.addProcessInst(processInst);
        //新增物品采购流程任务数据
        this.addProcessNextTask("applyPerson",userId,processInstanceId,currentTime);
        //更新物品采购业务实例ID和业务状态
        Map<String, Object> parameterObject=new HashMap<>();
        parameterObject.put("BIZNO",bizNo);
        parameterObject.put("INSTID",processInst.getProcInstId());
        parameterObject.put("BIZSTATE",ProcessInst.PROCESS_STATE_ACTIVE);
        this.updateMaterialInstIdAndBizState(parameterObject);
    }
    /**
     * 处理物品采购业务流程数据
     */
    @Transactional
    public void handleMaterialBuyProcessData(Map<String, Object> materialBuyMap){
        String userId=StringUtils.replaceNull(materialBuyMap.get("userId"));
        String bizId=StringUtils.replaceNull(materialBuyMap.get("bizId"));
        String bizNo=StringUtils.replaceNull(materialBuyMap.get("bizNo"));
        String processInstanceId=StringUtils.replaceNull(materialBuyMap.get("processInstanceId"));
        String processInstanceState=StringUtils.replaceNull(materialBuyMap.get("processInstanceState"));
        String currentTime=DateUtils.format(DateUtils.getCurrentDate(),DateUtils.YYYYMMDDHIMMSS);
        //更新流程任务处理状态
        ProcessTask processTask=new ProcessTask();
        processTask.setAssignee(userId);
        processTask.setProcInstId(processInstanceId);
        processTask.setTaskState(ProcessTask.PROCESS_STATE_COMPLETED);
        processTask.setEndTime(currentTime);
        this.processTaskService.updateProcessTaskOpinion(processTask);
        //判断流程实例是否结束
        if("true".equals(processInstanceState)){
            ProcessInst processInst=new ProcessInst();
            Map<String, Object> parameterObject=new HashMap<>();
            parameterObject.put("BIZNO",bizNo);
            parameterObject.put("INSTID",processInstanceId);
            parameterObject.put("BIZSTATE",ProcessInst.PROCESS_STATE_COMPLETED);
            //更新物品采购业务状态
            this.updateMaterialBizState(parameterObject);
            //更新业务流程实例状态
            processInst.setProcInstId(processInstanceId);
            processInst.setEndTime(currentTime);
            processInst.setProcState(ProcessInst.PROCESS_STATE_COMPLETED);
            processMgrService.updateProcState(processInst);
        }else{
            //新增物品采购流程下一个任务数据
            String assigneeName=StringUtils.replaceNull(materialBuyMap.get("assigneeName"));
            String assignees=StringUtils.replaceNull(materialBuyMap.get("assignees"));
            String[] assigneeList = assignees.split(",");
            if (assigneeList != null && assigneeList.length > 0) {
                for(String assignee:assigneeList){
                    this.addProcessNextTask(assigneeName,assignee,processInstanceId,currentTime);
                }
            }
        }
    }

    /**
     * 新增流程下一个任务数据
     * @param assignee
     * @param processInstanceId
     * @param currentTime
     */
    private void addProcessNextTask(String taskDefKey,String assignee,String processInstanceId,String currentTime){
        ProcessTask processTask=new ProcessTask();
        processTask.setId(StringUtils.getUUID());
        processTask.setProcInstId(processInstanceId);
        processTask.setTaskDefKey(taskDefKey);
        processTask.setAssignee(assignee);
        processTask.setStartTime(currentTime);
        processTask.setCreateUserId(assignee);
        processTask.setCreateTime(currentTime);
        processTaskService.addProcessTask(processTask);
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

    /**
     * 设置物品采购流程变量
     * @param paramMap
     */
    private void setMaterialBuyProcessVariables(Map<String, Object> paramMap,Map<String, Object> materialBuyMap){
        String userId = StringUtils.replaceNull(materialBuyMap.get("userId"));
        String depId = StringUtils.replaceNull(materialBuyMap.get("depId"));
        String processDefinitionId=StringUtils.replaceNull(materialBuyMap.get("processDefinitionId"));
        String processInstanceId = StringUtils.replaceNull(materialBuyMap.get("processInstanceId"));
        String taskId = StringUtils.replaceNull(materialBuyMap.get("taskId"));
        String taskDefKey=StringUtils.replaceNull(materialBuyMap.get("taskDefKey"));
        double amount=Double.parseDouble(StringUtils.replaceNull(materialBuyMap.get("totalAmt")));
        paramMap.put("userId",userId);
        paramMap.put("processDefinitionId",processDefinitionId);
        paramMap.put("processInstanceId",processInstanceId);
        paramMap.put("taskId",taskId);
        String assignees;
        Map<String,Object> parameterObject=new HashMap<>();
        parameterObject.put("depId",depId);
        if("applyPerson".equals(taskDefKey)){
            parameterObject.put("taskDefKey", Role.ROLE_DEP_LEADER);
            assignees=processTaskService.getTaskAssigneeList(parameterObject);
            paramMap.put("multiInstance","true");
            paramMap.put("assigneeName",Role.ROLE_DEP_LEADER);
            paramMap.put("assignees",assignees);
        }else if(Role.ROLE_DEP_LEADER.equals(taskDefKey)){
            if(amount<=5000){
                parameterObject.put("taskDefKey", Role.ROLE_REPO_ADMIN);
                assignees=processTaskService.getTaskAssigneeList(parameterObject);
                paramMap.put("assigneeName",Role.ROLE_REPO_ADMIN);
            }else{
                parameterObject.put("taskDefKey", Role.ROLE_CHARGE_LEADER);
                assignees=processTaskService.getTaskAssigneeList(parameterObject);
                paramMap.put("multiInstance","true");
                paramMap.put("assigneeName",Role.ROLE_CHARGE_LEADER);
            }
            paramMap.put("assignees",assignees);
        }else if(Role.ROLE_CHARGE_LEADER.equals(taskDefKey)){
            parameterObject.put("taskDefKey", Role.ROLE_REPO_ADMIN);
            assignees=processTaskService.getTaskAssigneeList(parameterObject);
            paramMap.put("assigneeName",Role.ROLE_REPO_ADMIN);
            paramMap.put("assignees",assignees);
        }
    }

    /**
     * 审批物品采购业务任务
     * @param materialBuyMap
     * @return
     */
    public ResponseResult handleMaterialBuyProcessTask(Map<String, Object> materialBuyMap){
        ResponseResult res;
        Map<String, Object> paramMap = new HashMap<>();
        this.setMaterialBuyProcessVariables(paramMap,materialBuyMap);
        String taskDefKey=StringUtils.replaceNull(materialBuyMap.get("taskDefKey"));
        if(!Role.ROLE_REPO_ADMIN.equals(taskDefKey)&&StringUtils.isNUll(paramMap.get("assignees"))){
            res=ResponseResult.error(ResponseResult.CODE_FAILURE,"对不起，实例任务下一个候选人不能为空！");
            return res;
        }
        res=taskService.complete(paramMap);
        if(ResponseResult.CODE_SUCCESS.equals(res.getRetCode())){
            res=processInstanceService.getProcessInstanceState(materialBuyMap);
            if(ResponseResult.CODE_SUCCESS.equals(res.getRetCode())){
                String processInstanceState=StringUtils.replaceNull(((Map<String, Object>)res.getData()).get("processInstanceState"));
                materialBuyMap.put("processInstanceState",processInstanceState);
            }
            materialBuyMap.put("assigneeName",paramMap.get("assigneeName"));
            materialBuyMap.put("assignees",paramMap.get("assignees"));
            this.handleMaterialBuyProcessData(materialBuyMap);
        }
        return res;
    }
}
