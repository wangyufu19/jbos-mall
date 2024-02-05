package com.mall.admin.common.sensitive;

import cn.hutool.core.util.DesensitizedUtil;
import lombok.AllArgsConstructor;

import java.util.function.Function;

/**
 * SensitiveStrategy
 *
 * @author youfu.wang
 * @date 2024/2/5 14:32
 */
@AllArgsConstructor
public enum SensitiveStrategy {
    /**
     * 身份证号码
     */
    ID_CARD(s -> DesensitizedUtil.idCardNum(s, 3, 4)),
    /**
     * 手机号码
     */
    MOBILE_PHONE(DesensitizedUtil::mobilePhone),
    /**
     * 姓名
     */
    CHINESE_NAME(DesensitizedUtil::chineseName),
    /**
     * 邮箱
     */
    EMAIL(DesensitizedUtil::email),
    /**
     * 密码
     */
    PASS_WORD(DesensitizedUtil::password);
    /**
     * 脱敏
     */
    private final Function<String, String> desensitizer;

    public Function<String, String> getDesensitizer() {
        return desensitizer;
    }
}
