package com.mall.admin.application.service.sm;

import com.mall.common.page.PageParam;
import com.mall.admin.domain.entity.sm.Org;
import com.mall.admin.domain.entity.comm.TreeNode;
import com.mall.admin.infrastructure.repository.sm.OrgMgrRepository;
import com.mall.common.response.ResponseResult;
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
    public ResponseResult getOrgList(PageParam pageParam, String parentId){
        List<Org> orgList=orgMgrRepository.getOrgList(pageParam,parentId);
        return ResponseResult.ok().isPage(true).data(orgList);
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
