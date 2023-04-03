package com.mall.business.infrastructure.repository.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.business.domain.entity.product.ProductList;

import java.util.List;
import java.util.Map;

/**
 * ProductMapper
 * @author youfu.wang
 * @date 2021-08-19
 */
public interface ProductListMapper extends BaseMapper<ProductList> {

    public List<ProductList> getProductList(Map<String, Object> parameterObject);

}
