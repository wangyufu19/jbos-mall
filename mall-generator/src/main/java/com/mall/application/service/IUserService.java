package com.mall.application.service;

import com.mall.application.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author k0091
 * @since 2023-01-11
 */
public interface IUserService extends IService<User> {
    public List<User> getUserList(Map<String, Object> params);
}
