package com.demo.mall.common.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;


// 使用redis進行分布式鎖
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RedisLock {

    // redis鎖 名字
    String lockName() default "";

    // redis鎖 key 支持spel表達式
    String key() default "";

    // 過期秒數 默認5秒
    int expire() default 5000;

    // 超過時間單位 秒
    TimeUnit timeUnit() default TimeUnit.MILLISECONDS;
}
