package com.mall.admin.application.api.sm;

import com.mall.admin.application.api.BaseApi;
import com.mall.admin.application.service.DepMgrService;
import com.mall.admin.domain.entity.Dep;
import com.mall.common.response.ResponseData;
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
 * DepMgrApi
 * @author youfu.wang
 * @date 2019-01-31
 */
@RestController
@RequestMapping("/dep")
@Slf4j
@Api("部门管理接口")
public class DepMgrApi extends BaseApi {
    @Autowired
    private DepMgrService depMgrService;
    /**
     * 查询组织机构数据
     * @return
     */
    @ResponseBody
    @RequestMapping("/getDepList")
    @ApiOperation("查询部门列表")
    public ResponseData getDepList(@RequestParam Map<String, Object> params){
        ResponseData res= ResponseData.ok();
        try{
            List<Dep> deps=depMgrService.getDepList(params);
            res.setData(deps);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res=ResponseData.error(ResponseData.RETCODE_FAILURE,ResponseData.RETMSG_FAILURE);
        }
        return res;
    }
    /**
     * 新增部门
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addDep", method = RequestMethod.POST)
    @ApiOperation("新增部门")
    public ResponseData addDep(@RequestBody Map<String, Object> params){
        ResponseData res= ResponseData.ok();
        try{
            params.put("id", UUID.randomUUID().toString());
            depMgrService.addDep(params);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res=ResponseData.error(ResponseData.RETCODE_FAILURE,ResponseData.RETMSG_FAILURE);
        }
        return res;
    }
    /**
     * 修改部门
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateDep", method = RequestMethod.POST)
    @ApiOperation("修改部门")
    public ResponseData updateDep(@RequestBody Map<String, Object> params){
        ResponseData res= ResponseData.ok();
        try{
            depMgrService.updateDep(params);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res=ResponseData.error(ResponseData.RETCODE_FAILURE,ResponseData.RETMSG_FAILURE);
        }
        return res;
    }
    /**
     * 删除部门
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deleteDep", method = RequestMethod.POST)
    @ApiOperation("删除部门")
    public ResponseData deleteDep(@RequestBody Map<String, Object> params){
        ResponseData res= ResponseData.ok();
        String id= StringUtils.replaceNull(params.get("id"));
        try{
            //查询部门下员工数量
            int count=depMgrService.getDepEmpCount(id);
            if(count<=0){
                depMgrService.deleteDep(id);
            }else{
                res=ResponseData.error(ResponseData.RETCODE_FAILURE,"对不起，该部门下存在员工数据，不能删除！");
            }

        }catch (Exception e){
            log.error(e.getMessage(),e);
            res=ResponseData.error(ResponseData.RETCODE_FAILURE,ResponseData.RETMSG_FAILURE);
        }
        return res;
    }
}
