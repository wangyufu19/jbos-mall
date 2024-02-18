package com.mall.gateway.infrastructure.repository.mapper.auth;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * UserMapper
 *
 * @author youfu.wang
 * @date 2019-01-31
 */
public interface UserAuthMapper {
    /**
     * 用户认证
     *
     * @param username
     * @return map
     */
    Map<String, Object> login(String username);

    /**
     * 得到用户认证信息
     *
     * @param username
     * @return map
     */
    Map<String, Object> getUserAuthInfo(String username);

    /**
     * 得到认证用户角色
     *
     * @param username
     * @return list
     */
    List<String> getAuthUserRole(String username);
    /**
     * getAuthUserPermission
     *
     * @param username
     * @return list
     */
    List<String> getAuthUserPermission(String username);
}
