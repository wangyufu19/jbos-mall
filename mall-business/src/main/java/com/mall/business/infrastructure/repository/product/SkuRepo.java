package com.mall.business.infrastructure.repository.product;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.mall.business.domain.entity.product.Sku;
import com.mall.business.infrastructure.repository.product.mapper.SkuMapper;
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
    public void addProductSku(Sku sku){
        skuMapper.insert(sku);
    }
    public void deleteProductSku(String productSeqId){
        UpdateWrapper<Sku> updateWrapper=new UpdateWrapper<Sku>();
        updateWrapper.eq("product_seq_id",productSeqId);
        skuMapper.delete(updateWrapper);
    }
}
