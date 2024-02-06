package com.mall.admin.common.shiro;

import com.mall.common.response.ResponseResult;
import com.mall.common.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * AuthTokenFilter
 *
 * @author youfu.wang
 * @date 2024/2/6 11:21
 */
@Slf4j
public class AuthTokenFilter extends AuthenticatingFilter {
    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        String token = this.getRequestToken((HttpServletRequest) servletRequest);
        if (StringUtils.isBlank(token)) {
            return null;
        }
        return new AuthToken(token);
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (((HttpServletRequest) request).getMethod().equals(RequestMethod.OPTIONS.name())) {
            return true;
        }
        return false;
    }
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        //获取请求token，如果token不存在，返回401
        String token = getRequestToken((HttpServletRequest) servletRequest);
        if (StringUtils.isBlank(token)) {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
            httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
            httpResponse.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
            httpResponse.setContentType("application/json;charset=UTF-8");
            String json = JsonUtils.toJson(ResponseResult.error(HttpStatus.SC_UNAUTHORIZED, "invalid token"));
            httpResponse.getWriter().print(json);
            return false;
        }
        return executeLogin(servletRequest, servletResponse);
    }

    private String getRequestToken(HttpServletRequest request) {
        //从header中获取token
        String token = request.getHeader("accessToken");
        //如果header中不存在token，则从参数中获取token
        if (StringUtils.isBlank(token)) {
            token = request.getParameter("accessToken");
        }
        return token;
    }
}
