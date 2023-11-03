package com.mall.gateway.common.util;

import com.mall.gateway.common.websecurity.jwt.JwtTokenProvider;
import org.springframework.http.server.PathContainer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.util.pattern.PathPatternParser;

/**
 * WebRequestUtil
 *
 * @author youfu.wang
 * @date 2023/11/1 15:46
 */
public class WebRequestUtil {

    public static String getRequestToken(ServerHttpRequest request) {

        String accessToken = request.getHeaders().getFirst(JwtTokenProvider.TOKEN);
        if (accessToken == null) {
            return request.getQueryParams().getFirst(JwtTokenProvider.TOKEN);
        } else {
            return accessToken;
        }
    }

    /**
     * isExcludeUri
     *
     * @param requestURI
     * @param excludeUri
     * @return
     */
    public static boolean isExcludeUri(String requestURI, String excludeUri) {
        String[] excludeUris = WebRequestUtil.splitExcludeUri(excludeUri);
        boolean matches = false;
        for (String uri : excludeUris) {
            matches = PathPatternParser.defaultInstance.parse(uri).matches(PathContainer.parsePath(requestURI));
            if (matches) {
                break;
            }
        }
        return matches;
    }

    /**
     * splitExcludeUri
     *
     * @param excludeUri
     * @return excludeUris
     */
    public static String[] splitExcludeUri(String excludeUri) {
        String[] excludeUris = excludeUri.split(",");
        return excludeUris;
    }
}
