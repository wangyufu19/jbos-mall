package com.mall.application.controller;


import com.mall.application.entity.User;
import com.mall.application.service.IUserService;
import com.mall.common.response.ResponseResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author k0091
 * @since 2023-01-11
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;
    /**
     * 查询用户信息数据
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getUserList", method = RequestMethod.GET)
    @ApiOperation("查询用户信息数据")
    public ResponseResult getUserList(@RequestParam Map<String, Object> params){
        ResponseResult res= ResponseResult.ok();
        List<User> users=userService.getUserList(params);
        res.setData(users);
        return res;
    }
    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.GET)
    @ApiOperation("保存用户信息数据")
    public ResponseResult saveUser(@RequestParam Map<String, Object> params){
        ResponseResult res= ResponseResult.ok();
        User user=new User();
        user.setId("1111111111");
        user.setUsername("save123");
        userService.save(user);
        return res;
    }
}
