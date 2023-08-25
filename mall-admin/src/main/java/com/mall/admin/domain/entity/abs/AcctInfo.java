package com.mall.admin.domain.entity.abs;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * AcctInfo
 *
 * @author youfu.wang
 * @date 2023/8/22
 **/
@Setter
@Getter
@TableName("abs_pro_acctinfo")
public class AcctInfo {
    /**
     * id
     */
    @TableField("ID")
    private String id;
    /**
     * 项目编号
     */
    @TableField("PROJECT_ID")
    private String projectId;
    /**
     * 内部账本金清算账号
     */
    @TableField("INNER_PRINCIPAL_ACCTNO")
    private String innerPrincipalAcctNo;
    /**
     * 内部账利息清算账号
     */
    @TableField("INNER_INTEREST_ACCTNO")
    private String innerInterestAcctNo;
    /**
     * 外部账本金收款账号
     */
    @TableField("OUTTER_PRINCIPAL_ACCTNO")
    private String outerPrincipalAcctNo;
    /**
     * 外部账利息收款账号
     */
    @TableField("OUTTER_INTEREST_ACCTNO")
    private String outerInterestAcctNo;
}
