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
    private String procInstId;
    private String bizId;
    private String bizNo;
    private String bizType;
    private String taskId;
    private String taskName;
    private String assignee;
    private String routeUrl;
    private String taskState;
    private String startTime;
    private String endTime;
    private String opinion;
}
