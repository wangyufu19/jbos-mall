package com.mall.admin.domain.entity;

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
    private int isValid;
    private String createUserId;
    private String createTime;
    private String updateUserId;
    private String updateTime;

}
