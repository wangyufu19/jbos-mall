package com.mall.admin.application.service.im;

import com.mall.admin.application.request.im.MaterialBuyDto;
import com.mall.admin.application.service.ProcessDefConstants;
import com.mall.admin.application.service.sm.ProcessMgrService;
import com.mall.admin.application.service.sm.ProcessTaskService;
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

    /**
     * 得到物品采购列表
     *
     * @param parameterObject
     * @return
     */
    public ResponseResult getMaterialBuyList(PageParam pageParam, Map<String, Object> parameterObject) {
        List<MaterialBuy> materialBuyList=materialBuyRepo.getMaterialBuyList(pageParam, parameterObject);
        return ResponseResult.ok().isPage(true).data(materialBuyList);
    }

    /**
     * 得到物品采购信息
     *
     * @param id
     * @return
     */
    public MaterialBuy getMaterialBuyById(String id) {
        return materialBuyRepo.getMaterialBuyById(id);
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
    public void handleMaterialStartProcess(String action,
                                           String processInstanceId,
                                           String processDefinitionId,
                                           MaterialBuyDto materialBuyDto) {
        if ("create".equals(action)) {
            //新增物品采购业务数据
            this.addMaterialBuy(materialBuyDto);
        } else {
            //更新物品采购业务数据
            this.updateMaterialBuy(materialBuyDto);
        }
        //新增物品采购流程实例数据
        ProcessInst processInst = ProcessInst.build(
                processInstanceId,
                processDefinitionId,
                materialBuyDto.getMaterialBuy().getApplyUserId(),
                materialBuyDto.getMaterialBuy().getId(),
                materialBuyDto.getMaterialBuy().getBizNo(),
                ProcessDefConstants.PROC_BIZTYPE_MATERIAL_BUY,
                businessDict.getDictValue("JBOS_PROC_ROUTE", ProcessDefConstants.PROC_BIZTYPE_MATERIAL_BUY));
        processMgrService.addProcessInst(processInst);
        //新增物品采购流程任务数据
        ProcessTask processTask = ProcessTask.build(StringUtils.getUUID(), processInstanceId, null,
                Role.ROLE_PROCESS_STARTER, Role.ROLE_PROCESS_STARTER_DESC, materialBuyDto.getMaterialBuy().getApplyUserId(),
                ProcessTask.PROCESS_STATE_ACTIVE
        );
        processTaskService.addProcessTask(processTask);

        //更新物品采购业务实例ID和业务状态
        Map<String, Object> parameterObject = new HashMap<>();
        parameterObject.put("BIZNO", materialBuyDto.getMaterialBuy().getBizNo());
        parameterObject.put("INSTID", processInstanceId);
        parameterObject.put("BIZSTATE", ProcessInst.PROCESS_STATE_ACTIVE);
        this.updateMaterialInstIdAndBizState(parameterObject);
    }

    /**
     * 处理物品采购业务流程任务数据
     */
    @Transactional
    public void handleMaterialBuyProcessTask(String processInstanceState,
                                             Map<String, Object> variables,
                                             ProcessTask processCurrentTask,
                                             String bizNo) {
        //更新流程当前任务处理状态
        this.processTaskService.updateProcessTaskOpinion(processCurrentTask);
        //判断流程实例是否结束
        if ("isEnd".equals(processInstanceState)) {
            Map<String, Object> parameterObject = new HashMap<>();
            parameterObject.put("BIZNO", bizNo);
            parameterObject.put("INSTID", processCurrentTask.getProcInstId());
            parameterObject.put("BIZSTATE", ProcessInst.PROCESS_STATE_COMPLETED);
            //更新物品采购业务状态
            this.updateMaterialBizState(parameterObject);
            //更新业务流程实例状态
            ProcessInst processInst = new ProcessInst();
            processInst.setProcInstId(processCurrentTask.getProcInstId());
            processInst.setEndTime(DateUtils.format(DateUtils.getCurrentDate(), DateUtils.YYYYMMDDHIMMSS));
            processInst.setProcState(ProcessInst.PROCESS_STATE_COMPLETED);
            processMgrService.updateProcState(processInst);
        } else {
            //新增物品采购流程下一个任务数据
            String taskDefKey = StringUtils.replaceNull(variables.get("taskDefKey"));
            String taskName = StringUtils.replaceNull(variables.get("taskName"));
            String assignees = StringUtils.replaceNull(variables.get("assignees"));
            String[] assigneeList = assignees.split(",");
            if (assigneeList != null && assigneeList.length > 0) {
                for (String assignee : assigneeList) {
                    ProcessTask processNextTask = ProcessTask.build(
                            StringUtils.getUUID(), processCurrentTask.getProcInstId(), null, taskDefKey, taskName,
                            assignee, ProcessTask.PROCESS_STATE_ACTIVE);
                    processTaskService.addProcessTask(processNextTask);
                }
            }
        }
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
    public Map<String, Object> getMaterialBuyProcessVariables(
            String processDefinitionId,
            String processInstanceId,
            String taskId,
            String taskDefKey,
            String userId,
            String depId,
            double amount) {
        Map<String, Object> variables = new HashMap<>();

        variables.put("userId", userId);
        variables.put("processDefinitionId", processDefinitionId);
        variables.put("processInstanceId", processInstanceId);
        variables.put("taskId", taskId);
        String assignees;
        variables.put("depId", depId);
        if (Role.ROLE_PROCESS_STARTER.equals(taskDefKey)) {
            variables.put("taskDefKey", Role.ROLE_DEP_LEADER);
            assignees = processTaskService.getTaskAssigneeList(variables);
            variables.put("multiInstance", "true");
            variables.put("taskName", Role.ROLE_DEP_LEADER_DESC);
            variables.put("assignees", assignees);
        } else if (Role.ROLE_DEP_LEADER.equals(taskDefKey)) {
            if (amount <= 5000) {
                variables.put("taskDefKey", Role.ROLE_REPO_ADMIN);
                assignees = processTaskService.getTaskAssigneeList(variables);
                variables.put("taskName", Role.ROLE_REPO_ADMIN_DESC);
            } else {
                variables.put("taskDefKey", Role.ROLE_CHARGE_LEADER);
                assignees = processTaskService.getTaskAssigneeList(variables);
                variables.put("multiInstance", "true");
                variables.put("taskName", Role.ROLE_CHARGE_LEADER_DESC);
            }
            variables.put("assignees", assignees);
        } else if (Role.ROLE_CHARGE_LEADER.equals(taskDefKey)) {
            variables.put("taskDefKey", Role.ROLE_REPO_ADMIN);
            assignees = processTaskService.getTaskAssigneeList(variables);
            variables.put("taskName", Role.ROLE_REPO_ADMIN_DESC);
            variables.put("assignees", assignees);
        }
        return variables;
    }
}
