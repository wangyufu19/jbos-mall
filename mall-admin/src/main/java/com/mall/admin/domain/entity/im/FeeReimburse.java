package com.mall.admin.domain.entity.im;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.mall.admin.domain.entity.comm.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * FeeRiemburse
 *
 * @author youfu.wang
 * @date 2023/7/25
 **/
@Setter
@Getter
@TableName("IM_FEE_REIMBURSE")
public class FeeReimburse extends BaseEntity {
    /**
     * 业务编号
     */
    @TableField("bizno")
    private String bizNo;
    /**
     * 实例Id
     */
    @TableField(exist = false)
    private String instId;
    /**
     * 申请人Id
     */
    @TableField("applyuserid")
    private String applyUserId;
    /**
     * 申请人名称
     */
    @TableField(exist = false)
    private String applyUserName;
    /**
     * 申请部门Id
     */
    @TableField("applydepid")
    private String applyDepId;
    /**
     * 申请部门名称
     */
    @TableField(exist = false)
    private String applyDepName;
    /**
     * 费用类型
     */
    @TableField("feetype")
    private String feeType;
    /**
     * 费用模板
     */
    @TableField("feetmplt")
    private String feeTmplt;
    /**
     * 申请时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @TableField("applytime")
    private Date applyTime;
    /**
     * 总金额
     */
    @TableField("totalamt")
    private double totalAmt;
    /**
     * 备注
     */
    @TableField("bizdesc")
    private String bizDesc;
    /**
     * 业务状态
     */
    @TableField("bizstate")
    private String bizState;


}
