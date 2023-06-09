package com.mall.admin.infrastructure.repository.wf.mapper;
import com.mall.admin.domain.entity.wf.ProcessDeployment;

import java.util.List;
import java.util.Map;

/**
 * ProcessInstMapper
 * @author youfu.wang
 * @date 2023/4/6
 **/
public interface ProcessDeploymentMapper {

    public List<ProcessDeployment> getProcessDeploymentList(Map<String, Object> parameterObject);

    public void addProcessDeployment(ProcessDeployment processDeployment);

    public void deleteProcessDeployment(ProcessDeployment processDeployment);
}
