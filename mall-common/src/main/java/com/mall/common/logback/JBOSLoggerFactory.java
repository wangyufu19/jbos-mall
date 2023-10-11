package com.mall.common.logback;

import ch.qos.logback.classic.LoggerContext;

/**
 * JBOSLoggerFactory
 *
 * @author youfu.wang
 * @date 2023/10/7 11:15
 */
public class JBOSLoggerFactory {
    /**
     * LoggerContext
     */
    private static final LoggerContext LOGGER_CONTEXT = new LoggerContext();

    /**
     * JBOSLoggerFactory
     */
    public JBOSLoggerFactory() {

    }

    /**
     * getLoggerContext
     * @return LoggerContext
     */
    public static LoggerContext getLoggerContext() {
        return LOGGER_CONTEXT;
    }
}
