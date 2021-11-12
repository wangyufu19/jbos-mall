package com.mall.member.application.service;


import com.mall.member.infrastructure.repository.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * AccountRepo
 * @author youfu.wang
 * @date 2021-08-19
 */
@Service
public class AccountService {
    @Autowired
    private AccountRepo accountRepo;

    public Map<String,Object> login(Map<String,Object> parameterObject){
        return this.accountRepo.login(parameterObject);
    }

    public void registry(Map<String,Object> parameterObject){
        this.accountRepo.registry(parameterObject);
    }
}
