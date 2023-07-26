package com.mall.admin.domain.entity.im;

import com.baomidou.mybatisplus.annotation.TableName;
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
public class Payee{
    private String bizId;
    private String acctName;
    private String acctNo;
    private String bankName;
    private String tradeType;
}
