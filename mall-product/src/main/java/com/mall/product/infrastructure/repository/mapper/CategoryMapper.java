package com.mall.product.infrastructure.repository.mapper;

import com.mall.product.domain.entity.Category;

import java.util.List;
import java.util.Map;

/**
 * CategoryMapper
 * @author youfu.wang
 * @date 2021-08-19
 */
public interface CategoryMapper {

    public List<Category> getProductCateGory(Map<String,Object> parameterObject);
}
