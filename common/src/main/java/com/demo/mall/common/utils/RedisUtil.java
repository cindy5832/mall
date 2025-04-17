package com.demo.mall.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Slf4j
public class RedisUtil {
    private static RedisTemplate<String, Object> redisTemplate = SpringContextUtils.getBean("redisTemplate", RedisTemplate.class);

    public static final StringRedisTemplate STRING_REDIS_TEMPLATE = SpringContextUtils.getBean("stringRedisTemplate", StringRedisTemplate.class);

    /**
     * @return time (second)
     * @description: 指定緩存失效時間
     * @param: key
     **/
    public static boolean expire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            log.error("設置Redis指定key失效時間錯誤：", e);
            return false;
        }
    }

    /**
     * @return time (seconds) 返回0表示永久有效，失效時間為負，表示該key未設置失效時間 (失效時間默認為-1)
     * @description: 根據key獲取過期時間
     * @param: key not null
     **/
    public static Long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * @return true 存在 false 不存在
     * @description: 判斷key是否存在
     * @param: key
     **/
    public static Boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            log.error("redis判斷ket是否存在發生錯誤：", e);
            return false;
        }
    }

    /**
     * @return
     * @description: 刪除緩存
     * @param: key 可傳 1~n 個值
     **/
    public static void delKey(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(Arrays.asList(key));
            }
        }
    }

    /**
     * @return value
     * @description: 獲取緩存
     * @param: key
     **/
    public static <T> T get(String key) {
        return key == null ? null : (T) redisTemplate.opsForValue().get(key);
    }

    /**
     * @return true / false
     * @description: 存放緩存
     * @param: key
     * @param: value
     **/
    public static boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            log.error("設置Redis緩存錯誤", e);
            return false;
        }
    }

    /**
     * @return true / false
     * @description: 設置緩存並設置時間
     * @param: key
     * @param: time (seconds) time >=0; time = 0 將設置為無限期
     **/
    public static boolean set(String key, Object value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    /**
     * @return
     * @description: 遞增
     * @param: key
     * @param: delta > 0 (要遞增多少)
     **/

    public static Long incr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("遞增因子必須大於0");
        }
        return STRING_REDIS_TEMPLATE.opsForValue().increment(key, delta);
    }

    /**
     * @return
     * @description: 遞減
     * @param: key
     * @param: delta > 0 (要遞減多少)
     **/

    public static Long decr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("遞減因子必須大於0");
        }
        return STRING_REDIS_TEMPLATE.opsForValue().decrement(key, delta);
    }
}
