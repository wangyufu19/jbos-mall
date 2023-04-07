package com.mall.admin.domain.entity.sm;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * DictType
 * @author youfu.wang
 * @date 2020-07-22
 */
@Setter
@Getter
@TableName("JBOS_DICT_TYPE")
public class DictType {
    @TableField("TYPEID")
    private String typeId;
    @TableField("TYPENAME")
    private String typeName;
}
