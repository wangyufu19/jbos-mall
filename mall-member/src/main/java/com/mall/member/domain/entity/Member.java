package com.mall.member.domain.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * Member
 * @author youfu.wang
 * @date 2021-08-19
 */
@Setter
@Getter
@TableName("MM_MEMBER_BASE")
public class Member {
    @TableField("seq_id")
    private String seqId;
    @TableField("nick_name")
    private String nickName;
    @TableField("full_name")
    private String fullName;
    @TableField("sex")
    private String sex;
    @TableField("mobile_phone")
    private String mobilePhone;
    @TableField("grade")
    private String grade;
    @TableField("integral")
    private String integral;
    @TableField(exist=false)
    private String registryTime;
    @TableField(exist=false)
    private int isValid;
    @TableField(exist=false)
    private String createTime;
    @TableField(exist=false)
    private String updateTime;

}
