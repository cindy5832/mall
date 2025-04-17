/*
 * Copyright (c) 2018-2999 广州市蓝海创新科技有限公司 All rights reserved.
 *
 * https://www.mall4j.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */
package com.demo.mall.bean.enums;

// 地區層級
public enum AreaLevelEnum {

    FIRST_LEVEL(1),
    SECOND_LEVEL(2),
    THIRD_LEVEL(3);

    private Integer num;

    public Integer value() {
        return num;
    }

    AreaLevelEnum(Integer num) {
        this.num = num;
    }

    public static AreaLevelEnum instance(Integer value) {
        AreaLevelEnum[] enums = values();
        for (AreaLevelEnum statusEnum : enums) {
            if (statusEnum.value().equals(value)) {
                return statusEnum;
            }
        }
        return null;
    }
}
