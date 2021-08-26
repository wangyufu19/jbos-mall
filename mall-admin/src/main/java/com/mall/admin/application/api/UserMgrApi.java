package com.mall.admin.application.api;

import com.mall.admin.application.external.UserAuthService;
import com.mall.admin.application.service.FuncMgrService;
import com.mall.admin.application.service.UserMgrService;
import com.mall.admin.domain.entity.Func;
import com.mall.common.response.ResponseData;
import com.mall.common.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Api("用户管理接口")
public class UserMgrApi {
    @Autowired
    private UserAuthService userAuthService;
    @Autowired
    private UserMgrService userMgrService;
    @Autowired
    private FuncMgrService funcMgrService;

    /**
     * 查询用户信息数据
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getUserInfo", method = RequestMethod.GET)
    @ApiOperation("查询用户信息数据")
    public ResponseData getUserInfo(@RequestParam Map<String, Object> params){
        ResponseData responseData=ResponseData.ok();
        ResponseData principalInfo=userAuthService.getPrincipalInfo(params);
        String username="";
        if(principalInfo!=null){
            if(!ResponseData.RETCODE_SUCCESS.equals(principalInfo.getRetCode())){
                return principalInfo;
            }
            username= StringUtils.replaceNull(((Map) principalInfo.getData()).get("username"));
        }
        responseData.setData(userMgrService.getUserInfoByLoginName(username));
        return responseData;
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
        ResponseData principalInfo=userAuthService.getPrincipalInfo(params);
        String username="";
        if(principalInfo!=null){
            if(!ResponseData.RETCODE_SUCCESS.equals(principalInfo.getRetCode())){
                return principalInfo;
            }
            username= StringUtils.replaceNull(((Map) principalInfo.getData()).get("username"));
        }
        List<Func> funcRouteList = null;
        funcRouteList=funcMgrService.getUserFuncList(username,username);
        Map<String,Object> data=new HashMap<String,Object>();
        data.put("userFuncs",funcRouteList);
        responseData.setData(data);
        return responseData;
    }
}
