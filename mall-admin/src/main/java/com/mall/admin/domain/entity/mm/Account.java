package com.mall.admin.domain.entity.mm;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("MM_ACCOUNT")
public class Account {
    public final static String ACCT_NORMAL="10";//正常
    public final static String ACCT_LOCKED="99";//锁定
    @TableId("seq_id")
    private String seqId;
    @TableField("account")
    private String account;
    @TableField("status")
    private String status;
    @TableField(exist=false)
    private Date updateTime;
}
