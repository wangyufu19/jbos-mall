package com.mall.auth.application.api;

import com.mall.auth.common.user.JwtUser;
import com.mall.common.response.ResponseData;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/principal")
public class UserAuthApi {

    @ResponseBody
    @RequestMapping(value = "/getPrincipalInfo", method = RequestMethod.GET)
    public ResponseData getPrincipalInfo(@RequestParam Map<String, Object> params) {
        ResponseData responseData=ResponseData.ok();
        //获取用户对象
        JwtUser principal = (JwtUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<String,Object> data=new HashMap<String,Object>();
        data.put("username",principal.getUsername());
        responseData.setData(data);
        return responseData;
    }
}
