package com.mall.admin.application.api.sm;
import com.mall.admin.application.external.auth.UserAuthService;
import com.mall.admin.application.service.FuncMgrService;
import com.mall.admin.application.service.UserMgrService;
import com.mall.admin.common.config.FeignConfig;
import com.mall.admin.common.jwt.JwtTokenProvider;
import com.mall.admin.common.utils.RequestContextUtils;
import com.mall.admin.domain.entity.Func;
import com.mall.common.response.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * UserMgrApi
 * @author youfu.wang
 * @date 2019-01-31
 */
@RestController
@RequestMapping("/user")
@Api("用户管理接口")
@Slf4j
public class UserMgrApi {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private UserAuthService userAuthService;
    @Autowired
    private UserMgrService userMgrService;
    @Autowired
    private FuncMgrService funcMgrService;

    private String getRequestToken() {
        // 获得request对象
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String accessToken = request.getHeader("accessToken");
        if (accessToken == null) {
            return request.getParameter("accessToken");
        } else {
            return accessToken;
        }
    }

    /**
     * 查询用户信息数据
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getUserInfo", method = RequestMethod.GET)
    @ApiOperation("查询用户信息数据")
    public ResponseData getUserInfo(@RequestParam Map<String, Object> params){
        ResponseData res=ResponseData.ok();
        try{
            res=userAuthService.getPrincipalInfo(params);
//            String username = JwtTokenProvider.getSignDataFromJWT(this.getRequestToken(), "username");
//            res.setData(userMgrService.getUserInfo(username));
        }catch (Exception e){
            log.error(e.getMessage());
            res=ResponseData.error(e.getMessage());
        }
        return res;
    }
    /**
     * 新增用户信息数据
     * @param params
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/add")
    @ApiOperation("新增用户信息数据")
    public ResponseData add(@RequestBody Map<String, Object> params){
        ResponseData res=ResponseData.ok();
        try{
            userMgrService.addUserInfo(params);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res=ResponseData.error(ResponseData.RETCODE_FAILURE,ResponseData.RETMSG_FAILURE);
        }
        return res;
    }
    /**
     * 查询用户菜单导航数据
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getUserFunc", method = RequestMethod.GET)
    @ApiOperation("查询用户功能数据")
    public ResponseData getUserFunc(@RequestParam Map<String, Object> params) {
        ResponseData responseData=ResponseData.ok();
        String username = jwtTokenProvider.getSignDataFromJWT(this.getRequestToken(), "username");
        List<Func> funcRouteList = null;
        funcRouteList=funcMgrService.getUserFuncList(username,username);
        responseData.setData(funcRouteList);
        return responseData;
    }
}
