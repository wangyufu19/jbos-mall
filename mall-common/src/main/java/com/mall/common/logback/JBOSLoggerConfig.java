package com.mall.common.logback;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import java.net.URL;

/**
 * JBOSLoggerConfig
 *
 * @author youfu.wang
 * @date 2023/10/7 11:19
 */
@Configuration
@Slf4j
public class JBOSLoggerConfig {
    /**
     * logConfigFile
     */
    @Value("${jbos-logger-config-file:jbos-logback-spring.xml}")
    private String logConfigFile;
    /**
     * applicationContext
     */
    private ApplicationContext applicationContext;

    /**
     * JBOSLoggerConfig
     * @param applicationContext
     */
    public JBOSLoggerConfig(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * jbosLoggerConfiguration
     */
    @Bean
    public void jbosLoggerConfiguration() {
        LoggerContext loggerContext = JBOSLoggerFactory.getLoggerContext();
        loggerContext.reset();
        JoranConfigurator configurator = new JoranConfigurator();
        log.info("jbosLoggerConfiguration");
        try {
            URL url = ResourceUtils.getURL("classpath:" + this.logConfigFile);
            configurator.setContext(loggerContext);
            configurator.doConfigure(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
