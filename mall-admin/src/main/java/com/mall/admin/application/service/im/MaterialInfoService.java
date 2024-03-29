package com.mall.admin.application.service.im;

import com.mall.admin.domain.entity.comm.TreeNode;
import com.mall.admin.infrastructure.repository.im.MaterialInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MaterialInfoService
 *
 * @author youfu.wang
 * @date 2023/7/18
 **/
@Service
public class MaterialInfoService {
    @Autowired
    private MaterialInfoRepo materialInfoRepo;
    /**
     * 查询物品下级节点
     * @param parentId
     * @return
     */
    public List<TreeNode> getMaterialChildrenNode(String parentId){
        List<TreeNode> funcTree=null;
        Map<String, Object> parameterObject=new HashMap<String, Object>();
        parameterObject.put("parentId",parentId);
        funcTree=materialInfoRepo.getMaterialChildrenNode(parameterObject);
        return funcTree;
    }
}
