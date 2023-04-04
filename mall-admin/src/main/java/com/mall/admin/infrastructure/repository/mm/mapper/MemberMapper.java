package com.mall.admin.infrastructure.repository.mm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.admin.domain.entity.mm.Member;

import java.util.List;
import java.util.Map;

/**
 * AccountMapper
 * @author youfu.wang
 * @date 2021-08-19
 */
public interface MemberMapper extends BaseMapper<Member> {

    public List<Member> getMemberList(Map<String, Object> parameterObject);

    public Member getMemberInfo(Map<String, Object> parameterObject);

    public void addMemberInfo(Member member);

    public void updateMemberInfo(Member member);
}
