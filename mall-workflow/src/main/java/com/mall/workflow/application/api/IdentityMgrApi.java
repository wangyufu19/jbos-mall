package com.mall.workflow.application.api;

import com.mall.common.response.ResponseData;
import com.mall.common.utils.StringUtils;
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
    public ResponseData create(@RequestBody Map<String, Object> params){
        ResponseData res=ResponseData.ok();
        String userId=StringUtils.replaceNull(params.get("userId"));
        String userName= StringUtils.replaceNull(params.get("userName"));
        try {
            identityMgrService.createUser(userId,userName);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res=ResponseData.error(ResponseData.RETCODE_FAILURE,e.getMessage());
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
