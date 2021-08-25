package com.mall.gateway.infrastructure.repository;


import com.mall.gateway.infrastructure.repository.mapper.UserAuthMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserAuthRepository {
    @Autowired
    private UserAuthMapper userAuthMapper;
    /**
     * 用户认证
     * @param username
     * @return
     */
    public Map<String,Object> login(String username){
        Map<String, Object> parameterObject=new HashMap<String, Object>();
        parameterObject.put("username",username);
        return userAuthMapper.login(parameterObject);
    }

    /**
     * 得到认证用户角色
     * @param username
     * @return
     */
    public List<HashMap> getAuthUserRole(String username){
        Map<String, Object> parameterObject=new HashMap<String, Object>();
        parameterObject.put("username",username);
        return userAuthMapper.getAuthUserRole(parameterObject);
    }
}
