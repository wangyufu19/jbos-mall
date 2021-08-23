package com.mall.gateway.common.zuul;

import com.mall.common.response.ResponseData;
import com.mall.common.utils.JacksonUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * MallGatewayFilter
 * @author youfu.wang
 * @date 2021-08-19
 */
@Component
public class MallGatewayFilter extends ZuulFilter {
    private static final Logger logger= LoggerFactory.getLogger(MallGatewayFilter.class);
    @Override
    public String filterType() {
        return  FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext ctx=RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String requestURI=request.getRequestURI();
        if(requestURI.indexOf("login")!=-1||requestURI.indexOf("logout")!=-1){
            return false;
        }
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx=RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String requestURI=request.getRequestURI();
        HttpServletResponse response = ctx.getResponse();
        response.setContentType("application/json;charset=utf-8");
        logger.info("send {} request to {}", request.getMethod(),requestURI);
        try{
            Object accessToken = request.getParameter("access_token");
            if(accessToken == null) {
                accessToken=request.getHeader("access_token");
            }
            if(accessToken == null) {
                logger.error("access token is empty");
                ctx.setSendZuulResponse(false);
                ctx.setResponseStatusCode(401);
                ctx.setResponseBody(JacksonUtils.toJson(ResponseData.error("access token is empty")));
                return null;
            }

        }catch (Exception e){
            logger.error("MallGateway Filter Exception");
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            ctx.setResponseBody(JacksonUtils.toJson(ResponseData.error("MallGateway Filter Exception")));
        }
        return null;
    }
}
