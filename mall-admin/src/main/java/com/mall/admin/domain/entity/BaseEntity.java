package com.mall.admin.domain.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * BaseEntity
 * @author youfu.wang
 * @date 2019-01-31
 */
@Setter
@Getter
public class BaseEntity implements Serializable{
    private String id;
    @TableField(exist=false)
    private int isValid;
    @TableField(exist=false)
    private String createUserId;
    @TableField(exist=false)
    private String createTime;
    @TableField(exist=false)
    private String updateUserId;
    @TableField(exist=false)
    private String updateTime;

}
