package com.mall.admin.application.api.dashboard;

import com.mall.admin.application.api.BaseApi;
import com.mall.admin.application.external.TaskService;
import com.mall.common.response.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dashboard")
@Slf4j
@Api("我的工作台接口")
public class MyWorkApi extends BaseApi {
    @Autowired
    private TaskService taskService;
    /**
     * 查询我的待办列表
     * @return
     */
    @ResponseBody
    @GetMapping("/getMyWorkList")
    @ApiOperation("查询我的待办列表")
    public ResponseData getMyWorkList(@RequestParam Map<String, Object> params){
        ResponseData res=ResponseData.ok();
        try{
            res=taskService.listForPage(params);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res=ResponseData.error(ResponseData.RETCODE_FAILURE,ResponseData.RETMSG_FAILURE);
        }
        return res;
    }
}
