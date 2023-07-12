package com.mall.admin.infrastructure.camunda;

import com.mall.admin.common.exception.CamundaException;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.impl.persistence.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * IdentityMgrService
 * @author youfu.wang
 * @date 2019-01-31
 */
@Service
public class IdentityMgrService {
    @Autowired
    private IdentityService identityService;

    /**
     * 用户认证
     * @param userId
     * @return
     * @throws CamundaException
     */
    public boolean auth(String userId) throws CamundaException {
        User user=identityService.createUserQuery().userId(userId).singleResult();
        if(user==null){
            throw new CamundaException("Camunda["+userId+"]用户认证失败！");
        }else{
            return true;
        }
    }

    /**
     * 创建用户
     * @param userId
     * @param userName
     */
    public void createUser(String userId,String userName){
        UserEntity userEntity=new UserEntity();
        userEntity.setId(userId);
        userEntity.setFirstName(userName);
        userEntity.setLastName(userName);
        userEntity.setPassword("123456");
        identityService.saveUser(userEntity);
    }
}
