package com.mall.admin.domain.entity.comm;

import com.alibaba.excel.annotation.ExcelIgnore;
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
    @ExcelIgnore
    private String id;
    /**
     * isValid
     */
    @TableField(exist = false)
    @ExcelIgnore
    private int isValid;
    /**
     * createUserId
     */
    @TableField("create_user_id")
    @ExcelIgnore
    private String createUserId;
    /**
     * createTime
     */
    @TableField("create_time")
    @ExcelIgnore
    private String createTime;
    /**
     * updateUserId
     */
    @TableField("update_user_id")
    @ExcelIgnore
    private String updateUserId;
    /**
     * updateTime
     */
    @TableField("update_time")
    @ExcelIgnore
    private String updateTime;

}
