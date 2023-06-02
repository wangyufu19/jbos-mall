package com.mall.admin.application.service.sm;

import com.mall.common.base.BaseService;
import com.mall.common.page.PageParam;
import com.mall.admin.domain.entity.sm.ProcessTask;
import com.mall.admin.domain.entity.sm.TaskStep;
import com.mall.admin.infrastructure.repository.sm.ProcessTaskRepo;
import com.mall.common.response.ResponseResult;
import com.mall.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public ResponseResult getUserTaskList(PageParam pageParam, Map<String, Object> parameterObject) {
        List<ProcessTask> processTasks = processTaskRepo.getUserTaskList(pageParam, parameterObject);
        return ResponseResult.ok().isPage(true).data(processTasks);
    }

    public ResponseResult getUserTaskProcessedList(PageParam pageParam, Map<String, Object> parameterObject) {
        List<ProcessTask> processTasks=processTaskRepo.getUserTaskProcessedList(pageParam, parameterObject);
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
}
