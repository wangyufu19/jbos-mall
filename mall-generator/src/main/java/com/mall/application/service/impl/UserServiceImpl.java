package com.mall.application.service.impl;

import com.mall.application.entity.User;
import com.mall.application.mapper.UserMapper;
import com.mall.application.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author k0091
 * @since 2023-01-11
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    private UserMapper userMapper;

    public List<User> getUserList(Map<String, Object> params){
        return userMapper.selectList(null);
    }
}
