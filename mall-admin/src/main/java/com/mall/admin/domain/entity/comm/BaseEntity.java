package com.mall.admin.domain.entity.comm;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * BaseEntity
 *
 * @author youfu.wang
 * @date 2019-01-31
 */
@Setter
@Getter
public class BaseEntity implements Serializable {
    /**
     * id
     */
    private String id;
    /**
     * isValid
     */
    @TableField(exist = false)
    private int isValid;
    /**
     * createUserId
     */
    @TableField(exist = false)
    private String createUserId;
    /**
     * createTime
     */
    @TableField(exist = false)
    private String createTime;
    /**
     * updateUserId
     */
    @TableField(exist = false)
    private String updateUserId;
    /**
     * updateTime
     */
    @TableField(exist = false)
    private String updateTime;

}
