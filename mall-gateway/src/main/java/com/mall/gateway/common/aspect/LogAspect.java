package com.mall.gateway.common.aspect;

import com.mall.common.response.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * LogAspect
 *
 * @author youfu.wang
 * @date 2023/9/15 14:15
 */
@Aspect
@Component
@Slf4j
public class LogAspect {

    @Pointcut("execution(public * *..*Api.*(..))")
    public void log() {
    }

    @Around("log()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        //获取切入点方法的名字,getSignature());信息 :修饰符+ 包名+组件名(类名) +方法名
        Object target = joinPoint.getTarget();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Object data = null;
        Annotation requestMapping = method.getDeclaredAnnotation(RequestMapping.class);
        Annotation getMapping = method.getDeclaredAnnotation(GetMapping.class);
        Annotation postMapping = method.getDeclaredAnnotation(PostMapping.class);
        if (requestMapping != null || getMapping != null || postMapping != null) {
            String methodName = joinPoint.getSignature().getName();
            String className = joinPoint.getTarget().getClass().getName();
            try {
                data = joinPoint.proceed();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                data = ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
            } finally {
                log.info("接口名称={}；接口方法={}", className, methodName);
            }
        } else {
            data = joinPoint.proceed();
        }
        return data;
    }
}
