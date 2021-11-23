package com.mall.product.application.service;
import com.mall.product.domain.entity.Category;
import com.mall.product.infrastructure.repository.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CategoryService
 * @author youfu.wang
 * @date 2021-08-19
 */
@Component
public class CategoryService {
    @Autowired
    private CategoryRepo categoryRepo;

    /**
     * 得到商品分类列表
     * @param parentCode
     * @return
     */
    public List<Category> getProductCateGory(String parentCode){
        Map<String,Object> parameterObject=new HashMap<String,Object>();
        parameterObject.put("parentCode",parentCode);
        return categoryRepo.getProductCateGory(parameterObject);
    }
}
