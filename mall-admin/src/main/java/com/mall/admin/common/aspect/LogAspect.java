package com.mall.admin.common.aspect;

import com.mall.common.response.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

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
    /**
     * log
     */
    @Pointcut("execution(public * *..*Api.*(..))")
    public void log() {
    }

    /**
     * doAround
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("log()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        //获取切入点方法的名字,getSignature());信息 :修饰符+ 包名+组件名(类名) +方法名
        Object target = joinPoint.getTarget();
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getName();
        Object data = null;
        try {
            data = joinPoint.proceed();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            data = ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        } finally {
            log.info("类名称={}；类方法={}", className, methodName);
        }
        return data;
    }
}
