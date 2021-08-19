package com.mall.admin.domain.entity;

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
    private String typeId;
    private String dictId;
    private String dictName;
    private int orderNo;
}
