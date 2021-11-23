package com.mall.product.application.service;
import com.mall.common.utils.DateUtils;
import com.mall.common.utils.StringUtils;
import com.mall.product.domain.entity.Product;
import com.mall.product.domain.entity.ProductList;
import com.mall.product.infrastructure.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public void addProductInfo(Product product){
        //新增商品信息
        this.productRepo.addProductInfo(product);
        //新增商品列表信息
        ProductList productList=new ProductList();
        productList.setSeqId(StringUtils.getUUID());
        productList.setProductSeqId(product.getSeqId());
        productList.setCreateTime(DateUtils.getCurrentDate());
        productList.setIsValid(1);
        this.productRepo.addProductList(productList);
    }

    public void updateProductInfo(Product product){
       this.productRepo.updateProductInfo(product);
    }
}
