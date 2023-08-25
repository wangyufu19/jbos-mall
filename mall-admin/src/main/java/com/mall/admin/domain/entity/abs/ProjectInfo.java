package com.mall.admin.domain.entity.abs;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mall.admin.domain.entity.comm.BaseEntity;
import lombok.Data;

/**
 * ProjectBaseInfo
 *
 * @author youfu.wang
 * @date 2023/8/21
 **/
@Data
@TableName("ABS_PROJECT")
public class ProjectInfo extends BaseEntity {
    /**
     * 项目状态(10:正常)
     */
    public static final String PROJECT_STE_NORMAL = "10";
    /**
     * 项目编号
     */
    @TableField("project_no")
    private String projectNo;
    /**
     * 项目名称
     */
    @TableField("project_name")
    private String projectName;
    /**
     * 项目类型
     */
    @TableField("project_Type")
    private String projectType;
    /**
     * 资产类型
     */
    @TableField("asset_type")
    private String assetType;
    /**
     * 项目金额
     */
    @TableField("project_amt")
    private double projectAmt;
    /**
     * 最高限额
     */
    @TableField("project_max_amt")
    private double projectMaxAmt;
    /**
     * 封包日期
     */
    @TableField("packet_date")
    private String packetDate;
    /**
     * 利息起算日
     */
    @TableField("irt_start_date")
    private String irtStartDate;
    /**
     * 发行日期
     */
    @TableField("issue_date")
    private String issueDate;
    /**
     * 结束日期
     */
    @TableField("end_date")
    private String endDate;
    /**
     * 法定到期日
     */
    @TableField("law_end_date")
    private String lawEndDate;
    /**
     * 是否循环购买
     */
    @TableField("is_recycle_buy")
    private String isRecycleBuy;
    /**
     * 循环购买开始日
     */
    @TableField("buy_sdate")
    private String buySDate;
    /**
     * 循环购买截止日
     */
    @TableField("buy_edate")
    private String buyEDate;
    /**
     * 项目状态
     */
    @TableField("project_ste")
    private String projectSte;
}
