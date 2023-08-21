package com.mall.admin.common.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;

import org.springframework.stereotype.Component;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * FeignConfig
 *
 * @author youfu.wang
 * @date 2019-01-31
 */
@Component
public class FeignConfig implements RequestInterceptor {
    /**
     * apply
     * @param requestTemplate
     */
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return;
        }
        HttpServletRequest request = attributes.getRequest();
        requestTemplate.header("accessToken", request.getHeader("accessToken"));
    }

    public static class RequestHeaderHandler {
        /**
         * RequestHeaderHandler
         */
        private static final ThreadLocal<ServletRequestAttributes> THREAD_LOCAL = new ThreadLocal();

        /**
         * setHeaders
         * @param headers
         */
        public static void setHeaders(ServletRequestAttributes headers) {
            THREAD_LOCAL.set(headers);
        }

        /**
         * getHeaders
         * @return ServletRequestAttributes
         */
        public static ServletRequestAttributes getHeaders() {
            return THREAD_LOCAL.get();
        }

        /**
         * remove
         */
        public static void remove() {
            THREAD_LOCAL.remove();
        }
    }
}
