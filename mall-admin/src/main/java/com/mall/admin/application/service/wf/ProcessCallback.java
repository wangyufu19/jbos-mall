package com.mall.admin.application.service.wf;

import java.util.Map;

/**
 * ProcessCallback
 *
 * @author youfu.wang
 * @date 2023/6/14
 **/
public interface ProcessCallback {
    void call(Map<String, String> data);
}
