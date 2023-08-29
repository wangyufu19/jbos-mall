package com.mall.admin.infrastructure.repository.sm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.admin.domain.entity.sm.Emp;

import java.util.List;
import java.util.Map;

/**
 * EmpMapper
 *
 * @author youfu.wang
 * @date 2020-06-24
 */
public interface EmpMapper extends BaseMapper<Emp> {
    /**
     * 查询机构员工数据列表
     *
     * @param parameterObject
     * @return list
     */
    List<Emp> getEmpList(Map<String, Object> parameterObject);

    /**
     * 新增人员信息
     *
     * @param parameterObject
     */
    void addEmp(Map<String, Object> parameterObject);

    /**
     * 新增人员用户信息
     *
     * @param parameterObject
     */
    void addEmpUser(Map<String, Object> parameterObject);

    /**
     * 更新人员信息
     *
     * @param parameterObject
     */
    void updateEmp(Map<String, Object> parameterObject);

    /**
     * 更新人员用户信息
     *
     * @param parameterObject
     */
    void updateEmpUser(Map<String, Object> parameterObject);

    /**
     * 删除人员信息
     *
     * @param id
     */
    void deleteEmp(String id);

    /**
     * 删除人员用户信息
     *
     * @param loginName
     */
    void deleteEmpUser(String loginName);
}
