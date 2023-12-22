package com.mall.admin.application.api.sm;

import cn.afterturn.easypoi.word.WordExportUtil;
import cn.afterturn.easypoi.word.entity.MyXWPFDocument;
import com.alibaba.excel.EasyExcel;
import com.github.pagehelper.PageInfo;
import com.mall.admin.application.service.sm.EmpMgrService;
import com.mall.admin.domain.entity.sm.Emp;
import com.mall.admin.infrastructure.camunda.IdentityMgrService;
import com.mall.common.page.PageParam;
import com.mall.common.office.excel.IPageExcel;
import com.mall.common.office.excel.PageExcelHandler;
import com.mall.common.response.ResponsePageResult;
import com.mall.common.response.ResponseResult;
import com.mall.common.utils.DateUtils;
import com.mall.common.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
@Api(tags = "员工管理接口")
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
    @ApiOperation("查询机构人员")
    public ResponsePageResult getEmpList(@RequestParam Map<String, Object> params) {
        ResponsePageResult res;
        try {
            PageParam pageParam = PageParam.getPageParam(params);
            List<Emp> empList = empMgrService.getEmpList(pageParam, params);
            res = ResponsePageResult.ok().isPage(true).setData(empList);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponsePageResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
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
    public void export(HttpServletResponse response, @RequestParam Map<String, Object> params) throws Exception {
        PageParam pageParam = PageParam.getPageParam(params);
        List<Emp> empList = empMgrService.getEmpList(pageParam, params);
//        EasyExcel.write(response.getOutputStream(), Emp.class).sheet("员工列表").doWrite(empList);
        ClassPathResource templatePath = new ClassPathResource("public/员工信息表.docx");
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("title", "员工信息表");
        dataMap.put("empList", empList);
        dataMap.put("date", DateUtils.format(DateUtils.getCurrentDate()));
        XWPFDocument document = new MyXWPFDocument(templatePath.getInputStream());
        WordExportUtil.exportWord07(document, dataMap);
        File file = new File("/opt/tmp/员工信息表.docx");
        FileOutputStream outputStream = new FileOutputStream(file);
        document.write(outputStream);
        outputStream.close();
    }
}
