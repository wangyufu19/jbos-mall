package com.mall.admin.application.api.sm;

import com.mall.admin.application.api.BaseApi;
import com.mall.admin.application.external.camunda.IdentityMgrService;
import com.mall.admin.application.service.EmpMgrService;
import com.mall.admin.domain.entity.Emp;
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
 * EmpMgrApi
 * @author youfu.wang
 * @date 2019-01-31
 */
@Slf4j
@RestController
@RequestMapping("/emp")
public class EmpMgrApi extends BaseApi {
    @Autowired
    private EmpMgrService empMgrService;
    @Autowired
    private IdentityMgrService identityMgrService;
    /**
     * 查询组织机构员工数据
     * @return
     */
    @ResponseBody
    @RequestMapping("/getEmpList")
    public ResponseResult getEmpList(@RequestParam Map<String, Object> params){
        ResponseResult res= ResponseResult.ok();
        String isPage= StringUtils.replaceNull(params.get("isPage"));
        try{
            if("true".equals(isPage)){
                this.doStartPage(params);
            }
            List<Emp> emps=empMgrService.getEmpList(params);
            if("true".equals(isPage)){
                this.doFinishPage(res,emps);
            }else{
                res.setData(emps);
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res= ResponseResult.error(ResponseResult.RETCODE_FAILURE, ResponseResult.RETMSG_FAILURE);
        }
        return res;
    }
    /**
     * 新增人员
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addEmp", method = RequestMethod.POST)
    @ApiOperation("新增人员")
    public ResponseResult addEmp(@RequestBody Map<String, Object> params){
        ResponseResult res= ResponseResult.ok();
        try{
            params.put("id", UUID.randomUUID().toString());
            empMgrService.addEmp(params);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res= ResponseResult.error(ResponseResult.RETCODE_FAILURE, ResponseResult.RETMSG_FAILURE);
        }
        return res;
    }
    /**
     * 修改人员
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateEmp", method = RequestMethod.POST)
    @ApiOperation("修改人员")
    public ResponseResult updateEmp(@RequestBody Map<String, Object> params){
        ResponseResult res= ResponseResult.ok();
        try{
            empMgrService.updateEmp(params);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res= ResponseResult.error(ResponseResult.RETCODE_FAILURE, ResponseResult.RETMSG_FAILURE);
        }
        return res;
    }
    /**
     * 删除人员
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deleteEmp", method = RequestMethod.POST)
    @ApiOperation("删除人员")
    public ResponseResult deleteEmp(@RequestBody Map<String, Object> params){
        ResponseResult res= ResponseResult.ok();
        String id= StringUtils.replaceNull(params.get("id"));
        try{
            empMgrService.deleteEmp(params);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res= ResponseResult.error(ResponseResult.RETCODE_FAILURE, ResponseResult.RETMSG_FAILURE);
        }
        return res;
    }
    /**
     * 删除人员
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/synchToCamunda", method = RequestMethod.POST)
    @ApiOperation("同步到CAMUNDA")
    public ResponseResult synchToCamunda(@RequestBody Map<String, Object> params){
        ResponseResult res= ResponseResult.ok();
        try{
            res=identityMgrService.create(params);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res= ResponseResult.error(ResponseResult.RETCODE_FAILURE, ResponseResult.RETMSG_FAILURE);
        }
        return res;
    }

}
