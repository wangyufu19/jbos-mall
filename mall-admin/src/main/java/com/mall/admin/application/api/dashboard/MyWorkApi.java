package com.mall.admin.application.api.dashboard;

import com.mall.admin.application.api.BaseApi;
import com.mall.admin.application.service.external.camunda.TaskService;
import com.mall.admin.application.service.sm.UserMgrService;
import com.mall.common.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/dashboard")
@Slf4j
@Api("我的工作台接口")
public class MyWorkApi extends BaseApi {
    @Autowired
    private TaskService taskService;
    @Autowired
    private UserMgrService userMgrService;
    /**
     * 查询我的待办列表
     * @return
     */
    @ResponseBody
    @GetMapping("/getMyWorkList")
    @ApiOperation("查询我的待办列表")
    public ResponseResult getMyWorkList(@RequestParam Map<String, Object> params){
        ResponseResult res= ResponseResult.ok();
        try{
            //res=taskService.listForPage(params);
            res.setData(userMgrService.getUserWorkList(params));
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res= ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }
}
