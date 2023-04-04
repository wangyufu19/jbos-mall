package com.mall.admin.domain.entity.sm;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Dep
 * @author youfu.wang
 * @date 2020-06-24
 */
@Setter
@Getter
public class Dep implements Serializable {
    private String id;
    private String parentId;
    private String depCode;
    private String depName;
    private String depLevel;
    private String orgId;
    private String orgName;
    private String depCharge;
    private String depDirector;
    private String depChargeName;
    private String depDirectorName;
}
