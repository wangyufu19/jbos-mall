package com.mall.common.page;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mall.common.response.ResponseResult;
import com.mall.common.utils.NumberUtils;
import com.mall.common.utils.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * PagingAspect
 *
 * @author youfu.wang
 * @date 2023/6/2
 **/
@Aspect
@Component
public class PagingAspect {

    @Pointcut("@annotation(com.mall.common.page.Paging)")
    public void paging() {}

    @Around("paging()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        ResponseResult res=ResponseResult.ok();
        int pageNum = 1;
        int pageSize = 10;
        PageHelper.startPage(pageNum, pageSize);
        Object dataList = joinPoint.proceed();
        if(dataList instanceof List){
            PageInfo pageInfo = new PageInfo((List)dataList);
            res.setData(pageInfo);
        }
        return res;
    }
}
