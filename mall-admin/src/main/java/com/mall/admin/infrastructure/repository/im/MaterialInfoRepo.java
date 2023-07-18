package com.mall.admin.infrastructure.repository.im;

import com.mall.admin.domain.entity.comm.TreeNode;
import com.mall.admin.infrastructure.repository.im.mapper.MaterialInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * MaterialInfoRepo
 *
 * @author youfu.wang
 * @date 2023/7/18
 **/
@Component
public class MaterialInfoRepo {
    @Autowired
    private MaterialInfoMapper materialInfoMapper;

    /**
     * 查询物品下表节点
     * @param parameterObject
     * @return
     */
    public List<TreeNode> getMaterialChildrenNode(Map<String, Object> parameterObject){
        return materialInfoMapper.getMaterialChildrenNode(parameterObject);
    }
}
