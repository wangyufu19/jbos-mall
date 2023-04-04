package com.mall.admin.application.service.sm;


import com.mall.admin.domain.entity.sm.Dep;
import com.mall.admin.infrastructure.repository.sm.DepMgrRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * DepMgrService
 * @author youfu.wang
 * @date 2020-06-24
 */
@Service
public class DepMgrService  {
    @Autowired
    private DepMgrRepository depMgrRepository;
    /**
     * 查询机构下级部门列表
     * @param parameterObject
     * @return
     */
    public List<Dep> getDepList(Map<String, Object> parameterObject){
        return depMgrRepository.getDepList(parameterObject);
    }
    /**
     * 查询部门员工数量
     * @param depId
     * @return
     */
    public int getDepEmpCount(String depId){
        return depMgrRepository.getDepEmpCount(depId);
    }
    /**
     * 新增部门
     * @param parameterObject
     */
    public void addDep(Map<String, Object> parameterObject){
        depMgrRepository.addDep(parameterObject);
    }
    /**
     * 修改部门
     * @param parameterObject
     */
    public void updateDep(Map<String, Object> parameterObject){
        depMgrRepository.updateDep(parameterObject);
    }
    /**
     * 删除部门
     * @param id
     */
    public void deleteDep(String id){
        depMgrRepository.deleteDep(id);
    }
}
