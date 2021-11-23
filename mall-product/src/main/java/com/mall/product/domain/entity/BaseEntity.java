package com.mall.product.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
/**
 * BaseEntity
 * @author youfu.wang
 * @date 2021-08-19
 */
@Setter
@Getter
public class BaseEntity {
    /**
     * 是否有效
     */
    @TableField("is_valid")
    private int isValid;
    /**
     * 创建时间
     */
    @TableField("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 更新时间
     */
    @TableField("update_time")
    private Date updateTime;
}
