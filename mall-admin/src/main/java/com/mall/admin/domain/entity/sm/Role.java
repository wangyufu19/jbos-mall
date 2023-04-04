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
    public static String ROOTROLE_ID="0";
    private String roleCode;
    private String roleName;
    private String roleType;
}
