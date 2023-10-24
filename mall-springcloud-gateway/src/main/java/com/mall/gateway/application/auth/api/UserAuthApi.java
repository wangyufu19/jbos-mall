package com.mall.gateway.application.auth.api;

import com.mall.common.response.ResponseResult;
import com.mall.gateway.common.websecurity.jwt.JwtTokenProvider;
import com.mall.gateway.common.websecurity.user.JwtUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@Api(tags = "用户认证接口")

public class UserAuthApi {
    /**
     * jwtTokenProvider
     */
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private String getRequestToken() {
        // 获得request对象
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String accessToken = request.getHeader(JwtTokenProvider.TOKEN);
        if (accessToken == null) {
            return request.getParameter(JwtTokenProvider.TOKEN);
        } else {
            return accessToken;
        }
    }

    //@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @ResponseBody
    @RequestMapping(value = "/getPrincipalInfo", method = RequestMethod.GET)
    @ApiOperation("得到用户凭据信息")
    public ResponseResult getPrincipalInfo(@RequestParam Map<String, Object> params) {
        ResponseResult res = ResponseResult.ok();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String accessToken = this.getRequestToken();
        //获取用户对象
        String username = jwtTokenProvider.getSignDataFromJWT(accessToken, "username");
        String nickName = jwtTokenProvider.getSignDataFromJWT(accessToken, "nickName");
        String depId = jwtTokenProvider.getSignDataFromJWT(accessToken, "depId");
        String depName = jwtTokenProvider.getSignDataFromJWT(accessToken, "depName");
        String orgId = jwtTokenProvider.getSignDataFromJWT(accessToken, "orgId");
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("username", username);
        data.put("nickName", nickName);
        data.put("depId", depId);
        data.put("depName", depName);
        data.put("orgId", orgId);
        res.setData(data);
        return res;
    }

    @ResponseBody
    @PostMapping("/freshToken")
    @ApiOperation("刷新Token")
    public ResponseResult freshToken() {
        ResponseResult res = ResponseResult.ok();
        String token = this.getRequestToken();
        String freshToken = jwtTokenProvider.freshToken(token);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("freshToken", freshToken);
        res.setData(data);
        return res;
    }
}
