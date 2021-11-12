package com.mall.member.application.api;

import com.mall.common.response.ResponseData;
import com.mall.member.application.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

/**
 * AccountMgrApi
 * @author youfu.wang
 * @date 2021-08-19
 */
@RestController
@RequestMapping("/account")
@Api("账户管理接口")
@Slf4j
public class AccountMgrApi {
    @Autowired
    private AccountService accountService;

    /**
     * 会员登录
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ApiOperation("会员登录")
    public ResponseData login(@RequestBody Map<String, Object> params){
        ResponseData res= ResponseData.ok();
        try{
            params.put("id", UUID.randomUUID().toString());
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res=ResponseData.error(ResponseData.RETCODE_FAILURE,ResponseData.RETMSG_FAILURE);
        }
        return res;
    }
    /**
     * 会员注册
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/registry", method = RequestMethod.POST)
    @ApiOperation("会员注册")
    public ResponseData registry(@RequestBody Map<String, Object> params){
        ResponseData res= ResponseData.ok();
        String password=String.valueOf(params.get("password"));
        PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
        params.put("password",passwordEncoder.encode(password));
        try{
            params.put("seqId", UUID.randomUUID().toString());
            accountService.registry(params);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res=ResponseData.error(ResponseData.RETCODE_FAILURE,ResponseData.RETMSG_FAILURE);
        }
        return res;
    }
}
