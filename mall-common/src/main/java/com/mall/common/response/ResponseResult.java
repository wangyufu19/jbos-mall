package com.mall.common.response;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

/**
 * ResponseResult
 * @author youfu.wang
 * @date 2021-08-19
 */
@Setter
@Getter
public class ResponseResult {
    public static final String CODE_SUCCESS="0000";
    public static final String CODE_FAILURE="9999";
    public static final String MSG_SUCCESS="操作成功";
    public static final String MSG_FAILURE="操作失败";
    private String retCode;
    private String retMsg;
    private Object data=new HashMap<String,Object>();


    public ResponseResult(){
        this.retCode=CODE_SUCCESS;
        this.retMsg=MSG_SUCCESS;
    }
    public ResponseResult(String retCode, String rerMsg){
        this.retCode=retCode;
        this.retMsg=rerMsg;
    }
    public static ResponseResult ok() {
        return new ResponseResult();
    }
    public static ResponseResult ok(String retMsg) {
        return new ResponseResult(CODE_SUCCESS,retMsg);
    }
    public static ResponseResult ok(String retCode, String retMsg) {
        return new ResponseResult(retCode,retMsg);
    }
    public static ResponseResult error() {
        return new ResponseResult(CODE_FAILURE, MSG_FAILURE);
    }
    public static ResponseResult error(String retMsg) {
        return new ResponseResult(CODE_FAILURE,retMsg);
    }
    public static ResponseResult error(String retCode, String retMsg) {
       return new ResponseResult(retCode,retMsg);
    }
}
