package com.mall.admin.infrastructure.repository.mm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.admin.domain.entity.mm.Account;

import java.util.Map;

/**
 * AccountMapper
 *
 * @author youfu.wang
 * @date 2021-08-19
 */
public interface AccountMapper extends BaseMapper<Account> {
    /**
     * 得到账户
     * @param parameterObject
     * @return 账户
     */
    Map<String, Object> getAccount(Map<String, Object> parameterObject);

    /**
     * 注册
     * @param parameterObject
     */
    void registry(Map<String, Object> parameterObject);
}
