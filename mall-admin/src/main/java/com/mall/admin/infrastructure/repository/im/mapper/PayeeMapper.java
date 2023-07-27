package com.mall.admin.infrastructure.repository.im.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.admin.domain.entity.im.Payee;

/**
 * PayeeMapper
 *
 * @author youfu.wang
 * @date 2023/7/26
 **/
public interface PayeeMapper extends BaseMapper<Payee> {

    public Payee getPayeeInfo(String bizId);
}
