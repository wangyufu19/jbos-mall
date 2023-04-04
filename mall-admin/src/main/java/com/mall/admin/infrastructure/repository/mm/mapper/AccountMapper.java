package com.mall.admin.infrastructure.repository.mm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.admin.domain.entity.mm.Account;

import java.util.Map;

/**
 * AccountMapper
 * @author youfu.wang
 * @date 2021-08-19
 */
public interface AccountMapper extends BaseMapper<Account> {

    public Map<String,Object> getAccount(Map<String, Object> parameterObject);

    public void registry(Map<String, Object> parameterObject);
}
