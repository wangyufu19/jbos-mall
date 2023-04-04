package com.mall.admin.infrastructure.repository.sm;

import com.mall.admin.domain.entity.sm.Org;
import com.mall.admin.domain.entity.comm.TreeNode;
import com.mall.admin.infrastructure.repository.sm.mapper.OrgMapper;
import com.mall.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * OrgMgrRepository
 * @author youfu.wang
 * @date 2020-06-24
 */
@Component
public class OrgMgrRepository {
    @Autowired
    private OrgMapper orgMapper;
    /**
     * 查询组织机构树数据
     * @param parentId
     * @return
     */
    public List<TreeNode> getOrgTree(String parentId){
        List<TreeNode> orgTree=null;
        Map<String, Object> parameterObject=new HashMap<String, Object>();
        parameterObject.put("parentId",parentId);
        orgTree=orgMapper.getOrgTree(parameterObject);
        return orgTree;
    }
    /**
     * 查询组织机构数据
     * @return
     */
    public List<Org> getOrgList(String parentId){
        List<Org> orgList=null;
        Map<String, Object> parameterObject=new HashMap<String, Object>();
        parameterObject.put("parentId",parentId);
        orgList=orgMapper.getOrgList(parameterObject);
        return orgList;
    }
    /**
     * 查询组织机构数据
     * @return
     */
    public Org getOrg(String orgId){
        Org org=null;
        org=orgMapper.getOrg(orgId);
        return org;
    }
    /**
     * 保存组织机构数据
     * @param org
     */
    public void addOrg(Org org){
        org.setId(StringUtils.getUUID());
        orgMapper.addOrg(org);
    }
    /**
     * 更新组织机构数据
     * @param org
     */
    public void updateOrg(Org org){
        orgMapper.updateOrg(org);
    }
    /**
     * 删除组织机构数据
     * @param orgs
     */
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
    public void deleteOrg(Org[] orgs){
        if(orgs==null){
            return;
        }
        for(int i=0;i<orgs.length;i++){
            orgMapper.deleteOrg(orgs[i].getId());
        }
    }
}
