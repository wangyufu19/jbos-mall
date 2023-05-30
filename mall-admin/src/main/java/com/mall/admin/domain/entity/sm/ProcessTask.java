package com.mall.admin.domain.entity.sm;

import com.mall.admin.domain.entity.comm.BaseEntity;
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
     * 10：审批中
     * 20：审批退回
     * 90：审批成功
     */
    public static final String PROCESS_STATE_ACTIVE="10";
    public static final String PROCESS_STATE_COMPLETED="90";
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
}
