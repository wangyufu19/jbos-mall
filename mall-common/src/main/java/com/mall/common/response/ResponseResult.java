package com.mall.common.response;

import com.github.pagehelper.PageInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * ResponseResult
 *
 * @author youfu.wang
 * @date 2021-08-19
 */
@Setter
@Getter
public class ResponseResult<T> {
    public static final String CODE_SUCCESS = "0000";
    public static final String CODE_FAILURE = "9999";
    public static final String MSG_SUCCESS = "操作成功";
    public static final String MSG_FAILURE = "操作失败";
    private String retCode;
    private String retMsg;
    private boolean isPage;
    private T data;


    public ResponseResult() {
        this.retCode = CODE_SUCCESS;
        this.retMsg = MSG_SUCCESS;
    }

    public ResponseResult(String retCode, String rerMsg) {
        this.retCode = retCode;
        this.retMsg = rerMsg;
    }

    public ResponseResult(String retCode, String rerMsg, T data) {
        this.retCode = retCode;
        this.retMsg = rerMsg;
        this.data = data;
    }

    public static ResponseResult ok() {
        return new ResponseResult();
    }

    public static ResponseResult ok(String retMsg) {
        return new ResponseResult(CODE_SUCCESS, retMsg);
    }

    public static ResponseResult ok(String retCode, String retMsg) {
        return new ResponseResult(retCode, retMsg);
    }

    public static ResponseResult error() {
        return new ResponseResult(CODE_FAILURE, MSG_FAILURE);
    }

    public static ResponseResult error(String retMsg) {
        return new ResponseResult(CODE_FAILURE, retMsg);
    }

    public static ResponseResult error(String retCode, String retMsg) {
        return new ResponseResult(retCode, retMsg);
    }

    public ResponseResult isPage(boolean isPage) {
        this.isPage = isPage;
        return this;
    }

    public ResponseResult setData(T data) {
        if (this.isPage) {
            PageInfo pageInfo = new PageInfo((List) data);
            this.data = (T) pageInfo;
        } else {
            this.data = data;
        }
        return this;
    }
}
