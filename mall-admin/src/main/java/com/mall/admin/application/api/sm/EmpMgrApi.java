package com.mall.admin.application.api.sm;

import com.github.pagehelper.PageInfo;
import com.mall.admin.application.api.BaseApi;
import com.mall.admin.application.service.external.camunda.IdentityMgrService;
import com.mall.admin.application.service.sm.EmpMgrService;
import com.mall.admin.domain.entity.sm.Emp;
import com.mall.common.office.excel.IPageExcel;
import com.mall.common.office.excel.PageExcelHandler;
import com.mall.common.response.PageResult;
import com.mall.common.response.ResponseResult;
import com.mall.common.utils.StringUtils;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

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
            res= ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
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
            empMgrService.addEmp(params);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res= ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
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
            res= ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
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
            res= ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
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
            res= ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }
    @PostMapping(value = "/export",produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ApiOperation("导出员工列表")
    public void export(HttpServletResponse response){
        Map<String,String> titles=new HashMap<>();
        titles.put("badge","员工号");
        titles.put("empName","姓名");
        titles.put("orgName","所属机构");
        titles.put("depName","所属部门");
        titles.put("headShipName","职务");
        PageExcelHandler pageExcelHandler=new PageExcelHandler(titles);
        try{
            pageExcelHandler.generateExcelSheet(response.getOutputStream(), new IPageExcel() {
                public PageInfo getSheetRowDataList(Map<String, Object> params) {
                    List dataList;
                    PageResult pageResult=new PageResult();
                    pageResult.doStartPage(params);
                    dataList=empMgrService.getEmpList(params);
                    pageResult.doFinishPage(dataList);
                    return pageResult.doFinishPage(dataList);
                }
            });
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
    }
}
