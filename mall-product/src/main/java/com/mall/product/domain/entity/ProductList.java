package com.mall.product.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * ProductList
 * @author youfu.wang
 * @date 2021-08-19
 */
@Setter
@Getter
@TableName("PM_PRODUCT_LIST")
public class ProductList {
    @TableField("seq_id")
    private String seqId;
    /**
     * 商品编号
     */
    private String productCode;
    /**
     * 商品名称
     */
    private String productName;
    /**
     * 价格范围
     */
    private String priceScope;
    /**
     * 累计销量
     */
    private int amount;
    /**
     * 库存数量
     */
    private int inventory;
    /**
     * 是否有效
     */
    @TableField("is_valid")
    private int isValid;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 更新时间
     */
    @TableField("update_time")
    private Date updateTime;
}
