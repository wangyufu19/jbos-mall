package com.mall.gateway.common.zuul;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;

/**
 * OpenApiErrorFilter
 * @author youfu.wang
 * @date 2021-05-05
 */
@Component
public class MallGatewayErrorFilter extends ZuulFilter{
    private static final Logger log= LoggerFactory.getLogger(MallGatewayErrorFilter.class);
    @Override
    public String filterType() {
        return  FilterConstants.ERROR_TYPE;
    }

    @Override
    public int filterOrder() {
        return 10;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx=RequestContext.getCurrentContext();
        int code= ctx.getResponseStatusCode();
        int status=ctx.getResponse().getStatus();
        ctx.set("error.status_code",HttpServletResponse.SC_NOT_FOUND);
        ctx.set("error.message","SC_NOT_FOUND");
        return null;
    }

}
