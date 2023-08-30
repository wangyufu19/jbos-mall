package com.mall.admin.infrastructure.repository.sm;

import com.mall.common.page.PageParam;
import com.mall.common.page.Paging;
import com.mall.admin.domain.entity.sm.Emp;
import com.mall.admin.infrastructure.repository.sm.mapper.EmpMapper;
import com.mall.admin.infrastructure.repository.sm.mapper.UserMapper;
import com.mall.common.utils.DateUtils;
import com.mall.common.utils.StringUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * EmpMgrRepository
 *
 * @author youfu.wang
 * @date 2020-06-24
 */
@Component
public class EmpMgrRepository {
    @Autowired
    private EmpMapper empMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 查询机构员工数据
     *
     * @param pageParam
     * @param parameterObject
     * @return list
     */
    @Paging
    public List<Emp> getEmpList(PageParam pageParam, Map<String, Object> parameterObject) {
        List<Emp> empList = empMapper.getEmpList(parameterObject);
        return empList;
    }

    /**
     * 查询机构员工数据
     *
     * @param page
     * @param pageSize
     * @return
     */
    public List<Emp> getEmpList(int page, int pageSize) {
        List<Emp> empList = new ArrayList<>();
        Object[] args = new Object[2];
        args[0] = (page - 1) * pageSize;
        args[1] = pageSize;
        empList = jdbcTemplate.query(
                "select badge,empName from user limit ?,?",
                new BeanPropertyRowMapper<>(Emp.class),
                (page - 1) * pageSize,
                pageSize);
        return empList;
    }

    /**
     * 新增人员信息
     *
     * @param parameterObject
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public void addEmp(Map<String, Object> parameterObject) {
        parameterObject.put("id", UUID.randomUUID().toString());
        parameterObject.put("createTime", DateUtils.format(DateUtils.getCurrentDate(), DateUtils.YYYYMMDDHIMMSS));
        empMapper.addEmp(parameterObject);
        String salt = RandomStringUtils.randomAlphanumeric(20);
        parameterObject.put("salt", salt);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        parameterObject.put("username", parameterObject.get("badge"));
        parameterObject.put("nickname", parameterObject.get("empName"));
        parameterObject.put("password", passwordEncoder.encode("123456"));
        userMapper.addUserInfo(parameterObject);
        parameterObject.put("userId", parameterObject.get("id"));
        userMapper.addUserDefaultRole(parameterObject);
    }

    /**
     * 更新人员信息
     *
     * @param parameterObject
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public void updateEmp(Map<String, Object> parameterObject) {
        empMapper.updateEmp(parameterObject);
        empMapper.updateEmpUser(parameterObject);
    }

    /**
     * 删除人员信息
     *
     * @param parameterObject
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public void deleteEmp(Map<String, Object> parameterObject) {
        String id = StringUtils.replaceNull(parameterObject.get("id"));
        String loginName = StringUtils.replaceNull(parameterObject.get("badge"));
        empMapper.deleteEmp(id);
        empMapper.deleteEmpUser(loginName);
    }
}
