package com.mall.admin.application.api.sm;


import com.mall.admin.application.service.sm.RoleMgrService;
import com.mall.common.page.PageParam;
import com.mall.admin.domain.entity.sm.Role;
import com.mall.admin.domain.entity.comm.TreeNode;
import com.mall.common.response.ResponseResult;
import com.mall.common.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 角色管理
 *
 * @author youfu.wang
 * @date 2020-07-09
 */
@RestController
@RequestMapping("/role")
@Slf4j
@Api("角色管理接口")
public class RoleMgrApi {
    @Autowired
    private RoleMgrService roleMgrService;

    /**
     * 查询下级角色
     *
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping("/getRoleChildrenNode")
    @ApiOperation("查询下级角色")
    public ResponseResult getRoleChildrenNode(@RequestParam Map<String, Object> params) {
        ResponseResult res = ResponseResult.ok();
        String parentId = StringUtils.replaceNull(params.get("parentId"));
        if (StringUtils.isNUll(parentId)) {
            parentId = Role.ROOTROLE_ID;
        }
        try {
            List<TreeNode> childrenNode = roleMgrService.getRoleChildrenNode(parentId);
            res.setData(childrenNode);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }

    /**
     * 查询角色数据列表
     *
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping("/getRoleList")
    @ApiOperation("查询角色数据列表")
    public ResponseResult getRoleList(@RequestParam Map<String, Object> params) {
        ResponseResult res;
        try {
            PageParam pageParam = PageParam.getPageParam(params);
            res = roleMgrService.getRoleList(pageParam, params);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }

    /**
     * 得到角色功能数据
     *
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping("/getRoleFuncs")
    @ApiOperation("得到角色功能数据")
    public ResponseResult getRoleFuncs(@RequestParam Map<String, Object> params) {
        ResponseResult res = ResponseResult.ok();
        try {
            List<String> roleFuncs = roleMgrService.getRoleFuncs(StringUtils.replaceNull(params.get("roleId")));
            res.setData(roleFuncs);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }

    /**
     * 保存角色功能
     *
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/saveRoleFuncs", method = RequestMethod.POST)
    @ApiOperation("新增角色")
    public ResponseResult saveRoleFuncs(@RequestBody Map<String, Object> params) {
        ResponseResult res = ResponseResult.ok();
        try {
            roleMgrService.saveRoleFuncs(params);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }

    /**
     * 新增角色
     *
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addRole", method = RequestMethod.POST)
    @ApiOperation("新增角色")
    public ResponseResult addRole(@RequestBody Map<String, Object> params) {
        ResponseResult res = ResponseResult.ok();
        String parentId = StringUtils.replaceNull(params.get("parentId"));
        if (StringUtils.isNUll(parentId)) {
            parentId = Role.ROOTROLE_ID;
            params.put("parentId", parentId);
        }
        try {
            params.put("id", UUID.randomUUID().toString());
            roleMgrService.insertRole(params);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }

    /**
     * 修改角色
     *
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateRole", method = RequestMethod.POST)
    @ApiOperation("修改角色")
    public ResponseResult updateRole(@RequestBody Map<String, Object> params) {
        ResponseResult res = ResponseResult.ok();
        try {
            roleMgrService.updateRole(params);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }

    /**
     * 删除角色
     *
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deleteRole", method = RequestMethod.POST)
    @ApiOperation("删除角色")
    public ResponseResult deleteRole(@RequestBody Map<String, Object> params) {
        ResponseResult res = ResponseResult.ok();
        String id = StringUtils.replaceNull(params.get("id"));
        try {
            roleMgrService.deleteRole(id);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }

    /**
     * 查询角色数据列表
     *
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping("/getRoleEmpList")
    @ApiOperation("查询角色用户列表")
    public ResponseResult getRoleEmpList(@RequestParam Map<String, Object> params) {
        ResponseResult res;
        try {
            PageParam pageParam = PageParam.getPageParam(params);
            res = roleMgrService.getRoleEmpList(pageParam, params);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }

    /**
     * 查询选择角色数据列表
     *
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping("/getSelectRoleEmpList")
    @ApiOperation("查询选择角色用户列表")
    public ResponseResult getSelectRoleEmpList(@RequestParam Map<String, Object> params) {
        ResponseResult res;
        try {
            PageParam pageParam = PageParam.getPageParam(params);
            res= roleMgrService.getSelectRoleEmpList(pageParam,params);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }

    /**
     * 保存角色用户
     *
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addRoleUser", method = RequestMethod.POST)
    @ApiOperation("新增角色用户")
    public ResponseResult addRoleUser(@RequestBody Map<String, Object> params) {
        ResponseResult res = ResponseResult.ok();
        try {
            roleMgrService.addRoleUser(params);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }
    /**
     * 删除角色用户
     *
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deleteRoleUser", method = RequestMethod.POST)
    @ApiOperation("删除角色用户")
    public ResponseResult deleteRoleUser(@RequestBody Map<String, Object> params) {
        ResponseResult res = ResponseResult.ok();
        try {
            roleMgrService.deleteRoleUser(params);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }
}
