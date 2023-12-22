package com.mall.admin.application.service.im;

import com.mall.admin.application.request.im.FeeReimburseDto;
import com.mall.admin.application.request.wf.ProcessTaskDto;
import com.mall.admin.application.service.sm.BusinessDict;
import com.mall.admin.application.service.wf.ProcessCallback;
import com.mall.admin.application.service.wf.ProcessDefConstants;
import com.mall.admin.application.service.wf.ProcessMgrService;
import com.mall.admin.application.service.wf.ProcessTaskService;
import com.mall.admin.common.exception.CamundaException;
import com.mall.admin.domain.entity.im.FeeReimburse;
import com.mall.admin.domain.entity.sm.Role;
import com.mall.admin.domain.entity.wf.ProcessInst;
import com.mall.admin.domain.entity.wf.ProcessTask;
import com.mall.admin.infrastructure.repository.im.FeeReimburseRepo;
import com.mall.admin.infrastructure.repository.im.InvoiceRepo;
import com.mall.admin.infrastructure.repository.im.PayeeRepo;
import com.mall.common.page.PageParam;
import com.mall.common.response.ResponsePageResult;
import com.mall.common.response.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * FeeReimburseService
 *
 * @author youfu.wang
 * @date 2023/7/25
 **/
@Service
public class FeeReimburseService {
    /**
     * 业务字典实例
     */
    @Autowired
    private BusinessDict businessDict;
    /**
     * 流程管理服务
     */
    @Autowired
    private ProcessMgrService processMgrService;
    /**
     * 任务服务
     */
    @Autowired
    private ProcessTaskService processTaskService;
    /**
     * FeeReimburseRepo
     */
    @Autowired
    private FeeReimburseRepo feeReimburseRepo;
    /**
     * PayeeRepo
     */
    @Autowired
    private PayeeRepo payeeRepo;
    /**
     * InvoiceRepo
     */
    @Autowired
    private InvoiceRepo invoiceRepo;

    /**
     * 查询费用报销列表
     *
     * @param pageParam
     * @param parameterObject
     * @return ResponseResult
     */
    public ResponsePageResult getFeeReimburseList(PageParam pageParam, Map<String, Object> parameterObject) {
        List<FeeReimburse> feeReimburseList = feeReimburseRepo.getFeeReimburseList(pageParam, parameterObject);
        return ResponsePageResult.ok().isPage(true).setData(feeReimburseList);
    }

    /**
     * 根据业务ID查询费用报销
     *
     * @param id
     * @return FeeReimburseDto
     */
    public FeeReimburseDto getFeeReimburseById(String id) {
        FeeReimburseDto feeReimburseDto = new FeeReimburseDto();
        //查询费用报销基本信息
        feeReimburseDto.setFeeReimburse(feeReimburseRepo.getFeeReimburseById(id));
        //查询费用报销收款人信息
        feeReimburseDto.setPayee(payeeRepo.getPayeeInfo(id));
        //查询费用报销科目明细
        feeReimburseDto.setFeeReimburseItems(feeReimburseRepo.getFeeReimburseItem(id));
        //查询费用报销发票明细
        feeReimburseDto.setInvoiceItems(invoiceRepo.getReimburseInvoiceItem(id));
        return feeReimburseDto;
    }

    /**
     * 新增费用报销
     *
     * @param feeReimburseDto
     */
    @Transactional
    public void addFeeReimburseItem(FeeReimburseDto feeReimburseDto) {
        //新增费用报销科目明细
        feeReimburseRepo.addFeeReimburseItem(feeReimburseDto.getFeeReimburseItems());
        //新增费用报销
        feeReimburseRepo.addFeeReimburse(feeReimburseDto.getFeeReimburse());
        //新增收款人信息
        payeeRepo.addPayeeInfo(feeReimburseDto.getPayee());
        //新增费用报销发票明细
        invoiceRepo.addReimburseInvoiceItem(feeReimburseDto.getInvoiceItems());
    }

    /**
     * 更新费用报销
     *
     * @param feeReimburseDto
     */
    @Transactional
    public void updateFeeReimburse(FeeReimburseDto feeReimburseDto) {
        //删除费用报销科目明细
        feeReimburseRepo.deleteFeeReimburseItem(feeReimburseDto.getFeeReimburse().getId());
        //删除费用报销发票明细
        invoiceRepo.deleteReimburseInvoiceItem(feeReimburseDto.getFeeReimburse().getId());
        //新增费用报销科目明细
        feeReimburseRepo.addFeeReimburseItem(feeReimburseDto.getFeeReimburseItems());
        //更新费用报销
        feeReimburseRepo.updateFeeReimburse(feeReimburseDto.getFeeReimburse());
        //更新收款人信息
        payeeRepo.updatePayeeInfo(feeReimburseDto.getPayee());
        //新增费用报销发票明细
        invoiceRepo.addReimburseInvoiceItem(feeReimburseDto.getInvoiceItems());
    }

    /**
     * 删除费用报销
     *
     * @param parameterObject
     */
    @Transactional
    public void deleteFeeReimburse(Map<String, Object> parameterObject) {
        //删除费用报销
        feeReimburseRepo.deleteFeeReimburse(parameterObject);
        //删除费用报销科目明细
        feeReimburseRepo.deleteFeeReimburseItem(String.valueOf(parameterObject.get("id")));
    }

    /**
     * 启动费用报销流程
     *
     * @param feeReimburseDto
     */
    @Transactional
    public void startFeeReimburse(FeeReimburseDto feeReimburseDto) throws CamundaException {
        //启动物品领取流程
        Map<String, Object> processMap = new HashMap<String, Object>();
        processMap.put("processDefinitionKey", ProcessDefConstants.PROC_DEF_FEE_REIMBURSE);
        processMap.put("businessKey", "KEY_" + feeReimburseDto.getFeeReimburse().getBizNo());
        processMap.put("userId", feeReimburseDto.getFeeReimburse().getApplyUserId());
        processMap.put("startActivityId", Role.ROLE_PROCESS_STARTER);
        processMap.put("routeUrl",
                businessDict.getDictValue("JBOS_PROC_ROUTE", ProcessDefConstants.PROC_DEF_FEE_REIMBURSE));
        processMap.put("bizId", feeReimburseDto.getFeeReimburse().getId());
        processMap.put("bizNo", feeReimburseDto.getFeeReimburse().getBizNo());
        processMap.put("bizType", ProcessDefConstants.PROC_DEF_FEE_REIMBURSE);
        processMap.put("amount", feeReimburseDto.getFeeReimburse().getTotalAmt());

        processMgrService.startProcessInstance(processMap, new ProcessCallback() {
            public void call(Map<String, String> data) {
                String processDefinitionId = data.get("processDefinitionId");
                String processInstanceId = data.get("processInstanceId");
                feeReimburseDto.getFeeReimburse().setInstId(processInstanceId);
                feeReimburseDto.getFeeReimburse().setBizState(ProcessInst.PROCESS_STATE_ACTIVE);
                if ("create".equals(feeReimburseDto.getAction())) {
                    //新增物品领取业务数据
                    addFeeReimburseItem(feeReimburseDto);
                } else {
                    //更新物品领取业务数据
                    updateFeeReimburse(feeReimburseDto);
                }
            }
        });
    }

    /**
     * 流转费用报销流程
     *
     * @param params
     * @throws CamundaException
     * @return ResponseResult
     */
    @Transactional
    public ResponseResult transFeeReimburse(Map<String, Object> params) throws CamundaException {
        ResponseResult res;
        Map<String, Object> feeReimburseMap = (Map<String, Object>) params.get("formObj");
        ProcessTask processTask = ProcessTaskDto.build(feeReimburseMap);
        FeeReimburseDto feeReimburseDto = FeeReimburseDto.build(params);
        //审批驳回
        if ("101".equals(processTask.getOpinion())) {
            res = processTaskService.rejectProcessTask(processTask);
        } else {
            String bizNo = feeReimburseDto.getFeeReimburse().getBizNo();
            double amount = feeReimburseDto.getFeeReimburse().getTotalAmt();
            //查询费用报销业务流程变量
            Map<String, Object> variables = this.getFeeReimburseProcessVariables(processTask);
            //流程实例非最后节点下一节点的候选人不能为空
            if (!Role.ROLE_IM_ADMIN.equals(processTask.getActivityId())
                    && StringUtils.isEmpty(String.valueOf(variables.get("nextAssignees")))) {
                res = ResponseResult.error(
                        ResponseResult.CODE_FAILURE,
                        "对不起，实例任务【" + variables.get("nextActivityName") + "】候选人不能为空！");
                return res;
            }
            res = processTaskService.completeTask(processTask, variables, new ProcessCallback() {
                public void call(Map<String, String> data) throws CamundaException {
                    //更新费用报销业务完成状态
                    Map<String, Object> parameterObject = new HashMap<>();
                    parameterObject.put("BIZNO", bizNo);
                    parameterObject.put("INSTID", processTask.getProcInstId());
                    parameterObject.put("BIZSTATE", ProcessInst.PROCESS_STATE_COMPLETED);
                    feeReimburseRepo.updateBizState(parameterObject);
                }
            });
        }
        return res;
    }

    private Map<String, Object> getFeeReimburseProcessVariables(ProcessTask processTask) {
        Map<String, Object> variables = new HashMap<>();

        variables.put("userId", processTask.getAssignee());
        variables.put("processDefinitionId", processTask.getProcDefId());
        variables.put("processInstanceId", processTask.getProcInstId());

        String nextAssignees;
        variables.put("depId", processTask.getAssigneeDepId());
        if (Role.ROLE_PROCESS_STARTER.equals(processTask.getActivityId())) {
            variables.put("currentActivityId", Role.ROLE_PROCESS_STARTER);
            variables.put("nextActivityId", Role.ROLE_REPO_ADMIN);
            nextAssignees = processTaskService.getTaskAssigneeList(variables, false);
            variables.put("nextActivityName", Role.ROLE_REPO_ADMIN_DESC);
            variables.put("nextAssignees", nextAssignees);
        } else if (Role.ROLE_REPO_ADMIN.equals(processTask.getActivityId())) {
            variables.put("currentActivityId", Role.ROLE_REPO_ADMIN);
            variables.put("nextActivityId", Role.ROLE_IM_ADMIN);
            nextAssignees = processTaskService.getTaskAssigneeList(variables, false);
            variables.put("nextActivityName", Role.ROLE_IM_ADMIN_DESC);
            variables.put("nextAssignees", nextAssignees);
        }
        return variables;
    }
}
