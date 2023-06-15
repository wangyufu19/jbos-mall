package com.mall.admin.application.service.wf;

import com.mall.admin.application.request.wf.ProcessInstanceDto;
import com.mall.admin.application.service.external.camunda.ProcessInstanceService;
import com.mall.admin.domain.entity.sm.Role;
import com.mall.admin.domain.entity.wf.ProcessInst;
import com.mall.admin.domain.entity.wf.ProcessTask;
import com.mall.admin.infrastructure.repository.wf.ProcessInstRepo;
import com.mall.common.page.PageParam;
import com.mall.common.response.ResponseResult;
import com.mall.common.utils.DateUtils;
import com.mall.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author youfu.wang
 * @date 2023/4/6
 **/
@Service
public class ProcessMgrService {
    @Autowired
    private ProcessInstanceService processInstanceService;
    @Autowired
    private ProcessTaskService processTaskService;
    @Autowired
    private ProcessInstRepo processInstRepo;

    /**
     * 得到流程实例列表
     * @param pageParam
     * @param parameterObject
     * @return
     */
    public ResponseResult getProcessInstList(PageParam pageParam,Map<String, Object> parameterObject){
        List<ProcessInst> processInstList=processInstRepo.getProcessInstList(pageParam,parameterObject);
        return ResponseResult.ok().isPage(true).data(processInstList);
    }

    /**
     * 启动流程实例
     * @param variable
     * @return
     */
    @Transactional
    public ResponseResult startProcessInstance(Map<String, Object> variable,ProcessCallback processCallback){
        ResponseResult res;
        String userId= StringUtils.replaceNull(variable.get("userId"));
        String startActivityId=StringUtils.replaceNull(variable.get("startActivityId"));
        variable.put(startActivityId,userId);
        //启动流程实例
        res=processInstanceService.startProcessInstance(variable);
        //启动实例成功，则处理相关业务逻辑
        if(ResponseResult.CODE_SUCCESS.equals(res.getRetCode())) {
            Map<String, String> data=(Map<String,String>)res.getData();
            String processDefinitionId = null;
            String processInstanceId=null;
            if (data != null) {
                processDefinitionId = data.get("processDefinitionId");
                processInstanceId = data.get("processInstanceId");
            }
            res.setData(data);
            //处理流程启动数据
            this.handleStartProcessInstance(processDefinitionId,processInstanceId,startActivityId,userId,variable);
            //处理流程回调业务
            if(processCallback!=null){
                processCallback.call(data);
            }
        }
        return res;
    }

    /**
     * 处理流程启动数据
     * @param processDefinitionId
     * @param processInstanceId
     * @param variable
     */
    private void handleStartProcessInstance(String processDefinitionId,
                                            String processInstanceId,
                                            String startActivityId,
                                            String userId,
                                            Map<String, Object> variable){
        //新增流程实例数据
        ProcessInst processInst=ProcessInstanceDto.build(variable);
        processInst.setProcDefId(processDefinitionId);
        processInst.setProcInstId(processInstanceId);
        this.addProcessInst(processInst);
        //新增流程任务数据
        ProcessTask processTask = new ProcessTask();
        processTask.setId(StringUtils.getUUID());
        processTask.setProcInstId(processInstanceId);
        processTask.setTaskDefKey(startActivityId);
        processTask.setTaskName(Role.ROLE_PROCESS_STARTER_DESC);
        processTask.setAssignee(userId);
        processTask.setTaskState(ProcessTask.PROCESS_STATE_ACTIVE);
        processTask.setStartTime(DateUtils.format(DateUtils.getCurrentDate(), DateUtils.YYYYMMDDHIMMSS));
        processTaskService.addProcessTask(processTask);
    }

    /**
     * 新增流程实例业务数据
     * @param processInst
     */
    public void addProcessInst(ProcessInst processInst){
        processInstRepo.addProcessInst(processInst);
    }

    /**
     * 更新流程实例状态
     * @param processInstanceId
     * @param procState
     */
    public void updateProcState(String processInstanceId,String procState){
        ProcessInst processInst=new ProcessInst();
        processInst.setProcInstId(processInstanceId);
        processInst.setProcState(procState);
        processInst.setEndTime(DateUtils.format(DateUtils.getCurrentDate(), DateUtils.YYYYMMDDHIMMSS));
        processInstRepo.updateProcState(processInst);
    }
}
