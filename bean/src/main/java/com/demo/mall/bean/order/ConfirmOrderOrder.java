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

// 提交訂單事件先後順序
public interface ConfirmOrderOrder {

    // 沒有任何活動時的順序
    int DEFAULT = 0;

    // 滿檢，排在DEFAULT後面
    int DISCOUNT = 100;

    // 優惠券，排在DISCOUNT後面
    int COUPON = 200;

    // 分銷，排在COUPON後面
    int DISTRIBUTION = 300;
}
