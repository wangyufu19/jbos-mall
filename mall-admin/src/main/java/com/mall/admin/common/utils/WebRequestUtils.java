package com.mall.admin.common.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * WebRequestUtils
 *
 * @author youfu.wang
 * @date 2023/11/1 16:14
 */
public class WebRequestUtils {
    private String getRequestToken() {
        // 获得request对象
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String accessToken = request.getHeader("accessToken");
        if (accessToken == null) {
            return request.getParameter("accessToken");
        } else {
            return accessToken;
        }
    }
}
