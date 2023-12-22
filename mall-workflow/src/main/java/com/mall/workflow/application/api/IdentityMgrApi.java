package com.mall.workflow.application.api;

import com.mall.common.response.ResponseResult;
import com.mall.workflow.application.service.IdentityMgrService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

/**
 * IdentityMgrApi
 * @author youfu.wang
 * @date 2019-01-31
 */
@Slf4j
@RestController
@RequestMapping("/user")
@Api("身份管理接口")
public class IdentityMgrApi {
    @Autowired
    private IdentityMgrService identityMgrService;

    @ResponseBody
    @PostMapping(value = "/create")
    @ApiOperation("创建用户")
    public ResponseResult create(@RequestBody Map<String, Object> params){
        ResponseResult res= ResponseResult.ok();
        String userId=String.valueOf(params.get("userId"));
        String userName= String.valueOf(params.get("userName"));
        try {
            identityMgrService.createUser(userId,userName);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res= ResponseResult.error(ResponseResult.CODE_FAILURE,e.getMessage());
        }
        if(log.isDebugEnabled()){
            log.info("============创建用户[" +
                    "userId="+userId+";" +
                    "userName="+userName+";" +
                    "]");
        }
        return res;
    }
}
