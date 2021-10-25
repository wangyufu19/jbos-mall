package com.mall.admin.application.api.sm;

import com.mall.admin.application.api.BaseApi;
import com.mall.admin.application.service.FuncMgrService;
import com.mall.admin.domain.entity.Func;
import com.mall.admin.domain.entity.TreeNode;
import com.mall.common.response.ResponseData;
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
public class FuncMgrApi extends BaseApi {
    @Autowired
    private FuncMgrService funcMgrService;

    /**
     * 查询下级功能节点
     * @param params
     * @ResponseData
     */
    @ResponseBody
    @RequestMapping("/getFuncChildrenNode")
    public ResponseData getFuncChildrenNode(@RequestParam Map<String, Object> params) {
        ResponseData res = ResponseData.ok();
        String parentId = StringUtils.replaceNull(params.get("parentId"));
        if (StringUtils.isNUll(parentId)) {
            parentId = Func.ROOTFUNC_ID;
        }
        try{
            List<TreeNode> childrenNode=funcMgrService.getFuncChildrenNode(parentId);
            res.setData(childrenNode);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res=ResponseData.error(ResponseData.RETCODE_FAILURE,ResponseData.RETMSG_FAILURE);
        }
        return res;
    }
    /**
     * 查询功能树所有节点
     * @param params
     * @ResponseData
     */
    @ResponseBody
    @RequestMapping("/getFuncTree")
    public ResponseData getFuncTree(@RequestParam Map<String, Object> params) {
        ResponseData res = ResponseData.ok();
        try{
            List<TreeNode> funcTree=funcMgrService.getFuncTree(Func.ROOTFUNC_ID);
            res.setData(funcTree);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res=ResponseData.error(ResponseData.RETCODE_FAILURE,ResponseData.RETMSG_FAILURE);
        }
        return res;
    }
    /**
     * 查询功能数据列表
     * @param params
     * @ResponseData
     */
    @ResponseBody
    @RequestMapping("/getFuncList")
    @ApiOperation("查询功能数据列表")
    public ResponseData getFuncList(@RequestParam Map<String, Object> params){
        ResponseData res = ResponseData.ok();
        try{
            List<Func> funcs=funcMgrService.getFuncList(params);
            res.setData(funcs);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res=ResponseData.error(ResponseData.RETCODE_FAILURE,ResponseData.RETMSG_FAILURE);
        }
        return res;
    }
    /**
     * 新增功能
     * @param params
     * @ResponseData
     */
    @ResponseBody
    @RequestMapping(value = "/addFunc", method = RequestMethod.POST)
    @ApiOperation("新增功能")
    public ResponseData addFunc(@RequestBody Map<String, Object> params){
        ResponseData res = ResponseData.ok();
        try{
            params.put("id", UUID.randomUUID().toString());
            funcMgrService.insertFunc(params);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res=ResponseData.error(ResponseData.RETCODE_FAILURE,ResponseData.RETMSG_FAILURE);
        }
        return res;
    }
    /**
     * 修改功能
     * @param params
     * @ResponseData
     */
    @ResponseBody
    @RequestMapping(value = "/updateFunc", method = RequestMethod.POST)
    @ApiOperation("修改功能")
    public ResponseData updateFunc(@RequestBody Map<String, Object> params){
        ResponseData res = ResponseData.ok();
        try{
            funcMgrService.updateFunc(params);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res=ResponseData.error(ResponseData.RETCODE_FAILURE,ResponseData.RETMSG_FAILURE);
        }
        return res;
    }
    /**
     * 删除功能
     * @param params
     * @ResponseData
     */
    @ResponseBody
    @RequestMapping(value = "/deleteFunc", method = RequestMethod.POST)
    @ApiOperation("删除功能")
    public ResponseData deleteFunc(@RequestBody Map<String, Object> params){
        ResponseData res = ResponseData.ok();
        String id= StringUtils.replaceNull(params.get("id"));
        try{
            funcMgrService.deleteFunc(id);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res=ResponseData.error(ResponseData.RETCODE_FAILURE,ResponseData.RETMSG_FAILURE);
        }
        return res;
    }
}
