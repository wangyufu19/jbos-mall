package com.mall.admin.application.api;


import com.mall.admin.application.service.RoleMgrService;
import com.mall.admin.domain.entity.Emp;
import com.mall.admin.domain.entity.Role;
import com.mall.admin.domain.entity.TreeNode;
import com.mall.common.response.ResponseData;
import com.mall.common.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 角色管理
 * @author youfu.wang
 * @date 2020-07-09
 */
@RestController
@RequestMapping("/role")
@Slf4j
@Api("角色管理接口")
public class RoleMgrApi extends BaseApi{
    @Autowired
    private RoleMgrService roleMgrService;
    /**
     * 查询下级角色
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping("/getRoleChildrenNode")
    @ApiOperation("查询下级角色")
    public ResponseData getRoleChildrenNode(@RequestParam Map<String, Object> params){
        ResponseData res=ResponseData.ok();
        String parentId = StringUtils.replaceNull(params.get("parentId"));
        if (StringUtils.isNUll(parentId)) {
            parentId = Role.ROOTROLE_ID;
        }
        try{
            List<TreeNode> childrenNode=roleMgrService.getRoleChildrenNode(parentId);
            res.setData(childrenNode);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res=ResponseData.error(ResponseData.RETCODE_FAILURE,ResponseData.RETMSG_FAILURE);
        }
        return res;
    }
    /**
     * 查询角色数据列表
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping("/getRoleList")
    @ApiOperation("查询角色数据列表")
    public ResponseData getRoleList(@RequestParam Map<String, Object> params){
        ResponseData res=ResponseData.ok();
        try{
            this.doStartPage(params);
            List<Role> roles=roleMgrService.getRoleList(params);
            this.doFinishPage(res,roles);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res=ResponseData.error(ResponseData.RETCODE_FAILURE,ResponseData.RETMSG_FAILURE);
        }
        return res;
    }
    /**
     * 得到角色功能数据
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping("/getRoleFuncs")
    @ApiOperation("得到角色功能数据")
    public ResponseData getRoleFuncs(@RequestParam Map<String, Object> params){
        ResponseData res=ResponseData.ok();
        try{
            List<String> roleFuncs=roleMgrService.getRoleFuncs(StringUtils.replaceNull(params.get("roleId")));
            res.setData(roleFuncs);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res=ResponseData.error(ResponseData.RETCODE_FAILURE,ResponseData.RETMSG_FAILURE);
        }
        return res;
    }
    /**
     * 保存角色功能
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/saveRoleFuncs", method = RequestMethod.POST)
    @ApiOperation("新增角色")
    public ResponseData saveRoleFuncs(@RequestBody Map<String, Object> params){
        ResponseData res=ResponseData.ok();
        try{
            roleMgrService.saveRoleFuncs(params);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res=ResponseData.error(ResponseData.RETCODE_FAILURE,ResponseData.RETMSG_FAILURE);
        }
        return res;
    }
    /**
     * 新增角色
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addRole", method = RequestMethod.POST)
    @ApiOperation("新增角色")
    public ResponseData addRole(@RequestBody Map<String, Object> params){
        ResponseData res=ResponseData.ok();
        try{
            params.put("id", UUID.randomUUID().toString());
            roleMgrService.insertRole(params);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res=ResponseData.error(ResponseData.RETCODE_FAILURE,ResponseData.RETMSG_FAILURE);
        }
        return res;
    }
    /**
     * 修改角色
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateRole", method = RequestMethod.POST)
    @ApiOperation("修改角色")
    public ResponseData updateRole(@RequestBody Map<String, Object> params){
        ResponseData res=ResponseData.ok();
        try{
            roleMgrService.updateRole(params);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res=ResponseData.error(ResponseData.RETCODE_FAILURE,ResponseData.RETMSG_FAILURE);
        }
        return res;
    }
    /**
     * 删除角色
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deleteRole", method = RequestMethod.POST)
    @ApiOperation("删除角色")
    public ResponseData deleteRole(@RequestBody Map<String, Object> params){
        ResponseData res=ResponseData.ok();
        String id= StringUtils.replaceNull(params.get("id"));
        try{
            roleMgrService.deleteRole(id);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res=ResponseData.error(ResponseData.RETCODE_FAILURE,ResponseData.RETMSG_FAILURE);
        }
        return res;
    }
    /**
     * 查询角色数据列表
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping("/getRoleEmpList")
    @ApiOperation("查询角色用户列表")
    public ResponseData getRoleEmpList(@RequestParam Map<String, Object> params){
        ResponseData res=ResponseData.ok();
        try{
            this.doStartPage(params);
            List<Emp> emps=roleMgrService.getRoleEmpList(params);
            this.doFinishPage(res,emps);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res=ResponseData.error(ResponseData.RETCODE_FAILURE,ResponseData.RETMSG_FAILURE);
        }
        return res;
    }
    /**
     * 查询选择角色数据列表
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping("/getSelectRoleEmpList")
    @ApiOperation("查询选择角色用户列表")
    public ResponseData getSelectRoleEmpList(@RequestParam Map<String, Object> params){
        ResponseData res=ResponseData.ok();
        try{
            this.doStartPage(params);
            List<Emp> emps=roleMgrService.getSelectRoleEmpList(params);
            this.doFinishPage(res,emps);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res=ResponseData.error(ResponseData.RETCODE_FAILURE,ResponseData.RETMSG_FAILURE);
        }
        return res;
    }
    /**
     * 保存角色用户
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addRoleUser", method = RequestMethod.POST)
    @ApiOperation("新增角色用户")
    public ResponseData addRoleUser(@RequestBody Map<String, Object> params){
        ResponseData res=ResponseData.ok();
        try{
            roleMgrService.addRoleUser(params);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res=ResponseData.error(ResponseData.RETCODE_FAILURE,ResponseData.RETMSG_FAILURE);
        }
        return res;
    }
}
