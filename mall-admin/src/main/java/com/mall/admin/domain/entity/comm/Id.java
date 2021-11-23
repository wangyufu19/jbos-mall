package com.mall.admin.domain.entity.comm;

import lombok.Getter;
import lombok.Setter;

/**
 * Id
 * @author youfu.wang
 * @date 2021-08-19
 */
@Setter
@Getter
public class Id {
    private int id;
    private int currentId=0;//当前ID
    private int maxId=0;//最大ID
    private int step=0;//号段步长
    private String bizType;//业务类型
    private int version;//版本号
}
