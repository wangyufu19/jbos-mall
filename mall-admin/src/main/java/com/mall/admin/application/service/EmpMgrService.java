package com.mall.admin.application.service;

import com.mall.admin.domain.entity.Emp;
import com.mall.admin.infrastructure.repository.EmpMgrRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

/**
 * EmpMgrService
 * @author youfu.wang
 * @date 2020-06-24
 */
@Service
public class EmpMgrService {
    @Autowired
    private EmpMgrRepository empMgrRepository;
    /**
     * 查询机构员工数据
     * @param parameterObject
     * @return
     */
    public List<Emp> getEmpList(Map<String, Object> parameterObject){
        return empMgrRepository.getEmpList(parameterObject);
    }
    /**
     * 新增人员信息
     */
    public void addEmp(Map<String, Object> parameterObject){
        empMgrRepository.addEmp(parameterObject);
    }
    /**
     * 更新人员信息
     * @param parameterObject
     */
    public void updateEmp(Map<String, Object> parameterObject){
        empMgrRepository.updateEmp(parameterObject);
    }

    /**
     * 删除人员信息
     * @param parameterObject
     */
    public void deleteEmp(Map<String, Object> parameterObject){
        empMgrRepository.deleteEmp(parameterObject);
    }
}
