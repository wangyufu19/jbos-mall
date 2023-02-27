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
    public static final String RETCODE_SUCCESS="0000";
    public static final String RETCODE_FAILURE="9999";
    public static final String RETMSG_SUCCESS="操作成功";
    public static final String RETMSG_FAILURE="操作失败";
    private String retCode;
    private String retMsg;
    private Object data=new HashMap<String,Object>();


    public ResponseResult(){
        this.retCode=RETCODE_SUCCESS;
        this.retMsg=RETMSG_SUCCESS;
    }
    public ResponseResult(String retCode, String rerMsg){
        this.retCode=retCode;
        this.retMsg=rerMsg;
    }
    public static ResponseResult ok() {
        return new ResponseResult();
    }
    public static ResponseResult ok(String retMsg) {
        return new ResponseResult(RETCODE_SUCCESS,retMsg);
    }
    public static ResponseResult ok(String retCode, String retMsg) {
        return new ResponseResult(retCode,retMsg);
    }
    public static ResponseResult error() {
        return new ResponseResult(RETCODE_FAILURE, RETMSG_FAILURE);
    }
    public static ResponseResult error(String retMsg) {
        return new ResponseResult(RETCODE_FAILURE,retMsg);
    }
    public static ResponseResult error(String retCode, String retMsg) {
       return new ResponseResult(retCode,retMsg);
    }
}
