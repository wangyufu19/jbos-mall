package com.mall.admin.domain.entity.im;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
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
@TableName("IM_MATERIAL_BUY")
public class MaterialBuy extends BaseEntity {
    @TableField("bizno")
    private String bizNo;
    @TableField(exist=false)
    private int instId;
    @TableField("applyuserid")
    private String applyUserId;
    @TableField("applydepid")
    private String applyDepId;
    @TableField("feetype")
    private String feeType;
    @TableField("applytime")
    private String applyTime;
    @TableField("totalamt")
    private double totalAmt;
    @TableField("gmotime")
    private String gmoTime;
    @TableField("purpose")
    private String purpose;

}
