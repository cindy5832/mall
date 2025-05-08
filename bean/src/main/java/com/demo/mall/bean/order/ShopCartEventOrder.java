/*
 * Copyright (c) 2018-2999 广州市蓝海创新科技有限公司 All rights reserved.
 *
 * https://www.mall4j.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.demo.mall.bean.order;

// 購物車事件先後
public interface ShopCartEventOrder {

    // 沒有任何活動時的順序
    int DEFAULT = 0;

    // 滿減活動的組裝順序，在DEFAULT後面
    int DISCOUNT = 100;
}
