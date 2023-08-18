package com.mall.admin.domain.entity.abs;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mall.admin.domain.entity.comm.BaseEntity;
import com.mall.common.office.excel.annotation.Cell;
import lombok.Getter;
import lombok.Setter;
import org.apache.poi.ss.usermodel.CellType;

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
    @Cell(index = 0, cellType = CellType.STRING)
    private String acctNo;
    /**
     * 客户名称
     */
    @TableField("custom_name")
    @Cell(index = 1, cellType = CellType.STRING)
    private String customName;
    /**
     * 性别
     */
    @TableField("sex")
    @Cell(index = 2, cellType = CellType.STRING)
    private String sex;
    /**
     * 身份证号码
     */
    @TableField("cert_id")
    @Cell(index = 3, cellType = CellType.STRING)
    private String certId;
    /**
     * 月收入
     */
    @TableField("month_income")
    @Cell(index = 4, cellType = CellType.NUMERIC)
    private double monthIncome;
    /**
     * 授信额度
     */
    @TableField("grant_limit")
    @Cell(index = 5, cellType = CellType.NUMERIC)
    private double grantLimit;
    /**
     * 剩余额度
     */
    @TableField("surplus_limit")
    @Cell(index = 6, cellType = CellType.NUMERIC)
    private double surplusLimit;
    /**
     * 贷款余额
     */
    @TableField("loan_surplus_amt")
    @Cell(index = 7, cellType = CellType.NUMERIC)
    private double loanSurplusAmt;
    /**
     * 贷款放款日
     */
    @TableField("loan_sdate")
    @Cell(index = 8, cellType = CellType.STRING)
    private String loanSdate;
    /**
     * 贷款到期日
     */
    @TableField("loan_edate")
    @Cell(index = 9, cellType = CellType.STRING)
    private String loanEdate;
    /**
     * 贷款利率
     */
    @TableField("loan_rate")
    @Cell(index = 10, cellType = CellType.NUMERIC)
    private double loanRate;
    /**
     * 还款方式
     */
    @TableField("refund_type")
    @Cell(index = 11, cellType = CellType.STRING)
    private String refundType;
    /**
     * 付息方式
     */
    @TableField("pay_irt_type")
    @Cell(index = 12, cellType = CellType.STRING)
    private String payIrtType;
    /**
     * 信用评级
     */
    @TableField("credit_rate")
    @Cell(index = 13, cellType = CellType.STRING)
    private String creditRate;
    /**
     * 五级分类
     */
    @TableField("five_classify")
    @Cell(index = 14, cellType = CellType.STRING)
    private String fiveClassify;
    /**
     * 贷款类型
     */
    @TableField("loan_type")
    @Cell(index = 15, cellType = CellType.STRING)
    private String loanType;
    /**
     * 资产状态
     */
    @TableField("asset_ste")
    private String assetSte;
}
