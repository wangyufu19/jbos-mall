package com.mall.admin.domain.entity.abs;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mall.admin.domain.entity.comm.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * BasicAsset
 *
 * @author youfu.wang
 * @date 2023/8/4
 **/
@Setter
@Getter
@TableName("ABS_BASIC_ASSET")
public class BasicAsset extends BaseEntity {
    /**
     * 贷款账号
     */
    @TableField("acct_no")
    private String acctNo;
    /**
     * 客户名称
     */
    @TableField("custom_name")
    private String customName;
    /**
     * 性别
     */
    @TableField("sex")
    private String sex;
    /**
     * 身份证号码
     */
    @TableField("cert_id")
    private String certId;
    /**
     * 月收入
     */
    @TableField("month_income")
    private double monthIncome;
    /**
     * 授信额度
     */
    @TableField("grant_limit")
    private double grantLimit;
    /**
     * 剩余额度
     */
    @TableField("surplus_limit")
    private double surplusLimit;
    /**
     * 贷款余额
     */
    @TableField("loan_surplus_amt")
    private double loanSurplusAmt;
    /**
     * 贷款放款日
     */
    @TableField("loan_sdate")
    private String loanSdate;
    /**
     * 贷款到期日
     */
    @TableField("loan_edate")
    private String loanEdate;
    /**
     * 信用评级
     */
    @TableField("credit_rate")
    private String creditRate;
    /**
     * 五级分类
     */
    @TableField("five_classify")
    private String fiveClassify;
    /**
     * 贷款类型
     */
    @TableField("loan_type")
    private String loanType;
    /**
     * 资产状态
     */
    @TableField("asset_ste")
    private String assetSte;
}
