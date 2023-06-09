package com.mall.admin.application.service.sm;
import com.mall.admin.domain.entity.sm.Emp;
import com.mall.admin.domain.entity.sm.Role;
import com.mall.common.page.PageParam;
import com.mall.admin.domain.entity.comm.TreeNode;
import com.mall.admin.infrastructure.repository.sm.RoleMgrRepository;
import com.mall.common.response.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public ResponseResult getRoleList(PageParam pageParam, Map<String, Object> parameterObject) {
        List<Role> roleList=roleMgrRepository.getRoleList(pageParam,parameterObject);
        return ResponseResult.ok().isPage(true).data(roleList);
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
    public ResponseResult getRoleEmpList(PageParam pageParam,Map<String, Object> parameterObject){
        List<Emp> empList=roleMgrRepository.getRoleEmpList(pageParam,parameterObject);
        return ResponseResult.ok().isPage(true).data(empList);
    }
    /**
     * 查询选择角色用户列表
     * @param parameterObject
     * @return
     */
    public ResponseResult getSelectRoleEmpList(PageParam pageParam,Map<String, Object> parameterObject){
        List<Emp> empList=roleMgrRepository.getSelectRoleEmpList(pageParam,parameterObject);
        return ResponseResult.ok().isPage(true).data(empList);
    }
    /**
     * 新增角色用户
     * @param parameterObject
     */
    public void addRoleUser(Map<String, Object> parameterObject){
        roleMgrRepository.addRoleUser(parameterObject);
    }
    /**
     * 删除角色用户
     * @param parameterObject
     */
    public void deleteRoleUser(Map<String, Object> parameterObject){
        roleMgrRepository.deleteRoleUser(parameterObject);
    }
}
