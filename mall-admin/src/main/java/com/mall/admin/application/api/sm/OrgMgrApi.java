package com.mall.admin.application.api.sm;

import com.mall.admin.application.service.sm.OrgMgrService;
import com.mall.common.page.PageParam;
import com.mall.admin.domain.entity.sm.Org;
import com.mall.admin.domain.entity.comm.TreeNode;
import com.mall.common.response.ResponsePageResult;
import com.mall.common.response.ResponseResult;
import com.mall.common.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * OrgMgrApi
 *
 * @author youfu.wang
 * @date 2019-01-31
 */
@RestController
@RequestMapping("/org")
@Slf4j
@Api(tags = "机构管理接口")
public class OrgMgrApi {

    @Autowired
    private OrgMgrService orgMgrService;

    /**
     * 得到组织机构页面
     *
     * @return
     */
    @RequestMapping("/getOrgPage")
    public String getOrgPage() {
        return "org";
    }

    /**
     * 查询组织机构树
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/getOgrTree")
    @ApiOperation("查询组织机构树")
    public ResponseResult getOgrTree(@RequestParam Map<String, Object> params) {
        ResponseResult ret = ResponseResult.ok();
        String parentId = StringUtils.replaceNull(params.get("parentId"));
        if (StringUtils.isNUll(parentId)) {
            parentId = Org.ROOTORG_ID;
        }
        try {
            List<TreeNode> orgTree = orgMgrService.getOrgTree(parentId);
            ret.setData(orgTree);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            ret = ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return ret;
    }

    /**
     * 查询组织机构数据
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/getOrgList")
    @ApiOperation("查询组织机构列表")
    public ResponsePageResult getOrgList(@RequestParam Map<String, Object> params) {
        ResponsePageResult res;
        try {
            PageParam pageParam = PageParam.getPageParam(params);
            res = orgMgrService.getOrgList(pageParam, Org.ROOTORG_ID);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponsePageResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }

    /**
     * 查询组织机构数据
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/getOrg")
    @ApiOperation("查询一个组织机构信息")
    public ResponseResult getOrg(@RequestParam String orgId) {
        ResponseResult ret = ResponseResult.ok();
        try {
            Org org = orgMgrService.getOrg(orgId);
            ret.setData(org);
        } catch (Exception e) {
            log.error(ResponseResult.MSG_FAILURE, e);
            ret = ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return ret;
    }

    /**
     * 保存组织机构数据
     *
     * @param org
     * @return
     */
    @ResponseBody
    @PostMapping("/saveOrg")
    @ApiOperation("保存组织机构信息")
    public ResponseResult saveOrg(@RequestBody Org org) {
        ResponseResult ret = ResponseResult.ok();
        try {
            orgMgrService.addOrg(org);
        } catch (Exception e) {
            log.error(ResponseResult.MSG_FAILURE, e);
            ret = ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return ret;
    }

    /**
     * 保存组织机构数据
     *
     * @param org
     * @return
     */
    @ResponseBody
    @PostMapping("/updateOrg")
    @ApiOperation("更新组织机构信息")
    public ResponseResult updateOrg(@RequestBody Org org) {
        ResponseResult ret = ResponseResult.ok();
        try {
            orgMgrService.updateOrg(org);
        } catch (Exception e) {
            log.error(ResponseResult.MSG_FAILURE, e);
            ret = ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return ret;
    }

    /**
     * 保存组织机构数据
     *
     * @param orgs
     * @return
     */
    @ResponseBody
    @PostMapping("/deleteOrg")
    @ApiOperation("删除组织机构信息")
    public ResponseResult deleteOrg(@RequestBody Org[] orgs) {
        ResponseResult ret = ResponseResult.ok();
        try {
            orgMgrService.deleteOrg(orgs);
        } catch (Exception e) {
            log.error(ResponseResult.MSG_FAILURE, e);
            ret = ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return ret;
    }
}
