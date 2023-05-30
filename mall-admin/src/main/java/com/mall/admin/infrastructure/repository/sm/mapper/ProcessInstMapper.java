package com.mall.admin.infrastructure.repository.sm.mapper;
import com.mall.admin.domain.entity.sm.ProcessInst;

import java.util.List;
import java.util.Map;

/**
 * ProcessInstMapper
 * @author youfu.wang
 * @date 2023/4/6
 **/
public interface ProcessInstMapper {

    public List<ProcessInst> getProcessInstList(Map<String,String> parameterObject);

    public void addProcessInst(ProcessInst processInst);

    public void updateProcState(ProcessInst processInst);
}
