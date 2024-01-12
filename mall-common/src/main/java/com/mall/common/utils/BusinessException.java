package com.mall.common.utils;

/**
 * BusinessException
 *
 * @author youfu.wang
 * @date 2019-01-31
 */
public class BusinessException extends RuntimeException {
    /**
     * CamundaException
     */
    public BusinessException() {
    }

    /**
     * CamundaException
     * @param message
     * @param cause
     */
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * CamundaException
     * @param message
     */
    public BusinessException(String message) {
        super(message);
    }

    /**
     * CamundaException
     * @param cause
     */
    public BusinessException(Throwable cause) {
        super(cause);
    }
}
