package com.mall.admin.common.aspect;

import com.github.pagehelper.PageHelper;
import com.mall.common.page.PageParam;
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
    /**
     * paging
     */
    @Pointcut("@annotation(com.mall.common.page.Paging)")
    public void paging() {
    }

    /**
     * doAround
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("paging()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
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
