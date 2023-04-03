package com.mall.business.domain.entity.product;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mall.business.domain.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * ProductPic
 * @author youfu.wang
 * @date 2021-08-19
 */
@Setter
@Getter
@TableName("PM_PRODUCT_PIC")
public class ProductPic extends BaseEntity {
    @TableField("seq_id")
    private String seqId;

    @TableField("product_seq_id")
    private String productSeqId;

    @TableField("pic_seq_id")
    private String picSeqId;
    /**
     * 图片名称
     */
    private String name;
    /**
     * 图片地址
     */
    private String url;
}
