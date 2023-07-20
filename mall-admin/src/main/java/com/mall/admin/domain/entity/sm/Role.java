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
    public static final String ROLE_PROCESS_STARTER="ROLE_PROCESS_STARTER";
    public static final String ROLE_PROCESS_STARTER_DESC="发起人";
    public static final String ROLE_DEP_LEADER="ROLE_DEP_LEADER";
    public static final String ROLE_DEP_LEADER_DESC="部门领导";
    public static final String ROLE_CHARGE_LEADER="ROLE_CHARGE_LEADER";
    public static final String ROLE_CHARGE_LEADER_DESC="分管领导";
    public static final String ROLE_REPO_ADMIN="ROLE_REPO_ADMIN";
    public static final String ROLE_REPO_ADMIN_DESC="仓库管理员";
    public static final String ROLE_IM_ADMIN="ROLE_IM_ADMIN";
    public static final String ROLE_IM_ADMIN_DESC="综合部负责人";

    public static String ROOTROLE_ID="0";
    private String roleCode;
    private String roleName;
    private String roleType;
}
