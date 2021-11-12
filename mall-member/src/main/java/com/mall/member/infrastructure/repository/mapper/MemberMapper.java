package com.mall.member.infrastructure.repository.mapper;

import com.mall.member.domain.entity.Member;

import java.util.List;
import java.util.Map;

/**
 * AccountMapper
 * @author youfu.wang
 * @date 2021-08-19
 */
public interface MemberMapper {

    public List<Member> getMemberList(Map<String, Object> parameterObject);

    public Map<String, Object> getMemberInfo(Map<String, Object> parameterObject);

    public void addMemberInfo(Map<String, Object> parameterObject);

    public void updateMemberInfo(Map<String, Object> parameterObject);
}
