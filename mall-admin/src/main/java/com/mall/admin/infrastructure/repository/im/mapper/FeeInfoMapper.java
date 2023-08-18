package com.mall.admin.infrastructure.repository.im.mapper;

import com.mall.admin.domain.entity.comm.TreeNode;

import java.util.List;
import java.util.Map;

/**
 * FeeInfoMapper
 *
 * @author youfu.wang
 * @date 2023/7/25
 **/
public interface FeeInfoMapper {
    /**
     * 得到费用叶子节点
     * @param parameterObject
     * @return list
     */
    List<TreeNode> getFeeChildrenNode(Map<String, Object> parameterObject);
}
