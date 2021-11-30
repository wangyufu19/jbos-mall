package com.mall.product.infrastructure.repository;
import com.mall.product.domain.entity.Category;
import com.mall.product.domain.entity.TreeNode;
import com.mall.product.infrastructure.repository.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * CategoryRepo
 * @author youfu.wang
 * @date 2021-08-19
 */
@Component
public class CategoryRepo {
    @Autowired
    private CategoryMapper categoryMapper;


    public List<TreeNode> getCateGoryTreeNode(Map<String, Object> parameterObject){
        return categoryMapper.getCateGoryTreeNode(parameterObject);
    }
    public List<Category> getProductCategory(Map<String,Object> parameterObject){
        return categoryMapper.getProductCategory(parameterObject);
    }
}
