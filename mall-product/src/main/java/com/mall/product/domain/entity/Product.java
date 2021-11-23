package com.mall.product.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Product
 * @author youfu.wang
 * @date 2021-08-19
 */
@Setter
@Getter
@TableName("PM_PRODUCT")
public class Product extends BaseEntity{
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
     * 商品标题
     */
    private String title;
    /**
     * 商品状态
     */
    private String status;
}
