package com.mall.product.infrastructure.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.product.domain.entity.Sku;

import java.util.List;
import java.util.Map;

/**
 * SkuMapper
 * @author youfu.wang
 * @date 2021-08-19
 */
public interface SkuMapper extends BaseMapper<Sku> {

    public List<Sku> getProductSkuList(Map<String, Object> parameterObject);
}
