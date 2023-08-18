package com.mall.admin.infrastructure.repository.sm.mapper;

import com.mall.admin.domain.entity.sm.Dep;

import java.util.List;
import java.util.Map;

/**
 * DepMapper
 *
 * @author youfu.wang
 * @date 2020-06-24
 */
public interface DepMapper {
    /**
     * 查询机构下级部门列表
     *
     * @param parameterObject
     * @return list
     */
    List<Dep> getDepList(Map<String, Object> parameterObject);

    /**
     * 查询部门员工数量
     *
     * @param depId
     * @return in
     */
    int getDepEmpCount(String depId);

    /**
     * 新增部门
     *
     * @param parameterObject
     */
    void addDep(Map<String, Object> parameterObject);

    /**
     * 修改部门
     *
     * @param parameterObject
     */
    void updateDep(Map<String, Object> parameterObject);

    /**
     * 删除部门
     *
     * @param id
     */
    void deleteDep(String id);
}
