package com.mall.gateway.common.websecurity.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * AuthRequest
 *
 * @author youfu.wang
 * @date 2023/10/11 10:43
 */
@Data
@Slf4j
public class AuthRequestDto {
    /**
     * username
     */
    private String username;
    /**
     * password
     */
    private String password;
    /**
     * captchaToken
     */
    private String captchaToken;
    /**
     * captchaText
     */
    private String captchaText;

    /**
     * getAuthRequest
     * @param request
     * @return AuthRequest
     */
    public static AuthRequestDto getInstance(HttpServletRequest request) {
        AuthRequestDto authRequest = new AuthRequestDto();
        if (request.getContentType().indexOf(MediaType.APPLICATION_FORM_URLENCODED_VALUE) != -1) {
            authRequest.setUsername(String.valueOf(request.getParameter("username")));
            authRequest.setPassword(String.valueOf(request.getParameter("password")));
            authRequest.setCaptchaToken(String.valueOf(request.getParameter("captchaToken")));
            authRequest.setCaptchaText(String.valueOf(request.getParameter("captchaText")));
        } else if (request.getContentType().indexOf(MediaType.APPLICATION_JSON_VALUE) != -1) {
            // 判断请求是否是json格式
            // 把request的json数据转换为Map
            Map<String, String> loginData = new HashMap<>();
            try {
                loginData = new ObjectMapper().readValue(request.getInputStream(), Map.class);
            } catch (IOException e) {
                log.error(e.getMessage());
            }
            authRequest.setUsername(String.valueOf(loginData.get("username")));
            authRequest.setPassword(String.valueOf(loginData.get("password")));
            authRequest.setCaptchaToken(String.valueOf(loginData.get("captchaToken")));
            authRequest.setCaptchaText(String.valueOf(loginData.get("captchaText")));

        }
        return authRequest;
    }
}
