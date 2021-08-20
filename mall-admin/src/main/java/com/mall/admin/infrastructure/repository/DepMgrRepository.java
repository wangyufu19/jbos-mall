package com.mall.admin.infrastructure.repository;

import com.mall.admin.domain.entity.Dep;
import com.mall.admin.infrastructure.repository.mapper.DepMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;

/**
 * DepMgrRepository
 * @author youfu.wang
 * @date 2020-06-24
 */
@Component
public class DepMgrRepository {
    @Autowired
    private DepMapper depMapper;
    /**
     * 查询机构下级部门列表
     * @param parameterObject
     * @return
     */
    public List<Dep> getDepList(Map<String, Object> parameterObject){
        List<Dep> deps=depMapper.getDepList(parameterObject);
        return deps;
    }
    /**
     * 查询部门员工数量
     * @param depId
     * @return
     */
    public int getDepEmpCount(String depId){
        int datacount=depMapper.getDepEmpCount(depId);
        return datacount;
    }
    /**
     * 新增部门
     * @param parameterObject
     */
    public void addDep(Map<String, Object> parameterObject){
        depMapper.addDep(parameterObject);
    }
    /**
     * 修改部门
     * @param parameterObject
     */
    public void updateDep(Map<String, Object> parameterObject){
        depMapper.updateDep(parameterObject);
    }
    /**
     * 删除部门
     * @param id
     */
    public void deleteDep(String id){
        depMapper.deleteDep(id);
    }
}
