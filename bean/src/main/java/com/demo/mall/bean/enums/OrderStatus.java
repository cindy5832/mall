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

public enum OrderStatus {

    // 未付款 / 待付款
    UNPAY(1),

    // 已付款 待發貨
    PADYED(2),

    // 發貨 -> 實際庫存減少，尚未確認收貨，待收貨
    CONSIGNMENT(3),

    // 已收貨，待評價
    CONFIRM(4),

    // 交易成功且已評價，購買數+1
    SUCCESS(5),

    // 交易失敗，還原庫存量
    CLOSE(6);

    private Integer num;

    public Integer value() {
        return num;
    }

    OrderStatus(Integer num) {
        this.num = num;
    }

    public static OrderStatus instance(Integer value) {
        OrderStatus[] enums = values();
        for (OrderStatus statusEnum : enums) {
            if (statusEnum.value().equals(value)) {
                return statusEnum;
            }
        }
        return null;
    }
}
