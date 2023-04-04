package com.mall.admin.infrastructure.repository.sm;

import com.mall.admin.domain.entity.sm.UserInfo;
import com.mall.admin.infrastructure.repository.sm.mapper.UserMapper;
import com.mall.common.utils.StringUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * UserMgrRepository
 * @author youfu.wang
 * @date 2020-06-24
 */
@Component
public class UserMgrRepository {
    @Autowired
    private UserMapper userMapper;
    /**
     * 查询用户数据列表
     * @param params
     * @return
     */
    public void getUserList(Map<String, Object> params) {
        return;
    }
    /**
     * 根据ID查询用户信息
     * @param userid
     * @return
     */
    public UserInfo getUserInfoById(String userid){
        Map<String, Object> parameterObject=new HashMap<String, Object>();
        parameterObject.put("userid",userid);
        Map<String, Object> dataMap=userMapper.getUserInfoById(parameterObject);
        if(dataMap!=null){
            UserInfo userInfo=new UserInfo();
//			userInfo.setUserId(String.valueOf(dataMap.get("ID")));
//			userInfo.setLoginName(StringUtils.replaceNull(dataMap.get("LOGIN_NAME")));
//			userInfo.setUsername(StringUtils.replaceNull(dataMap.get("USER_NAME")));
//			userInfo.setUserStatus(StringUtils.replaceNull(dataMap.get("USER_STATUS")));
//			userInfo.setOrgId(StringUtils.replaceNull(dataMap.get("ORG_ID")));
//			userInfo.setDepId(StringUtils.replaceNull(dataMap.get("DEP_ID")));
            return userInfo;
        }else{
            return null;
        }
    }
    /**
     * 根据登录名称查询用户信息
     * @param username
     * @return
     */
    public Map<String, Object> getUserInfoByLoginName(String username){
        Map<String, Object> parameterObject=new HashMap<String, Object>();
        parameterObject.put("username",username);
        Map<String, Object> dataMap=userMapper.getUserInfoByLoginName(parameterObject);
        return dataMap;
    }
    /**
     * 得到用户信息
     * @param loginName
     */
    public Map<String, Object> getUserInfo(String loginName){
        Map<String, Object> parameterObject=new HashMap<String, Object>();
        parameterObject.put("loginName",loginName);
        Map<String, Object> dataMap=userMapper.getUserInfoByLoginName(parameterObject);
        return dataMap;
    }
    /**
     * 新增用户信息
     * @param parameterObject
     */
    public void addUserInfo(Map<String, Object> parameterObject){
        String salt = RandomStringUtils.randomAlphanumeric(20);
        parameterObject.put("salt",salt);
        PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
        parameterObject.put("password",passwordEncoder.encode(StringUtils.replaceNull(parameterObject.get("password"))));
        userMapper.addUserInfo(parameterObject);
    }
    /**
     * 更新用户信息
     * @param user
     */
    //@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
    public void updateUserInfo(UserInfo user){

    }
    /**
     * 新增用户默认角色
     * @param parameterObject
     */
    public void addUserDefaultRole(Map<String, Object> parameterObject){
        userMapper.addUserDefaultRole(parameterObject);
    }
}
