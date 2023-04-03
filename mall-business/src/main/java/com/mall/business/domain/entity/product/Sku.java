package com.mall.business.domain.entity.product;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mall.business.domain.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@TableName("PM_PRODUCT_SKU")
public class Sku extends BaseEntity {
    @TableField("seq_id")
    private String seqId;
    @TableField("product_seq_id")
    private String productSeqId;
    /**
     * 规格名称
     */
    @TableField("specs_name")
    private String specsName;
    /**
     * 销售价格
     */
    @TableField("sell_price")
    private double sellPrice;
    /**
     * 市场价格
     */
    @TableField("market_price")
    private double marketPrice;
    /**
     * 库存数量
     */
    @TableField("inventory_amount")
    private int inventoryAmount;
    /**
     * 预警数量
     */
    @TableField("warn_amount")
    private int warnAmount;
    /**
     * 排序
     */
    @TableField("order_no")
    private int orderNo;
}
