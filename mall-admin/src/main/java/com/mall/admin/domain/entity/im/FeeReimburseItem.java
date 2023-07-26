package com.mall.admin.domain.entity.im;

import com.baomidou.mybatisplus.annotation.TableName;
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
public class FeeReimburseItem {
    private String bizId;
    private String feeId;
    private String feeName;
    private double amount;
    private String feeDesc;
}
