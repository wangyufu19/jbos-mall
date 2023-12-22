package com.mall.admin.domain.entity.abs;

import com.alibaba.excel.annotation.ExcelProperty;
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
     * 资产状态(0:正常)
     */
    public static final String ASSET_STE_NORMAL = "0";
    /**
     * 贷款账号
     */
    @TableField("acct_no")
    @ExcelProperty
    private String acctNo;
    /**
     * 客户名称
     */
    @TableField("custom_name")
    @ExcelProperty
    private String customName;
    /**
     * 性别
     */
    @TableField("sex")
    @ExcelProperty
    private String sex;
    /**
     * 身份证号码
     */
    @TableField("cert_id")
    @ExcelProperty
    private String certId;
    /**
     * 月收入
     */
    @TableField("month_income")
    @ExcelProperty
    private double monthIncome;
    /**
     * 授信额度
     */
    @TableField("grant_limit")
    @ExcelProperty
    private double grantLimit;
    /**
     * 剩余额度
     */
    @TableField("surplus_limit")
    @ExcelProperty
    private double surplusLimit;
    /**
     * 贷款余额
     */
    @TableField("loan_surplus_amt")
    @ExcelProperty
    private double loanSurplusAmt;
    /**
     * 贷款放款日
     */
    @TableField("loan_sdate")
    @ExcelProperty
    private String loanSdate;
    /**
     * 贷款到期日
     */
    @TableField("loan_edate")
    @ExcelProperty
    private String loanEdate;
    /**
     * 贷款利率
     */
    @TableField("loan_rate")
    @ExcelProperty
    private double loanRate;
    /**
     * 还款方式
     */
    @TableField("refund_type")
    @ExcelProperty
    private String refundType;
    /**
     * 付息方式
     */
    @TableField("pay_irt_type")
    @ExcelProperty
    private String payIrtType;
    /**
     * 信用评级
     */
    @TableField("credit_rate")
    @ExcelProperty
    private String creditRate;
    /**
     * 五级分类
     */
    @TableField("five_classify")
    @ExcelProperty
    private String fiveClassify;
    /**
     * 贷款类型
     */
    @TableField("loan_type")
    @ExcelProperty
    private String loanType;
    /**
     * 资产状态
     */
    @TableField("asset_ste")
    private String assetSte;
}
