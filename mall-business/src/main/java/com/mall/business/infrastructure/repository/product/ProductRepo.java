package com.mall.business.infrastructure.repository.product;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.mall.business.domain.entity.product.Product;
import com.mall.business.domain.entity.product.ProductList;
import com.mall.business.infrastructure.repository.product.mapper.ProductListMapper;
import com.mall.business.infrastructure.repository.product.mapper.ProductMapper;
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
}
