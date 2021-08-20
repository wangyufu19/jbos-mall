package com.mall.admin.infrastructure.repository;

import com.mall.admin.domain.entity.UserToken;
import com.mall.admin.infrastructure.repository.mapper.UserTokenMapper;
import com.mall.common.utils.TokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * UserTokenRepository
 * @author youfu.wang
 * @date 2020-06-24
 */
@Component
public class UserTokenRepository {
    //12小时后过期
    private final static int EXPIRE = 3600 * 12;
    @Autowired
    private UserTokenMapper userTokenMapper;
    /**
     * 创建用户Token
     * @param userid
     */
    @Transactional(rollbackFor=Exception.class)
    public String createToken(String userid){
        //当前时间
        Date now = new Date();
        //过期时间
        Date expireTime = new Date(now.getTime() + EXPIRE * 1000);
        //查询用户Token数据
        UserToken userToken=getUserTokenByUserId(userid);
        //生成一个Token
        String token = TokenGenerator.generateValue();
        if(null==userToken){
            userToken=new UserToken();
            userToken.setUserId(userid);
            userToken.setToken(token);
            userToken.setExpireTime(expireTime);
            userToken.setUpdateTime(now);
            userTokenMapper.addUserToken(userToken);
            return token;
        }else{
            //更新Token过期时间
            userToken.setUserId(userid);
            userToken.setToken(token);
            userToken.setExpireTime(expireTime);
            userToken.setUpdateTime(now);
            userTokenMapper.updateUserToken(userToken);
            return userToken.getToken();
        }
    }
    /**
     * 失效用户Token
     * @param userid
     */
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
    public void invalidToken(String userid){
        //查询用户Token数据
        UserToken userToken=getUserTokenByUserId(userid);
        if(userToken==null){
            return;
        }else{
            //当前时间
            Date now = new Date();
            //更新Token过期时间
            userToken.setExpireTime(now);
            userToken.setUpdateTime(now);
            userTokenMapper.updateUserToken(userToken);
        }
    }
    /**
     * 查询ID用户Token
     * @param userid
     * @return
     */
    public UserToken getUserTokenByUserId(String userid){
        Map<String, Object> parameterObject=new HashMap<String, Object>();
        parameterObject.put("userid",userid);
        UserToken userToken=userTokenMapper.getUserTokenByUserId(parameterObject);
        return userToken;
    }
    /**
     * 根据accessToken查询用户Token
     * @param accessToken
     * @return
     */
    public UserToken getUserTokenByAccessToken(String accessToken){
        Map<String, Object> parameterObject=new HashMap<String, Object>();
        parameterObject.put("token",accessToken);
        UserToken userToken=userTokenMapper.getUserTokenByAccessToken(parameterObject);
        return userToken;
    }
}
