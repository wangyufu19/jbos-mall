package com.mall.admin.infrastructure.repository.im;

import com.mall.admin.domain.entity.comm.TreeNode;
import com.mall.admin.infrastructure.repository.im.mapper.FeeInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * FeeInfoRepo
 *
 * @author youfu.wang
 * @date 2023/7/25
 **/
@Component
public class FeeInfoRepo {
    @Autowired
    private FeeInfoMapper feeInfoMapper;

    /**
     * 得到费用叶子节点
     * @param parameterObject
     * @return list
     */
    public List<TreeNode> getFeeChildrenNode(Map<String, Object> parameterObject) {
        return feeInfoMapper.getFeeChildrenNode(parameterObject);
    }
}
