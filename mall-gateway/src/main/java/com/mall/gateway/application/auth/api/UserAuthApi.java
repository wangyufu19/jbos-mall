package com.mall.gateway.application.auth.api;

import com.mall.common.utils.StringUtils;
import com.mall.gateway.common.util.WebRequestUtil;
import com.mall.gateway.common.websecurity.jwt.JwtTokenProvider;
//import com.mall.gateway.common.websecurity.user.UserDetailsExt;
import com.mall.common.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@Api(tags = "用户认证接口")

public class UserAuthApi {
    public static final String USERNAME_PARAMETER = "username";
    public static final String PASSWORD_PARAMETER = "password";
    public static final String NICKNAME_PARAMETER = "nickName";
    public static final String DEPID_PARAMETER = "depId";
    public static final String ORGID_PARAMETER = "orgId";
    /**
     * jwtTokenProvider
     */
    @Autowired
    private JwtTokenProvider jwtTokenProvider;


    //@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @ResponseBody
    @GetMapping("/getPrincipalInfo")
    @ApiOperation("得到用户凭据信息")
    public ResponseResult getPrincipalInfo(ServerWebExchange exchange) {
        ResponseResult res = ResponseResult.ok();
        String accessToken = WebRequestUtil.getRequestToken(exchange.getRequest());
        Map<String, Object> data = new HashMap<String, Object>();
        data.put(UserAuthApi.USERNAME_PARAMETER, jwtTokenProvider.getSignDataFromJWT(accessToken, UserAuthApi.USERNAME_PARAMETER));
        data.put(UserAuthApi.NICKNAME_PARAMETER, jwtTokenProvider.getSignDataFromJWT(accessToken, UserAuthApi.NICKNAME_PARAMETER));
        data.put(UserAuthApi.DEPID_PARAMETER, jwtTokenProvider.getSignDataFromJWT(accessToken, UserAuthApi.DEPID_PARAMETER));
        data.put(UserAuthApi.ORGID_PARAMETER, jwtTokenProvider.getSignDataFromJWT(accessToken, UserAuthApi.ORGID_PARAMETER));
        res.setData(data);
        return res;
    }
//
//    @ResponseBody
//    @PostMapping("/login")
//    @ApiOperation("用户登录")
//    public ResponseResult login(@RequestBody Map<String, Object> params) {
//        ResponseResult res = ResponseResult.ok("登录成功！");
//        Map<String, String> signData = new HashMap<String, String>();
//        String username = StringUtils.replaceNull(params.get("username"));
//        signData.put(UserAuthApi.USERNAME_PARAMETER, username);
//        String token = jwtTokenProvider.generateToken(signData);
//        Map<String, Object> data = new HashMap<String, Object>();
//        data.put(UserAuthApi.USERNAME_PARAMETER, username);
//        data.put(UserAuthApi.NICKNAME_PARAMETER, username);
//        data.put(JwtTokenProvider.TOKEN, token);
//        res.setData(data);
//        return res;
//    }
//    @ResponseBody
//    @PostMapping("/logout")
//    @ApiOperation("用户登出")
//    public ResponseResult logout() {
//        ResponseResult res = ResponseResult.ok();
//        return res;
//    }
    @ResponseBody
    @PostMapping("/freshToken")
    @ApiOperation("刷新Token")
    public ResponseResult freshToken(ServerWebExchange exchange) {
        ResponseResult res = ResponseResult.ok();
        String accessToken = WebRequestUtil.getRequestToken(exchange.getRequest());
        String freshToken = jwtTokenProvider.freshToken(accessToken);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put(JwtTokenProvider.FRESH_TOKEN, freshToken);
        res.setData(data);
        return res;
    }
}
