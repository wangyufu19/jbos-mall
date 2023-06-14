package com.mall.admin.domain.entity.wf;

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
     * 10：NONE
     * 20：活动中
     * 90：被作废或删除
     * 90：已结束
     * 99：已暂停
     */
    public static final String PROCESS_STATE_START="NONE";
    public static final String PROCESS_STATE_ACTIVE="20";
    public static final String PROCESS_STATE_CANCELED="80";
    public static final String PROCESS_STATE_COMPLETED="90";
    public static final String PROCESS_STATE_SUSPENDED="99";

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
