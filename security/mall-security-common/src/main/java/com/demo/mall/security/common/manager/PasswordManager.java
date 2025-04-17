package com.demo.mall.security.common.manager;

import cn.hutool.crypto.symmetric.AES;
import com.demo.mall.common.exception.ShopException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class PasswordManager {
    private static final Logger logger = LoggerFactory.getLogger(PasswordManager.class);

    @Value("${auth.password.signKey:-mall-password}")
    private String passwordSignKey;

    public String decryptPassword(String password) {
        AES aes = new AES(passwordSignKey.getBytes(StandardCharsets.UTF_8));
        String decryptStr;
        String decryptPassword;
        try {
            decryptStr = aes.decryptStr(password);
            decryptPassword = decryptStr.substring(13);
        } catch (Exception e) {
            logger.error("decryptPassword error", e);
            throw new ShopException("AES解密錯誤", e);
        }
        return decryptPassword;
    }
}
