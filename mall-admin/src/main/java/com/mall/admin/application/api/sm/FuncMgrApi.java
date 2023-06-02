package com.mall.admin.application.api.sm;

import com.mall.admin.application.service.sm.FuncMgrService;
import com.mall.admin.domain.entity.sm.Func;
import com.mall.admin.domain.entity.comm.TreeNode;
import com.mall.common.response.ResponseResult;
import com.mall.common.utils.StringUtils;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * FuncMgrController
 * @author youfu.wang
 * @date 2019-03-01
 */
@Slf4j
@RestController
@RequestMapping("/func")
public class FuncMgrApi{
    @Autowired
    private FuncMgrService funcMgrService;

    /**
     * 查询下级功能节点
     * @param params
     * @res
     */
    @ResponseBody
    @RequestMapping("/getFuncChildrenNode")
    public ResponseResult getFuncChildrenNode(@RequestParam Map<String, Object> params) {
        ResponseResult res = ResponseResult.ok();
        String parentId = StringUtils.replaceNull(params.get("parentId"));
        if (StringUtils.isNUll(parentId)) {
            parentId = Func.ROOTFUNC_ID;
        }
        try{
            List<TreeNode> childrenNode=funcMgrService.getFuncChildrenNode(parentId);
            res.setData(childrenNode);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res= ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }
    /**
     * 查询功能树所有节点
     * @param params
     * @res
     */
    @ResponseBody
    @RequestMapping("/getFuncTree")
    public ResponseResult getFuncTree(@RequestParam Map<String, Object> params) {
        ResponseResult res = ResponseResult.ok();
        try{
            List<TreeNode> funcTree=funcMgrService.getFuncTree(Func.ROOTFUNC_ID);
            res.setData(funcTree);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res= ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }
    /**
     * 查询功能数据列表
     * @param params
     * @res
     */
    @ResponseBody
    @RequestMapping("/getFuncList")
    @ApiOperation("查询功能数据列表")
    public ResponseResult getFuncList(@RequestParam Map<String, Object> params){
        ResponseResult res = ResponseResult.ok();
        String parentId = StringUtils.replaceNull(params.get("parentId"));
        if (StringUtils.isNUll(parentId)) {
            parentId = Func.ROOTFUNC_ID;
            params.put("parentId",parentId);
        }
        try{
            List<Func> funcs=funcMgrService.getFuncList(params);
            res.setData(funcs);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res= ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }
    /**
     * 新增功能
     * @param params
     * @res
     */
    @ResponseBody
    @RequestMapping(value = "/addFunc", method = RequestMethod.POST)
    @ApiOperation("新增功能")
    public ResponseResult addFunc(@RequestBody Map<String, Object> params){
        ResponseResult res = ResponseResult.ok();
        String parentId = StringUtils.replaceNull(params.get("parentId"));
        if (StringUtils.isNUll(parentId)) {
            parentId = Func.ROOTFUNC_ID;
            params.put("parentId",parentId);
        }
        try{
            params.put("id", UUID.randomUUID().toString());
            funcMgrService.insertFunc(params);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res= ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }
    /**
     * 修改功能
     * @param params
     * @res
     */
    @ResponseBody
    @RequestMapping(value = "/updateFunc", method = RequestMethod.POST)
    @ApiOperation("修改功能")
    public ResponseResult updateFunc(@RequestBody Map<String, Object> params){
        ResponseResult res = ResponseResult.ok();
        try{
            funcMgrService.updateFunc(params);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res= ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }
    /**
     * 删除功能
     * @param params
     * @res
     */
    @ResponseBody
    @RequestMapping(value = "/deleteFunc", method = RequestMethod.POST)
    @ApiOperation("删除功能")
    public ResponseResult deleteFunc(@RequestBody Map<String, Object> params){
        ResponseResult res = ResponseResult.ok();
        String id= StringUtils.replaceNull(params.get("id"));
        try{
            funcMgrService.deleteFunc(id);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res= ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }
}
