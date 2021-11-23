package com.mall.product.infrastructure.repository;

import com.mall.product.domain.entity.Category;
import com.mall.product.domain.entity.Sku;
import com.mall.product.infrastructure.repository.mapper.SkuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * SkuRepo
 * @author youfu.wang
 * @date 2021-08-19
 */
@Component
public class SkuRepo {
    @Autowired
    private SkuMapper skuMapper;

    public List<Sku> getProductSkuList(Map<String, Object> parameterObject){
        return skuMapper.getProductSkuList(parameterObject);
    }
}
