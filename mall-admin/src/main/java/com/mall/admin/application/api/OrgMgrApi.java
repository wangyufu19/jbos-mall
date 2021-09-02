package com.mall.admin.application.api;
import com.mall.admin.application.service.OrgMgrService;
import com.mall.admin.domain.entity.Org;
import com.mall.admin.domain.entity.TreeNode;
import com.mall.common.response.ResponseData;
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
 * @author youfu.wang
 * @date 2019-01-31
 */
@RestController
@RequestMapping("/org")
@Slf4j
@Api("机构管理接口")
public class OrgMgrApi extends BaseApi {

    @Autowired
    private OrgMgrService orgMgrService;
    /**
     * 得到组织机构页面
     * @return
     */
    @RequestMapping("/getOrgPage")
    public String getOrgPage(){
        return "org";
    }
    /**
     * 查询组织机构树
     * @return
     */
    @ResponseBody
    @RequestMapping("/getOgrTree")
    @ApiOperation("查询组织机构树")
    public ResponseData getOgrTree(@RequestParam Map<String, Object> params){
        ResponseData ret=ResponseData.ok();
        String parentId= StringUtils.replaceNull(params.get("parentId"));
        if(StringUtils.isNUll(parentId)){
            parentId= Org.ROOTORG_ID;
        }
        try{
            List<TreeNode> orgTree=orgMgrService.getOrgTree(parentId);
            ret.setData(orgTree);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            ret=ResponseData.error(ResponseData.RETCODE_FAILURE,ResponseData.RETMSG_FAILURE);
        }
        return ret;
    }
    /**
     * 查询组织机构数据
     * @return
     */
    @ResponseBody
    @RequestMapping("/getOrgList")
    @ApiOperation("查询组织机构列表")
    public ResponseData getOrgList(@RequestParam Map<String, Object> params){
        ResponseData ret=ResponseData.ok();
        try{
            this.doStartPage(params);
            List<Org> orgs=orgMgrService.getOrgList(Org.ROOTORG_ID);
            this.doFinishPage(ret,orgs);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            ret=ResponseData.error(ResponseData.RETCODE_FAILURE,ResponseData.RETMSG_FAILURE);
        }
        return ret;
    }
    /**
     * 查询组织机构数据
     * @return
     */
    @ResponseBody
    @RequestMapping("/getOrg")
    @ApiOperation("查询一个组织机构信息")
    public ResponseData getOrg(@RequestParam String orgId){
        ResponseData ret=ResponseData.ok();
        try{
            Org org=orgMgrService.getOrg(orgId);
            ret.setData(org);
        }catch(Exception e){
            log.error(ResponseData.RETMSG_FAILURE,e);
            ret=ResponseData.error(ResponseData.RETCODE_FAILURE,ResponseData.RETMSG_FAILURE);
        }
        return ret;
    }
    /**
     * 保存组织机构数据
     * @param org
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/saveOrg", method = RequestMethod.POST)
    @ApiOperation("保存组织机构信息")
    public ResponseData saveOrg(@RequestBody Org org){
        ResponseData ret=ResponseData.ok();
        try{
            orgMgrService.addOrg(org);
        }catch(Exception e){
            log.error(ResponseData.RETMSG_FAILURE,e);
            ret=ResponseData.error(ResponseData.RETCODE_FAILURE,ResponseData.RETMSG_FAILURE);
        }
        return ret;
    }
    /**
     * 保存组织机构数据
     * @param org
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateOrg", method = RequestMethod.POST)
    @ApiOperation("更新组织机构信息")
    public ResponseData updateOrg(@RequestBody Org org){
        ResponseData ret=ResponseData.ok();
        try{
            orgMgrService.updateOrg(org);
        }catch(Exception e){
            log.error(ResponseData.RETMSG_FAILURE,e);
            ret=ResponseData.error(ResponseData.RETCODE_FAILURE,ResponseData.RETMSG_FAILURE);
        }
        return ret;
    }
    /**
     * 保存组织机构数据
     * @param orgs
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deleteOrg", method = RequestMethod.POST)
    @ApiOperation("删除组织机构信息")
    public ResponseData deleteOrg(@RequestBody Org[] orgs){
        ResponseData ret=ResponseData.ok();
        try{
            orgMgrService.deleteOrg(orgs);
        }catch(Exception e){
            log.error(ResponseData.RETMSG_FAILURE,e);
            ret=ResponseData.error(ResponseData.RETCODE_FAILURE,ResponseData.RETMSG_FAILURE);
        }
        return ret;
    }
}
