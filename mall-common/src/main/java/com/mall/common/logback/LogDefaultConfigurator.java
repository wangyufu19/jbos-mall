package com.mall.common.logback;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.classic.spi.Configurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.spi.ContextAwareBase;
import org.slf4j.helpers.Util;
import org.springframework.util.ResourceUtils;

import java.net.URL;

/**
 * LogDefaultConfigurator
 *
 * @author youfu.wang
 * @date 2023/10/7 16:21
 */
public class LogDefaultConfigurator extends ContextAwareBase implements Configurator {
    public void configure(LoggerContext loggerContext) {
        this.addInfo("Setting up retail default configuration.");
        // 清除loggerContext已加载配置，重新加载
        loggerContext.reset();
        JoranConfigurator configurator = new JoranConfigurator();
        try {
            //  获取jar中默认配置文件路径
            URL url = ResourceUtils.getURL("classpath:jbos-logback-spring.xml");
            // 设置loggerContext到JoranConfigurator
            configurator.setContext(loggerContext);
            // 加载默认配置
            configurator.doConfigure(url);
        } catch (Exception e) {
            Util.report("Failed to instantiate [" + LoggerContext.class.getName() + "]", e);
        }
    }
}