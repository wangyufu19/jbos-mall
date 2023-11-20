package com.mall.admin.common.log;

import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


/**
 * HttpServletRequestFilter
 *
 * @author youfu.wang
 * @date 2023/11/17 17:23
 */
@Component
@ServletComponentScan
public class HttpServletRequestFilter implements Filter {

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        ServletRequest request = null;
        if (servletRequest instanceof HttpServletRequest) {
            request = new RequestLogWrapper((HttpServletRequest) servletRequest);
        }
        if (request != null) {
            filterChain.doFilter(request, servletResponse);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
