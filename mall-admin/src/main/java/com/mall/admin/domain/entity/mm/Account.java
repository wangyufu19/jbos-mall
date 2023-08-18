package com.mall.admin.domain.entity.mm;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Member
 *
 * @author youfu.wang
 * @date 2021-08-19
 */
@Setter
@Getter
@TableName("MM_ACCOUNT")
public class Account {
    /**
     * 正常
     */
    public static final String ACCT_NORMAL = "10";
    /**
     * 锁定
     */
    public static final String ACCT_LOCKED = "99";
    /**
     * seqId
     */
    @TableId("seq_id")
    private String seqId;
    /**
     * 账户号
     */
    @TableField("account")
    private String account;
    /**
     * 状态
     */
    @TableField("status")
    private String status;
    /**
     * 更新时间
     */
    @TableField(exist = false)
    private Date updateTime;
}
