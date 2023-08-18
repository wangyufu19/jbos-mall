package com.mall.admin.application.api.mm;

import com.mall.common.response.ResponseResult;
import com.mall.common.utils.StringUtils;
import com.mall.admin.application.service.mm.AccountService;
import com.mall.admin.domain.entity.mm.Account;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

/**
 * AccountMgrApi
 *
 * @author youfu.wang
 * @date 2021-08-19
 */
@RestController
@RequestMapping("/member/account")
@Api("账户管理接口")
@Slf4j
public class AccountMgrApi {
    @Autowired
    private AccountService accountService;

    /**
     * 会员登录
     *
     * @param params
     * @return res
     */
    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ApiOperation("会员登录")
    public ResponseResult login(@RequestBody Map<String, Object> params) {
        ResponseResult res = ResponseResult.ok();
        try {
            params.put("id", UUID.randomUUID().toString());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }

    /**
     * 会员注册
     *
     * @param params
     * @return res
     */
    @ResponseBody
    @RequestMapping(value = "/registry", method = RequestMethod.POST)
    @ApiOperation("会员注册")
    public ResponseResult registry(@RequestBody Map<String, Object> params) {
        ResponseResult res = ResponseResult.ok();
        try {
            //会员账户是否存在
            if (accountService.isExists(params)) {
                res = ResponseResult.error(ResponseResult.CODE_FAILURE, "对不起，该手机或邮箱已存在!");
                return res;
            }
            accountService.registry(params);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }

    /**
     * 会员锁定"
     *
     * @param params
     * @return res
     */
    @ResponseBody
    @RequestMapping(value = "/lock", method = RequestMethod.POST)
    @ApiOperation("会员锁定")
    public ResponseResult lock(@RequestBody Map<String, Object> params) {
        ResponseResult res = ResponseResult.ok();
        try {
            Account account = new Account();
            account.setAccount(StringUtils.replaceNull(params.get("account")));
            account.setStatus(Account.ACCT_LOCKED);
            accountService.updateAccountStatus(account);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }

    /**
     * 会员解锁
     *
     * @param params
     * @return res
     */
    @ResponseBody
    @RequestMapping(value = "/unlock", method = RequestMethod.POST)
    @ApiOperation("会员解锁")
    public ResponseResult unlock(@RequestBody Map<String, Object> params) {
        ResponseResult res = ResponseResult.ok();
        try {
            Account account = new Account();
            account.setAccount(StringUtils.replaceNull(params.get("account")));
            account.setStatus(Account.ACCT_NORMAL);
            accountService.updateAccountStatus(account);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }
}
