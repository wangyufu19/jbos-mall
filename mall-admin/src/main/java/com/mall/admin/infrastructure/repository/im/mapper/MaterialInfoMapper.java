package com.mall.admin.infrastructure.repository.im.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.admin.domain.entity.comm.TreeNode;
import com.mall.admin.domain.entity.im.MaterialInfo;

import java.util.List;
import java.util.Map;


/**
 * MaterialInfoMapper
 *
 * @author youfu.wang
 * @date 2023/7/18
 **/
public interface MaterialInfoMapper extends BaseMapper<MaterialInfo> {

    public List<TreeNode> getMaterialChildrenNode(Map<String, Object> parameterObject);
}
