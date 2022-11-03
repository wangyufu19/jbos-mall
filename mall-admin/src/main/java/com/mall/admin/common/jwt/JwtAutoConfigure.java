package com.mall.admin.common.jwt;

import com.auth0.jwt.JWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * JwtAutoConfigure
 * @author youfu.wang
 * @date 2021-01-01
 */
@Configuration
@ConditionalOnClass({JWT.class})
@EnableConfigurationProperties({JwtProperties.class})
public class JwtAutoConfigure {
    private static final Logger logger=LoggerFactory.getLogger(JwtAutoConfigure.class);

    @Bean
    @ConditionalOnMissingBean
    public JwtTokenProvider jwtTokenProvider(JwtProperties jwtProperties){
        logger.info("初始化JwtTokenProvider属性配置");
        JwtTokenProvider jwtTokenProvide=new JwtTokenProvider();
        jwtTokenProvide.setSecret(jwtProperties.getSecret());
        jwtTokenProvide.setExpireTime(jwtProperties.getExpireTime()*60*1000L);
        return jwtTokenProvide;
    }
}
