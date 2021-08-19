package com.mall.admin.infrastructure.repository.mapper;
import com.mall.admin.domain.entity.*;

import java.util.List;
import java.util.Map;

/**
 * DepMapper
 * @author youfu.wang
 * @date 2020-06-24
 */
public interface RoleMapper {
    /**
     * 查询下级角色
     * @param parentId
     * @return
     */
    public List<TreeNode> getRoleChildrenNode(String parentId);
    /**
     * 查询角色数据列表
     * @param parameterObject
     * @return
     */
    public List<Role> getRoleList(Map<String, Object> parameterObject);
    /**
     * 查询角色功能数据
     * @param roleId
     * @return
     */
    public List<String> getRoleFuncs(String roleId);
    /**
     * 新增角色
     * @param parameterObject
     */
    public void insertRole(Map<String, Object> parameterObject);

    /**
     * 修改角色
     * @param parameterObject
     */
    public void updateRole(Map<String, Object> parameterObject);

    /**
     * 删除角色
     * @param id
     */
    public void deleteRole(String id);

    /**
     * 删除角色功能
     * @param roleId
     */
    public void deleteRoleFunc(String roleId);

    /**
     * 新增角色功能
     * @param roleFuncs
     */
    public void insertRoleFunc(List<RoleFunc> roleFuncs);
    /**
     * 查询角色用户列表
     * @param parameterObject
     * @return
     */
    public List<Emp> getRoleEmpList(Map<String, Object> parameterObject);
    /**
     * 查询选择角色用户列表
     * @param parameterObject
     * @return
     */
    public List<Emp> getSelectRoleEmpList(Map<String, Object> parameterObject);

    /**
     * 新增角色用户
     * @param roleUsers
     */
    public void insertRoleUser(List<UserRole> roleUsers);
}
