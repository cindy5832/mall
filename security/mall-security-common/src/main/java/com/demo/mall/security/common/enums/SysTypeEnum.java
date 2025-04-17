/*
 * Copyright (c) 2018-2999 广州市蓝海创新科技有限公司 All rights reserved.
 *
 * https://www.mall4j.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */
package com.demo.mall.security.common.enums;

/**
 * 系統類型
 */
public enum SysTypeEnum {

    // 普通用戶系統
    ORDINARY(0),

    // 後台
    ADMIN(1),
    ;

    private final Integer value;

    public Integer value() {
        return value;
    }

    SysTypeEnum(Integer value) {
        this.value = value;
    }

}
