package com.mall.business.infrastructure.repository.product;
import com.mall.business.domain.entity.product.Category;
import com.mall.business.domain.entity.TreeNode;
import com.mall.business.infrastructure.repository.product.mapper.CategoryMapper;
import com.mall.common.page.Paging;
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
    @Paging
    public List<Category> getProductCategory(Map<String,Object> parameterObject){
        return categoryMapper.getProductCategory(parameterObject);
    }
}
