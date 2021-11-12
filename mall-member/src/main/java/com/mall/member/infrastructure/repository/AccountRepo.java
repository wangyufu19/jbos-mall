package com.mall.member.infrastructure.repository;

import com.mall.member.infrastructure.repository.mapper.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * AccountRepo
 * @author youfu.wang
 * @date 2021-08-19
 */
@Component
public class AccountRepo {
    @Autowired
    private AccountMapper accountMapper;

    public Map<String,Object> login(Map<String,Object> parameterObject){
        return this.accountMapper.login(parameterObject);
    }

    public void registry(Map<String,Object> parameterObject){
        this.accountMapper.registry(parameterObject);
    }
}
