package com.mall.admin.application.service.sm;

import com.mall.admin.domain.entity.sm.Emp;
import com.mall.common.page.PageParam;
import com.mall.admin.infrastructure.repository.sm.EmpMgrRepository;
import com.mall.common.response.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * EmpMgrService
 *
 * @author youfu.wang
 * @date 2020-06-24
 */
@Service
public class EmpMgrService {
    /**
     * EmpMgrRepository
     */
    @Autowired
    private EmpMgrRepository empMgrRepository;

    /**
     * 查询机构员工数据
     * @param pageParam
     * @param parameterObject
     * @return list
     */
    public ResponseResult getEmpList(PageParam pageParam, Map<String, Object> parameterObject) {
        List<Emp> empList = empMgrRepository.getEmpList(pageParam, parameterObject);
        return ResponseResult.ok().isPage(true).setData(empList);
    }

    /**
     * 新增人员信息
     * @param parameterObject
     */
    public void addEmp(Map<String, Object> parameterObject) {
        empMgrRepository.addEmp(parameterObject);
    }

    /**
     * 更新人员信息
     *
     * @param parameterObject
     */
    public void updateEmp(Map<String, Object> parameterObject) {
        empMgrRepository.updateEmp(parameterObject);
    }

    /**
     * 删除人员信息
     *
     * @param parameterObject
     */
    public void deleteEmp(Map<String, Object> parameterObject) {
        empMgrRepository.deleteEmp(parameterObject);
    }
}
