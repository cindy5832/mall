package com.demo.mall.security.common.adapter;

import com.anji.captcha.service.CaptchaCacheService;
import com.demo.mall.common.utils.RedisUtil;

public class CaptchaCacheServiceRedisImpl implements CaptchaCacheService {

    @Override
    public void set(String key, String value, long expireInSeconds) {
        RedisUtil.set(key, value, expireInSeconds);
    }

    @Override
    public boolean exists(String key) {
        return RedisUtil.hasKey(key);
    }

    @Override
    public void delete(String key) {
        RedisUtil.delKey(key);
    }

    @Override
    public String get(String key) {
        return RedisUtil.get(key);
    }

    @Override
    public String type() {
        return "redis";
    }
}
