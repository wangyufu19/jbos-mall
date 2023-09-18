package com.mall.business.common.aspect;

import com.github.pagehelper.PageHelper;
import com.mall.common.page.PageParam;
import com.mall.common.response.ResponseResult;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * PagingAspect
 *
 * @author youfu.wang
 * @date 2023/6/2
 **/
@Aspect
@Component
public class PagingAspect {

    @Pointcut("@annotation(com.mall.starter.page.Paging)")
    public void paging() {
    }

    @Around("paging()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        ResponseResult res = ResponseResult.ok();
        int pageNum = PageParam.DEFAULT_PAGE_NUM;
        int pageSize = PageParam.DEFAULT_PAGE_SIZE;

        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof PageParam) {
                PageParam page = (PageParam) arg;
                pageNum = page.getPageNum() > 0 ? page.getPageNum() : pageNum;
                pageSize = page.getPageSize() > 0 ? page.getPageSize() : pageSize;
                break;
            }
        }
        PageHelper.startPage(pageNum, pageSize);
        Object data = joinPoint.proceed();
        return data;
    }
}
