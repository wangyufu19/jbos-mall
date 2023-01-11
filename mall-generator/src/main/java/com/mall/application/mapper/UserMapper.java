package com.mall.application.mapper;

import com.mall.application.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author k0091
 * @since 2023-01-11
 */
public interface UserMapper extends BaseMapper<User> {
    List<User> getUserList(Map<String, Object> params);
}
