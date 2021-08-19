package com.mall.admin.infrastructure.repository.mapper;
import com.mall.admin.domain.entity.Func;
import com.mall.admin.domain.entity.TreeNode;

import java.util.List;
import java.util.Map;

/**
 * FuncMapper
 * @author youfu.wang
 * @date 2019-01-31
 */
public interface FuncMapper {
    /**
     * 查询所有菜单权限列表
     * @param parameterObject
     * @return
     */
    public List<Func> getAllFuncList(Map<String, Object> parameterObject);

    /**
     * 查询用户菜单权限列表
     * @param parameterObject
     * @return
     */
    public List<Func> getUserFuncList(Map<String, Object> parameterObject);
    /**
     * 查询功能树
     * @param parameterObject
     * @return
     */
    public List<TreeNode> getFuncChildrenNode(Map<String, Object> parameterObject);

    /**
     * 查询功能列表
     * @param parameterObject
     * @return
     */
    public List<Func> getFuncList(Map<String, Object> parameterObject);
    /**
     * 新增功能
     * @param parameterObject
     */
    public void insertFunc(Map<String, Object> parameterObject);

    /**
     * 修改功能
     * @param parameterObject
     */
    public void updateFunc(Map<String, Object> parameterObject);

    /**
     * 删除功能
     * @param id
     */
    public void deleteFunc(String id);
}
