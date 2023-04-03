package com.mall.business.domain.entity.product;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mall.business.domain.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * Category
 * @author youfu.wang
 * @date 2021-08-19
 */
@Setter
@Getter
@TableName("PM_PRODUCT_CATEGORY")
public class Category extends BaseEntity {
    @TableField("seq_id")
    private String seqId;
    @TableField("code")
    private String code;
    @TableField("code_tree")
    private String codeTree;
    @TableField("parent_code")
    private String parentCode;
    @TableField("name")
    private String name;
    @TableField("order_no")
    private int orderNo;
    @TableField("keywords")
    private String keywords;
    @TableField("remark")
    private String remark;

}
