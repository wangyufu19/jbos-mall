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
    /**
     * 物品购买
     */
    public static final String BIZ_BUY = "BIZ_BUY";
    /**
     * 物品入库
     */
    public static final String BIZ_IN_STORE = "BIZ_IN_STORE";
    /**
     * 物品领取
     */
    public static final String BIZ_OUT_STORE = "BIZ_OUT_STORE";

    public String getBizNo() {
        return DateUtils.format(DateUtils.getCurrentDate(), "yyyyMMddHHmmss");
    }

    /**
     * 得到业务编号
     * @param bizType
     * @return bizNo
     */
    public String getBizNo(String bizType) {
        return bizType + "_" + DateUtils.format(DateUtils.getCurrentDate(), "yyyyMMddHHmmss");
    }

}
