package com.mall.admin.infrastructure.repository.comm.mapper;

import com.mall.admin.domain.entity.comm.Id;

import java.util.Map;

/**
 * IdMapper
 * @author youfu.wang
 * @date 2021-08-19
 */
public interface IdMapper {

    public Id getId(String bizType);

    public void updateId(Map<String,Object> parameterObject);
}
