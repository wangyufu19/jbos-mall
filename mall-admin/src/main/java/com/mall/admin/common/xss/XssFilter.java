package com.mall.admin.common.xss;

import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * XssFilter
 *
 * @author youfu.wang
 * @date 2024/2/5 10:35
 */
public class XssFilter implements Filter {
    /**
     * antPathMatcher
     */
    private AntPathMatcher antPathMatcher = new AntPathMatcher(File.separator);
    /**
     * excludesUris
     */
    private List<String> excludesUriList = new ArrayList<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String excludesUris = filterConfig.getInitParameter("excludesUris");
        if (StringUtils.hasLength(excludesUris)) {
            String[] uris = excludesUris.split(",");
            excludesUriList.addAll(Arrays.asList(uris));
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        if (this.isExcludeUri(request.getRequestURI())) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    /**
     * isExcludeUri
     *
     * @param requestURI
     * @return true/false
     */
    private boolean isExcludeUri(String requestURI) {
        boolean matches = false;
        for (String uri : this.excludesUriList) {
            matches = antPathMatcher.match(uri, requestURI);
            if (matches) {
                break;
            }
        }
        return matches;
    }
}
