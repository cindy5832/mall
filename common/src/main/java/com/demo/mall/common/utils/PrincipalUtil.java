package com.demo.mall.common.utils;

import cn.hutool.core.util.StrUtil;

import java.util.regex.Pattern;

// 正則表達式工具
public class PrincipalUtil {

    // 1開頭 後面10位數字
    public static final String MOBILE_REGEXP = "1[0-9]{10}";

    // 數字、字母、_ 4-16位
    public static final String USER_NAME_REGEXP = "([a-zA-Z0-9_]{4,16})";

    // 由簡單的字母拼接而成的string，不含_ 、大寫字母
    public static final String SIMPLE_CHAR_REGEXP = "([a-z0-9]+)";

    public static boolean isMobile(String mobile) {
        if (StrUtil.isBlank(mobile)) {
            return false;
        }
        return Pattern.matches(MOBILE_REGEXP, mobile);
    }

    public static boolean isUserName(String userName) {
        if (StrUtil.isBlank(userName)) {
            return false;
        }
        return Pattern.matches(USER_NAME_REGEXP, userName);
    }


    public static boolean isMatching(String regexp, String value) {
        if (StrUtil.isBlank(value)) {
            return false;
        }
        return Pattern.matches(regexp, value);
    }

    /**
     * 是否由簡單的字母數字拼接而成的string
     * @param value 输入值
     * @return 匹配结果
     */
    public static boolean isSimpleChar(String value) {
        return isMatching(SIMPLE_CHAR_REGEXP, value);
    }
}
