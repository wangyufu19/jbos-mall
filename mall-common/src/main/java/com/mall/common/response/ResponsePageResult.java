package com.mall.common.response;

import com.github.pagehelper.PageInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * ResponsePageResult
 *
 * @author youfu.wang
 * @date 2021-08-19
 */
@Setter
@Getter
public class ResponsePageResult<T> {
    public static final String CODE_SUCCESS = "0000";
    public static final String CODE_FAILURE = "9999";
    public static final String MSG_SUCCESS = "操作成功";
    public static final String MSG_FAILURE = "操作失败";
    private String retCode;
    private String retMsg;
    private T data;


    public ResponsePageResult() {
        this.retCode = CODE_SUCCESS;
        this.retMsg = MSG_SUCCESS;
    }

    public ResponsePageResult(String retCode, String rerMsg) {
        this.retCode = retCode;
        this.retMsg = rerMsg;
    }

    public ResponsePageResult(String retCode, String rerMsg, T data) {
        this.retCode = retCode;
        this.retMsg = rerMsg;
        this.data = data;
    }

    public static ResponsePageResult ok() {
        return new ResponsePageResult();
    }

    public static ResponsePageResult ok(String retMsg) {
        return new ResponsePageResult(CODE_SUCCESS, retMsg);
    }

    public static ResponsePageResult ok(String retCode, String retMsg) {
        return new ResponsePageResult(retCode, retMsg);
    }

    public static ResponsePageResult error() {
        return new ResponsePageResult(CODE_FAILURE, MSG_FAILURE);
    }

    public static ResponsePageResult error(String retMsg) {
        return new ResponsePageResult(CODE_FAILURE, retMsg);
    }

    public static ResponsePageResult error(String retCode, String retMsg) {
        return new ResponsePageResult(retCode, retMsg);
    }


    public ResponsePageResult setData(T data) {
        PageInfo pageInfo = new PageInfo((List) data);
        this.data = (T) pageInfo;
        return this;
    }
}
