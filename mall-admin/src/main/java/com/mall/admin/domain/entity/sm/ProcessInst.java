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
    private String procInstId;
    private String procDefId;
    private String bizNo;
    private String userId;
    private String businessKey;
    private String startTime;
    private String endTime;
    private String procState;

}
