package com.mall.product.infrastructure.repository.mapper;

import com.mall.product.domain.entity.Sku;

import java.util.List;
import java.util.Map;

/**
 * SkuMapper
 * @author youfu.wang
 * @date 2021-08-19
 */
public interface SkuMapper {

    public List<Sku> getProductSkuList(Map<String, Object> parameterObject);
}
