package com.mall.admin.common.utils;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.UUID;

/**
 * TokenGenerator
 * @author youfu.wang
 */
public class TokenGenerator {
    /**
     * 生成一个Token
     * @return
     */
    public static String generateValue() {
        return generateValue(UUID.randomUUID().toString());
    }

    /**
     * 生成一个Token
     * @param str
     * @return
     */
    public static String generateValue(String str) {
        return DigestUtils.sha256Hex(str.getBytes());
    }
    public static void main(String[] args){
        System.out.println(TokenGenerator.generateValue());
    }
}
