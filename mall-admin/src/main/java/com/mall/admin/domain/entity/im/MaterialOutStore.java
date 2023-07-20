package com.mall.admin.domain.entity.im;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.mall.admin.domain.entity.comm.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * MaterialOutStore
 * @author youfu.wang
 * @date 2020-06-24
 */
@Setter
@Getter
@TableName("IM_MATERIAL_OUTSTORE")
public class MaterialOutStore extends BaseEntity {

    @TableField("bizno")
    private String bizNo;
    @TableField(exist=false)
    private String instId;
    @TableField("applyuserid")
    private String applyUserId;
    @TableField(exist=false)
    private String applyUserName;
    @TableField("applydepid")
    private String applyDepId;
    @TableField(exist=false)
    private String applyDepName;
    @TableField("feetype")
    private String feeType;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @TableField("applytime")
    private Date applyTime;
    @TableField("totalamt")
    private double totalAmt;
    @TableField("bizstate")
    private String bizState;
}
