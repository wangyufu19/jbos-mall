package com.mall.product.application.service;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.mall.product.domain.entity.Product;
import com.mall.product.domain.entity.ProductList;
import com.mall.product.infrastructure.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * ProductService
 * @author youfu.wang
 * @date 2021-08-19
 */
@Service
public class ProductService {
    @Autowired
    private ProductRepo productRepo;

    public List<ProductList> getProductList(Map<String,Object> parameterObject){
        return productRepo.getProductList(parameterObject);
    }

    public Product getProductInfo(Map<String,Object> parameterObject){
        return productRepo.getProductInfo(parameterObject);
    }

    public void addProductInfo(Product product){
        this.productRepo.addProductInfo(product);
    }

    public void updateProductInfo(Product product){
       this.productRepo.updateProductInfo(product);
    }
}
