package com.mall.gateway.application.service.auth;

import com.mall.gateway.infrastructure.repository.mapper.auth.UserAuthMapper;
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
    /**
     * userAuthMapper
     */
    @Autowired
    private UserAuthMapper userAuthMapper;


    /**
     * 用户认证
     *
     * @param username
     * @return
     */
    public Map<String, Object> login(String username) {
        return userAuthMapper.login(username);
    }
    /**
     * 得到用户认证信息
     *
     * @param username
     * @return
     */
    public Map<String, Object> getUserAuthInfo(String username) {
        return userAuthMapper.getUserAuthInfo(username);
    }
    /**
     * 得到认证用户角色
     *
     * @param username
     * @return
     */
    public List<String> getAuthUserRole(String username) {
        return userAuthMapper.getAuthUserRole(username);
    }
    /**
     * 查询用户权限
     *
     * @param username
     * @return
     */
    public List<String> getAuthUserPermission(String username) {
        return userAuthMapper.getAuthUserPermission(username);
    }

}
