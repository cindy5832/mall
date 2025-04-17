package com.demo.mall.security.common.manager;

import cn.hutool.core.util.StrUtil;
import com.demo.mall.common.exception.ShopException;
import com.demo.mall.common.utils.IpHelper;
import com.demo.mall.common.utils.RedisUtil;
import com.demo.mall.security.common.enums.SysTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordCheckManager {

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 半小時內最多錯誤10次
    private static final int TIMES_CHECK_INPUT_PASSWORD_NUM = 10;

    // 檢查用戶輸入錯誤的驗證碼次數
    private static final String CHECK_VALID_CODE_NUM_PREFIX = "checkUserInputErrorPassword_";

    public void checkPassword(SysTypeEnum sysTypeEnum, String userNameOrMobile, String rawPassword, String encodedPassword) {
        String checkPrefix = sysTypeEnum.value() + CHECK_VALID_CODE_NUM_PREFIX + IpHelper.getIpAddr();
        String key = checkPrefix + userNameOrMobile;

        int count = 0;
        if (RedisUtil.hasKey(key)) {
            count = RedisUtil.get(key);
        }

        if (count >= TIMES_CHECK_INPUT_PASSWORD_NUM) {
            throw new ShopException("密碼輸入錯誤10次，已限制登入30分鐘");
        }

        // 30 分鐘後失效
        RedisUtil.set(key, count, 1800);

        // 密碼不正確
        if (StrUtil.isBlank(encodedPassword) || !passwordEncoder.matches(rawPassword, encodedPassword)) {
            count++;
            // 半小時候失效
            RedisUtil.set(key, count, 1800);
            throw new ShopException("帳號密碼不正確");
        }
    }
}
