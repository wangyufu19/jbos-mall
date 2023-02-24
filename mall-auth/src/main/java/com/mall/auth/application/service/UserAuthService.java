package com.mall.auth.application.service;

import com.mall.auth.infrastructure.repository.UserAuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户认证服务类
 */
@Service
public class UserAuthService {
    @Autowired
    private UserAuthRepository userAuthRepository;


    /**
     * 用户认证
     * @param username
     * @return
     */
    public Map<String,Object> login(String username){
        return userAuthRepository.login(username);
    }

    /**
     * 得到认证用户角色
     * @param username
     * @return
     */
    public List<HashMap> getAuthUserRole(String username){
        return userAuthRepository.getAuthUserRole(username);
    }
    /**
     * 得到用户认证信息
     * @param id
     * @return
     */
    public Map<String,Object> getUserAuthInfoById(String id){
        return userAuthRepository.getUserAuthInfoById(id);
    }
}
