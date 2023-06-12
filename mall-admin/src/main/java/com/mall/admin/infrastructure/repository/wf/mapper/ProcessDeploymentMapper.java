package com.mall.admin.infrastructure.repository.wf.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.admin.domain.entity.wf.ProcessDeployment;

import java.util.List;
import java.util.Map;

/**
 * ProcessInstMapper
 * @author youfu.wang
 * @date 2023/4/6
 **/
public interface ProcessDeploymentMapper extends BaseMapper<ProcessDeployment> {

    public List<ProcessDeployment> getProcessDeploymentList(Map<String, Object> parameterObject);

}
