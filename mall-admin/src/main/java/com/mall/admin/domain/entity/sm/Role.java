package com.mall.admin.domain.entity.sm;

import com.mall.admin.domain.entity.comm.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * 角色实体
 */
@Setter
@Getter
public class Role extends BaseEntity {
    public static final String ROLE_DEP_LEADER="ROLE_DEP_LEADER";
    public static final String ROLE_CHARGE_LEADER="ROLE_CHARGE_LEADER";
    public static final String ROLE_REPO_ADMIN="ROLE_REPO_ADMIN";

    public static String ROOTROLE_ID="0";
    private String roleCode;
    private String roleName;
    private String roleType;
}
