package com.mall.admin.application.api.sm;

import com.github.pagehelper.PageInfo;
import com.mall.admin.application.service.sm.EmpMgrService;
import com.mall.admin.domain.entity.sm.Emp;
import com.mall.admin.infrastructure.camunda.IdentityMgrService;
import com.mall.common.page.PageParam;
import com.mall.common.office.excel.IPageExcel;
import com.mall.common.office.excel.PageExcelHandler;
import com.mall.common.response.ResponseResult;
import com.mall.common.utils.StringUtils;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * EmpMgrApi
 *
 * @author youfu.wang
 * @date 2019-01-31
 */
@Slf4j
@RestController
@RequestMapping("/emp")
public class EmpMgrApi {
    //@Value("${spring.servlet.upload.repo}")
    private String uploadRepo = "/nas/upload";
    @Autowired
    private EmpMgrService empMgrService;
    @Autowired
    private IdentityMgrService identityMgrService;

    /**
     * 查询组织机构员工数据
     *
     * @param params
     * @return ResponseResult
     */
    @ResponseBody
    @GetMapping("/getEmpList")
    public ResponseResult getEmpList(@RequestParam Map<String, Object> params) {
        ResponseResult res;
        try {
            PageParam pageParam = PageParam.getPageParam(params);
            List<Emp> empList = empMgrService.getEmpList(pageParam, params);
            res = ResponseResult.ok().isPage(true).setData(empList);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }

    /**
     * 新增人员
     *
     * @param params
     * @return ResponseResult
     */
    @ResponseBody
    @PostMapping("/addEmp")
    @ApiOperation("新增人员")
    public ResponseResult addEmp(@RequestBody Map<String, Object> params) {
        ResponseResult res = ResponseResult.ok();
        try {
            empMgrService.addEmp(params);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }

    /**
     * 修改人员
     *
     * @param params
     * @return ResponseResult
     */
    @ResponseBody
    @PostMapping("/updateEmp")
    @ApiOperation("修改人员")
    public ResponseResult updateEmp(@RequestBody Map<String, Object> params) {
        ResponseResult res = ResponseResult.ok();
        try {
            empMgrService.updateEmp(params);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }

    /**
     * 删除人员
     *
     * @param params
     * @return ResponseResult
     */
    @ResponseBody
    @PostMapping("/deleteEmp")
    @ApiOperation("删除人员")
    public ResponseResult deleteEmp(@RequestBody Map<String, Object> params) {
        ResponseResult res = ResponseResult.ok();
        String id = StringUtils.replaceNull(params.get("id"));
        try {
            empMgrService.deleteEmp(params);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }

    /**
     * 删除人员
     *
     * @param params
     * @return ResponseResult
     */
    @ResponseBody
    @PostMapping("/synchToCamunda")
    @ApiOperation("同步到CAMUNDA")
    public ResponseResult synchToCamunda(@RequestBody Map<String, Object> params) {
        ResponseResult res = ResponseResult.ok();
        String userId = StringUtils.replaceNull(params.get("userId"));
        String userName = StringUtils.replaceNull(params.get("userName"));
        try {
            identityMgrService.createUser(userId, userName);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }

    @PostMapping(value = "/export", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ApiOperation("导出员工列表")
    public void export(HttpServletResponse response) {
        Map<String, String> titles = new HashMap<>();
        titles.put("badge", "员工号");
        titles.put("empName", "姓名");
        titles.put("orgName", "所属机构");
        titles.put("depName", "所属部门");
        titles.put("headShipName", "职务");
        PageExcelHandler pageExcelHandler = new PageExcelHandler(titles);
        try {
            pageExcelHandler.generateExcel(response.getOutputStream(), new IPageExcel() {
//                public int getPageCount() {
//                    List<Emp> empList = empMgrService.getEmpList(PageParam.getPageParam(), null);
//                    PageInfo pageInfo = new PageInfo(empList);
//                    int total = (int) pageInfo.getTotal();
//                    if (total >= IPageExcel.SHEET_MAX_ROW) {
//                        total = IPageExcel.SHEET_MAX_ROW;
//                    }
//                    return total;
//                }

                public PageInfo getPageDataList(int page, int limit) {
                    log.info("******读取数据[page={},limit={}]", page, limit);
                    PageParam pageParam = PageParam.getPageParam(page, limit);
                    Map<String, Object> params = new HashMap<>();
                    List<Emp> empList = empMgrService.getEmpList(pageParam, params);
                    PageInfo pageInfo = new PageInfo(empList);
                    return pageInfo;
                }
            }, PageParam.DEFAULT_PAGE_SIZE);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
