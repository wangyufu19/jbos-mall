package com.mall.auth.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mall.auth.application.service.CaptchaService;
import com.mall.auth.common.user.JwtUser;
import com.mall.common.jwt.JwtTokenProvider;
import com.mall.common.response.ResponseData;
import com.mall.common.utils.JacksonUtils;
import com.mall.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
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
import java.util.Map;


/**
 * WebSecurityConfig
 * @author youfu.wang
 * @date 2021-05-21
 */
@Slf4j
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true ,jsr250Enabled=true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Value("${spring.security.filter.loginUri}")
    private String loginUri;
    @Value("${spring.security.filter.excludeUri}")
    private String excludeUri;
    /**
     * 用户认证配置
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
     * HTTP安全配置
     * @param http
     * @throws Exception
     */
    protected void configure(HttpSecurity http) throws Exception {
        String[] excludeUris=excludeUri.split(",");
        http
                .csrf().disable()  //禁用csrf
                .formLogin().disable() //禁用form登录
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()  //禁用session
                .authorizeRequests()
                .antMatchers(excludeUris).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterAt(loginFilter(),UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(new JwtAuthenticationFilter(),LoginFilter.class)
                .logout()
                .invalidateHttpSession(true)
                .addLogoutHandler(new WebLogoutHandler()).logoutSuccessHandler(new WebLogoutSuccessHandler())
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new AuthenticationEntryPoint(){
                    //未通过认证请求，返回异常信息
                    public void commence(HttpServletRequest req, HttpServletResponse resp, AuthenticationException e) throws IOException {
                        ResponseData r= ResponseData.error("403","对不起，非法请求");
                        resp.setContentType("application/json;charset=utf-8");
                        PrintWriter out = resp.getWriter();
                        out.write(JacksonUtils.toJson(r));
                        out.flush();
                        out.close();
                    }
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
        loginFilter.setFilterProcessesUrl(this.loginUri);
        // 返回登录成功后数据
        loginFilter.setAuthenticationSuccessHandler(new AuthenticationSuccessHandler() {
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
                JwtUser principal = (JwtUser) authentication.getPrincipal(); // 获取用户对象
                Map<String,String> signData=new HashMap<String,String>();
                signData.put("username",principal.getUsername());
                String token = jwtTokenProvider.generateToken(signData);
                ResponseData r= ResponseData.ok("登录成功！");
                Map<String,Object> data=new HashMap<String,Object>();
                data.put("username", principal.getUsername());
                data.put("userInfo",principal.getUserInfo());
                data.put("accessToken", token);
                r.setData(data);
                response.setContentType("application/json;charset=utf-8");
                PrintWriter out = response.getWriter();
                out.write(JacksonUtils.toJson(r));
                out.flush();
                out.close();
            }
        });
        // 返回登录失败后数据
        loginFilter.setAuthenticationFailureHandler(new AuthenticationFailureHandler() {
            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
                ResponseData r= ResponseData.error(e.getMessage());
                if(e.getMessage().indexOf("Bad captcha")!=-1){
                    r= ResponseData.error("验证码错误！");
                }else if(e.getMessage().indexOf("Bad credentials")!=-1){
                    r= ResponseData.error("用户名或密码错误！");
                }
                response.setContentType("application/json;charset=utf-8");
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

        public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
            if (!request.getMethod().equals("POST")) {
                throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
            }
            // 判断请求是否是json格式，如果不是直接调用默认表单认证方式
            if (request.getContentType().indexOf(MediaType.APPLICATION_JSON_VALUE)!=-1) {
                // 把request的json数据转换为Map
                Map<String, String> loginData = new HashMap<>();
                try {
                    loginData = new ObjectMapper().readValue(request.getInputStream(), Map.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // 判断验证码
                String captchaToken=StringUtils.replaceNull(loginData.get("captchaToken"));
                String captchaText=StringUtils.replaceNull(loginData.get("captchaText"));
                if(!captchaService.validate(captchaToken,captchaText)){
                   throw new CaptchaAuthenticationException("Bad captcha");
                }
                // 调用父类的getParameter() 方法获取key值
                String username = StringUtils.replaceNull(loginData.get(this.getUsernameParameter()));
                String password = StringUtils.replaceNull(loginData.get(this.getPasswordParameter()));
                UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username.trim(), password);
                this.setDetails(request, authRequest);
                return this.getAuthenticationManager().authenticate(authRequest);
            } else {
                return super.attemptAuthentication(request, response);
            }
        }
    }
    public class CaptchaAuthenticationException extends AuthenticationException{

        public CaptchaAuthenticationException(String msg, Throwable t) {
            super(msg, t);
        }
        public CaptchaAuthenticationException(String msg) {
            super(msg);
        }
    }
    public class WebLogoutHandler implements LogoutHandler {

        public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
            if(authentication!=null){
                JwtUser user = (JwtUser) authentication.getPrincipal();
                String username = user.getUsername();
                log.info("username: {}  is offline now", username);
            }
        }
    }
    public class WebLogoutSuccessHandler implements LogoutSuccessHandler{

        public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
            authentication=SecurityContextHolder.getContext().getAuthentication();
            response.setStatus(HttpServletResponse.SC_OK);
            response.setCharacterEncoding("utf-8");
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            PrintWriter out = response.getWriter();
            ResponseData r=ResponseData.ok("退出成功！");
            out.write(JacksonUtils.toJson(r));
            out.flush();
            out.close();
        }
    }
    public class JwtAuthenticationFilter extends OncePerRequestFilter {
        private AntPathMatcher antPathMatcher=new AntPathMatcher(File.separator);
        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
                throws ServletException, IOException {
            try {
                String requestURI=request.getRequestURI();
                String[] excludeUris=excludeUri.split(",");
                boolean matches=false;
                for(String uri:excludeUris){
                    matches=antPathMatcher.match(uri,requestURI);
                    if(matches){
                        break;
                    }
                }
                if(matches){
                    filterChain.doFilter(request, response);
                    return;
                }

                String token = getRequestToken(request);
                if (token == null || !jwtTokenProvider.verifyToken(token)) {
                    ResponseData r = ResponseData.error("token失效或认证过期！");
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.setContentType("application/json;charset=utf-8");
                    PrintWriter out = response.getWriter();
                    out.write(JacksonUtils.toJson(r));
                    out.flush();
                    out.close();
                    return;
                }
                UsernamePasswordAuthenticationToken authentication;
                String username = jwtTokenProvider.getSignDataFromJWT(token, "username");
                String userInfo = jwtTokenProvider.getSignDataFromJWT(token, "userInfo");
                UserDetails userDetails = new JwtUser(username, userInfo,null, null);
                // 构建认证过的token
                authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                if (authentication != null) {
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                }
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                logger.error("无法给 Security 上下文设置用户验证对象", e);
            }
            filterChain.doFilter(request, response);
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
