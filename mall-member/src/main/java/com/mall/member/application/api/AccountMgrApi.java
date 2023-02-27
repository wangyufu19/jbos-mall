package com.mall.member.application.api;

import com.mall.common.response.ResponseResult;
import com.mall.common.utils.StringUtils;
import com.mall.member.application.external.admin.UserMgrService;
import com.mall.member.application.service.AccountService;
import com.mall.member.domain.entity.Account;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
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
    @Autowired
    private UserMgrService userMgrService;
    /**
     * 会员登录
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ApiOperation("会员登录")
    public ResponseResult login(@RequestBody Map<String, Object> params){
        ResponseResult res= ResponseResult.ok();
        try{
            params.put("id", UUID.randomUUID().toString());
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res= ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
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
    public ResponseResult registry(@RequestBody Map<String, Object> params){
        ResponseResult res= ResponseResult.ok();
        try{
            //会员账户是否存在
            if(accountService.isExists(params)){
                res= ResponseResult.error(ResponseResult.CODE_FAILURE,"对不起，该手机或邮箱已存在!");
                return res;
            }
            Map<String, Object> paramMap=new HashMap<String, Object>();
            paramMap.put("loginName",params.get("account"));
            paramMap.put("username",params.get("account"));
            paramMap.put("password",params.get("password"));
            params.put("seqId", UUID.randomUUID().toString());
            accountService.registry(params);
            res=userMgrService.add(paramMap);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res= ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }
    /**
     * 会员锁定"
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/lock", method = RequestMethod.POST)
    @ApiOperation("会员锁定")
    public ResponseResult lock(@RequestBody Map<String, Object> params){
        ResponseResult res= ResponseResult.ok();
        try{
            Account account=new Account();
            account.setAccount(StringUtils.replaceNull(params.get("account")));
            account.setStatus(Account.ACCT_LOCKED);
            accountService.updateAccountStatus(account);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res= ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }
    /**
     * 会员解锁
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/unlock", method = RequestMethod.POST)
    @ApiOperation("会员解锁")
    public ResponseResult unlock(@RequestBody Map<String, Object> params){
        ResponseResult res= ResponseResult.ok();
        try{
            Account account=new Account();
            account.setAccount(StringUtils.replaceNull(params.get("account")));
            account.setStatus(Account.ACCT_NORMAL);
            accountService.updateAccountStatus(account);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res= ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }
}
