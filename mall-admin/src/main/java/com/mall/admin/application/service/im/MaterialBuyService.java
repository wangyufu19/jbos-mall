package com.mall.admin.application.service.im;

import com.mall.admin.application.request.im.MaterialBuyDto;
import com.mall.admin.application.request.wf.ProcessTaskDto;
import com.mall.admin.application.service.wf.ProcessDefConstants;
import com.mall.admin.application.service.external.camunda.ProcessInstanceService;
import com.mall.admin.application.service.external.camunda.TaskService;
import com.mall.admin.application.service.wf.ProcessMgrService;
import com.mall.admin.application.service.wf.ProcessTaskService;
import com.mall.common.base.BaseService;
import com.mall.common.page.PageParam;
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
    @Autowired
    private ProcessInstanceService processInstanceService;
    @Autowired
    private TaskService taskService;

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
        parameterObject.put("biztype", ProcessDefConstants.PROC_BIZTYPE_MATERIAL_BUY);
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
        listParams.put("biztype", ProcessDefConstants.PROC_BIZTYPE_MATERIAL_BUY);
        //删除物品采购清单
        materialListService.deleteMaterial(listParams);
    }

    /**
     * 处理物品采购业务流程数据
     */
    @Transactional
    public ResponseResult handleMaterialStartProcess(String action,
                                                     MaterialBuyDto materialBuyDto) {
        ResponseResult res;
        String processInstanceId = null;
        String processDefinitionId = null;
        String businessKey = "KEY_" + materialBuyDto.getMaterialBuy().getBizNo();
        //启动物品采购流程
        Map<String, Object> processMap = new HashMap<String, Object>();
        processMap.put("processDefinitionKey", ProcessDefConstants.PROC_BIZTYPE_MATERIAL_BUY);
        processMap.put("businessKey", businessKey);
        processMap.put("userId", materialBuyDto.getMaterialBuy().getApplyUserId());
        processMap.put(Role.ROLE_PROCESS_STARTER, materialBuyDto.getMaterialBuy().getApplyUserId());
        processMap.put("amount", materialBuyDto.getMaterialBuy().getTotalAmt());
        res = processInstanceService.startProcessInstance(processMap);

        //处理物品采购业务数据
        if (ResponseResult.CODE_SUCCESS.equals(res.getRetCode())) {
            Map<String, String> data = (Map<String, String>) res.getData();
            if (data != null) {
                processInstanceId = data.get("processInstanceId");
                processDefinitionId = data.get("processDefinitionId");
            }
            if ("create".equals(action)) {
                //新增物品采购业务数据
                this.addMaterialBuy(materialBuyDto);
            } else {
                //更新物品采购业务数据
                this.updateMaterialBuy(materialBuyDto);
            }
            //新增物品采购流程实例数据
            String currentTime = DateUtils.format(DateUtils.getCurrentDate(), DateUtils.YYYYMMDDHIMMSS);
            ProcessInst processInst = new ProcessInst();
            processInst.setId(StringUtils.getUUID());
            processInst.setProcInstId(processInstanceId);
            processInst.setProcDefId(processDefinitionId);
            processInst.setUserId(materialBuyDto.getMaterialBuy().getApplyUserId());
            processInst.setBizId(materialBuyDto.getMaterialBuy().getId());
            processInst.setBizNo(materialBuyDto.getMaterialBuy().getBizNo());
            processInst.setBizType(ProcessDefConstants.PROC_BIZTYPE_MATERIAL_BUY);
            processInst.setBusinessKey(businessKey);
            processInst.setRouteUrl(businessDict.getDictValue("JBOS_PROC_ROUTE", ProcessDefConstants.PROC_BIZTYPE_MATERIAL_BUY));
            processInst.setStartTime(currentTime);
            processInst.setCreateUserId(materialBuyDto.getMaterialBuy().getApplyUserId());
            processInst.setCreateTime(currentTime);
            processMgrService.addProcessInst(processInst);
            //新增物品采购流程任务数据
            ProcessTask processTask = new ProcessTask();
            processTask.setId(StringUtils.getUUID());
            processTask.setProcInstId(processInstanceId);
            processTask.setTaskDefKey(Role.ROLE_PROCESS_STARTER);
            processTask.setTaskName(Role.ROLE_PROCESS_STARTER_DESC);
            processTask.setAssignee(materialBuyDto.getMaterialBuy().getApplyUserId());
            processTask.setTaskState(ProcessTask.PROCESS_STATE_ACTIVE);
            processTask.setStartTime(currentTime);
            processTaskService.addProcessTask(processTask);

            //更新物品采购业务实例ID和业务状态
            Map<String, Object> parameterObject = new HashMap<>();
            parameterObject.put("BIZNO", materialBuyDto.getMaterialBuy().getBizNo());
            parameterObject.put("INSTID", processInstanceId);
            parameterObject.put("BIZSTATE", ProcessInst.PROCESS_STATE_ACTIVE);
            this.updateMaterialInstIdAndBizState(parameterObject);
        }
        return res;
    }

    /**
     * 处理物品采购业务流程任务数据
     */
    @Transactional
    public ResponseResult handleMaterialBuyProcessTask(ProcessTaskDto processTaskDto, MaterialBuyDto materialBuyDto) {
        ResponseResult res;
        String taskId = null;
        String processInstanceState = "active";
        String bizNo=materialBuyDto.getMaterialBuy().getBizNo();
        double amount = materialBuyDto.getMaterialBuy().getTotalAmt();

        //查询物品采购业务流程变量
        Map<String, Object> variables = this.getMaterialBuyProcessVariables(processTaskDto, amount);
        if (!Role.ROLE_REPO_ADMIN.equals(processTaskDto.getTaskDefKey()) && StringUtils.isNUll(variables.get("nextAssignees"))) {
            res = ResponseResult.error(ResponseResult.CODE_FAILURE, "对不起，实例任务下一个候选人不能为空！");
            return res;
        }
        //审批物品采购业务任务
        res = taskService.complete(variables);
        if (ResponseResult.CODE_SUCCESS.equals(res.getRetCode())) {
            //得到流程当前执行任务和实例状态(active;isEnd)
            Map<String, Object> taskMap = (Map<String, Object>) res.getData();
            taskId = StringUtils.replaceNull(taskMap.get("taskId"));
            processInstanceState = StringUtils.replaceNull(taskMap.get("processInstanceState"));
            //更新流程当前任务状态和审批意见
            ProcessTask processCurrentTask = new ProcessTask();
            processCurrentTask.setProcInstId(processTaskDto.getProcInstId());
            processCurrentTask.setTaskId(taskId);
            processCurrentTask.setTaskDefKey(processTaskDto.getTaskDefKey());
            processCurrentTask.setAssignee(processTaskDto.getAssignee());
            processCurrentTask.setTaskState(ProcessTask.PROCESS_STATE_COMPLETED);
            processCurrentTask.setOpinion(processTaskDto.getOpinion());
            processCurrentTask.setOpinionDesc(processTaskDto.getOpinionDesc());
            processCurrentTask.setEndTime(DateUtils.format(DateUtils.getCurrentDate(), DateUtils.YYYYMMDDHIMMSS));
            this.processTaskService.updateProcessTaskOpinion(processCurrentTask);
            //多实例节点更新流程任务处理后未审批的所有任务状态为NONE(除当前用户任务)
            if (Role.ROLE_DEP_LEADER.equals(processTaskDto.getTaskDefKey())) {
                processTaskService.updateHandlePostProcessTask(processCurrentTask);
            }
            //判断流程实例是否结束
            if ("isEnded".equals(processInstanceState)) {
                Map<String, Object> parameterObject = new HashMap<>();
                parameterObject.put("BIZNO", bizNo);
                parameterObject.put("INSTID", processTaskDto.getProcInstId());
                parameterObject.put("BIZSTATE", ProcessInst.PROCESS_STATE_COMPLETED);
                //更新物品采购业务状态
                this.updateMaterialBizState(parameterObject);
                //更新业务流程实例状态
                processMgrService.updateProcState(processTaskDto.getProcInstId(),ProcessInst.PROCESS_STATE_COMPLETED);
            } else {
                //新增物品采购流程下一个任务数据
                processTaskService.addProcessNexTask(processTaskDto.getProcInstId(),variables);
            }
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
    public Map<String, Object> getMaterialBuyProcessVariables(ProcessTaskDto processTaskDto, double amount) {
        Map<String, Object> variables = new HashMap<>();

        variables.put("userId", processTaskDto.getAssignee());
        variables.put("processDefinitionId", processTaskDto.getProcDefId());
        variables.put("processInstanceId", processTaskDto.getProcInstId());

        String nextAssignees;
        variables.put("depId", processTaskDto.getAssigneeDepId());
        if (Role.ROLE_PROCESS_STARTER.equals(processTaskDto.getTaskDefKey())) {
            variables.put("currentTaskDefKey", Role.ROLE_PROCESS_STARTER);
            variables.put("nextTaskDefKey", Role.ROLE_DEP_LEADER);
            nextAssignees = processTaskService.getTaskAssigneeList(variables,false);
            variables.put("multiInstance", "true");
            variables.put("nextTaskName", Role.ROLE_DEP_LEADER_DESC);
            variables.put("nextAssignees", nextAssignees);
        } else if (Role.ROLE_DEP_LEADER.equals(processTaskDto.getTaskDefKey())) {
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
        } else if (Role.ROLE_CHARGE_LEADER.equals(processTaskDto.getTaskDefKey())) {
            variables.put("currentTaskDefKey", Role.ROLE_CHARGE_LEADER);
            variables.put("nextTaskDefKey", Role.ROLE_REPO_ADMIN);
            nextAssignees = processTaskService.getTaskAssigneeList(variables,true);
            variables.put("nextTaskName", Role.ROLE_REPO_ADMIN_DESC);
            variables.put("nextAssignees", nextAssignees);
        }
        return variables;
    }
}
