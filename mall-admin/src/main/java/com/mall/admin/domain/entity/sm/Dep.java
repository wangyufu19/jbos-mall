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
    /**
     * id
     */
    private String id;
    /**
     * parentId
     */
    private String parentId;
    /**
     * 部门编码
     */
    private String depCode;
    /**
     * 部门名称
     */
    private String depName;
    /**
     * 部门级别
     */
    private String depLevel;
    /**
     * 所属机构id
     */
    private String orgId;
    /**
     * 所属机构
     */
    private String orgName;
    /**
     * 部门负责人
     */
    private String depCharge;
    /**
     * 部门分管领导
     */
    private String depDirector;
    /**
     * 部门负责人
     */
    private String depChargeName;
    /**
     * 部门分管领导
     */
    private String depDirectorName;
}
