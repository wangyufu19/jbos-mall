package com.mall.admin.application.service.sm;

import com.mall.admin.application.request.im.ProcessTaskDto;
import com.mall.admin.application.service.external.camunda.TaskService;
import com.mall.admin.domain.entity.sm.Role;
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

    public String getTaskAssigneeList(Map<String, Object> parameterObject) {
        String assignees = "";
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
    @Transactional
    public ResponseResult handleDrawbackProcessTask(ProcessTaskDto processTaskDto) {
        ResponseResult res;
        Map<String, Object> params = new HashMap<>();
        params.put("userId", processTaskDto.getAssignee());
        params.put("processDefinitionId", processTaskDto.getProcDefId());
        params.put("processInstanceId", processTaskDto.getProcInstId());
        params.put("taskId", processTaskDto.getTaskId());
        res = taskService.drawback(params);
        String isDrawback=StringUtils.replaceNull(((Map<String,Object>)res.getData()).get("isDrawback"));
        if (ResponseResult.CODE_SUCCESS.equals(res.getRetCode())
                &&res.getData()!=null
                &&"true".equals(StringUtils.replaceNull(((Map<String,Object>)res.getData()).get("isDrawback")))
        ) {
            //新增流程任务数据
            ProcessTask processTask = new ProcessTask();
            processTask.setId(StringUtils.getUUID());
            processTask.setProcInstId(processTaskDto.getProcInstId());
            processTask.setTaskDefKey(processTaskDto.getTaskDefKey());
            processTask.setTaskName(processTaskDto.getTaskName());
            processTask.setAssignee(processTaskDto.getAssignee());
            processTask.setTaskState( ProcessTask.PROCESS_STATE_ACTIVE);
            processTask.setStartTime(DateUtils.format(DateUtils.getCurrentDate(),DateUtils.YYYYMMDDHIMMSS));
            this.addProcessTask(processTask);
            //更新流程任务撤回任务后未审批的任务状态为NONE
            processTaskRepo.updateDrawbackPostProcessTask(processTask);
        }
        return res;
    }
}
