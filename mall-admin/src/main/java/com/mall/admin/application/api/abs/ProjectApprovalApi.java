package com.mall.admin.application.api.abs;

import com.mall.admin.application.request.abs.ProjectApprovalRequestDto;
import com.mall.admin.application.response.abs.ProjectApprovalResponseDto;
import com.mall.admin.application.service.abs.ProjectApprovalService;
import com.mall.admin.domain.entity.abs.ProjectInfo;
import com.mall.common.page.PageParam;
import com.mall.common.response.ResponsePageResult;
import com.mall.common.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * ProjectApprovalApi
 *
 * @author youfu.wang
 * @date 2023/8/21
 **/
@RestController
@RequestMapping("/projectApproval")
@Api(tags = "项目立项接口")
@Slf4j
public class ProjectApprovalApi {
    @Autowired
    private ProjectApprovalService projectApprovalService;

    @ResponseBody
    @GetMapping(value = "/getProjectList")
    @ApiOperation("查询项目立项信息")
    public ResponsePageResult getProjectList(@RequestParam Map<String, Object> params) {
        ResponsePageResult res = ResponsePageResult.ok();
        try {
            PageParam pageParam = PageParam.getPageParam(params);
            List<ProjectInfo> projectList = projectApprovalService.getProjectList(pageParam, params);
            res.setData(projectList);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponsePageResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }

    @ResponseBody
    @GetMapping(value = "/getRegistrationInfo")
    @ApiOperation("查询项目登记信息")
    public ResponseResult getRegistrationInfo(@RequestParam Map<String, Object> params) {
        ResponseResult res = ResponseResult.ok();
        String id = String.valueOf(params.get("id"));
        try {
            ProjectApprovalResponseDto dto = projectApprovalService.getProjectApproval(id);
            res.setData(dto);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }

    @ResponseBody
    @PostMapping(value = "/registration")
    @ApiOperation("项目立项登记或变更")
    public ResponseResult registration(@RequestBody ProjectApprovalRequestDto projectApprovalDto) {
        ResponseResult res = ResponseResult.ok();
        try {
            projectApprovalService.addOrUpdateProjectApproval(projectApprovalDto);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }

    @ResponseBody
    @PostMapping(value = "/deleteRegistrationInfo")
    @ApiOperation("删除项目登记信息")
    public ResponseResult deleteRegistrationInfo(@RequestBody Map<String, Object> params) {
        ResponseResult res = ResponseResult.ok();
        String id = String.valueOf(params.get("id"));
        String projectNo = String.valueOf(params.get("projectNo"));
        try {
            projectApprovalService.deleteProjectInfo(id, projectNo);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }
}
