package com.mall.admin.infrastructure.repository.wf;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.admin.domain.entity.wf.ProcessDeployment;
import com.mall.admin.infrastructure.repository.wf.mapper.ProcessDeploymentMapper;
import com.mall.common.page.PageParam;
import com.mall.common.page.Paging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * ProcessDefRepo
 * @author youfu.wang
 * @date 2023/4/6
 **/
@Component
public class ProcessDeploymentRepo extends ServiceImpl<ProcessDeploymentMapper, ProcessDeployment> {
    @Autowired
    private ProcessDeploymentMapper processDeploymentMapper;
    @Paging
    public List<ProcessDeployment> getProcessDeploymentList(PageParam pageParam, Map<String, Object> parameterObject){
        return processDeploymentMapper.getProcessDeploymentList(parameterObject);
    }

}
