package com.mall.member.infrastructure.repository;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.mall.member.domain.entity.Account;
import com.mall.member.domain.entity.Member;
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

    public Map<String,Object> getAccount(Map<String,Object> parameterObject){
        return this.accountMapper.getAccount(parameterObject);
    }

    public void registry(Map<String,Object> parameterObject){
        this.accountMapper.registry(parameterObject);
    }

    public void updateAccountStatus(Account account){
        UpdateWrapper<Account> updateWrapper=new UpdateWrapper<Account>();
        updateWrapper.eq("account",account.getAccount());
        this.accountMapper.update(account,updateWrapper);
    }
}
