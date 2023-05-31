package com.mall.admin.domain.entity.sm;

import com.mall.admin.domain.entity.comm.BaseEntity;
import com.mall.common.utils.DateUtils;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author youfu.wang
 * @date 2023/4/6
 **/
@Setter
@Getter
public class ProcessTask extends BaseEntity implements Serializable {
    /**
     * 10：NONE
     * 20：审批中
     * 99：审批退回
     * 90：审批成功
     */
    public static final String PROCESS_STATE_NONE="10";
    public static final String PROCESS_STATE_ACTIVE="20";
    public static final String PROCESS_STATE_COMPLETED="90";
    public static final String PROCESS_STATE_REJECT="99";
    private String procInstId;
    private String bizId;
    private String bizNo;
    private String bizType;
    private String taskId;
    private String taskDefKey;
    private String taskName;
    private String assignee;
    private String routeUrl;
    private String taskState;
    private String startTime;
    private String endTime;
    private String opinion;

    public static ProcessTask build(
            String id,
            String processInstanceId,
            String taskId,
            String taskDefKey,
            String taskName,
            String assignee,
            String taskState){
        String currentTime= DateUtils.format(DateUtils.getCurrentDate(),DateUtils.YYYYMMDDHIMMSS);
        ProcessTask processTask=new ProcessTask();
        processTask.setId(id);
        processTask.setProcInstId(processInstanceId);
        processTask.setTaskId(taskId);
        processTask.setTaskDefKey(taskDefKey);
        processTask.setTaskName(taskName);
        processTask.setAssignee(assignee);
        processTask.setTaskState(taskState);
        processTask.setStartTime(currentTime);
        processTask.setCreateUserId(assignee);
        processTask.setCreateTime(currentTime);
        return processTask;
    }
}
