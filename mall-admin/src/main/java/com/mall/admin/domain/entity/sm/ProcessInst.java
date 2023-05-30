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
public class ProcessInst extends BaseEntity implements Serializable {
    /**
     * 10：草稿
     * 20：活动中
     * 90：已结束
     */
    public static final String PROCESS_STATE_START="10";
    public static final String PROCESS_STATE_ACTIVE="20";
    public static final String PROCESS_STATE_COMPLETED="90";
    private String procInstId;
    private String procDefId;
    private String bizId;
    private String bizNo;
    private String bizType;
    private String userId;
    private String businessKey;
    private String startTime;
    private String endTime;
    private String procState;
    private String routeUrl;
}
