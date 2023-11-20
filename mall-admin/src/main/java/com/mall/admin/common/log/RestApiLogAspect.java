package com.mall.admin.common.log;

import com.mall.admin.common.log.annotation.ResponseLog;
import com.mall.common.response.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.StringBuilderWriter;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Enumeration;

/**
 * RestApiLogAspect
 *
 * @author youfu.wang
 * @date 2023/11/17 10:24
 */
@Aspect
@Component
@Slf4j
public class RestApiLogAspect {

    @Pointcut("execution(public * *..*Api.*(..))")
    public void log() {
    }

    @Before("log()")
    public void before(JoinPoint joinPoint) {
        log.info("class=【{}】；method=【{}】", joinPoint.getSignature().getDeclaringType(), joinPoint.getSignature().getName());
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            log.info("header=【{}】", this.printRequestHeader(request));
            log.info("queryString=【{}】", request.getQueryString());
            log.info("body=【{}】", this.printRequestBody(request));

        } else {
            log.info("切面日志：ServletRequestAttributes对象为空!");
        }
    }

    @Around("log()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object object;
        try {

            object = joinPoint.proceed();
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            Method method = methodSignature.getMethod();
            ResponseLog responseLog = method.getDeclaredAnnotation(ResponseLog.class);
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                HttpServletRequest request = attributes.getRequest();
                log.info("uri={} cost {} ms", request.getRequestURI(), System.currentTimeMillis() - startTime);
            }

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            object = ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return object;
    }

    private String printRequestHeader(HttpServletRequest request) {
        StringBuffer buf = new StringBuffer();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            buf.append(headerName);
            buf.append("=");
            buf.append(request.getHeader(headerName));
            buf.append(";");
        }
        buf.toString();
        return buf.toString();
    }

    private String printRequestBody(HttpServletRequest request) {
        String body = "";
        InputStream inputStream = null;
        try {
            inputStream = request.getInputStream();
            StringBuilderWriter sw = new StringBuilderWriter();
            IOUtils.copy(inputStream, sw, Charsets.UTF_8);
            body = sw.toString();
        } catch (IOException e) {
            log.error(e.getMessage());
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
        return body;
    }
}
