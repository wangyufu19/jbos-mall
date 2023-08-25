package com.mall.admin.application.request;

import lombok.Getter;
import lombok.Setter;

/**
 * BaseRequestDto
 *
 * @author youfu.wang
 * @date 2023/8/22
 **/
@Setter
@Getter
public class BaseRequestDto {
    /**
     * 操作类型（新增）
     */
    public static final String ACTION_CREATE = "create";
    /**
     * 操作类型（修改）
     */
    public static final String ACTION_UPDATE = "update";
    /**
     * 操作类型(create;update)
     */
    private String action;
}
