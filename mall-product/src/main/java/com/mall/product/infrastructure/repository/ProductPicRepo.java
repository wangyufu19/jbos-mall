package com.mall.product.infrastructure.repository;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.mall.product.domain.entity.ProductList;
import com.mall.product.domain.entity.ProductPic;
import com.mall.product.infrastructure.repository.mapper.ProductPicMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * ProductPicRepo
 * @author youfu.wang
 * @date 2021-08-19
 */
@Component
public class ProductPicRepo {
    @Autowired
    private ProductPicMapper productPicMapper;

    /**
     * 得到商品主图列表
     * @param parameterObject
     * @return
     */
    public List<ProductPic> getProductMainPicList(Map<String, Object> parameterObject){
        return productPicMapper.getProductMainPicList(parameterObject);
    }

    /**
     * 新增商品主图
     * @param productPic
     */
    public void addProductMainPic(ProductPic productPic){
        productPicMapper.insert(productPic);
    }
    /**
     * 删除商品主图
     * @param picSeqId
     */
    public void deleteProductMainPic(String picSeqId){
        UpdateWrapper<ProductPic> updateWrapper=new UpdateWrapper<ProductPic>();
        updateWrapper.eq("pic_seq_id",picSeqId);
        productPicMapper.delete(updateWrapper);
    }
}
