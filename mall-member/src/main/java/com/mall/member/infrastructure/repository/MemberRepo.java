package com.mall.member.infrastructure.repository;

import com.mall.member.domain.entity.Member;
import com.mall.member.infrastructure.repository.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * MemberRepo
 * @author youfu.wang
 * @date 2021-08-19
 */
@Component
public class MemberRepo {
    @Autowired
    private MemberMapper memberMapper;

    public List<Member> getMemberList(Map<String, Object> parameterObject){
        return this.memberMapper.getMemberList(parameterObject);
    }
    public Map<String, Object> getMemberInfo(Map<String, Object> parameterObject){
        return this.memberMapper.getMemberInfo(parameterObject);
    }
    public void addMemberInfo(Map<String, Object> parameterObject){
        this.memberMapper.addMemberInfo(parameterObject);
    }

    public void updateMemberInfo(Map<String, Object> parameterObject){
        this.memberMapper.updateMemberInfo(parameterObject);
    }
}
