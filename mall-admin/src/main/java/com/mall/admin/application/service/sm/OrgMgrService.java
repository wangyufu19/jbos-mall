package com.mall.admin.application.service.sm;

import com.mall.admin.domain.entity.sm.Org;
import com.mall.admin.domain.entity.comm.TreeNode;
import com.mall.admin.infrastructure.repository.sm.OrgMgrRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * OrgMgrService
 * @author youfu.wang
 * @date 2019-01-31
 */
@Service
public class OrgMgrService{
    @Autowired
    private OrgMgrRepository orgMgrRepository;
    /**
     * 查询组织机构树数据
     * @param parentId
     * @return
     */
    public List<TreeNode> getOrgTree(String parentId){
        return orgMgrRepository.getOrgTree(parentId);
    }
    /**
     * 查询组织机构数据
     * @return
     */
    public List<Org> getOrgList(String parentId){
        return orgMgrRepository.getOrgList(parentId);
    }
    /**
     * 查询组织机构数据
     * @return
     */
    public Org getOrg(String orgId){
        return orgMgrRepository.getOrg(orgId);
    }
    /**
     * 保存组织机构数据
     * @param org
     */
    public void addOrg(Org org){
        orgMgrRepository.addOrg(org);
    }
    /**
     * 更新组织机构数据
     * @param org
     */
    public void updateOrg(Org org){
        orgMgrRepository.updateOrg(org);
    }
    /**
     * 删除组织机构数据
     * @param orgs
     */
    public void deleteOrg(Org[] orgs){
        orgMgrRepository.deleteOrg(orgs);
    }
}
