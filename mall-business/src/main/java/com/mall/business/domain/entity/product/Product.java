package com.mall.business.domain.entity.product;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mall.business.domain.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * Product
 * @author youfu.wang
 * @date 2021-08-19
 */
@Setter
@Getter
@TableName("PM_PRODUCT")
public class Product extends BaseEntity {
    public final static String PRODUCT_STATUS_DRAFT="10";//草稿中
    public final static String PRODUCT_STATUS_SHELF="20";//已上架
    public final static String PRODUCT_STATUS_OFF_SHELF="50";//已下架
    public final static String PRODUCT_STATUS_DELETED="99";//已删除
    @TableId("seq_id")
    private String seqId;
    /**
     * 商品分类
     */
    private String categoryCode;
    /**
     * 商品分类
     */
    private String categoryName;
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
