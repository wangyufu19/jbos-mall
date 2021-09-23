package com.mall.admin.domain.entity.im;

import com.mall.admin.domain.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * MaterialList
 * @author youfu.wang
 * @date 2020-06-24
 */
@Setter
@Getter
public class MaterialList extends BaseEntity {
    private String bizId;
    private String bizNo;
    private String bizType;
    private String materialName;
    private double amount;
    private double price;

}
