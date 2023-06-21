package com.mall.common.utils;

/**
 * PlaceholderUtils
 *
 * @author youfu.wang
 * @date 2023/6/21
 **/
public class PlaceholderUtils {
    /**
     * 得到占位符内容
     * @param prefix
     * @param suffix
     * @return
     */
    public static String getPlaceholder(String value, String prefix,String suffix){
        int startIndex = value.indexOf(prefix);
        int endIndex = value.indexOf(suffix);
        if (startIndex == -1) {
            return value;
        } else {
            StringBuilder result = new StringBuilder(value);
            if (endIndex != -1) {
                return result.substring(startIndex + prefix.length(), endIndex);
            }
        }
        return value;
    }
}
