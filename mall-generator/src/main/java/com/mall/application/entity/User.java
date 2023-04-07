package com.mall.application.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author k0091
 * @since 2023-01-11
 */
@Getter
@Setter
@TableName("jbos_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private String id;

    /**
     * 用户名称
     */
    @TableField("USERNAME")
    private String username;

    /**
     * 登录名称
     */
    @TableField("NICKNAME")
    private String nickname;

    /**
     * 登录密码
     */
    @TableField("PASSWORD")
    private String password;

    /**
     * 密码盐值
     */
    @TableField("SALT")
    private String salt;

    /**
     * 用户状态(01：启用：02：锁定)
     */
    @TableField("USERSTATUS")
    private String userstatus;

    /**
     * 是否有效(1：是；0：否)
     */
    @TableField("ISVALID")
    private BigDecimal isvalid;

    /**
     * 创建用户
     */
    @TableField("CREATEUSERID")
    private String createuserid;

    /**
     * 创建时间
     */
    @TableField("CREATETIME")
    private LocalDateTime createtime;

    /**
     * 更新用户
     */
    @TableField("UPDATEUSERID")
    private String updateuserid;

    /**
     * 更新时间
     */
    @TableField("UPDATETIME")
    private LocalDateTime updatetime;


}
