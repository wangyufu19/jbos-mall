package com.mall.admin.infrastructure.repository.sm.mapper;



import com.mall.admin.domain.entity.sm.UserToken;

import java.util.Map;

/**
 * UserTokenMapper
 * @author youfu.wang
 */

public interface UserTokenMapper {
    /**
     * 根据ID查询用户Token
     * @param parameterObject
     * @return
     */
    public UserToken getUserTokenByUserId(Map<String, Object> parameterObject);
    /**
     * 根据accessToken查询用户Token
     * @param parameterObject
     * @return
     */
    public UserToken getUserTokenByAccessToken(Map<String, Object> parameterObject);
    /**
     * 添加用户Token
     * @param userToken
     * @return
     */
    public void addUserToken(UserToken userToken);
    /**
     * 更新用户Token
     * @param userToken
     * @return
     */
    public void updateUserToken(UserToken userToken);

    public void updateUserInfo(Map<String, Object> parameterObject);
}