package com.mall.admin.domain.entity.im;

import com.baomidou.mybatisplus.annotation.TableName;
import com.mall.admin.domain.entity.comm.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * FeeReimburseItem
 *
 * @author youfu.wang
 * @date 2023/7/25
 **/
@Setter
@Getter
@TableName("IM_FEE_REIMBURSE_ITEM")
public class FeeReimburseItem extends BaseEntity {
    /**
     * 业务Id
     */
    private String bizId;
    /**
     * 费用Id
     */
    private String feeId;
    /**
     * 费用名称
     */
    private String feeName;
    /**
     * 费用金额
     */
    private double amount;
    /**
     * 费用备注
     */
    private String feeDesc;
}
