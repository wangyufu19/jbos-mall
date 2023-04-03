package com.mall.business.infrastructure.repository.product.mapper;

import com.mall.business.domain.entity.product.Category;
import com.mall.business.domain.entity.TreeNode;

import java.util.List;
import java.util.Map;

/**
 * CategoryMapper
 * @author youfu.wang
 * @date 2021-08-19
 */
public interface CategoryMapper {

    public List<TreeNode> getCateGoryTreeNode(Map<String, Object> parameterObject);

    public List<Category> getProductCategory(Map<String, Object> parameterObject);
}
