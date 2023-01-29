package com.mall.admin.common.config;

import com.mall.admin.common.utils.RequestContextUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.NamedThreadLocal;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Map;

/**
 * FeignConfig
 * @author youfu.wang
 * @date 2019-01-31
 */
@Component
public class FeignConfig implements RequestInterceptor {

    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(attributes==null){
            return;
        }
        HttpServletRequest request = attributes.getRequest();
        requestTemplate.header("accessToken",request.getHeader("accessToken"));
    }
    public static class RequestHeaderHandler{
        private static final ThreadLocal<ServletRequestAttributes> THREAD_LOCAL = new ThreadLocal();

        public static void setHeaders(ServletRequestAttributes headers){
            THREAD_LOCAL.set(headers);
        }

        public static ServletRequestAttributes getHeaders() {
            return THREAD_LOCAL.get();
        }
        public static void remove(){
            THREAD_LOCAL.remove();
        }
    }
}
