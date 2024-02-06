package com.mall.common.response;

import lombok.Getter;
import lombok.Setter;


/**
 * ResponseResult
 *
 * @author youfu.wang
 * @date 2021-08-19
 * @param <T>
 */
@Setter
@Getter
public class ResponseResult<T> {
    /**
     * CODE_SUCCESS
     */
    public static final String CODE_SUCCESS = "0000";
    /**
     * CODE_FAILURE
     */
    public static final String CODE_FAILURE = "9999";
    /**
     * MSG_SUCCESS
     */
    public static final String MSG_SUCCESS = "操作成功";
    /**
     * MSG_FAILURE
     */
    public static final String MSG_FAILURE = "操作失败";
    /**
     * retCode
     */
    private String retCode;
    /**
     * retMsg
     */
    private String retMsg;
    /**
     * data
     */
    private T data;

    /**
     * ResponseResult
     */
    public ResponseResult() {
        this.retCode = CODE_SUCCESS;
        this.retMsg = MSG_SUCCESS;
    }

    /**
     * ResponseResult
     * @param retCode
     * @param rerMsg
     */
    public ResponseResult(String retCode, String rerMsg) {
        this.retCode = retCode;
        this.retMsg = rerMsg;
    }

    /**
     * ResponseResult
     * @param retCode
     * @param rerMsg
     * @param data
     */
    public ResponseResult(String retCode, String rerMsg, T data) {
        this.retCode = retCode;
        this.retMsg = rerMsg;
        this.data = data;
    }

    /**
     * ok
     * @return ResponseResult
     */
    public static ResponseResult ok() {
        return new ResponseResult();
    }

    /**
     * ok
     * @param retMsg
     * @return ResponseResult
     */
    public static ResponseResult ok(String retMsg) {
        return new ResponseResult(CODE_SUCCESS, retMsg);
    }

    /**
     * ok
     * @param retCode
     * @param retMsg
     * @return ResponseResult
     */
    public static ResponseResult ok(String retCode, String retMsg) {
        return new ResponseResult(retCode, retMsg);
    }

    /**
     * error
     * @return ResponseResult
     */
    public static ResponseResult error() {
        return new ResponseResult(CODE_FAILURE, MSG_FAILURE);
    }

    /**
     * error
     * @param retMsg
     * @return ResponseResult
     */
    public static ResponseResult error(String retMsg) {
        return new ResponseResult(CODE_FAILURE, retMsg);
    }

    /**
     * error
     * @param retCode
     * @param retMsg
     * @return ResponseResult
     */
    public static ResponseResult error(String retCode, String retMsg) {
        return new ResponseResult(retCode, retMsg);
    }
    /**
     * error
     * @param retCode
     * @param retMsg
     * @return ResponseResult
     */
    public static ResponseResult error(int retCode, String retMsg) {
        return new ResponseResult(String.valueOf(retCode), retMsg);
    }
}
