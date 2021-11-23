package com.mall.admin.domain.entity.im;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mall.admin.domain.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * MaterialList
 * @author youfu.wang
 * @date 2020-06-24
 */
@Setter
@Getter
@TableName("im_material_list")
public class MaterialList extends BaseEntity {
    @TableField("bizid")
    private String bizId;
    @TableField("bizno")
    private String bizNo;
    @TableField("biztype")
    private String bizType;
    @TableField("materialname")
    private String materialName;
    @TableField("amount")
    private double amount;
    @TableField("price")
    private double price;

}
