package com.mall.admin.application.service.comm;

import java.util.Map;


/**
 * ProcessVariableService
 *
 * @author youfu.wang
 * @date 2023/5/29
 **/
public class ProcessVariableService {

    public void setProcessVariables(Map<String, Object> params,String key, Object value){
        params.put(key,value);
    }
}
