package com.mall.admin.application.service.wf;

import com.mall.admin.application.request.wf.ProcessTaskDto;
import com.mall.admin.application.service.external.camunda.TaskService;
import com.mall.common.base.BaseService;
import com.mall.common.page.PageParam;
import com.mall.admin.domain.entity.sm.ProcessTask;
import com.mall.admin.domain.entity.sm.TaskStep;
import com.mall.admin.infrastructure.repository.sm.ProcessTaskRepo;
import com.mall.common.response.ResponseResult;
import com.mall.common.utils.DateUtils;
import com.mall.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ProcessTaskService
 *
 * @author youfu.wang
 * @date 2023/4/6
 **/
@Service
public class ProcessTaskService extends BaseService {
    @Autowired
    private ProcessTaskRepo processTaskRepo;
    @Autowired
    private TaskService taskService;

    public ResponseResult getUserTaskList(PageParam pageParam, Map<String, Object> parameterObject) {
        List<ProcessTask> processTasks = processTaskRepo.getUserTaskList(pageParam, parameterObject);
        return ResponseResult.ok().isPage(true).data(processTasks);
    }

    public ResponseResult getUserTaskProcessedList(PageParam pageParam, Map<String, Object> parameterObject) {
        List<ProcessTask> processTasks = processTaskRepo.getUserTaskProcessedList(pageParam, parameterObject);
        return ResponseResult.ok().isPage(true).data(processTasks);
    }

    public List<TaskStep> getUserTaskStepList(Map<String, Object> parameterObject) {
        List<TaskStep> taskSteps = processTaskRepo.getUserTaskStepList(parameterObject);
        return taskSteps;
    }

    public String getTaskAssigneeList(Map<String, Object> parameterObject, boolean multiInstance) {
        List<Map> processedAssigneeList;
        String assignees = "";
        //判断当前实例任务是否多实例(除当前处理用户必须全部已处理才能得到下一个任务的领取人)
        if (multiInstance) {
            processedAssigneeList = processTaskRepo.getInstanceTaskProcessedAssigneeList(parameterObject);
            if(processedAssigneeList!=null&&processedAssigneeList.size()>0){
                return assignees;
            }
        }
        List<Map> assigneeList = processTaskRepo.getTaskAssigneeList(parameterObject);
        if (assigneeList != null) {
            for (int i = 0; i < assigneeList.size(); i++) {
                if (i == assigneeList.size() - 1) {
                    assignees = assignees + StringUtils.replaceNull(assigneeList.get(i).get("BADGE"));
                } else {
                    assignees = assignees + StringUtils.replaceNull(assigneeList.get(i).get("BADGE")) + ",";
                }
            }
        }
        return assignees;
    }

    public void addProcessTask(ProcessTask processTask) {
        processTaskRepo.addProcessTask(processTask);
    }

    public void updateProcessTaskOpinion(ProcessTask processTask) {
        processTaskRepo.updateProcessTaskOpinion(processTask);
    }

    public void addProcessNexTask(String processInstanceId, Map<String, Object> variables) {
        //新增物品采购流程下一个任务数据
        String nextTaskDefKey = StringUtils.replaceNull(variables.get("nextTaskDefKey"));
        String nextTaskName = StringUtils.replaceNull(variables.get("nextTaskName"));
        String nextAssignees = StringUtils.replaceNull(variables.get("nextAssignees"));
        String[] assigneeList = StringUtils.split(nextAssignees,',');
        if (assigneeList != null && assigneeList.length > 0) {
            for (String assignee : assigneeList) {
                ProcessTask processNextTask = new ProcessTask();
                processNextTask.setId(StringUtils.getUUID());
                processNextTask.setProcInstId(processInstanceId);
                processNextTask.setTaskDefKey(nextTaskDefKey);
                processNextTask.setTaskName(nextTaskName);
                processNextTask.setAssignee(assignee);
                processNextTask.setTaskState(ProcessTask.PROCESS_STATE_ACTIVE);
                processNextTask.setStartTime(DateUtils.format(DateUtils.getCurrentDate(), DateUtils.YYYYMMDDHIMMSS));
                this.addProcessTask(processNextTask);
            }
        }
    }

    @Transactional
    public ResponseResult handleDrawbackProcessTask(ProcessTaskDto processTaskDto) {
        ResponseResult res;
        Map<String, Object> params = new HashMap<>();
        params.put("userId", processTaskDto.getAssignee());
        params.put("processDefinitionId", processTaskDto.getProcDefId());
        params.put("processInstanceId", processTaskDto.getProcInstId());
        params.put("taskId", processTaskDto.getTaskId());
        res = taskService.drawback(params);
        if (ResponseResult.CODE_SUCCESS.equals(res.getRetCode())
                && res.getData() != null
                && "true".equals(StringUtils.replaceNull(((Map<String, Object>) res.getData()).get("isDrawback")))
        ) {
            //新增流程任务数据
            ProcessTask processTask = new ProcessTask();
            processTask.setId(StringUtils.getUUID());
            processTask.setProcInstId(processTaskDto.getProcInstId());
            processTask.setTaskDefKey(processTaskDto.getTaskDefKey());
            processTask.setTaskName(processTaskDto.getTaskName());
            processTask.setAssignee(processTaskDto.getAssignee());
            processTask.setTaskState(ProcessTask.PROCESS_STATE_ACTIVE);
            processTask.setStartTime(DateUtils.format(DateUtils.getCurrentDate(), DateUtils.YYYYMMDDHIMMSS));
            this.addProcessTask(processTask);
            //更新流程任务撤回任务后未审批的任务状态为NONE
            processTaskRepo.updateDrawbackPostProcessTask(processTask);
        }
        return res;
    }

    @Transactional
    public ResponseResult handleRejectProcessTask(ProcessTaskDto processTaskDto) {
        ResponseResult res;
        Map<String, Object> params = new HashMap<>();
        params.put("userId", processTaskDto.getAssignee());
        params.put("processDefinitionId", processTaskDto.getProcDefId());
        params.put("processInstanceId", processTaskDto.getProcInstId());
        res = taskService.reject(params);
        if (ResponseResult.CODE_SUCCESS.equals(res.getRetCode()) && res.getData() != null) {
            Map<String, Object> data = (Map<String, Object>) res.getData();
            //新增流程任务数据
            ProcessTask processTask = new ProcessTask();
            processTask.setId(StringUtils.getUUID());
            processTask.setProcInstId(processTaskDto.getProcInstId());
            processTask.setTaskDefKey(StringUtils.replaceNull(data.get("toActivityId")));
            processTask.setTaskName(StringUtils.replaceNull(data.get("toActivityName")));
            processTask.setAssignee(StringUtils.replaceNull(data.get("toAssignee")));
            processTask.setTaskState(ProcessTask.PROCESS_STATE_ACTIVE);
            processTask.setStartTime(DateUtils.format(DateUtils.getCurrentDate(), DateUtils.YYYYMMDDHIMMSS));
            this.addProcessTask(processTask);
            //更新流程驳回任务审批状态数据
            ProcessTask processCurrentTask = new ProcessTask();
            processCurrentTask.setProcInstId(processTaskDto.getProcInstId());
            processCurrentTask.setTaskId(StringUtils.replaceNull(data.get("taskId")));
            processCurrentTask.setTaskDefKey(processTaskDto.getTaskDefKey());
            processCurrentTask.setAssignee(processTaskDto.getAssignee());
            processCurrentTask.setTaskState(ProcessTask.PROCESS_STATE_REJECT);
            processCurrentTask.setOpinion(processTaskDto.getOpinion());
            processCurrentTask.setOpinionDesc(processTaskDto.getOpinionDesc());
            processCurrentTask.setEndTime(DateUtils.format(DateUtils.getCurrentDate(), DateUtils.YYYYMMDDHIMMSS));
            processTaskRepo.updateProcessTaskOpinion(processCurrentTask);
            //更新流程任务驳回后未审批的所有任务状态为NONE(除当前用户任务)
            processTaskRepo.updateHandlePostProcessTask(processCurrentTask);
        }
        return res;
    }

    public void updateHandlePostProcessTask(ProcessTask processTask) {
        processTaskRepo.updateHandlePostProcessTask(processTask);
    }
}
