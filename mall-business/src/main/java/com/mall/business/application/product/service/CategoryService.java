package com.mall.business.application.product.service;
import com.mall.business.domain.entity.product.Category;
import com.mall.business.domain.entity.TreeNode;
import com.mall.business.infrastructure.repository.product.CategoryRepo;
import com.mall.common.response.ResponsePageResult;
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
     * 得到商品分类树
     * @param parentCode
     * @return
     */
    public List<TreeNode> getCateGoryTreeNode(String parentCode){
        Map<String,Object> parameterObject=new HashMap<String,Object>();
        parameterObject.put("parentCode",parentCode);
        return categoryRepo.getCateGoryTreeNode(parameterObject);
    }
    /**
     * 得到商品分类列表
     * @param parentCode
     * @return
     */
    public ResponsePageResult getProductCategory(String parentCode){
        Map<String,Object> parameterObject=new HashMap<String,Object>();
        parameterObject.put("parentCode",parentCode);
        List<Category> categoryList=categoryRepo.getProductCategory(parameterObject);
        return ResponsePageResult.ok().isPage(true).setData(categoryList);
    }
}
