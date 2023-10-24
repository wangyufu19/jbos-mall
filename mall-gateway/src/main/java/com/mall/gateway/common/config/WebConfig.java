//package com.mall.gateway.common.config;
//
//import com.mall.common.response.ResponseResult;
//import com.mall.common.utils.JacksonUtils;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.PrintWriter;
//
//@Slf4j
//@Configuration
//public class WebConfig implements WebMvcConfigurer {
//    @Value("${spring.security.filter.excludeUri}")
//    private String excludeUri;
//
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
////                .allowedOrigins("*")
//                .allowedOriginPatterns("*")
//                .allowCredentials(true)
//                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
//                .maxAge(3600);
//    }
////    @ConditionalOnMissingBean(WebSecurityConfig.class)
////    public void addInterceptors(InterceptorRegistry registry) {
////        registry.addInterceptor(new TokenInterceptor())
////                .excludePathPatterns(excludeUri.split(","))
////                .addPathPatterns("/**");
////    }
//    public class TokenInterceptor implements HandlerInterceptor {
//        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//            log.info("uri={}",request.getRequestURI());
//            String accessToken = getRequestToken(request);
//            if(accessToken == null) {
//                ResponseResult r = ResponseResult.error("token失效或认证过期！");
//                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                response.setContentType("application/json;charset=utf-8");
//                PrintWriter out = response.getWriter();
//                out.write(JacksonUtils.toJson(r));
//                out.flush();
//                out.close();
//                return false;
//            }
//            return true;
//        }
//        private String getRequestToken(HttpServletRequest request) {
//            String accessToken = request.getHeader("accessToken");
//            if (accessToken == null) {
//                return request.getParameter("accessToken");
//            } else {
//                return accessToken;
//            }
//        }
//    }
//}