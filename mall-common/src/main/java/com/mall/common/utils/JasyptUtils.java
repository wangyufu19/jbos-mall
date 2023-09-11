package com.mall.common.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.EnvironmentStringPBEConfig;

/**
 * JasyptUtils
 *
 * @author youfu.wang
 * @date 2023/7/13
 **/
public class JasyptUtils {


    public static String encrypt(String algorithm, String password, String plaintext) {
        //加密工具
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        //加密配置
        EnvironmentStringPBEConfig config = new EnvironmentStringPBEConfig();
        // 算法类型
        config.setAlgorithm(algorithm);
        //秘钥
        config.setPassword(password);
        //应用配置
        encryptor.setConfig(config);
        //加密
        String ciphertext = encryptor.encrypt(plaintext);
        return ciphertext;
    }

    /**
     * 解密
     *
     * @param ciphertext
     * @return
     */
    public static String decrypt(String algorithm, String password, String ciphertext) {
        //加密工具
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        //加密配置
        EnvironmentStringPBEConfig config = new EnvironmentStringPBEConfig();
        config.setAlgorithm(algorithm);
        //秘钥
        config.setPassword(password);
        //应用配置
        encryptor.setConfig(config);
        //解密
        String pText = encryptor.decrypt(ciphertext);
        return pText;
    }

//    public static void main(String[] args) {
//        String key = DigestUtils.sha256Hex("123456".getBytes());
//        System.out.println("key: " + key);
//        String encrypt = JasyptUtils.encrypt("PBEWithMD5AndDES", key, "jbos");
//
//        System.out.println("encrypt: " + encrypt);
//        System.out.println("decrypt: " + JasyptUtils.decrypt("PBEWithMD5AndDES",key,encrypt));
//    }
}
