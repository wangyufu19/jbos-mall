package com.mall.admin.application.service;
import com.mall.admin.domain.entity.*;
import com.mall.admin.infrastructure.repository.RoleMgrRepository;
import com.mall.admin.infrastructure.repository.mapper.RoleMapper;
import com.mall.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 角色管理实现类
 * @author youfu.wang
 * @date 2020-07-10
 */
@Service
public class RoleMgrService {
    @Autowired
    private RoleMgrRepository roleMgrRepository;
    /**
     * 查询下级角色
     * @param parentId
     * @return
     */
    public List<TreeNode> getRoleChildrenNode(String parentId){
        return roleMgrRepository.getRoleChildrenNode(parentId);
    }
    /**
     * 查询角色数据列表
     * @param parameterObject
     * @return
     */
    public List<Role> getRoleList(Map<String, Object> parameterObject) {
        return roleMgrRepository.getRoleList(parameterObject);
    }

    /**
     * 查询角色功能数据
     * @param roleId
     * @return
     */
    public List<String> getRoleFuncs(String roleId){
        return roleMgrRepository.getRoleFuncs(roleId);
    }
    /**
     * 保存角色功能
     * @param parameterObject
     */
    public void saveRoleFuncs(Map<String, Object> parameterObject){
        roleMgrRepository.saveRoleFuncs(parameterObject);
    }
    /**
     * 新增角色
     * @param parameterObject
     */
    public void insertRole(Map<String, Object> parameterObject) {
        roleMgrRepository.insertRole(parameterObject);
    }

    /**
     * 修改角色
     * @param parameterObject
     */
    public void updateRole(Map<String, Object> parameterObject) {
        roleMgrRepository.updateRole(parameterObject);
    }
    /**
     * 删除角色
     * @param id
     */
    public void deleteRole(String id) {
        roleMgrRepository.deleteRole(id);
    }
    /**
     * 查询角色用户列表
     * @param parameterObject
     * @return
     */
    public List<Emp> getRoleEmpList(Map<String, Object> parameterObject){
        return roleMgrRepository.getRoleEmpList(parameterObject);
    }
    /**
     * 查询选择角色用户列表
     * @param parameterObject
     * @return
     */
    public List<Emp> getSelectRoleEmpList(Map<String, Object> parameterObject){
        return roleMgrRepository.getSelectRoleEmpList(parameterObject);
    }
    /**
     * 新增角色用户
     * @param parameterObject
     */
    public void addRoleUser(Map<String, Object> parameterObject){
        roleMgrRepository.addRoleUser(parameterObject);
    }
}
