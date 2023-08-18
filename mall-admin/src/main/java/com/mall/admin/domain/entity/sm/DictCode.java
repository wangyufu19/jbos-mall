package com.mall.admin.domain.entity.sm;

import lombok.Getter;
import lombok.Setter;

/**
 * DictCode
 * @author youfu.wang
 * @date 2020-07-22
 */
@Setter
@Getter
public class DictCode {
    /**
     * 字典类型Id
     */
    private String typeId;
    /**
     * 字典Id
     */
    private String dictId;
    /**
     * 字典名称
     */
    private String dictName;
    /**
     * 排序
     */
    private int orderNo;
}
