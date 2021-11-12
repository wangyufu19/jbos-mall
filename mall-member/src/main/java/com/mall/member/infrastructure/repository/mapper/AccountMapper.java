package com.mall.member.infrastructure.repository.mapper;

import java.util.Map;

/**
 * AccountMapper
 * @author youfu.wang
 * @date 2021-08-19
 */
public interface AccountMapper {

    public Map<String,Object> login(Map<String,Object> parameterObject);

    public void registry(Map<String,Object> parameterObject);
}
