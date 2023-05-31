package com.mall.admin.domain.entity.sm;

import com.mall.admin.domain.entity.comm.BaseEntity;
import com.mall.common.utils.DateUtils;
import com.mall.common.utils.StringUtils;
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

    public static ProcessInst build(
            String processInstanceId,
            String processDefinitionId,
            String userId,
            String bizId,
            String bizNo,
            String bizType,
            String routeUrl){
        String currentTime= DateUtils.format(DateUtils.getCurrentDate(),DateUtils.YYYYMMDDHIMMSS);
        ProcessInst processInst=new ProcessInst();
        processInst.setId(StringUtils.getUUID());
        processInst.setProcInstId(processInstanceId);
        processInst.setProcDefId(processDefinitionId);
        processInst.setUserId(userId);
        processInst.setBizId(bizId);
        processInst.setBizNo(bizNo);
        processInst.setBizType(bizType);
        processInst.setBusinessKey(bizNo);
        processInst.setRouteUrl(routeUrl);
        processInst.setStartTime(currentTime);
        processInst.setCreateUserId(userId);
        processInst.setCreateTime(currentTime);
        return processInst;
    }
}
