package com.mall.workflow.common.exception;
/**
 * IdentityException
 * @author youfu.wang
 * @date 2019-01-31
 */
public class IdentityException extends Exception{

    public IdentityException() {
    }

    public IdentityException(String message, Throwable cause) {
        super(message, cause);
    }

    public IdentityException(String message) {
        super(message);
    }

    public IdentityException(Throwable cause) {
        super(cause);
    }
}
