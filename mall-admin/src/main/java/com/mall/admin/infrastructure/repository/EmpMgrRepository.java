package com.mall.admin.infrastructure.repository;

import com.mall.admin.domain.entity.Emp;
import com.mall.admin.infrastructure.repository.mapper.EmpMapper;
import com.mall.common.utils.StringUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * EmpMgrRepository
 * @author youfu.wang
 * @date 2020-06-24
 */
@Component
public class EmpMgrRepository {
    @Autowired
    private EmpMapper empMapper;
    /**
     * 查询机构员工数据
     * @param parameterObject
     * @return
     */
    public List<Emp> getEmpList(Map<String, Object> parameterObject){
        List<Emp> emps=empMapper.getEmpList(parameterObject);
        return emps;
    }
    /**
     * 新增人员信息
     */
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
    public void addEmp(Map<String, Object> parameterObject){
        empMapper.addEmp(parameterObject);
        String salt = RandomStringUtils.randomAlphanumeric(20);
        parameterObject.put("salt",salt);
        PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
        parameterObject.put("password",passwordEncoder.encode("123456"));
        empMapper.addEmpUser(parameterObject);
    }
    /**
     * 更新人员信息
     * @param parameterObject
     */
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
    public void updateEmp(Map<String, Object> parameterObject){
        empMapper.updateEmp(parameterObject);
        empMapper.updateEmpUser(parameterObject);
    }

    /**
     * 删除人员信息
     * @param parameterObject
     */
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
    public void deleteEmp(Map<String, Object> parameterObject){
        String id= StringUtils.replaceNull(parameterObject.get("id"));
        String loginName= StringUtils.replaceNull(parameterObject.get("badge"));
        empMapper.deleteEmp(id);
        empMapper.deleteEmpUser(loginName);
    }
}
