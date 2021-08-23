package com.mall.gateway.common.zuul;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;

/**
 * OpenApiPreFilter
 * @author youfu.wang
 * @date 2021-05-05
 */
@Component
public class MallGatewayPostFilter extends ZuulFilter{
    private static final Logger logger= LoggerFactory.getLogger(MallGatewayPostFilter.class);
    @Override
    public String filterType() {
        return  FilterConstants.POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return 5;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx=RequestContext.getCurrentContext();
        HttpServletResponse response = ctx.getResponse();
        response.setContentType("application/json;charset=utf-8");
        try{
            if(HttpServletResponse.SC_NOT_FOUND==ctx.getResponseStatusCode()){
                ctx.setSendZuulResponse(false);
                ctx.setResponseStatusCode(HttpServletResponse.SC_NOT_FOUND);
                return null;
            }
        }catch (Exception e){
            logger.error(e.getMessage());
            ctx.set("error.status_code",HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            ctx.set("error.message",e);
        }
        return null;
    }

}
