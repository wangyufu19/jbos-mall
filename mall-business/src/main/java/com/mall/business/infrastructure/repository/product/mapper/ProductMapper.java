package com.mall.business.infrastructure.repository.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.business.domain.entity.product.Product;

import java.util.Map;

/**
 * ProductMapper
 * @author youfu.wang
 * @date 2021-08-19
 */
public interface ProductMapper extends BaseMapper<Product> {

    public Product getProductInfo(Map<String, Object> parameterObject);
}
