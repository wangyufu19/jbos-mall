package com.mall.gateway.common.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * SpringSecurityProperties
 *
 * @author youfu.wang
 * @date 2023/10/9 14:40
 */
@Data
@RefreshScope
@ConfigurationProperties(prefix = "spring.security.filter")
public class WebSecurityProperties implements Serializable {
    /**
     * loginUri
     */
    private String loginUri;
    /**
     * logoutUri
     */
    private String logoutUri;
    /**
     * excludeUri
     */
    private String excludeUri;
    /**
     * enableCaptcha
     */
    private boolean enableCaptcha;
}
