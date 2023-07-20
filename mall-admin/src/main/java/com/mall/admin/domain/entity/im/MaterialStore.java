package com.mall.admin.domain.entity.im;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mall.admin.domain.entity.comm.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * MaterialStore
 *
 * @author youfu.wang
 * @date 2023/7/14
 **/
@Setter
@Getter
@TableName("IM_MATERIAL_STORE")
public class MaterialStore extends BaseEntity {
    @TableField("batchno")
    private String batchNo;

    @TableField("inbizId")
    private String inBizId;

    @TableField("materialid")
    private String materialId;

    @TableField("materialname")
    private String materialName;

    @TableField("amount")
    private double amount;

    @TableField("surplusamt")
    private double surplusAmt;

    @TableField("price")
    private double price;

    @TableField("intime")
    private String inTime;

    private boolean hasChildren=true;

}
