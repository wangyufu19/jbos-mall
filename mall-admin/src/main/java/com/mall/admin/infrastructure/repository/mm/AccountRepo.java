package com.mall.admin.infrastructure.repository.mm;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.mall.admin.domain.entity.mm.Account;
import com.mall.admin.infrastructure.repository.mm.mapper.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * AccountRepo
 *
 * @author youfu.wang
 * @date 2021-08-19
 */
@Component
public class AccountRepo {
    @Autowired
    private AccountMapper accountMapper;

    /**
     * 得到账户
     * @param parameterObject
     * @return map
     */
    public Map<String, Object> getAccount(Map<String, Object> parameterObject) {
        return this.accountMapper.getAccount(parameterObject);
    }

    /**
     * 注册
     * @param parameterObject
     */
    public void registry(Map<String, Object> parameterObject) {
        this.accountMapper.registry(parameterObject);
    }

    /**
     * 更新状态
     * @param account
     */
    public void updateAccountStatus(Account account) {
        UpdateWrapper<Account> updateWrapper = new UpdateWrapper<Account>();
        updateWrapper.eq("account", account.getAccount());
        this.accountMapper.update(account, updateWrapper);
    }
}
