//package com.mall.admin.application.api.auth;
//
//import com.mall.admin.common.user.JwtUser;
//import com.mall.common.jwt.JwtTokenProvider;
//import com.mall.common.response.ResponseData;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.HashMap;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/principal")
//@Api("用户认证接口")
//public class UserAuthApi {
//    @Autowired
//    private JwtTokenProvider jwtTokenProvider;
//    private String getRequestToken() {
//        // 获得request对象
//        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = attributes.getRequest();
//        String accessToken = request.getHeader("accessToken");
//        if (accessToken == null) {
//            return request.getParameter("accessToken");
//        } else {
//            return accessToken;
//        }
//    }
//    @ResponseBody
//    @RequestMapping(value = "/getPrincipalInfo", method = RequestMethod.GET)
//    @ApiOperation("得到用户凭据信息")
//    public ResponseData getPrincipalInfo(@RequestParam Map<String, Object> params) {
//        ResponseData responseData=ResponseData.ok();
//        //获取用户对象
//        JwtUser principal = (JwtUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String userInfo = jwtTokenProvider.getSignDataFromJWT(this.getRequestToken(), "userInfo");
//        Map<String,Object> data=new HashMap<String,Object>();
//        data.put("username",principal.getUsername());
//        data.put("userInfo",userInfo);
//        responseData.setData(data);
//        return responseData;
//    }
//}
