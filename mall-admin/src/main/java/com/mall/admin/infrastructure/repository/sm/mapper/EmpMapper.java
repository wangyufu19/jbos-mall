package com.mall.admin.infrastructure.repository.sm.mapper;

import com.mall.admin.domain.entity.sm.Emp;

import java.util.List;
import java.util.Map;

/**
 * EmpMapper
 * @author youfu.wang
 * @date 2020-06-24
 */
public interface EmpMapper {
    /**
     * 查询机构员工数据列表
     * @param parameterObject
     * @return
     */
    public List<Emp> getEmpList(Map<String, Object> parameterObject);

    /**
     * 新增人员信息
     */
    public void addEmp(Map<String, Object> parameterObject);

    /**
     * 新增人员用户信息
     * @param parameterObject
     */
    public void addEmpUser(Map<String, Object> parameterObject);

    /**
     * 更新人员信息
     * @param parameterObject
     */
    public void updateEmp(Map<String, Object> parameterObject);

    /**
     * 更新人员用户信息
     * @param parameterObject
     */
    public void updateEmpUser(Map<String, Object> parameterObject);

    /**
     * 删除人员信息
     * @param id
     */
    public void deleteEmp(String id);

    /**
     * 删除人员用户信息
     * @param loginName
     */
    public void deleteEmpUser(String loginName);
}
