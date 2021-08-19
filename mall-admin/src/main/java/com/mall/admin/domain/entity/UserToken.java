package com.mall.admin.domain.entity;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * UserTokenMapper.xml
 * @author youfu.wang
 */
@Setter
@Getter
public class UserToken implements Serializable {

    private String userId;

    private String token;

    private Date expireTime;

    private Date updateTime;
}
