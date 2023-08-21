package com.mall.admin.application.api.abs;

import com.google.gson.Gson;
import com.mall.admin.application.request.ProjectApprovalDto;
import com.mall.admin.application.service.abs.ProjectApprovalService;
import com.mall.admin.domain.entity.abs.ProjectInfo;
import com.mall.common.page.PageParam;
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
@Api("项目立项登记")
@Slf4j
public class ProjectApprovalApi {
    @Autowired
    private ProjectApprovalService projectApprovalService;

    @ResponseBody
    @GetMapping(value = "/getProjectList")
    @ApiOperation("查询项目立项数据")
    public ResponseResult getProjectList(@RequestParam Map<String, Object> params) {
        ResponseResult res = ResponseResult.ok();
        try {
            PageParam pageParam = PageParam.getPageParam(params);
            List<ProjectInfo> projectList = projectApprovalService.getProjectList(pageParam, params);
            res.isPage(true).setData(projectList);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }
    @ResponseBody
    @PostMapping(value = "/registration")
    @ApiOperation("项目立项登记")
    public ResponseResult registration(@RequestBody ProjectApprovalDto projectApprovalDto) {
        ResponseResult res = ResponseResult.ok();
        try {
            projectApprovalService.addProjectInfo(projectApprovalDto.getProjectInfo());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }

}
