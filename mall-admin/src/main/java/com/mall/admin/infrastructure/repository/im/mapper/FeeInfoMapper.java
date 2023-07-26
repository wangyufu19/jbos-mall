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

    public List<TreeNode> getFeeChildrenNode(Map<String, Object> parameterObject);
}
