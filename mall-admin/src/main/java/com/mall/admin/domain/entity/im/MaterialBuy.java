package com.mall.admin.domain.entity.im;

import com.mall.admin.domain.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * MaterialBuy
 * @author youfu.wang
 * @date 2020-06-24
 */
@Setter
@Getter
public class MaterialBuy extends BaseEntity {
    private String bizNo;
    private int instId;
    private String applyUserId;
    private String applyDepId;
    private String feeType;
    private String applyTime;
    private double totalAmt;
    private String gmoTime;
    private String purpose;

}
