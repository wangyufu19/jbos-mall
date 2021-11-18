package com.mall.member.application.api;

import com.mall.common.response.BaseApi;
import com.mall.common.response.ResponseData;
import com.mall.common.utils.StringUtils;
import com.mall.member.application.service.MemberService;
import com.mall.member.domain.entity.Member;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * MemberMgrApi
 * @author youfu.wang
 * @date 2021-08-19
 */
@RestController
@RequestMapping("/info")
@Api("会员管理接口")
@Slf4j
public class MemberMgrApi extends BaseApi {
    @Autowired
    private MemberService memberService;
    /**
     * 得到会员信息列表
     * @param params
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/list")
    @ApiOperation("新增会员个人信息")
    public ResponseData list(@RequestParam Map<String, Object> params){
        ResponseData res= ResponseData.ok();
        String isPage= StringUtils.replaceNull(params.get("isPage"));
        try{
            if("true".equals(isPage)){
                this.doStartPage(params);
            }
            List<Member> members=memberService.getMemberList(params);
            if("true".equals(isPage)){
                this.doFinishPage(res,members);
            }else{
                res.setData(members);
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res=ResponseData.error(ResponseData.RETCODE_FAILURE,ResponseData.RETMSG_FAILURE);
        }
        return res;
    }
    /**
     * 得到会员信息
     * @param params
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/get")
    @ApiOperation("得到会员信息")
    public ResponseData get(@RequestParam Map<String, Object> params){
        ResponseData res= ResponseData.ok();
        try{
            Member member=memberService.getMemberInfo(params);
            res.setData(member);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res=ResponseData.error(ResponseData.RETCODE_FAILURE,ResponseData.RETMSG_FAILURE);
        }
        return res;
    }
    /**
     * 新增会员信息
     * @param params
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/add")
    @ApiOperation("新增会员个人信息")
    public ResponseData add(@RequestBody Map<String, Object> params){
        ResponseData res= ResponseData.ok();
        try{
            Member member=new Member();
            member.setSeqId(UUID.randomUUID().toString());
            memberService.addMemberInfo(member);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res=ResponseData.error(ResponseData.RETCODE_FAILURE,ResponseData.RETMSG_FAILURE);
        }
        return res;
    }
    /**
     * 修改会员信息
     * @param params
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/update")
    @ApiOperation("修改会员个人信息")
    public ResponseData update(@RequestBody Map<String, Object> params){
        ResponseData res= ResponseData.ok();
        try{
            Member member=new Member();
            member.setAccount(StringUtils.replaceNull(params.get("account")));
            member.setFullName(StringUtils.replaceNull(params.get("fullName")));
            member.setNickName(StringUtils.replaceNull(params.get("nickName")));
            member.setSex(Integer.parseInt(StringUtils.replaceNull(params.get("sex"))));
            member.setMobilePhone(StringUtils.replaceNull(params.get("mobilePhone")));
            member.setEmail(StringUtils.replaceNull(params.get("email")));
            memberService.updateMemberInfo(member);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res=ResponseData.error(ResponseData.RETCODE_FAILURE,ResponseData.RETMSG_FAILURE);
        }
        return res;
    }
}
