package com.mall.member.infrastructure.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.member.domain.entity.Member;
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
