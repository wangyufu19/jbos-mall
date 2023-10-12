package com.mall.admin.application.api.mm;

import com.mall.common.page.PageParam;
import com.mall.common.response.ResponsePageResult;
import com.mall.common.response.ResponseResult;
import com.mall.common.utils.StringUtils;
import com.mall.admin.application.service.mm.MemberService;
import com.mall.admin.domain.entity.mm.Member;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

/**
 * MemberMgrApi
 *
 * @author youfu.wang
 * @date 2021-08-19
 */
@RestController
@RequestMapping("/member/info")
@Api(tags = "会员管理接口")
@Slf4j
public class MemberMgrApi {
    @Autowired
    private MemberService memberService;

    /**
     * 得到会员信息列表
     *
     * @param params
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/list")
    @ApiOperation("得到会员信息列表")
    public ResponsePageResult list(@RequestParam Map<String, Object> params) {
        ResponsePageResult res;
        try {
            res = memberService.getMemberList(PageParam.getPageParam(params),params);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponsePageResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }

    /**
     * 得到会员信息
     *
     * @param params
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/get")
    @ApiOperation("得到会员信息")
    public ResponseResult get(@RequestParam Map<String, Object> params) {
        ResponseResult res = ResponseResult.ok();
        try {
            Member member = memberService.getMemberInfo(params);
            res.setData(member);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }

    /**
     * 新增会员信息
     *
     * @param params
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/add")
    @ApiOperation("新增会员个人信息")
    public ResponseResult add(@RequestBody Map<String, Object> params) {
        ResponseResult res = ResponseResult.ok();
        try {
            Member member = new Member();
            member.setSeqId(UUID.randomUUID().toString());
            memberService.addMemberInfo(member);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }

    /**
     * 修改会员信息
     *
     * @param params
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/update")
    @ApiOperation("修改会员个人信息")
    public ResponseResult update(@RequestBody Map<String, Object> params) {
        ResponseResult res = ResponseResult.ok();
        try {
            Member member = new Member();
            member.setAccount(StringUtils.replaceNull(params.get("account")));
            member.setFullName(StringUtils.replaceNull(params.get("fullName")));
            member.setNickName(StringUtils.replaceNull(params.get("nickName")));
            member.setSex(Integer.parseInt(StringUtils.replaceNull(params.get("sex"))));
            member.setMobilePhone(StringUtils.replaceNull(params.get("mobilePhone")));
            member.setEmail(StringUtils.replaceNull(params.get("email")));
            memberService.updateMemberInfo(member);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }
}
