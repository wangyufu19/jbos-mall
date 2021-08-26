package com.mall.auth.application.api;

import com.mall.auth.common.user.JwtUser;
import com.mall.common.response.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/principal")
@Api("用户认证接口")
public class UserAuthApi {

    @ResponseBody
    @RequestMapping(value = "/getPrincipalInfo", method = RequestMethod.GET)
    @ApiOperation("得到用户凭据信息")
    public ResponseData getPrincipalInfo(@RequestParam Map<String, Object> params) {
        ResponseData responseData=ResponseData.ok();
        //获取用户对象
        JwtUser principal = (JwtUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<String,Object> data=new HashMap<String,Object>();
        data.put("username",principal.getUsername());
        data.put("userInfo",principal.getUserInfo());
        responseData.setData(data);
        return responseData;
    }
}
