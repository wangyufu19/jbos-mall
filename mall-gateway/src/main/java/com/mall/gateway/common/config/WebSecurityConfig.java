package com.mall.gateway.common.config;


import com.mall.gateway.application.service.auth.CaptchaService;
import com.mall.gateway.common.config.properties.WebSecurityProperties;
import com.mall.gateway.common.websecurity.dto.AuthRequestDto;
import com.mall.gateway.common.websecurity.jwt.JwtTokenProvider;
import com.mall.gateway.common.websecurity.user.JwtUser;
import com.mall.common.response.ResponseResult;
import com.mall.common.utils.JacksonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * WebSecurityConfig
 *
 * @author youfu.wang
 * @date 2021-05-21
 */
@Slf4j
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true, jsr250Enabled = true)
@EnableConfigurationProperties({WebSecurityProperties.class})
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private WebSecurityProperties webSecurityProperties;

    /**
     * AuthenticationManagerBuilder
     *
     * @param auth
     * @throws Exception
     */
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /**
         * 获取认证用户信息
         */
        auth.userDetailsService(userDetailsService);
    }

    /**
     * WebSecurity
     *
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        //swagger2所需要用到的静态资源，允许访问
        web.ignoring().antMatchers(this.getExcludeUris());


    }

    /**
     * getExcludeUris
     *
     * @return excludeUris
     */
    private String[] getExcludeUris() {
        String excludeUri = webSecurityProperties.getExcludeUri();
        String[] excludeUris = excludeUri.split(",");
        return excludeUris;
    }

    /**
     * HttpSecurity
     *
     * @param http
     * @throws Exception
     */
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()  //禁用csrf
                .formLogin().disable() //禁用form登录
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()  //禁用session
                .authorizeRequests()
                .antMatchers(this.getExcludeUris()).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterAt(loginFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(new JwtAuthenticationFilter(), LoginFilter.class)
                .logout().logoutUrl(webSecurityProperties.getLogoutUri())
                .invalidateHttpSession(true)
                .addLogoutHandler(new WebLogoutHandler()).logoutSuccessHandler(new WebLogoutSuccessHandler())
                .and()
                .exceptionHandling()
                .authenticationEntryPoint((req, res, ex) -> {
                    //请求认证异常
                    ResponseResult r = ResponseResult.error("403", "对不起，非法请求");
                    res.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                    PrintWriter out = res.getWriter();
                    out.write(JacksonUtils.toJson(r));
                    out.flush();
                    out.close();
                })
                .accessDeniedHandler((req, res, ex) -> {
                    //请求权限异常
                    ResponseResult r = ResponseResult.error("403", "对不起，没有权限");
                    res.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                    PrintWriter out = res.getWriter();
                    out.write(JacksonUtils.toJson(r));
                    out.flush();
                    out.close();
                })
                .and().cors(); //启用跨域请求
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public LoginFilter loginFilter() throws Exception {
        LoginFilter loginFilter = new LoginFilter();
        // 登录请求地址
        loginFilter.setFilterProcessesUrl(webSecurityProperties.getLoginUri());
        // 返回登录成功后数据
        loginFilter.setAuthenticationSuccessHandler(new AuthenticationSuccessHandler() {
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
                //获取用户对象
                JwtUser principal = (JwtUser) authentication.getPrincipal(); // 获取用户对象
                Map<String, String> signData = new HashMap<String, String>();
                signData.put("username", principal.getUsername());
                signData.put("nickName", principal.getNickName());
                signData.put("depId", principal.getDepId());
                signData.put("depName", principal.getDepName());
                signData.put("orgId", principal.getOrgId());
                String token = jwtTokenProvider.generateToken(signData, principal.getAuthorities());
                ResponseResult r = ResponseResult.ok("登录成功！");
                Map<String, Object> data = new HashMap<String, Object>();
                data.put("username", principal.getUsername());
                data.put("nickName", principal.getNickName());
                data.put("accessToken", token);
                r.setData(data);
                response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                PrintWriter out = response.getWriter();
                out.write(JacksonUtils.toJson(r));
                out.flush();
                out.close();
            }
        });
        // 返回登录失败后数据
        loginFilter.setAuthenticationFailureHandler(new AuthenticationFailureHandler() {
            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
                ResponseResult r = ResponseResult.error(e.getMessage());
                if (e.getMessage().indexOf("Bad captcha") != -1) {
                    r = ResponseResult.error("验证码错误！");
                } else if (e.getMessage().indexOf("Bad credentials") != -1) {
                    r = ResponseResult.error("用户名或密码错误！");
                } else if (e.getMessage().indexOf("Bad grant") != -1) {
                    r = ResponseResult.error(" 用户没有操作权限，请联系管理员！");
                }
                response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                PrintWriter out = response.getWriter();
                out.write(JacksonUtils.toJson(r));
                out.flush();
                out.close();
            }
        });
        loginFilter.setAuthenticationManager(authenticationManagerBean());
        return loginFilter;
    }

    /**
     * 自定义 UsernamePasswordAuthenticationFilter 过滤器
     */
    public class LoginFilter extends UsernamePasswordAuthenticationFilter {
        @Autowired
        private CaptchaService captchaService;

        /**
         * attemptAuthentication
         * @param request
         * @param response
         * @return Authentication
         * @throws AuthenticationException
         */
        public Authentication attemptAuthentication(
                HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
            if (!request.getMethod().equals("POST")) {
                throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
            }
            AuthRequestDto authRequestDto = AuthRequestDto.getInstance(request);
            if (webSecurityProperties.isEnableCaptcha()) {
                // 判断验证码
                if (!captchaService.validate(authRequestDto.getCaptchaToken(), authRequestDto.getCaptchaText())) {
                    throw new CaptchaAuthenticationException("Bad captcha");
                }
            }
            String username = String.valueOf(authRequestDto.getUsername()).trim();
            String password = String.valueOf(authRequestDto.getPassword());
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
            this.setDetails(request, authRequest);
            return this.getAuthenticationManager().authenticate(authRequest);
        }

    }

    public class CaptchaAuthenticationException extends AuthenticationException {

        public CaptchaAuthenticationException(String msg, Throwable t) {
            super(msg, t);
        }

        public CaptchaAuthenticationException(String msg) {
            super(msg);
        }
    }

    public class WebLogoutHandler implements LogoutHandler {

        public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
            if (authentication != null) {
                JwtUser user = (JwtUser) authentication.getPrincipal();
                String username = user.getUsername();
                log.info("username: {}  is offline now", username);
            }
        }
    }

    public class WebLogoutSuccessHandler implements LogoutSuccessHandler {

        public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
            response.setStatus(HttpServletResponse.SC_OK);
            response.setCharacterEncoding("utf-8");
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            PrintWriter out = response.getWriter();
            ResponseResult r = ResponseResult.ok("退出成功！");
            out.write(JacksonUtils.toJson(r));
            out.flush();
            out.close();
        }
    }

    public class JwtAuthenticationFilter extends OncePerRequestFilter {
        private AntPathMatcher antPathMatcher = new AntPathMatcher(File.separator);

        private boolean isExcludeUri(String requestURI) {
            String[] excludeUris = getExcludeUris();
            boolean matches = false;
            for (String uri : excludeUris) {
                matches = antPathMatcher.match(uri, requestURI);
                if (matches) {
                    break;
                }
            }
            return matches;
        }

        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
                throws ServletException, IOException {
            String requestURI = request.getRequestURI();
            try {
                //无需鉴权URI
                if (this.isExcludeUri(requestURI)) {
                    filterChain.doFilter(request, response);
                    return;
                }
                //验证Token有效性和构建用户上下文认证对象
                this.checkToken(request, response);
            } catch (Exception e) {
                logger.error("无法给 Security 上下文设置用户验证对象", e);
            }
            filterChain.doFilter(request, response);
        }

        private void checkToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
            String token = getRequestToken(request);
            if (token == null || !jwtTokenProvider.verifyToken(token)) {
                logger.info("token失效或认证过期！");
                ResponseResult r = ResponseResult.error("token失效或认证过期！");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                PrintWriter out = response.getWriter();
                out.write(JacksonUtils.toJson(r));
                out.flush();
                out.close();
                return;
            }
            //解码JWT用户数据
            String username = jwtTokenProvider.getSignDataFromJWT(token, "username");
            String nickName = jwtTokenProvider.getSignDataFromJWT(token, "nickName");
            List<GrantedAuthority> grantedAuthorities = jwtTokenProvider.getGrantedAuthorityFromJWT(token, JwtUser.AUTHORITIES);
            UserDetails userDetails = new JwtUser(username, nickName, grantedAuthorities);
            // 构建认证过的token
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            if (authentication != null) {
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            }
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        private String getRequestToken(HttpServletRequest request) {
            String accessToken = request.getHeader("accessToken");
            if (accessToken == null) {
                return request.getParameter("accessToken");
            } else {
                return accessToken;
            }
        }
    }


}
