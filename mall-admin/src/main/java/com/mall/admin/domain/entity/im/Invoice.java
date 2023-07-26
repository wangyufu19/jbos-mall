package com.mall.admin.domain.entity.im;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * Invoice
 *
 * @author youfu.wang
 * @date 2023/7/25
 **/
@Setter
@Getter
@TableName("IM_REIMBURSE_INVOICE")
public class Invoice {
    private String bizId;
    private String invoiceCode;
    private String invoiceNo;
    private double amount;
}
