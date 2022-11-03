package com.mall.gateway.common.zuul.filter;

import com.mall.common.response.ResponseData;
import com.mall.common.utils.JacksonUtils;
import com.mall.gateway.common.jwt.JwtTokenProvider;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;


/**
 * ZuulFilter
 * @author youfu.wang
 * @date 2021-08-19
 */
@Component
public class TokenFilter extends ZuulFilter {
    private static final Logger logger= LoggerFactory.getLogger(TokenFilter.class);
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Override
    public String filterType() {
        return  FilterConstants.PRE_TYPE;
    }
    @Value("${zuul.filter.excludeUri}")
    private String excludeUri;
    private AntPathMatcher antPathMatcher=new AntPathMatcher(File.separator);

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext ctx=RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        //只过滤OPTIONS 请求
        if(request.getMethod().equals(RequestMethod.OPTIONS.name())){
            return true;
        }
        String requestURI=request.getRequestURI();
        //过滤URI白名单
        String[] excludeUris=excludeUri.split(",");
        for(String uri:excludeUris){
            if(antPathMatcher.match(uri,requestURI)){
                return false;
            }
        }
        return true;
    }
    private String getRequestToken(HttpServletRequest request) {
        String accessToken = request.getHeader("accessToken");
        if (accessToken == null) {
            return request.getParameter("accessToken");
        } else {
            return accessToken;
        }
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
            String accessToken = this.getRequestToken(request);
            if(accessToken == null) {
                logger.error("access token is empty");
                ctx.setSendZuulResponse(false);
                ctx.setResponseStatusCode(HttpServletResponse.SC_UNAUTHORIZED);
                ctx.setResponseBody(JacksonUtils.toJson(ResponseData.error("access token is empty")));
                return null;
            }
            if (!jwtTokenProvider.verifyToken(accessToken)) {
                ctx.setSendZuulResponse(false);
                ctx.setResponseStatusCode(HttpServletResponse.SC_UNAUTHORIZED);
                ctx.setResponseBody(JacksonUtils.toJson(ResponseData.error("token失效或认证过期！")));
                return null;
            }

        }catch (Exception e){
            logger.error("TokenFilter Filter Exception");
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            ctx.setResponseBody(JacksonUtils.toJson(ResponseData.error("TokenFilter Filter Exception")));
        }
        return null;
    }
}
