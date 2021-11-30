package com.mall.product.application.service;

import com.mall.common.utils.StringUtils;
import com.mall.product.domain.entity.Sku;
import com.mall.product.infrastructure.repository.SkuRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
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

    /**
     * 查询商品SKU
     * @param parameterObject
     * @return
     */
    public List<Sku> getProductSkuList(Map<String, Object> parameterObject){
        return skuRepo.getProductSkuList(parameterObject);
    }
    /**
     * 新增商品SKU
     * @param skuList
     */
    public void addProductSku(String productSeqId,List<Map<String,Object>> skuList){
        //新增商品SKU
        if(skuList!=null){
            int orderNo=0;
            for(Map<String,Object> skuMap:skuList){
                Sku sku=new Sku();
                sku.setSeqId(StringUtils.getUUID());
                sku.setProductSeqId(productSeqId);
                sku.setSpecsName(StringUtils.replaceNull(skuMap.get("specsName")));
                sku.setInventoryAmount(Integer.parseInt(StringUtils.replaceNull(skuMap.get("inventoryAmount"))));
                sku.setSellPrice(Double.parseDouble(StringUtils.replaceNull(skuMap.get("sellPrice"))));
                sku.setOrderNo(orderNo++);
                skuRepo.addProductSku(sku);
            }
        }
    }

    /**
     * 删除商品SKU
     * @param productSeqId
     */
    public void deleteProductSku(String productSeqId){
        skuRepo.deleteProductSku(productSeqId);
    }
}
