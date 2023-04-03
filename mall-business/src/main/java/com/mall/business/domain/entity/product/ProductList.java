package com.mall.business.domain.entity.product;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mall.business.domain.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * ProductList
 * @author youfu.wang
 * @date 2021-08-19
 */
@Setter
@Getter
@TableName("PM_PRODUCT_LIST")
public class ProductList extends BaseEntity {
    @TableField("seq_id")
    private String seqId;

    @TableField("product_seq_id")
    private String productSeqId;
    /**
     * 商品编号
     */
    private String productCode;
    /**
     * 商品名称
     */
    private String productName;
    /**
     * 商品标题
     */
    private String title;
    /**
     * 价格范围
     */
    @TableField("price_scope")
    private String priceScope;
    /**
     * 累计销量
     */
    @TableField("amount")
    private int amount;
    /**
     * 库存数量
     */
    @TableField("inventory")
    private int inventory;
    /**
     * 商品状态
     */
    private String status;
}
