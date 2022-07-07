package com.mall.gateway.common.filter;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

/**
 * ResponseData
 * @author youfu.wang
 * @date 2021-08-19
 */
@Setter
@Getter
public class ResponseData {
    public static final String RETCODE_SUCCESS="0000";
    public static final String RETCODE_FAILURE="9999";
    public static final String RETMSG_SUCCESS="操作成功";
    public static final String RETMSG_FAILURE="操作失败";
    private String retCode;
    private String retMsg;
    private Object data=new HashMap<String,Object>();


    public ResponseData(){
        this.retCode=RETCODE_SUCCESS;
        this.retMsg=RETMSG_SUCCESS;
    }
    public ResponseData(String retCode, String rerMsg){
        this.retCode=retCode;
        this.retMsg=rerMsg;
    }
    public static ResponseData ok() {
        return new ResponseData();
    }
    public static ResponseData ok(String retMsg) {
        return new ResponseData(RETCODE_SUCCESS,retMsg);
    }
    public static ResponseData ok(String retCode, String retMsg) {
        return new ResponseData(retCode,retMsg);
    }
    public static ResponseData error() {
        return new ResponseData(RETCODE_FAILURE, RETMSG_FAILURE);
    }
    public static ResponseData error(String retMsg) {
        return new ResponseData(RETCODE_FAILURE,retMsg);
    }
    public static ResponseData error(String retCode, String retMsg) {
       return new ResponseData(retCode,retMsg);
    }
}
