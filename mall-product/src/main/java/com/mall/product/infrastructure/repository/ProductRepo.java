package com.mall.product.infrastructure.repository;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.mall.product.domain.entity.Product;
import com.mall.product.domain.entity.ProductList;
import com.mall.product.infrastructure.repository.mapper.ProductListMapper;
import com.mall.product.infrastructure.repository.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * ProductRepo
 * @author youfu.wang
 * @date 2021-08-19
 */
@Component
public class ProductRepo {
    @Autowired
    private ProductListMapper productListMapper;
    @Autowired
    private ProductMapper productMapper;

    public List<ProductList> getProductList(Map<String,Object> parameterObject){
        return productListMapper.getProductList(parameterObject);
    }

    public Product getProductInfo(Map<String,Object> parameterObject){
        return productMapper.getProductInfo(parameterObject);
    }

    public void addProductInfo(Product product){
        this.productMapper.insert(product);
    }
    public void updateProductInfo(Product product){
        UpdateWrapper<Product> updateWrapper=new UpdateWrapper<Product>();
        updateWrapper.eq("seq_id",product.getSeqId());
        this.productMapper.update(product,updateWrapper);
    }
    public void addProductList(ProductList productList){
        productListMapper.insert(productList);
    }
    public void updateProductList(ProductList productList){
        UpdateWrapper<ProductList> updateWrapper=new UpdateWrapper<ProductList>();
        updateWrapper.eq("product_seq_id",productList.getProductSeqId());
        productListMapper.update(productList,updateWrapper);
    }
    public void deleteProductList(String productSeqId){
        UpdateWrapper<ProductList> updateWrapper=new UpdateWrapper<ProductList>();
        updateWrapper.eq("product_seq_id",productSeqId);
        productListMapper.delete(updateWrapper);
    }
}
