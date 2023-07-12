package com.mall.admin.common.exception;
/**
 * CamundaException
 * @author youfu.wang
 * @date 2019-01-31
 */
public class CamundaException extends Exception{

    public CamundaException() {
    }

    public CamundaException(String message, Throwable cause) {
        super(message, cause);
    }

    public CamundaException(String message) {
        super(message);
    }

    public CamundaException(Throwable cause) {
        super(cause);
    }
}
