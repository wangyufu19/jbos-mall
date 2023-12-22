package com.mall.admin.application.api.sm;


import com.mall.admin.application.service.sm.RoleMgrService;
import com.mall.common.page.PageParam;
import com.mall.admin.domain.entity.sm.Role;
import com.mall.admin.domain.entity.comm.TreeNode;
import com.mall.common.response.ResponsePageResult;
import com.mall.common.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

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
@Api(tags = "角色管理接口")
public class RoleMgrApi {
    /**
     * roleMgrService
     */
    @Autowired
    private RoleMgrService roleMgrService;

    /**
     * 查询下级角色
     *
     * @param params
     * @return ResponseResult
     */
    @ResponseBody
    @GetMapping("/getRoleChildrenNode")
    @ApiOperation("查询下级角色")
    public ResponseResult getRoleChildrenNode(@RequestParam Map<String, Object> params) {
        ResponseResult res = ResponseResult.ok();
        String parentId = String.valueOf(params.get("parentId"));
        if (StringUtils.isEmpty(parentId)) {
            parentId = Role.ROOTROLE_ID;
        }
        List<TreeNode> childrenNode = roleMgrService.getRoleChildrenNode(parentId);
        res.setData(childrenNode);
        return res;
    }

    /**
     * 查询角色数据列表
     *
     * @param params
     * @return ResponsePageResult
     */
    @ResponseBody
    @GetMapping("/getRoleList")
    @ApiOperation("查询角色数据列表")
    public ResponsePageResult getRoleList(@RequestParam Map<String, Object> params) {
        ResponsePageResult res;
        PageParam pageParam = PageParam.getPageParam(params);
        res = roleMgrService.getRoleList(pageParam, params);
        return res;
    }

    /**
     * 得到角色功能数据
     *
     * @param params
     * @return ResponseResult
     */
    @ResponseBody
    @GetMapping("/getRoleFuncs")
    @ApiOperation("得到角色功能数据")
    public ResponseResult getRoleFuncs(@RequestParam Map<String, Object> params) {
        ResponseResult res = ResponseResult.ok();
        List<String> roleFuncs = roleMgrService.getRoleFuncs(String.valueOf(params.get("roleId")));
        res.setData(roleFuncs);
        return res;
    }

    /**
     * 保存角色功能
     *
     * @param params
     * @return ResponseResult
     */
    @ResponseBody
    @PostMapping("/saveRoleFuncs")
    @ApiOperation("新增角色")
    public ResponseResult saveRoleFuncs(@RequestBody Map<String, Object> params) {
        roleMgrService.saveRoleFuncs(params);
        return ResponseResult.ok();
    }

    /**
     * 新增角色
     *
     * @param params
     * @return ResponseResult
     */
    @ResponseBody
    @PostMapping("/addRole")
    @ApiOperation("新增角色")
    public ResponseResult addRole(@RequestBody Map<String, Object> params) {
        ResponseResult res = ResponseResult.ok();
        String parentId = String.valueOf(params.get("parentId"));
        if (StringUtils.isEmpty(parentId)) {
            parentId = Role.ROOTROLE_ID;
            params.put("parentId", parentId);
        }
        params.put("id", UUID.randomUUID().toString());
        roleMgrService.insertRole(params);
        return res;
    }

    /**
     * 修改角色
     *
     * @param params
     * @return ResponseResult
     */
    @ResponseBody
    @PostMapping("/updateRole")
    @ApiOperation("修改角色")
    public ResponseResult updateRole(@RequestBody Map<String, Object> params) {
        roleMgrService.updateRole(params);
        return ResponseResult.ok();
    }

    /**
     * 删除角色
     *
     * @param params
     * @return ResponseResult
     */
    @ResponseBody
    @PostMapping("/deleteRole")
    @ApiOperation("删除角色")
    public ResponseResult deleteRole(@RequestBody Map<String, Object> params) {
        ResponseResult res = ResponseResult.ok();
        String id = String.valueOf(params.get("id"));
        roleMgrService.deleteRole(id);
        return ResponseResult.ok();
    }

    /**
     * 查询角色数据列表
     *
     * @param params
     * @return ResponsePageResult
     */
    @ResponseBody
    @GetMapping("/getRoleEmpList")
    @ApiOperation("查询角色用户列表")
    public ResponsePageResult getRoleEmpList(@RequestParam Map<String, Object> params) {
        ResponsePageResult res;
        PageParam pageParam = PageParam.getPageParam(params);
        res = roleMgrService.getRoleEmpList(pageParam, params);
        return res;
    }

    /**
     * 查询选择角色数据列表
     *
     * @param params
     * @return ResponsePageResult
     */
    @ResponseBody
    @GetMapping("/getSelectRoleEmpList")
    @ApiOperation("查询选择角色用户列表")
    public ResponsePageResult getSelectRoleEmpList(@RequestParam Map<String, Object> params) {
        ResponsePageResult res;
        PageParam pageParam = PageParam.getPageParam(params);
        res = roleMgrService.getSelectRoleEmpList(pageParam, params);
        return res;
    }

    /**
     * 保存角色用户
     *
     * @param params
     * @return ResponseResult
     */
    @ResponseBody
    @PostMapping("/addRoleUser")
    @ApiOperation("新增角色用户")
    public ResponseResult addRoleUser(@RequestBody Map<String, Object> params) {
        roleMgrService.addRoleUser(params);
        return ResponseResult.ok();
    }

    /**
     * 删除角色用户
     *
     * @param params
     * @return ResponseResult
     */
    @ResponseBody
    @PostMapping("/deleteRoleUser")
    @ApiOperation("删除角色用户")
    public ResponseResult deleteRoleUser(@RequestBody Map<String, Object> params) {
        roleMgrService.deleteRoleUser(params);
        return ResponseResult.ok();
    }
}
