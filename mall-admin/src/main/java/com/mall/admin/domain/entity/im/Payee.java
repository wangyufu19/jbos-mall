package com.mall.admin.domain.entity.im;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mall.admin.domain.entity.comm.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * Payee
 *
 * @author youfu.wang
 * @date 2023/7/25
 **/
@Setter
@Getter
@TableName("IM_REIMBURSE_PAYEE")
public class Payee extends BaseEntity {
    @TableField("bizid")
    private String bizId;
    @TableField("acctname")
    private String acctName;
    @TableField("acctno")
    private String acctNo;
    @TableField("bankname")
    private String bankName;
    @TableField("tradetype")
    private String tradeType;
}
