package com.mall.admin.application.service.comm;

import com.mall.common.utils.DateUtils;
import org.springframework.stereotype.Service;

/**
 * BizGeneratorService
 *
 * @author youfu.wang
 * @date 2023/7/14
 **/
@Service
public class BizGeneratorService {
    public static final String BIZ_BUY = "BIZ_BUY";
    public static final String BIZ_IN_STORE = "BIZ_IN_STORE";
    public static final String BIZ_out_STORE = "BIZ_OUT_STORE";

    public String getBizNo() {
        return DateUtils.format(DateUtils.getCurrentDate(), "yyyyMMddHHmmss");
    }
    public String getBizNo(String bizType) {
        return bizType + "_" + DateUtils.format(DateUtils.getCurrentDate(), "yyyyMMddHHmmss");
    }

}
