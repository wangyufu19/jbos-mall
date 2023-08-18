package com.mall.admin.common.exception;

/**
 * CamundaException
 *
 * @author youfu.wang
 * @date 2019-01-31
 */
public class CamundaException extends Exception {
    /**
     * CamundaException
     */
    public CamundaException() {
    }

    /**
     * CamundaException
     * @param message
     * @param cause
     */
    public CamundaException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * CamundaException
     * @param message
     */
    public CamundaException(String message) {
        super(message);
    }

    /**
     * CamundaException
     * @param cause
     */
    public CamundaException(Throwable cause) {
        super(cause);
    }
}
