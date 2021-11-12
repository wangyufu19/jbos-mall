package com.mall.member.application.service;

import com.mall.member.domain.entity.Member;
import com.mall.member.infrastructure.repository.MemberRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * MemberService
 * @author youfu.wang
 * @date 2021-08-19
 */
@Component
public class MemberService {
    @Autowired
    private MemberRepo memberRepo;

    public List<Member> getMemberList(Map<String, Object> parameterObject){
        return this.memberRepo.getMemberList(parameterObject);
    }
    public Map<String, Object> getMemberInfo(Map<String, Object> parameterObject){
        return this.memberRepo.getMemberInfo(parameterObject);
    }
    public void addMemberInfo(Map<String, Object> parameterObject){
        this.memberRepo.addMemberInfo(parameterObject);
    }

    public void updateMemberInfo(Map<String, Object> parameterObject){
        this.memberRepo.updateMemberInfo(parameterObject);
    }
}
