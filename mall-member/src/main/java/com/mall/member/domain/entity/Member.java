package com.mall.member.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

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
    @TableField("account")
    private String account;
    @TableField("nick_name")
    private String nickName;
    @TableField("full_name")
    private String fullName;
    @TableField("sex")
    private int sex;
    @TableField("mobile_phone")
    private String mobilePhone;
    @TableField("email")
    private String email;
    @TableField("grade")
    private String grade;
    @TableField("integral")
    private String integral;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("registry_time")
    private Date registryTime;
    @TableField(exist=false)
    private int isValid;
    @TableField("create_time")
    private Date createTime;
    @TableField(exist=false)
    private Date updateTime;

}
