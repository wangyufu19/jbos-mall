package com.mall.product.application.service;

import com.mall.product.domain.entity.Sku;
import com.mall.product.infrastructure.repository.SkuRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * SkuService
 * @author youfu.wang
 * @date 2021-08-19
 */
@Component
public class SkuService {
    @Autowired
    private SkuRepo skuRepo;

    public List<Sku> getProductSkuList(Map<String, Object> parameterObject){
        return skuRepo.getProductSkuList(parameterObject);
    }
}
